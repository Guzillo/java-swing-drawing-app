package application;

import ui.menu.ApplicationMenuBar;
import ui.panel.DrawingPanel;
import ui.toolbar.DrawingToolBar;

import javax.swing.*;
import java.awt.*;

public class PaintApplication {
    private static ApplicationMenuBar applicationMenuBar;
    private static DrawingPanel drawingPanel;
    private static DrawingToolBar drawingToolBar;
    public static JFrame applicationFrame;

    public PaintApplication() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                ()-> {
                    applicationFrame = new JFrame("Simple Draw");
                    applicationMenuBar = new ApplicationMenuBar();
                    drawingPanel = new DrawingPanel();
                    drawingToolBar = DrawingToolBar.getDrawingToolBarInstance();

                    applicationFrame.setLayout(new BorderLayout());
                    applicationFrame.setResizable(false);
                    applicationFrame.setJMenuBar(applicationMenuBar);
                    applicationFrame.add(drawingPanel, BorderLayout.CENTER);
                    applicationFrame.add(drawingToolBar, BorderLayout.SOUTH);

                    applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    applicationFrame.pack();
                    applicationFrame.setVisible(true);
                }
        );
    }
}
