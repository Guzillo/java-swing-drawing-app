package ui.menu.submenus;

import manager.FileOperationsManager;

import javax.swing.*;
import javax.swing.event.MenuKeyEvent;

public class FileOptionMenu extends JMenu {

    public FileOptionMenu() {
        this.setText("File");

        JMenuItem openOption = new JMenuItem("Open");
        JMenuItem saveOption = new JMenuItem("Save");
        JMenuItem saveAsOption = new JMenuItem("Save As...");
        JMenuItem quitOption = new JMenuItem("Quit");

        openOption.setMnemonic(MenuKeyEvent.VK_O);
        saveOption.setMnemonic(MenuKeyEvent.VK_S);
        saveAsOption.setMnemonic(MenuKeyEvent.VK_A);
        quitOption.setMnemonic(MenuKeyEvent.VK_Q);

        openOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_O, MenuKeyEvent.CTRL_DOWN_MASK));
        saveOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_S, MenuKeyEvent.CTRL_DOWN_MASK));
        saveAsOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_S, MenuKeyEvent.CTRL_DOWN_MASK | MenuKeyEvent.SHIFT_DOWN_MASK));
        quitOption.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_S, MenuKeyEvent.CTRL_DOWN_MASK));

        openOption.addActionListener(e -> {
            FileOperationsManager.loadFile();});

        saveOption.addActionListener(e-> {
            FileOperationsManager.save();});

        saveAsOption.addActionListener(e-> {
            FileOperationsManager.saveAs();});

        quitOption.addActionListener(e-> {
            FileOperationsManager.quit();});

        this.add(openOption);
        this.add(saveOption);
        this.add(saveAsOption);
        this.addSeparator();
        this.add(quitOption);
    }
}
