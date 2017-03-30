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
 *
 * @author Petr Kozler
 */
public class PlotView extends View {

    private final double GRAPH_MIN_VALUE = Integer.MIN_VALUE / 2 + 1;
    private final double GRAPH_MAX_VALUE = Integer.MAX_VALUE / 2 - 1;

    protected HashMap<EvaluatorExceptionCode, String> errorMessages;
    private TextView outputTextView;
    private Evaluator evaluator;
    private double scaleX;
    private double scaleY;
    private double translateX;
    private double translateY;
    private boolean touch;

    public class TouchListener implements OnTouchListener {

        private double translateX0;
        private double translateY0;
        private double translateX1;
        private double translateY1;

        public TouchListener() {
            clearGraphTranslationValues();
        }

        public void clearGraphTranslationValues() {
            translateX0 = 0;
            translateY0 = 0;
            translateX1 = 0;
            translateY1 = 0;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                translateX0 = motionEvent.getX();
                translateY0 = motionEvent.getY();
                touch = true;

                return true;
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                translateX1 = translateX;
                translateY1 = translateY;
                touch = false;
                invalidate();

                return true;
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if (touch) {
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

    public TouchListener createGraphListeners(Button zoomInButton, Button zoomOutButton) {
        if (zoomInButton == null || zoomOutButton == null) {
            return null;
        }

        touch = false;

        zoomInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scaleX *= 2;
                scaleY *= 2;
                touch = false;
                invalidate();
            }

        });

        zoomOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scaleX /= 2;
                scaleY /= 2;
                touch = false;
                invalidate();
            }

        });

        TouchListener touchListener = new TouchListener();
        this.setOnTouchListener(touchListener);

        return touchListener;
    }

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

        centerGraph(touchListener);

        try {
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
            double range = (100.0f / scaleX) * (graphWidth / 200.0f);

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

    private void centerGraph(TouchListener touchListener) {
        if (touchListener != null) {
            touchListener.clearGraphTranslationValues();
        }

        translateX = 0;
        translateY = 0;
        scaleX = 100;
        scaleY = 100;
    }

    private void clearCanvas(Canvas canvas, Paint paint, double graphWidth, double graphHeight) {
        // přemazání aktuálního obsahu plátna
        paint.setColor(Color.WHITE);
        canvas.drawRect((float) 0, (float) 0, (float) graphWidth, (float) graphHeight, paint);
        // nakreslení nového obrysu plátna
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect((float) 0, (float) 0, (float) graphWidth, (float) graphHeight, paint);
    }

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
        for (axisX = originX; axisX <= graphWidth; axisX += 100) {
            canvas.drawLine((float) axisX, (float) originY - 5, (float) axisX, (float) originY + 5, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) axisX - 10, (float) originY - 10, paint);
            }

            d += 100 / scaleX;
        }

        // vykreslení popisků záporné poloosy X
        d = 0;
        for (axisX = originX; axisX >= 0; axisX -= 100) {
            canvas.drawLine((float) axisX, (float) originY - 5, (float) axisX, (float) originY + 5, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) axisX - 15, (float) originY + 25, paint);
            }

            d -= 100 / scaleX;
        }

        // vykreslení popisků záporné poloosy Y
        d = 0;
        double axisY;
        for (axisY = originY; axisY <= graphHeight; axisY += 100) {
            canvas.drawLine((float) originX - 5, (float) axisY, (float) originX + 5, (float) axisY, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) originX - 40, (float) axisY + 5, paint);
            }

            d -= 100 / scaleY;
        }

        // vykreslení popisků kladné poloosy Y
        d = 0;
        for (axisY = originY; axisY >= 0; axisY -= 100) {
            canvas.drawLine((float) originX - 5, (float) axisY, (float) originX + 5, (float) axisY, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), (float) originX + 10, (float) axisY + 5, paint);
            }

            d += 100 / scaleY;
        }
    }

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

    private boolean exceedsGraphLimits(double value) {
        return value <= GRAPH_MIN_VALUE || value >= GRAPH_MAX_VALUE;
    }

    private boolean canDrawPlotLine(double valueY0, double valueY) {
        return !(Math.abs(valueY - valueY0) >= 1000 / scaleY
                && Math.min(valueY0, valueY) < 0
                && Math.max(valueY0, valueY) > 0);
    }

}
