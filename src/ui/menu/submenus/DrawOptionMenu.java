package ui.menu.submenus;

import manager.DrawingManager;
import manager.FileOperationsManager;
import ui.panel.DrawingPanel;
import ui.toolbar.DrawingToolBar;
import enums.FileStatus;
import enums.DrawingTools;

import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DrawOptionMenu extends JMenu implements ItemListener {

    private final JRadioButtonMenuItem circleOption;
    private final JRadioButtonMenuItem squareOption;
    private final JRadioButtonMenuItem penOption;

    public DrawOptionMenu() {
        this.setText("Draw");

        JMenu figureSubMenu = new JMenu("Figure");
        circleOption = new JRadioButtonMenuItem("Circle");
        squareOption = new JRadioButtonMenuItem("Square");
        penOption = new JRadioButtonMenuItem("Pen");

        ButtonGroup figureOptions = new ButtonGroup();
        figureOptions.add(circleOption);
        figureOptions.add(squareOption);
        figureOptions.add(penOption);

        figureSubMenu.add(circleOption);
        figureSubMenu.add(squareOption);
        figureSubMenu.add(penOption);

        JMenuItem colorOption = new JMenuItem("Color");

        JMenuItem clearOption = new JMenuItem("Clear");

        //mnemonics
        figureSubMenu.setMnemonic(MenuKeyEvent.VK_F);
        circleOption.setMnemonic(MenuKeyEvent.VK_C);
        squareOption.setMnemonic(MenuKeyEvent.VK_R);
        penOption.setMnemonic(MenuKeyEvent.VK_P);
        colorOption.setMnemonic(MenuKeyEvent.VK_C);
        clearOption.setMnemonic(MenuKeyEvent.VK_L);

        //accelerators
        circleOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_C, MenuKeyEvent.CTRL_DOWN_MASK));
        squareOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_R, MenuKeyEvent.CTRL_DOWN_MASK));
        penOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_E, MenuKeyEvent.CTRL_DOWN_MASK));
        colorOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_C, MenuKeyEvent.ALT_DOWN_MASK | MenuKeyEvent.SHIFT_DOWN_MASK));
        clearOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_N, MenuKeyEvent.CTRL_DOWN_MASK | MenuKeyEvent.SHIFT_DOWN_MASK));

        circleOption.addItemListener(this);
        squareOption.addItemListener(this);
        penOption.addItemListener(this);

        colorOption.addActionListener(e -> {
            if (DrawingManager.getTool() == DrawingTools.PEN) {
                Color currentColor = DrawingManager.getColor();
                DrawingManager.setColor(JColorChooser.showDialog(DrawingPanel.getInstance(), "Choose Color", currentColor));
            }
        });

        clearOption.addActionListener(e -> {
            DrawingManager.getFigures().clear();
            DrawingPanel.getInstance().repaint();
            if (FileOperationsManager.getCurrentFileState() == FileStatus.SAVED)
                FileOperationsManager.setCurrentFileState(FileStatus.MODIFIED);
            DrawingToolBar.getDrawingToolBarInstance().updateFileStateInfo();
        });

        this.add(figureSubMenu);
        this.add(colorOption);
        this.addSeparator();
        this.add(clearOption);
    }

    //radio boxes change
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();

        if (source == circleOption)
            DrawingManager.setTool(DrawingTools.CIRCLE);
        else if (source == squareOption)
            DrawingManager.setTool(DrawingTools.SQUARE);
        else if (source == penOption)
            DrawingManager.setTool(DrawingTools.PEN);

        DrawingToolBar.updateDrawToolInfo();
    }
}