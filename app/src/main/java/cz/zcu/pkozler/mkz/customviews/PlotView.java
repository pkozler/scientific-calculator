package cz.zcu.pkozler.mkz.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import cz.zcu.pkozler.mkz.R;
import cz.zcu.pkozler.mkz.core.Expression;
import cz.zcu.pkozler.mkz.core.ExpressionException;

/**
 * TODO: document your custom view class.
 */
public class PlotView extends View {

    private String funcStr;
    private Expression expression;
    private TextView outputTextView;
    private float ox;
    private float oy;
    private float range;
    private float graphWidth;
    private float graphHeight;
    private float pX = 0;
    private float pY = 0;
    private float zX = 100;
    private float zY = 100;
    private float pX0;
    private float pY0;
    private float pX1;
    private float pY1;
    private boolean click = false;
    private boolean start = true;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!start) {
            graphWidth = canvas.getWidth();
            graphHeight = canvas.getHeight();

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            clearCanvas(canvas, paint);
            paint.setColor(Color.GRAY);
            paint.setStyle(Paint.Style.STROKE);
            //Font font = Font.font("Arial", FontWeight.NORMAL, 10);
            drawAxes(canvas, paint);
            paint.setColor(Color.BLACK);

            try {
                drawFunction(canvas, paint);
                outputTextView.setText("f(x):");
            }
            catch (ExpressionException e) {
                outputTextView.setText(e.getMessage());
            }
        }

        start = true;
    }

    public void draw(String funcStr, Expression expression, TextView outputTextView) {
        start = false;
        this.expression = expression;
        this.funcStr = funcStr;
        this.outputTextView = outputTextView;
        invalidate();
    }

    private void clearCanvas(Canvas canvas, Paint paint) {
        canvas.drawRect(0, 0, graphWidth, graphHeight, paint);
    }

    private void drawAxes(Canvas canvas, Paint paint) {
        canvas.drawRect(0, 0, graphWidth, graphHeight, paint);
        ox = graphWidth / 2 - pX;
        oy = graphHeight / 2 + pY;

        canvas.drawLine(0, oy, graphWidth, oy, paint);
        canvas.drawLine(ox, 0, ox, graphHeight, paint);

        // kladná poloosa X
        double d = 0;
        int axisX;
        for (axisX = (int) ox; axisX <= graphWidth; axisX += 100) {
            canvas.drawLine(axisX, oy - 5, axisX, oy + 5, paint);

            /*if (d != 0) {
                canvas.drawText(Double.toString(d), axisX - 5, oy - 10);
            }*/

            d += 100 / zX;
        }

        // záporná poloosa X
        d = 0;
        for (axisX = (int) ox; axisX >= 0; axisX -= 100) {
            canvas.drawLine(axisX, oy - 5, axisX, oy + 5, paint);

            /*if (d != 0) {
                canvas.drawText(Double.toString(d), axisX - 5, oy + 15);
            }*/

            d -= 100 / zX;
        }

        // záporná poloosa Y
        d = 0;
        int axisY;
        for (axisY = (int) oy; axisY <= graphHeight; axisY += 100) {
            canvas.drawLine(ox - 5, axisY, ox + 5, axisY, paint);

            /*if (d != 0) {
                canvas.drawText(Double.toString(d), ox - 25, axisY);
            }*/

            d -= 100 / zY;
        }

        // kladná poloosa Y
        d = 0;
        for (axisY = (int) oy; axisY >= 0; axisY -= 100) {
            canvas.drawLine(ox - 5, axisY, ox + 5, axisY, paint);

            /*if (d != 0) {
                canvas.drawText(Double.toString(d), ox + 10, axisY + 5);
            }*/

            d += 100 / zY;
        }
    }

    private void drawFunction(Canvas canvas, Paint paint) throws ExpressionException {
        int min = Integer.MIN_VALUE / 2 + 1;
        int max = Integer.MAX_VALUE / 2 - 1;

        double X0, Y0, X, Y;

        range = (100.0f / zX) * (graphWidth / 200.0f);
        X0 = (pX / zX) - range;
        Y0 = 0;
        X = X0;

        while (X <= (pX / zX) + range) {
            Y = expression.eval(funcStr, X);

            int a0, b0, a1, b1;

            if (X == X0 || oy - Y * zY <= min || oy - Y * zY >= max
                    || oy + X * zX <= min || oy + X * zX >= max
                    || oy - Y0 * zY <= min || oy - Y0 * zY >= max
                    || oy + X0 * zX <= min || oy + X0 * zX >= max
                    || Double.isInfinite(Y) || Double.isNaN(Y)) {
                X0 = X;
                Y0 = Y;
            }
            else {
                try {
                    a0 = (int) ((ox + X0 * zX));
                    b0 = (int) ((oy - Y0 * zY));
                    a1 = (int) ((ox + X * zX));
                    b1 = (int) ((oy - Y * zY));

                    if (!(Math.abs(Y - Y0) >= 1000 / zY && Math.min(Y0, Y) < 0 && Math
                            .max(Y0, Y) > 0)) {
                        canvas.drawLine(a0, b0, a1, b1, paint);
                    }
                }
                catch (Exception e) {
                    // chyba při vykreslení
                }

                X0 = X;
                Y0 = Y;
            }
            X += (1.0 / zX);
        }
    }

    /*private void setListeners() {

        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                    switch (arg0.getButton()) {
                        case MouseEvent.BUTTON1: {
                            pX0 = 0;
                            pY0 = 0;
                            pX1 = 0;
                            pY1 = 0;
                            pX = 0;
                            pY = 0;

                            repaint();
                            break;
                        }
                        case MouseEvent.BUTTON2: {
                            zX = 100;
                            zY = 100;

                            repaint();
                            break;
                        }
                        case MouseEvent.BUTTON3: {
                            pX0 = 0;
                            pY0 = 0;
                            pX1 = 0;
                            pY1 = 0;
                            pX = 0;
                            pY = 0;
                            zX = 100;
                            zY = 100;

                            repaint();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                pX0 = arg0.getX();
                pY0 = arg0.getY();
                click = true;
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                repaint();
                pX1 = pX;
                pY1 = pY;
                click = false;
            }

        });

        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent arg0) {
                if (click) {
                    pX = -(arg0.getX() - pX0) + pX1;
                    pY = (arg0.getY() - pY0) + pY1;
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent arg0) {

            }

        });

        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {

                if (arg0.getPreciseWheelRotation() > 0) {
                    zX *= 2;
                    zY *= 2;
                    repaint();
                } else if (arg0.getPreciseWheelRotation() < 0) {
                    zX /= 2;
                    zY /= 2;
                    repaint();
                }

            }

        });
    }*/

}
