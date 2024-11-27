package model;

import enums.DrawingTools;

import java.awt.*;
import java.io.Serializable;

public class DrawingFigure implements Serializable {
    private Point lastPoint;
    private Point currentPoint;
    private Point initialPoint;
    private final Color figureColor;
    private final DrawingTools drawingTool;
    private  int figureSize;

    public DrawingFigure(Color figureColor, Point lastPoint, Point currentPoint, DrawingTools drawingTool) {
        this.figureColor = figureColor;
        this.lastPoint = lastPoint;
        this.currentPoint = currentPoint;
        this.drawingTool = drawingTool;
    }
    //hello
    public DrawingFigure(Color figureColor, Point initialPoint, DrawingTools drawingTool) {
        this.figureColor = figureColor;
        this.initialPoint = initialPoint;
        this.drawingTool = drawingTool;
    }

    public  int getFigureSize() {
        return figureSize;
    }

    public void setFigureSize(int figureSize) {
        this.figureSize = figureSize;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public Point getLastPoint() {
        return lastPoint;
    }

    public Point getInitialPoint() {
        return initialPoint;
    }

    public Color getFigureColor() {
        return figureColor;
    }

    public DrawingTools getDrawingTool() {
        return drawingTool;
    }

}
