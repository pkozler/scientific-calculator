package cz.zcu.pkozler.mkz.customviews;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cz.zcu.pkozler.mkz.BaseActivity;
import cz.zcu.pkozler.mkz.R;
import cz.zcu.pkozler.mkz.core.Expression;
import cz.zcu.pkozler.mkz.core.ExpressionException;

/**
 * TODO: document your custom view class.
 */
public class PlotView extends View {
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
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
            clearCanvas(canvas, paint);
            drawAxes(canvas, paint);

            if (!click) {
                try {
                    drawFunction(canvas, paint);
                    outputTextView.setText(R.string.plot_output);
                }
                catch (ExpressionException e) {
                    outputTextView.setText(e.getMessage());
                }
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
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, graphWidth, graphHeight, paint);
    }

    private void drawAxes(Canvas canvas, Paint paint) {
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, graphWidth, graphHeight, paint);
        ox = graphWidth / 2 - pX;
        oy = graphHeight / 2 + pY;

        canvas.drawLine(0, oy, graphWidth, oy, paint);
        canvas.drawLine(ox, 0, ox, graphHeight, paint);
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.FILL);

        // kladná poloosa X
        double d = 0;
        float axisX;
        for (axisX = ox; axisX <= graphWidth; axisX += 100) {
            canvas.drawLine(axisX, oy - 5, axisX, oy + 5, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), axisX - 10, oy - 10, paint);
            }

            d += 100 / zX;
        }

        // záporná poloosa X
        d = 0;
        for (axisX = ox; axisX >= 0; axisX -= 100) {
            canvas.drawLine(axisX, oy - 5, axisX, oy + 5, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), axisX - 15, oy + 25, paint);
            }

            d -= 100 / zX;
        }

        // záporná poloosa Y
        d = 0;
        float axisY;
        for (axisY = oy; axisY <= graphHeight; axisY += 100) {
            canvas.drawLine(ox - 5, axisY, ox + 5, axisY, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), ox - 40, axisY + 5, paint);
            }

            d -= 100 / zY;
        }

        // kladná poloosa Y
        d = 0;
        for (axisY = oy; axisY >= 0; axisY -= 100) {
            canvas.drawLine(ox - 5, axisY, ox + 5, axisY, paint);

            if (d != 0) {
                canvas.drawText(Double.toString(d), ox + 10, axisY + 5, paint);
            }

            d += 100 / zY;
        }
    }

    private void drawFunction(Canvas canvas, Paint paint) throws ExpressionException {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
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

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        scaleGestureDetector.onTouchEvent(e);
        return gestureDetector.onTouchEvent(e);
    }

    public void addDragListener(final BaseActivity activity) {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("MOVE", Integer.toString(motionEvent.getAction()));

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    pX0 = motionEvent.getX();
                    pY0 = motionEvent.getY();
                    click = true;

                    return true;
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    pX1 = pX;
                    pY1 = pY;
                    click = false;

                    start = false;
                    invalidate();

                    return true;
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    if (click) {
                        pX = -(motionEvent.getX() - pX0) + pX1;
                        pY = (motionEvent.getY() - pY0) + pY1;

                        start = false;
                        invalidate();
                    }

                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }

    public  void addScaleGestureListener(final BaseActivity activity) {
        scaleGestureDetector = new ScaleGestureDetector(activity, new ScaleGestureDetector.SimpleOnScaleGestureListener() {

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Log.d("TAG", "PINCH");

                float factor = Math.abs(detector.getScaleFactor());

                if (factor > 1) {
                    zX *= 2;
                    zY *= 2;
                    start = false;
                    invalidate();

                    return true;
                }

                if (factor < 1) {
                    zX /= 2;
                    zY /= 2;
                    start = false;
                    invalidate();

                    return true;
                }

                return false;
            }

        });
    }

    public void addDoubleTapListener(final BaseActivity activity) {
        gestureDetector = new GestureDetector(activity, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("TAG", "DOUBLETAP");

                pX0 = 0;
                pY0 = 0;
                pX1 = 0;
                pY1 = 0;
                pX = 0;
                pY = 0;
                zX = 100;
                zY = 100;
                start = false;
                invalidate();

                return true;
            }

        });
    }

}
