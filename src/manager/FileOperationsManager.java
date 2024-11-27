package manager;

import model.DrawingFigure;
import ui.panel.DrawingPanel;
import ui.toolbar.DrawingToolBar;
import enums.FileStatus;
import application.PaintApplication;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

public class FileOperationsManager {
    private static FileStatus currentFileState = FileStatus.NEW;
    private static File activeFile;

    public static FileStatus getCurrentFileState() {
        return currentFileState;
    }

    public static void setCurrentFileState(FileStatus currentFileState) {
            FileOperationsManager.currentFileState = currentFileState;
            DrawingToolBar.getDrawingToolBarInstance().updateFileStateInfo();
    }

    @SuppressWarnings("unchecked")
    public static void loadFile() {
        JFileChooser jFileChooser = new JFileChooser();
        DrawingPanel panel = DrawingPanel.getInstance();

        int returnVal = jFileChooser.showOpenDialog(panel);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
             try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                DrawingManager.setFigures( (ArrayList<DrawingFigure>) ois.readObject() );
                panel.repaint();
                activeFile = file;
                currentFileState = FileStatus.SAVED;
                DrawingToolBar.getDrawingToolBarInstance().updateFileStateInfo();
                PaintApplication.applicationFrame.setTitle("Simple Draw: " + file.getName());
                ois.close();
            } catch (IOException | ClassCastException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(DrawingPanel.getInstance(), "There is a problem with chosen file, it should be .iml");
            }
        }
    }

    public static void save() {
        if (activeFile != null) {
            saveFile(activeFile);
        } else
            saveAs();
    }

    public static void saveAs() {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".iml files", "iml");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setSelectedFile(new File("change_me.iml"));

        int returnVal = jFileChooser.showOpenDialog(DrawingPanel.getInstance());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            if (selectedFile.exists()){
                int answer = JOptionPane.showConfirmDialog(DrawingPanel.getInstance(),
                        "Do you want to replace the existing file?");
                if (answer == JOptionPane.NO_OPTION){
                    return;
                }
            }
            activeFile = jFileChooser.getSelectedFile();
            save();
        }
    }

    private static void saveFile(File file) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(DrawingManager.getFigures());
            currentFileState = FileStatus.SAVED;
            DrawingToolBar.getDrawingToolBarInstance().updateFileStateInfo();
            oos.close();
            PaintApplication.applicationFrame.setTitle("Simple Draw: " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(DrawingPanel.getInstance(), "There is a problem with chosen file, it should be .ser");
        }
    }

    public static void quit() {
        if (currentFileState != FileStatus.SAVED) {
            int answer = JOptionPane.showConfirmDialog(DrawingPanel.getInstance(),
                    "Do you want to save this file?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION)
                save();
        }
        System.exit(0);

    }

}