package cz.zcu.pkozler.mkz.core;

import cz.zcu.pkozler.mkz.core.helpers.ExpressionParsing;
import cz.zcu.pkozler.mkz.core.tokens.Function;
import cz.zcu.pkozler.mkz.core.tokens.Token;
import cz.zcu.pkozler.mkz.core.tokens.Number;
import cz.zcu.pkozler.mkz.core.tokens.Operator;
import cz.zcu.pkozler.mkz.core.tokens.Variable;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;

/**
 * Hlavní třída pro zpracování a vyhodnocení zadaných matematických výrazů.
 * Výrazy, které představují předpis funkce nebo stranu rovnice,
 * mohou obsahovat proměnnou.
 *
 * @author Petr Kozler
 */
public class Evaluator {
    /**
     * poslední zpracovaný výraz
     */
    private Queue<Token> postfix;

    /**
     * Zpracuje matematický výraz předaný jako řetězec do podoby vhodné k jeho následnému vyhodnocení a v této podobě
     * jej uloží. Při opakovaném volání zpracování je poslední uložený výraz nahrazen novým.
     * Zpracování je rozděleno do tří fází:
     * 1. Předzpracování řetězce výrazu
     * 2. Lexikální analýza (identifikace tokenů výrazu v řetězci)
     * 3. Syntaktická analýza (převod výrazu z běžného in-fixového do post-fixového zápisu)
     *
     * @param str řetězec představující matematický výraz v běžném zápisu
     * @throws EvaluatorException
     **/
    public void parse(String str) throws EvaluatorException {
        String expression = preprocessInput(str);
        Queue<Token> infix = tokenizeToInfix(expression);

        if (infix == null || infix.isEmpty()) {
            postfix = null;

            return;
        }

        postfix = convertToPostfix(infix);
    }

    /**
     * Provede sémantickou analýzu, tj. vlastní vyhodnocení (spolu s případným dosazením hodnot za proměnnou)
     * aktuálně uloženého posledního zpracovaného výrazu, a vrátí výslednou hodnotu.
     * Vyhodnocení aktuálního výrazu je možné volat opakovaně např. za účelem získání funkčních hodnot
     * pro různé hodnoty proměnné při zobrazování funkce do grafu nebo při hledání řešení rovnice
     * s použitím numerické metody.
     *
     * @param val hodnota proměnné
     * @return výsledek vyhodnocení
     * @throws EvaluatorException
     */
    public double evaluate(Double val) throws EvaluatorException {
        if (postfix == null || postfix.isEmpty()) {
            throw new EvaluatorException(null);
        }

        return calculateValue(val);
    }

    /**
     * Provede předzpracování zadaného řetězce matematického výrazu - upraví jej do podoby
     * umožňující snadnou identifikaci jednotlivých tokenů. To zahrnuje např. odstranění mezer,
     * doplnění vynechaných znaků násobení, odstranění nadbytečných kladných zmanének, přeznačení
     * záporných znamének před závorkami nebo symboly proměnné pro jejich jednoduché odlišení
     * od operátorů odčítání, a další drobné úpravy.
     *
     * @param str řetězec původního zadaného výrazu
     * @return řetězec předzpracovaného výrazu
     **/
    private String preprocessInput(String str) {
        str = str.toLowerCase();

        // přeznačení podle předdefinovaných přepisovacích pravidel
        for (Map.Entry<String, String> t : ExpressionParsing.TRANSCRIPTIONS.entrySet()) {
            str = str.replaceAll(t.getKey(), t.getValue());
        }

        return str;
    }

    /**
     * Rozdělí zadaný řetězec matematického výrazu v in-fixovém zápisu na jeho jednotlivé tokeny
     * (čísla, operátory, funkce, závorky, symboly proměnné atd.), které jsou uloženy do pomocné
     * fronty. Ta je určena k použití při následném převodu
     * do post-fixového zápisu (reverzní polské notace). Je očekáván řetězec, který předtím prošel
     * fází předzpracování.
     *
     * @param str řetězec předzpracovaného výrazu
     * @return seznam tokenů výrazu v in-fixové notaci
     * @throws EvaluatorException
     **/
    private Queue<Token> tokenizeToInfix(String str) throws EvaluatorException {
        Queue<Token> infix = new LinkedList<>();
        Matcher matcher;
        int i = 0;

        // otestuje výskyt jednotlivých typů tokenů a vybere ten, který se nachází na počátku aktuálního podřetězce
        while (i < str.length()) {
            // test výskytu funkce
            if ((matcher = ExpressionParsing.FUNCTION_REGEX.matcher(str.substring(i))).find()) {
                infix.add(new Function(matcher.group()));
            }
            // test výskytu operátoru
            else if ((matcher = ExpressionParsing.OPERATOR_REGEX.matcher(str.substring(i))).find()) {
                infix.add(new Operator(matcher.group()));
            }
            // test výskytu proměnné
            else if ((matcher = ExpressionParsing.VARIABLE_REGEX.matcher(str.substring(i))).find()) {
                infix.add(new Variable(matcher.group()));
            }
            // test výskytu čísla
            else if ((matcher = ExpressionParsing.NUMBER_REGEX.matcher(str.substring(i))).find()) {
                infix.add(new Number(matcher.group()));
            }
            // test výskytu konstanty
            else if ((matcher = ExpressionParsing.CONSTANT_REGEX.matcher(str.substring(i))).find()) {
                infix.add(new Number(matcher.group()));
            }
            // test výskytu závorky
            else if ((matcher = ExpressionParsing.OTHER_REGEX.matcher(str.substring(i))).find()) {
                infix.add(new Token(matcher.group()));
            }
            // nenalezen platný token
            else {
                throw new EvaluatorException(EvaluatorExceptionCode.INVALID_SYMBOLS);
            }

            // posun indexu podle délky nalezeného tokenu
            i += matcher.group().length();
        }

        return infix;
    }

    /**
     * Převede zadaný výraz z in-fixového do post-fixového zápisu s použitím algoritmu Shunting-Yard.
     * Jednotlivé tokeny takto vytvořeného zápisu jsou uloženy do fronty pro následné vyhodnocování.
     *
     * @param infix seznam tokenů výrazu v in-fixové notaci
     * @return seznam tokenů výrazu v post-fixové notaci
     * @throws EvaluatorException
     **/
    private Queue<Token> convertToPostfix(Queue<Token> infix) throws EvaluatorException {
        postfix = new LinkedList<>();
        Stack<Token> stack = new Stack<>();

        // Dokud jsou zde tokeny ke čtení: Přečti token.
        for (Token t : infix) {
            // Pokud je token číslo, přidej ho do výstupní fronty.
            if (t instanceof Number || t instanceof Variable) {
                postfix.add(t);
            }
            // Pokud je token funkce, vlož ho na zásobník.
            else if (t instanceof Function) {
                stack.push(t);
            }
            // Pokud je token oddělovač argumentů funkce (nejčastěji čárka):
            else if (t.isArgumentSeparator()) {
                // Dokud nebude na vrcholu zásobníku levá závorka, vybírej z něj operátory a vkládej je do výstupu.
                while (!stack.isEmpty() && !stack.peek().isLeftParenthesis()) {
                    postfix.add(stack.pop());
                }

                // Jestliže se zásobník vyprázdní a závorka nebude nalezena, oddělovač byl buď na nesprávném místě, nebo ve vstupu chybí otevírací závorka.
                if (stack.isEmpty()) {
                    throw new EvaluatorException(EvaluatorExceptionCode.MISPLACED_ARG_SEPARATOR);
                }

            }
            // Pokud je token operátor (dále jen o1), tak:
            else if (t instanceof Operator) {
                // dokud bude na vrcholu zásobníku operátor (s výjimkou závorek), o2, tak:
                while (!stack.isEmpty() && stack.peek() instanceof Operator &&
                        // pokud je o1 asociativní zleva a jeho priorita je menší nebo stejná (≤) jako priorita o2,
                        // nebo o1 je asociativní zprava a jeho priorita je nižší (<) než priorita operátoru o2,
                        // vyjmi operátor o2 ze zásobníku a vlož ho do výstupní fronty, jinak ukonči cyklus.
                        (((Operator) t).isLeftAssoc() && ((Operator) t).PRECEDENCE <= ((Operator) stack.peek()).PRECEDENCE ||
                                ((Operator) t).isRightAssoc() && ((Operator) t).PRECEDENCE < ((Operator) stack.peek()).PRECEDENCE)) {
                    postfix.add(stack.pop());
                }

                // vlož operátor o1 na zásobník.
                stack.push(t);
            }
            // Pokud je token levá závorka, vlož ho na zásobník.
            else if (t.isLeftParenthesis()) {
                stack.push(t);
            }
            // Pokud je token pravá závorka:
            else if (t.isRightParenthesis()) {
                // Dokud na vrcholu zásobníku nebude levá závorka, vybírej operátory ze zásobníku a zapisuj je do výstupní fronty.
                while (!stack.isEmpty() && !stack.peek().isLeftParenthesis()) {
                    postfix.add(stack.pop());
                }

                // Pokud je zásobník prázdný a nepodařilo se najít levou závorku, jedná se o neuzavřený výraz a je vhodné oznámit chybu.
                if (stack.isEmpty()) {
                    throw new EvaluatorException(EvaluatorExceptionCode.MISSING_LEFT_PARENTHESES);
                }

                // Vyjmi ze zásobníku levou závorku, ale nevkládej jí do výstupní fronty.
                stack.pop();

                // Pokud je na vrcholu zásobníku token identifikovaný jako funkce, vlož ho do výstupu.
                if (!stack.isEmpty() && stack.peek() instanceof Function) {
                    postfix.add(stack.pop());
                }
            }
        }

        // Jestliže byly zpracovány všechny tokeny ze vstupu: Dokud budou v zásobníku operátory:
        while (!stack.isEmpty()) {
            // Vyjmi operátor ze zásobníku a vlož ho do výstupní fronty.
            if (stack.peek() instanceof Operator || stack.peek() instanceof Function) {
                postfix.add(stack.pop());
            }
            // Jestliže operátor na vrcholu zásobníku je závorka, ve výrazu jsou neuzavřené závorky a je vhodné ohlásit chybu.
            else {
                throw new EvaluatorException(EvaluatorExceptionCode.MISSING_RIGHT_PARENTHESES);
            }
        }

        return postfix;
    }

    /**
     * Vyhodnotí aktuálně zpracovaný a uložený výraz s dosazením zadané číselné hodnoty
     * za proměnnou. Tato číselná hodnota je nepovinná - je možné předat hodnoty null.
     * V takovém případě se očekává, že uložený výraz neobsahuje žádné symboly proměnné.
     *
     * @param x hodnota proměnné
     * @return vypočtená hodnota
     * @throws EvaluatorException
     **/
    private double calculateValue(Double x) throws EvaluatorException {
        Stack<Token> stack = new Stack<>();

        // pokud jsou na vstupu znaky přečti další znak
        for (Token t : postfix) {
            // jestliže je znak hodnota ulož ji na zásobník
            if (t instanceof Number) {
                stack.push(t);
            } else if (t instanceof Variable) {
                if (x == null) {
                    throw new EvaluatorException(EvaluatorExceptionCode.WRONG_VAR_SYMBOL);
                }

                stack.push(Number.createNumber(x));
            }
            // jinak znak značí funkci (operátory, jako je + jsou jednoduché funkce přebírající dva argumenty)
            else if (t instanceof Operator) {
                if (stack.size() < 2) {
                    throw new EvaluatorException(EvaluatorExceptionCode.MISSING_OPERANDS);
                }

                Number b = (Number) stack.pop();
                Number a = (Number) stack.pop();
                stack.push(((Operator) t).calc(a, b));
            } else if (t instanceof Function) {
                // je známo, že funkce přebírá n parametrů, jestliže je na zásobníku méně než n hodnot: Chyba - uživatel nezadal dostatečný počet parametrů
                if (stack.size() < ((Function) t).MIN_ARGC) {
                    throw new EvaluatorException(EvaluatorExceptionCode.MISSING_ARGS);
                }

                Stack<Number> temp = new Stack<>();

                while (stack.size() > 0 && stack.peek() instanceof Number && temp.size() < ((Function) t).MAX_ARGC) {
                    // vyber nejvyšší n-tou hodnotu ze zásobníku
                    temp.push((Number) stack.pop());
                }

                Number[] tempArray = new Number[temp.size()];
                // vypočti hodnotu funkce a v případě výsledku jej ulož zpět na zásobník
                stack.push(((Function) t).calc(temp.toArray(tempArray)));
            }
        }

        // jestliže je na zásobníku více hodnot: Chyba - uživatel zadal příliš hodnot
        if (stack.size() != 1) {
            throw new EvaluatorException(EvaluatorExceptionCode.TOO_MANY_VALUES);
        }

        // jestliže je na zásobníku jen jedna hodnota je to výsledek výpočtu
        return ((Number) stack.pop()).VALUE;
    }

}