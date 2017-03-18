package cz.zcu.pkozler.mkz.handlers;
/*
import core.AExpressionException;
import core.Expression;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;*/

/**
 *
 * @author Petr Kozler
 */
public class PlotDrawing {

    /*private Expression expression;
    private Canvas canvas;
    private double pX = 0;
    private double pY = 0;
    private double zX = 100;
    private double zY = 100;
    private double pX0, pY0, pX1, pY1;
    private boolean click = false;
    private double ox, oy, range;
    private double graphWidth;
    private double graphHeight;

    public PlotDrawing(Expression expression, Canvas canvas) {
        this.expression = expression;
        this.canvas = canvas;
    }

    public void draw(String funcStr) throws AExpressionException {
        graphWidth = canvas.getWidth();
        graphHeight = canvas.getHeight();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas(gc);
        drawAxes(gc);
        drawFunction(gc, funcStr);
    }
    
    private void clearCanvas(GraphicsContext g2) {
        Font font = Font.font("Arial", FontWeight.NORMAL, 10);
        g2.setFont(font);
        g2.setFill(Color.WHITE);
        g2.fillRect(0, 0, graphWidth, graphHeight);
        g2.setStroke(Color.BLACK);
        g2.strokeRect(0, 0, graphWidth, graphHeight);
    }

    private void drawAxes(GraphicsContext g2) {
        ox = graphWidth / 2 - pX;
        oy = graphHeight / 2 + pY;

        g2.setStroke(Color.GRAY);
        g2.strokeLine(0, (int) oy, graphWidth, (int) oy);
        g2.strokeLine((int) ox, 0, (int) ox, graphHeight);

        // kladná poloosa X
        double d = 0;
        int axisX;
        for (axisX = (int) ox; axisX <= graphWidth; axisX += 100) {
            g2.strokeLine(axisX, (int) oy - 5, axisX, (int) oy + 5);
            
            if (d != 0) {
                g2.strokeText(Double.toString(d), axisX - 5, (int) oy - 10);
            }
            
            d += 100 / zX;
        }
        
        // záporná poloosa X
        d = 0;
        for (axisX = (int) ox; axisX >= 0; axisX -= 100) {
            g2.strokeLine(axisX, (int) oy - 5, axisX, (int) oy + 5);
            
            if (d != 0) {
                g2.strokeText(Double.toString(d), axisX - 5, (int) oy + 15);
            }
            
            d -= 100 / zX;
        }
        
        // záporná poloosa Y
        d = 0;
        int axisY;
        for (axisY = (int) oy; axisY <= graphHeight; axisY += 100) {
            g2.strokeLine((int) ox - 5, axisY, (int) ox + 5, axisY);
            
            if (d != 0) {
                g2.strokeText(Double.toString(d), (int) ox - 25, axisY);
            }
            
            d -= 100 / zY;
        }
        
        // kladná poloosa Y
        d = 0;
        for (axisY = (int) oy; axisY >= 0; axisY -= 100) {
            g2.strokeLine((int) ox - 5, axisY, (int) ox + 5, axisY);
            
            if (d != 0) {
                g2.strokeText(Double.toString(d), (int) ox + 10, axisY + 5);
            }
            
            d += 100 / zY;
        }
    }

    private void drawFunction(GraphicsContext g2, String funcStr) throws AExpressionException {
        int min = Integer.MIN_VALUE / 2 + 1;
        int max = Integer.MAX_VALUE / 2 - 1;

        double X0, Y0, X, Y;

        range = (100.0 / zX) * (graphWidth / 200.0);
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
                        g2.strokeLine(a0, b0, a1, b1);
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
    }*/

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
