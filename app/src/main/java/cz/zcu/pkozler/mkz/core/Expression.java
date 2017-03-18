package cz.zcu.pkozler.mkz.core;

import cz.zcu.pkozler.mkz.core.helpers.TokenParsing;
import cz.zcu.pkozler.mkz.core.tokens.Function;
import cz.zcu.pkozler.mkz.core.tokens.Token;
import cz.zcu.pkozler.mkz.core.tokens.Number;
import cz.zcu.pkozler.mkz.core.tokens.Operator;
import cz.zcu.pkozler.mkz.core.tokens.Variable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;

/**
 * Třída představující matematické výrazy, které je možné vyhodnotit.
 * Výrazy, které představují předpis funkce nebo stranu rovnice,
 * mohou obsahovat proměnné.
 * 
 * @author Petr Kozler
 */
public class Expression
{
    /// <summary>
    /// poslední zadaný výraz
    /// </summary>
    private Queue<Token> lastInfix = null;

    /// <summary>
    /// postfixový výraz
    /// </summary>
    private Queue<Token> lastPostfix = null;

    /// <summary>
    /// výsledná vypočtená hodnota
    /// </summary>
    //private double value = 0;

    /// <summary>
    /// Vyhodnotí zadaný výraz. Vyhodnocení je rozděleno do čtyř fází:
    /// 1. Předzpracování výrazu (zahrnuje odstranění mezer, doplnění znaku násobení při zkratkovitém zápisu apod.)
    /// 2. Lexikální analýza - identifikace jednotlivých tokenů (čísla, operátory, funkce, závorky atd.) v řetězci výrazu a jejich uložení do kolekce
    /// 3. Syntaktická analýza - převod výrazu z in-fixového do post-fixového zápisu (reverzní polské notace) pomocí algoritmu Shunting-yard
    /// 4. Sémantická analýza - vlastní vyhodnocení (výpočet - při této fázi jsou dosazeny hodnoty do proměnných)
    /// Algoritmus vyhodnocení je navržen tak, aby při změně výrazu (resp. zadání nového výrazu k vyhodnocení)
    /// provedl všechny fáze vyhodnocení pouze v případě, že se nový výraz liší od předchozího.
    /// Pokud je po předzpracování stejný, je proveden pouze výpočet již vytvořeného post-fixového zápisu uloženého při předchozím vyhodnocení
    /// s dosazením nových hodnot proměnných.
    /// Pokud se od předchozích neliší ani hodnoty proměnných, neproběhne ani výpočet a je rovnou navrácena předchozí již vypočtená hodnota.
    /// </summary>
    /// <param name="str">matematický výraz v in-fixovém zápisu</param>
    /// <param name="var">hodnoty proměnné v matematickém výrazu</param>
    /// <returns>výsledek vyhodnocení</returns>
    public double eval(String str, Double var) throws AExpressionException
    {
        // předzpracování výrazu
        String exp = preprocess(str);
        Queue<Token> infix = tokenize(exp);
        double value;

        if (infix == null || infix.isEmpty())
        {
            this.lastInfix = null;
            this.lastPostfix = null;
            
            throw new EmptyExpressionException("");
        }

        // nový zadaný výraz je odlišný od předchozího
        if (this.lastInfix == null || !areEqual(this.lastInfix, infix))
        {
            // program provede nový převod i výpočet
            this.lastInfix = infix;
            this.lastPostfix = parse(infix);
        }
        
        value = calculate(lastPostfix, var);
        
        // výsledek vyhodnocení
        return value;
    }

    /// <summary>
    /// Předzpracuje zadané proměnné a matematický výraz před rozdělením na tokeny.
    /// </summary>
    /// <param name="str">řetězec původního zadaného výrazu</param>
    /// <returns>řetězec předzpracovaného výrazu</returns>
    private String preprocess(String str)
    {
        str = str.toLowerCase();
        
        // přeznačení podle předdefinovaných přepisovacích pravidel
        for (Map.Entry<String, String> t : TokenParsing.TRANSCRIPTIONS.entrySet())
        {
            str = str.replaceAll(t.getKey(), t.getValue());
        }

        return str;
    }

    /// <summary>
    /// Rozdělí zadaný výraz na seznam tokenů matematického výrazu v infixovém zápisu.
    /// </summary>
    /// <param name="str">řetězec předzpracovaného výrazu</param>
    /// <returns>seznam tokenů výrazu v in-fixové notaci</returns>
    private Queue<Token> tokenize(String str) throws InvalidExpressionFormatException
    {
        Queue<Token> infix = new LinkedList<>();
        Matcher matcher;
        int i = 0;

        // otestuje výskyt jednotlivých typů tokenů a vybere ten, který se nachází na počátku aktuálního podřetězce
        while (i < str.length())
        {
            // test výskytu funkce
            if ((matcher = TokenParsing.FUNCTION_REGEX.matcher(str.substring(i))).find())
            {
                infix.add(new Function(matcher.group()));
            }
            // test výskytu operátoru
            else if ((matcher = TokenParsing.OPERATOR_REGEX.matcher(str.substring(i))).find())
            {
                infix.add(new Operator(matcher.group()));
            }
            // test výskytu proměnné
            else if ((matcher = TokenParsing.VARIABLE_REGEX.matcher(str.substring(i))).find())
            {
                infix.add(new Variable(matcher.group()));
            }
            // test výskytu čísla
            else if ((matcher = TokenParsing.NUMBER_REGEX.matcher(str.substring(i))).find())
            {
                infix.add(new Number(matcher.group()));
            }
            // test výskytu konstanty
            else if ((matcher = TokenParsing.CONSTANT_REGEX.matcher(str.substring(i))).find())
            {
                infix.add(new Number(matcher.group()));
            }
            // test výskytu závorky
            else if ((matcher = TokenParsing.OTHER_REGEX.matcher(str.substring(i))).find())
            {
                infix.add(new Token(matcher.group()));
            }
            // nenalezen platný token
            else
            {
                throw new InvalidExpressionFormatException("Zadaný výraz obsahuje neplatné symboly.");
            }

            // posun indexu podle délky nalezeného tokenu
            i += matcher.group().length();
        }

        return infix;
    }

    /// <summary>
    /// Převede zadaný výraz z infixového do postfixového zápisu.
    /// </summary>
    /// <param name="lastInfix">seznam tokenů výrazu v in-fixové notaci</param>
    /// <returns>seznam tokenů výrazu v post-fixové notaci</returns>
    private Queue<Token> parse(Queue<Token> infix) throws InvalidExpressionFormatException
    {
        int n = 0;

        for (Token t : infix)
        {
            if (!(t.isLeftParenthesis() || t.isRightParenthesis()))
            {
                n++;
            }
        }

        Queue<Token> postfix = new LinkedList<>();
        Stack<Token> stack = new Stack<>();

        // Dokud jsou zde tokeny ke čtení: Přečti token.
        for (Token t : infix)
        {
            // Pokud je token číslo, přidej ho do výstupní fronty.
            if (t instanceof Number || t instanceof Variable)
            {
                postfix.add(t);
            }
            // Pokud je token funkce, vlož ho na zásobník.
            else if (t instanceof Function)
            {
                stack.push(t);
            }
            // Pokud je token oddělovač argumentů funkce (nejčastěji čárka):
            else if (t.isArgumentSeparator())
            {
                // Dokud nebude na vrcholu zásobníku levá závorka, vybírej z něj operátory a vkládej je do výstupu.
                while (!stack.isEmpty() && !stack.peek().isLeftParenthesis())
                {
                    postfix.add(stack.pop());
                }

                // Jestliže se zásobník vyprázdní a závorka nebude nalezena, oddělovač byl buď na nesprávném místě, nebo ve vstupu chybí otevírací závorka.
                if (stack.isEmpty())
                {
                    throw new InvalidExpressionFormatException("Oddělovač argumentů funkce je na nesprávném místě nebo chybí otevírací závorka.");
                }

            }
            // Pokud je token operátor (dále jen o1), tak:
            else if (t instanceof Operator)
            {
                // dokud bude na vrcholu zásobníku operátor (s výjimkou závorek), o2, tak:
                while (!stack.isEmpty() && stack.peek() instanceof Operator &&
                    // pokud je o1 asociativní zleva a jeho priorita je menší nebo stejná (≤) jako priorita o2, 
                    // nebo o1 je asociativní zprava a jeho priorita je nižší (<) než priorita operátoru o2, 
                    // vyjmi operátor o2 ze zásobníku a vlož ho do výstupní fronty, jinak ukonči cyklus.
                      (((Operator)t).isLeftAssoc() && ((Operator)t).PRECEDENCE <= ((Operator)stack.peek()).PRECEDENCE ||
                        ((Operator)t).isRightAssoc() && ((Operator)t).PRECEDENCE < ((Operator)stack.peek()).PRECEDENCE))
                {
                    postfix.add(stack.pop());
                }

                // vlož operátor o1 na zásobník.
                stack.push(t);
            }
            // Pokud je token levá závorka, vlož ho na zásobník.
            else if (t.isLeftParenthesis())
            {
                stack.push(t);
            }
            // Pokud je token pravá závorka:
            else if (t.isRightParenthesis())
            {
                // Dokud na vrcholu zásobníku nebude levá závorka, vybírej operátory ze zásobníku a zapisuj je do výstupní fronty.
                while (!stack.isEmpty() && !stack.peek().isLeftParenthesis())
                {
                    postfix.add(stack.pop());
                }

                // Pokud je zásobník prázdný a nepodařilo se najít levou závorku, jedná se o neuzavřený výraz a je vhodné oznámit chybu.
                if (stack.isEmpty())
                {
                    throw new InvalidExpressionFormatException("Ve výrazu se nacházejí neotevřené závorky.");
                }

                // Vyjmi ze zásobníku levou závorku, ale nevkládej jí do výstupní fronty.
                stack.pop();

                // Pokud je na vrcholu zásobníku token identifikovaný jako funkce, vlož ho do výstupu.
                if (!stack.isEmpty() && stack.peek() instanceof Function)
                {
                    postfix.add(stack.pop());
                }
            }
        }

        // Jestliže byly zpracovány všechny tokeny ze vstupu: Dokud budou v zásobníku operátory:
        while (!stack.isEmpty())
        {
            // Vyjmi operátor ze zásobníku a vlož ho do výstupní fronty.
            if (stack.peek() instanceof Operator || stack.peek() instanceof Function)
            {
                postfix.add(stack.pop());
            }
            // Jestliže operátor na vrcholu zásobníku je závorka, ve výrazu jsou neuzavřené závorky a je vhodné ohlásit chybu.
            else
            {
                throw new InvalidExpressionFormatException("Ve výrazu se nacházejí neuzavřené závorky.");
            }
        }

        return postfix;
    }

    /// <summary>
    /// Vyhodnotí zadaný výraz v postfixovém zápisu.
    /// </summary>
    /// <param name="lastPostfix">seznam tokenů výrazu v in-fixové notaci</param>
    /// <param name="x">hodnota proměnné</param>
    /// <returns>vypočtená hodnota</returns>
    private double calculate(Queue<Token> postfix, Double x) throws InvalidExpressionFormatException, MissingValueException
    {
        Stack<Token> stack = new Stack<>();

        // pokud jsou na vstupu znaky přečti další znak
        for (Token t : postfix)
        {
            // jestliže je znak hodnota ulož ji na zásobník
            if (t instanceof Number)
            {
                stack.push(t);
            }
            else if (t instanceof Variable)
            {
                if (x == null) {
                    throw new MissingValueException("Nebyla dosazena hodnota x.");
                }
                
                stack.push(Number.createNumber(x));
            }
            // jinak znak značí funkci (operátory, jako je + jsou jednoduché funkce přebírající dva argumenty)
            else if (t instanceof Operator)
            {
                if (stack.size() < 2)
                {
                    throw new InvalidExpressionFormatException("Nebyl zadán dostatečný počet operandů.");
                }

                Number b = (Number)stack.pop();
                Number a = (Number)stack.pop();
                stack.push(((Operator)t).calc(a, b));
            }
            else if (t instanceof Function)
            {
                // je známo, že funkce přebírá n parametrů, jestliže je na zásobníku méně než n hodnot: Chyba - uživatel nezadal dostatečný počet parametrů
                if (stack.size() < ((Function)t).MIN_ARGC)
                {
                    throw new InvalidExpressionFormatException("Nebyl zadán dostatečný počet parametrů.");
                }

                Stack<Number> temp = new Stack<>();

                while (stack.size() > 0 && stack.peek() instanceof Number && temp.size() < ((Function)t).MAX_ARGC)
                {
                    // vyber nejvyšší n-tou hodnotu ze zásobníku
                    temp.push((Number)stack.pop());
                }

                Number[] tempArray = new Number[temp.size()];
                // vypočti hodnotu funkce a v případě výsledku jej ulož zpět na zásobník
                stack.push(((Function)t).calc(temp.toArray(tempArray)));
            }
        }

        // jestliže je na zásobníku více hodnot: Chyba - uživatel zadal příliš hodnot
        if (stack.size() != 1)
        {
            throw new InvalidExpressionFormatException("Byl zadán přebytečný počet hodnot.");
        }

        // jestliže je na zásobníku jen jedna hodnota je to výsledek výpočtu
        return ((Number)stack.pop()).VALUE;
    }

    /// <summary>
    /// Vrátí in-fixový výraz po předzpracování.
    /// </summary>
    /// <returns>in-fixový výraz</returns>
    public String GetLastInfix()
    {
        return lastInfix == null ? "" : joinStr(" ", lastInfix);
    }

    /// <summary>
    /// Vrátí výraz po převodu do post-fixového zápisu.
    /// </summary>
    /// <returns>post-fixový výraz</returns>
    public String GetLastPostfix()
    {
        return lastPostfix == null ? "" : joinStr(" ", lastPostfix);
    }
    
    /**
     * Spojí pole tokenů do řetězce.
     * 
     * @param separator oddělovač
     * @param tokens tokeny
     * @return řetězec
     */
    private static String joinStr(String separator, Queue<Token> tokens) {
        if (tokens.size() < 1) {
            return "";
        }
        
        Iterator<Token> it = tokens.iterator();
        
        StringBuilder sb = new StringBuilder(it.next().toString());
        
        while (it.hasNext()) {
            sb.append(separator).append(it.next());
        }
        
        return sb.toString();
    }
    
    private static boolean areEqual(Queue<Token> lastInfix, Queue<Token> infix) {
        if (lastInfix == null || infix == null) {
            return false;
        }
        
        if (lastInfix.size() != infix.size()) {
            return false;
        }
        
        Iterator<Token> it0 = lastInfix.iterator();
        Iterator<Token> it = infix.iterator();
        
        while (it0.hasNext()) {
            Token t0 = it0.next();
            Token t = it.next();
            
            if (t0 == null) {
                if (t != null) {
                    return false;
                }
            }
            else {
                if (!t0.equals(t)) {
                    return false;
                }
            }
        }
        
        return true;
    }
}