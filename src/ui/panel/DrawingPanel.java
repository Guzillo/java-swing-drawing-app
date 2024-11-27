package ui.panel;

import manager.DrawingManager;

import enums.DrawingTools;
import model.DrawingFigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class DrawingPanel extends JPanel
{
    DrawingManager drawingManager = new DrawingManager();
    private static DrawingPanel instance;

    public static DrawingPanel getInstance(){
        return instance;
    }

    public DrawingPanel() {
        instance = this;
        this.setPreferredSize(new Dimension(400, 400));
        Color whiteGreyColor = new Color(
                238,
                238,
                238
        );
        this.setBackground(whiteGreyColor);
        this.setFocusable(true);
        this.requestFocusInWindow();

        //PAINTING WITH PEN
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drawingManager.drawDuringMouseDrag(e);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                drawingManager.updateMousePosition(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
             drawingManager.drawOnMouseClick(e);
          }

          @Override
          public void mouseReleased(MouseEvent e) {
              drawingManager.resetLastMousePosition(e);
          }});

        //PAINTING FIGURES
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                drawingManager.drawFigureOnKeyPress(e);
            }
        });

        //DELETING FIGURE
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                drawingManager.deleteFigureOnKeyPress(e);

            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<DrawingFigure> drawingFigures = DrawingManager.getFigures();
        for (DrawingFigure drawingFigure : drawingFigures){
            DrawingTools tool = drawingFigure.getDrawingTool();
            Color color = drawingFigure.getFigureColor();
            Point point = drawingFigure.getInitialPoint();
            g.setColor(color);
            int figSize = drawingFigure.getFigureSize();

            if (tool == DrawingTools.PEN) {
                Point lastPoint = drawingFigure.getLastPoint();
                Point currentPoint = drawingFigure.getCurrentPoint();
                g.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
            }else if (tool == DrawingTools.CIRCLE) {
                g.fillOval(
                        point.x ,
                        point.y,
                        figSize,
                        figSize);
            }else if (tool == DrawingTools.SQUARE) {
                g.fillRect(
                        point.x,
                        point.y,
                        figSize,
                        figSize);
            }
        }
    }
}