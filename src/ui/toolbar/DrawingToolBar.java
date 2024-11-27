package ui.toolbar;

import manager.DrawingManager;
import manager.FileOperationsManager;
import enums.FileStatus;
import enums.DrawingTools;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class DrawingToolBar extends JToolBar {
    private static JLabel drawingToolInfo;
    private static JLabel fileStateInfo;
    public static DrawingToolBar drawingToolBarInstance;

    public static DrawingToolBar getDrawingToolBarInstance() {
        if (drawingToolBarInstance == null)
            return new DrawingToolBar();
        return drawingToolBarInstance;
    }

    private DrawingToolBar() {
        Color whiteGreyColor = new Color(
                238,
                238,
                238
        );
        this.setBackground(whiteGreyColor);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        this.setLayout(new BorderLayout());
        drawingToolInfo = new JLabel();
        fileStateInfo = new JLabel();
        updateFileStateInfo();
        this.add(drawingToolInfo, BorderLayout.WEST);
        this.add(fileStateInfo, BorderLayout.EAST);
        drawingToolBarInstance = this;
    }

    static public void updateDrawToolInfo() {
        DrawingTools tool = DrawingManager.getTool();

        if (tool == DrawingTools.CIRCLE)
            drawingToolInfo.setText("Circle");
        else if (tool == DrawingTools.SQUARE)
            drawingToolInfo.setText("Square");
        else if (tool == DrawingTools.PEN)
            drawingToolInfo.setText("Pen");
    }

    public  void updateFileStateInfo(){
        FileStatus fileState = FileOperationsManager.getCurrentFileState();

        if (fileState == FileStatus.NEW)
            fileStateInfo.setText("New");
        else if (fileState == FileStatus.MODIFIED)
            fileStateInfo.setText("Modified");
        else if (fileState == FileStatus.SAVED)
            fileStateInfo.setText("Saved");
    }

}