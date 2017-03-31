package cz.zcu.pkozler.mkz.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import cz.zcu.pkozler.mkz.R;
import cz.zcu.pkozler.mkz.core.Evaluator;
import cz.zcu.pkozler.mkz.core.EvaluatorException;
import cz.zcu.pkozler.mkz.core.EvaluatorExceptionCode;

/**
 * Třída uživatelského rozhraní, představující plátno pro vizualizaci průběhu matematických funkcí
 * do grafu, ve kterém je možné se přesouvat pomocí pohybů po obrazovce a měnit měřítko kreslení.
 *
 * @author Petr Kozler
 */
public class PlotView extends View {

    /**
     * výchozí souřadnice posunu zobrazeného grafu od počátku soustavy souřadnic
     */
    private final double DEFAULT_TRANSLATE = 0;

    /**
     * výchozí měřítko zobrazeného grafu
     */
    private final double DEFAULT_SCALE = 100;

    /**
     * dolní limit pro pozici bodů kreslených do grafu
     */
    private final double GRAPH_MIN_VALUE = Integer.MIN_VALUE / 2 + 1;

    /**
     * horní limit pro pozici bodů kreslených do grafu
     */
    private final double GRAPH_MAX_VALUE = Integer.MAX_VALUE / 2 - 1;

    /**
     * slovník chybových kódů a odpovídajících chybových hlášek
     */
    protected HashMap<EvaluatorExceptionCode, String> errorMessages;

    /**
     * textové pole pro výpis výsledku
     */
    private TextView outputTextView;

    /**
     * objekt pro zpracovávání a vyhodnocování zadaných výrazů
     */
    private Evaluator evaluator;

    /**
     * měřítko na ose X
     */
    private double scaleX;

    /**
     * měřítko na ose Y
     */
    private double scaleY;

    /**
     * posun na ose X
     */
    private double translateX;

    /**
     * posun na ose Y
     */
    private double translateY;

    /**
     * příznak dotyku na obrazovce nad komponentou grafu
     */
    private boolean touch;

    /**
     * Třída, představující posluchač události pohybu po obrazovce
     * nad komponentou grafu. Pohyb je interpretován jako posun grafu.
     */
    public class TouchListener implements OnTouchListener {

        /**
         * počáteční souřadnice při posunu na ose X
         */
        private double translateX0;

        /**
         * počáteční souřadnice při posunu na ose Y
         */
        private double translateY0;

        /**
         * koncová souřadnice při posunu na ose X
         */
        private double translateX1;

        /**
         * koncová souřadnice při posunu na ose Y
         */
        private double translateY1;

        /**
         * Vytvoří nový posluchač pohybu v grafu.
         */
        public TouchListener() {
            clearGraphTranslationValues();
        }

        /**
         * Nastaví výchozí hodnoty souřadnic pro určení vzdálenosti a směru pohybu.
         */
        public void clearGraphTranslationValues() {
            translateX0 = DEFAULT_TRANSLATE;
            translateY0 = DEFAULT_TRANSLATE;
            translateX1 = DEFAULT_TRANSLATE;
            translateY1 = DEFAULT_TRANSLATE;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                // změna souřadnic při zahájení pohybu
                translateX0 = motionEvent.getX();
                translateY0 = motionEvent.getY();
                touch = true;

                return true;
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // změna souřadnic při ukončení pohybu
                translateX1 = translateX;
                translateY1 = translateY;
                touch = false;
                invalidate();

                return true;
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if (touch) {
                    // změna souřadnic v průběhu pohybu
                    translateX = -(motionEvent.getX() - translateX0) + translateX1;
                    translateY = (motionEvent.getY() - translateY0) + translateY1;
                    invalidate();
                }

                return true;
            }
            else {
                return false;
            }
        }

    }

    public PlotView(Context context) {
        super(context);
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Nastaví posluchače pro posun v grafu a změnu měřítka.
     *
     * @param zoomInButton přiblížení v grafu
     * @param zoomOutButton oddálení v grafu
     * @return posluchač pohybu v grafu
     */
    public TouchListener createGraphListeners(Button zoomInButton, Button zoomOutButton) {
        if (zoomInButton == null || zoomOutButton == null) {
            return null;
        }

        touch = false;

        // nastavení posluchače pro přiblížení
        zoomInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scaleX *= 2;
                scaleY *= 2;
                touch = false;
                invalidate();
            }

        });

        // nastavení posluchače pro oddálení
        zoomOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scaleX /= 2;
                scaleY /= 2;
                touch = false;
                invalidate();
            }

        });

        // vytvoření posluchače pro posun v grafu
        TouchListener touchListener = new TouchListener();
        this.setOnTouchListener(touchListener);

        return touchListener;
    }

    /**
     * Spustí vykreslování grafu zadané funkce.
     *
     * @param funcStr řetězcová reprezentace matematického výrazu
     * @param evaluator objekt pro vyhodnocování
     * @param outputTextView textové pole pro výpis výsledku
     * @param touchListener posluchač pro posun v grafu
     * @param errorMessages slovník chybových kódů a odpovídajících chybových hlášek
     */
    public void draw(String funcStr, Evaluator evaluator, TextView outputTextView,
                     TouchListener touchListener, HashMap<EvaluatorExceptionCode, String> errorMessages) {
        if (evaluator == null || outputTextView == null
                || errorMessages == null || errorMessages.isEmpty()
                ||funcStr == null || funcStr.isEmpty()) {
            return;
        }

        this.evaluator = evaluator;
        this.outputTextView = outputTextView;
        this.errorMessages = errorMessages;

        // vycentrování grafu
        centerGraph(touchListener);

        try {
            // zpracování řetězcové reprezentace výrazu představujícího funkci zadanou k zobrazení
            evaluator.parse(funcStr);

            invalidate();
        }
        catch (EvaluatorException e) {
            outputTextView.setText(errorMessages.get(e.CODE));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (evaluator == null || outputTextView == null) {
            return;
        }

        // určení rozměrů grafu a souřadnic počátku
        double graphWidth = canvas.getWidth();
        double graphHeight = canvas.getHeight();
        double originX = graphWidth / 2 - translateX;
        double originY = graphHeight / 2 + translateY;

        // vyčištění plátna a nakreslení číslovaných os nového grafu funkce
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearCanvas(canvas, paint, graphWidth, graphHeight);
        drawAxes(canvas, paint, graphWidth, graphHeight, originX, originY);

        if (!touch) {
            // určení maximální vzdálenosti zobrazených útvarů od středu grafu
            double range = (DEFAULT_SCALE / scaleX) * (graphWidth / (2 * DEFAULT_SCALE));

            try {
                // nakreslení grafu funkce
                drawPlot(canvas, paint, originX, originY, range);
                outputTextView.setText(R.string.plot_output);
            }
            catch (EvaluatorException e) {
                outputTextView.setText(errorMessages.get(e.CODE));
            }
        }
    }

    /**
     * Nastaví výchozí hodnoty měřítka a souřadnic pro určení posunu
     * (včetně pomocných souřadnic posluchače pohybu v grafu, je-li předán).
     * Používá se k vycentrování grafu při zadání nové funkce k vykreslení.
     *
     * @param touchListener posluchač pohybu v grafu
     */
    private void centerGraph(TouchListener touchListener) {
        if (touchListener != null) {
            touchListener.clearGraphTranslationValues();
        }

        translateX = DEFAULT_TRANSLATE;
        translateY = DEFAULT_TRANSLATE;
        scaleX = DEFAULT_SCALE;
        scaleY = DEFAULT_SCALE;
    }

    /**
     * Přemaže obsah plátna bílým obdélníkem (s obrysem pro větší přehlednost v GUI).
     * Určeno k použití před každým vykreslením nové podoby grafu.
     *
     * @param canvas komponenta pro kreslení
     * @param paint komponenta uchovávající informace o způsobu kreslení
     * @param graphWidth šířka grafu
     * @param graphHeight výška grafu
     */
    private void clearCanvas(Canvas canvas, Paint paint, double graphWidth, double graphHeight) {
        // přemazání aktuálního obsahu plátna
        paint.setColor(Color.WHITE);
        canvas.drawRect((float) 0, (float) 0, (float) graphWidth, (float) graphHeight, paint);

        // nakreslení nového obrysu plátna
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect((float) 0, (float) 0, (float) graphWidth, (float) graphHeight, paint);
    }

    /**
     * Nakreslí osy souřadnicové soustavy grafu spolu s číselnými popisky.
     *
     * @param canvas komponenta pro kreslení
     * @param paint komponenta uchovávající informace o způsobu kreslení
     * @param graphWidth šířka grafu
     * @param graphHeight výška grafu
     * @param originX souřadnice počátku grafu na ose X
     * @param originY souřadnice počátku grafu na ose Y
     */
    private void drawAxes(Canvas canvas, Paint paint, double graphWidth, double graphHeight,
                          double originX, double originY) {
        // nakreslení os grafu
        canvas.drawLine((float) 0, (float) originY, (float) graphWidth, (float) originY, paint);
        canvas.drawLine((float) originX, (float) 0, (float) originX, (float) graphHeight, paint);

        // nastavení velikosti písma pro popisky os
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.FILL);

        // vykreslení popisků kladné poloosy X
        double d = 0;
        double axisX;
        for (axisX = originX; axisX <= graphWidth; axisX += DEFAULT_SCALE) {
            canvas.drawLine((float) axisX, (float) originY - 5, (float) axisX, (float) originY + 5, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) axisX - 10, (float) originY - 10, paint);
            }

            d += DEFAULT_SCALE / scaleX;
        }

        // vykreslení popisků záporné poloosy X
        d = 0;
        for (axisX = originX; axisX >= 0; axisX -= DEFAULT_SCALE) {
            canvas.drawLine((float) axisX, (float) originY - 5, (float) axisX, (float) originY + 5, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) axisX - 15, (float) originY + 25, paint);
            }

            d -= DEFAULT_SCALE / scaleX;
        }

        // vykreslení popisků záporné poloosy Y
        d = 0;
        double axisY;
        for (axisY = originY; axisY <= graphHeight; axisY += DEFAULT_SCALE) {
            canvas.drawLine((float) originX - 5, (float) axisY, (float) originX + 5, (float) axisY, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) originX - 40, (float) axisY + 5, paint);
            }

            d -= DEFAULT_SCALE / scaleY;
        }

        // vykreslení popisků kladné poloosy Y
        d = 0;
        for (axisY = originY; axisY >= 0; axisY -= DEFAULT_SCALE) {
            canvas.drawLine((float) originX - 5, (float) axisY, (float) originX + 5, (float) axisY, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) originX + 10, (float) axisY + 5, paint);
            }

            d += DEFAULT_SCALE / scaleY;
        }
    }

    /**
     * Nakreslí křivku představující průběh aktuálně zadané funkce do grafu
     * s použitím metody elementárních úseček.
     *
     * @param canvas komponenta pro kreslení
     * @param paint komponenta uchovávající informace o způsobu kreslení
     * @param originX souřadnice počátku grafu na ose X
     * @param originY souřadnice počátku grafu na ose Y
     * @param range maximální vzdálenost zobrazených objektů od středu grafu
     * @throws EvaluatorException
     */
    private void drawPlot(Canvas canvas, Paint paint, double originX, double originY, double range)
            throws EvaluatorException {
        paint.setStyle(Paint.Style.STROKE);

        // inicializace proměnných pro uchování souřadnic bodů aktuálně kreslené spojnice v grafu
        double valueX0 = (translateX / scaleX) - range;
        double valueY0 = 0;
        double valueX = valueX0;
        double valueY;

        while (valueX <= (translateX / scaleX) + range) {
            // výpočet funkční hodnoty z aktuální hodnoty X
            valueY = evaluator.evaluate(valueX);

            if (valueX == valueX0 || Double.isInfinite(valueY) || Double.isNaN(valueY)
                        || exceedsGraphLimits(originY - valueY * scaleY)
                        || exceedsGraphLimits(originY + valueX * scaleX)
                        || exceedsGraphLimits(originY - valueY0 * scaleY)
                        || exceedsGraphLimits(originY + valueX0 * scaleX)) {
                // nastavení průhledné barvy spojnice pro nezobrazení v případě neplatné hodnoty
                paint.setColor(Color.TRANSPARENT);
            }
            else {
                // nastavení viditelné barvy spojnice pro zobrazení v případě platné hodnoty
                paint.setColor(Color.BLACK);
            }

            if (canDrawPlotLine(valueY0, valueY)) {
                // nakreslení spojnice mezi aktuálně vypočtenými 2 body funkce
                canvas.drawLine(
                        (float) (originX + valueX0 * scaleX),
                        (float) (originY - valueY0 * scaleY),
                        (float) (originX + valueX * scaleX),
                        (float) (originY - valueY * scaleY),
                        paint);
            }

            // nastavení aktuálního koncového bodu spojnice jako nového počátečního
            valueX0 = valueX;
            valueY0 = valueY;

            // přesun na další souřadnici X koncového bodu nové spojnice podle aktuálního měřítka
            valueX += (1.0 / scaleX);
        }
    }

    /**
     * Určí, zda předaná souřadnice bodu přesahuje stanovené limity pro kreslení do grafu.
     *
     * @param value souřadnice
     * @return true, pokud hodnota přesahuje limit, jinak false
     */
    private boolean exceedsGraphLimits(double value) {
        return value <= GRAPH_MIN_VALUE || value >= GRAPH_MAX_VALUE;
    }

    /**
     * Určí, zda má být do grafu zobrazena elementární úsečka s předanými souřadnicemi počátečního
     * a koncového bodu na ose Y. Zabraňuje kreslení "spojnic" v bodech, kde hodnota funkce
     * není definována (typicky se blíží k nekonečnu z jedné strany a k mínus nekonečnu z druhé).
     *
     * @param valueY0 souřadnice Y počátečního bodu úsečky
     * @param valueY souřadnice Y koncového bodu úsečky
     * @return true, má-li být úsečka zobrazena, jinak false
     */
    private boolean canDrawPlotLine(double valueY0, double valueY) {
        return !(Math.abs(valueY - valueY0) >= (10 * DEFAULT_SCALE) / scaleY
                && Math.min(valueY0, valueY) < 0
                && Math.max(valueY0, valueY) > 0);
    }

}
