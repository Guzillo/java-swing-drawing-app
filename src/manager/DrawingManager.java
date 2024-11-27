package manager;

import ui.panel.DrawingPanel;
import enums.FileStatus;
import enums.DrawingTools;
import model.DrawingFigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawingManager {
    private static ArrayList<DrawingFigure> drawingFigures;
    private Point lastPoint;
    private Point currentPoint;
    private static DrawingTools tool;
    private static Color color;
    private static int figureSize;

    public DrawingManager() {
        drawingFigures = new ArrayList<>();
        tool = null;
        setFigureSize(20);
    }

    public static List<DrawingFigure> getFigures() {
        return drawingFigures;
    }

    public static void setFigures(ArrayList<DrawingFigure> drawingFigures) {
        DrawingManager.drawingFigures = drawingFigures;
    }

    public static DrawingTools getTool() {
        return tool;
    }

    public static void setTool(DrawingTools tool) {
        DrawingManager.tool = tool;
    }

    public static Color getColor() {
        return color;
    }

    public static void setColor(Color color) {
        DrawingManager.color = color;
    }

    public static void setFigureSize(int figureSize) {
        DrawingManager.figureSize = figureSize;
    }

    public void drawDuringMouseDrag(MouseEvent e) {
        if (tool == DrawingTools.PEN) {
            currentPoint = e.getPoint();
            if (lastPoint != null) {
                drawingFigures.add(new DrawingFigure(color, lastPoint, currentPoint, DrawingTools.PEN));
            }
            FileOperationsManager.setCurrentFileState(FileStatus.MODIFIED);
            lastPoint = currentPoint;
            DrawingPanel.getInstance().repaint();
        }
    }

    public void updateMousePosition(MouseEvent e) {
        currentPoint = e.getPoint();
    }

    public void drawOnMouseClick(MouseEvent e) {
        if (tool == DrawingTools.PEN) {
            currentPoint = e.getPoint();
            drawingFigures.add(new DrawingFigure(color, currentPoint, currentPoint, DrawingTools.PEN));
            FileOperationsManager.setCurrentFileState(FileStatus.MODIFIED);
        }
    }

    public void resetLastMousePosition(MouseEvent e) {
        if (tool == DrawingTools.PEN) {
            lastPoint = null;
        }
    }

    public void drawFigureOnKeyPress(KeyEvent e) {
        Color generatedRandomColor = new Color(
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)
        );
        if (e.getKeyCode() == KeyEvent.VK_L) {
            if (tool == DrawingTools.SQUARE) {
                Point correctedPoint = new Point(currentPoint.x - (figureSize/2), currentPoint.y - (figureSize/2));
                DrawingFigure drawingFigure = new DrawingFigure(generatedRandomColor, correctedPoint, DrawingTools.SQUARE);
                drawingFigure.setFigureSize(figureSize);
                drawingFigures.add(drawingFigure);
                if (FileOperationsManager.getCurrentFileState() == FileStatus.SAVED)
                    FileOperationsManager.setCurrentFileState(FileStatus.MODIFIED);
            }
            else if (tool == DrawingTools.CIRCLE) {
                Point correctedPoint = new Point(currentPoint.x - (figureSize/2), currentPoint.y - (figureSize/2));
                DrawingFigure drawingFigure = new DrawingFigure(generatedRandomColor, correctedPoint, DrawingTools.CIRCLE);
                drawingFigure.setFigureSize(figureSize);
                drawingFigures.add(drawingFigure);
                if (FileOperationsManager.getCurrentFileState() == FileStatus.SAVED)
                    FileOperationsManager.setCurrentFileState(FileStatus.MODIFIED);
            }
            DrawingPanel.getInstance().repaint();
        }
    }

    public void deleteFigureOnKeyPress(KeyEvent e) {
        Point p = currentPoint;
        List<DrawingFigure> figuresToDeletion = new ArrayList<>();
        Integer dialogAnswer = null;
        if (e.getKeyCode() == KeyEvent.VK_D){
            for (DrawingFigure drawingFigure : drawingFigures){
                int figurePointX = 0;
                int figurePointY = 0;
                int figSize = 0;
                if (drawingFigure.getInitialPoint() != null) {
                    figurePointX = drawingFigure.getInitialPoint().x;
                    figurePointY = drawingFigure.getInitialPoint().y;
                    figSize = drawingFigure.getFigureSize();
                }
                //checking if point is in circle
                if (drawingFigure.getDrawingTool() == DrawingTools.CIRCLE) {
                    int r = figSize / 2;
                    int dx = figurePointX + r;
                    int dy = figurePointY + r;
                    if (Math.pow( (p.x - dx) ,  2) + Math.pow( (p.y - dy) , 2) <= r*r) {
                        if (dialogAnswer == null) {
                            dialogAnswer = JOptionPane.showConfirmDialog(DrawingPanel.getInstance(),
                                    "Do you want to delete those Circles?",
                                    "Warning",
                                    JOptionPane.YES_NO_OPTION
                            );
                        }

                        if (dialogAnswer == 0) {
                            figuresToDeletion.add(drawingFigure);
                            if (FileOperationsManager.getCurrentFileState() == FileStatus.SAVED)
                                FileOperationsManager.setCurrentFileState(FileStatus.MODIFIED);
                        }
                    }
                    //checking if point is in square
                }else if (drawingFigure.getDrawingTool() == DrawingTools.SQUARE) {
                    if (p.x >= figurePointX && p.x <= figurePointX + figSize && (p.y >= figurePointY && p.y <= figurePointY + figSize)) {
                        if (dialogAnswer == null) {
                            dialogAnswer = JOptionPane.showConfirmDialog(DrawingPanel.getInstance(),
                                    "Do you want to delete those Squares?",
                                    "Warning",
                                    JOptionPane.YES_NO_OPTION
                            );
                        }

                        if (dialogAnswer == 0) {
                            figuresToDeletion.add(drawingFigure);
                            if (FileOperationsManager.getCurrentFileState() == FileStatus.SAVED)
                                FileOperationsManager.setCurrentFileState(FileStatus.MODIFIED);
                        }

                    }
                }
            }
        }
            //deleting figures
            for (DrawingFigure drawingFigure : figuresToDeletion)
                drawingFigures.remove(drawingFigure);
            DrawingPanel.getInstance().repaint();
    }
}