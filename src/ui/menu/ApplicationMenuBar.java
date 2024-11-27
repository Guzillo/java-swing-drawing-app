package ui.menu;

import ui.menu.submenus.DrawOptionMenu;
import ui.menu.submenus.FileOptionMenu;

import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import java.awt.*;

public class ApplicationMenuBar extends JMenuBar{
    public ApplicationMenuBar() {
        FileOptionMenu fileOptionsMenu = new FileOptionMenu();
        DrawOptionMenu drawOptionMenu = new DrawOptionMenu();

        fileOptionsMenu.setMnemonic(MenuKeyEvent.VK_F);
        drawOptionMenu.setMnemonic(MenuKeyEvent.VK_D);

        this.setBackground(Color.LIGHT_GRAY);
        Color lightGreyColor = new Color(
                238,
                238,
                238
        );
        this.setBackground(lightGreyColor);

        this.add(fileOptionsMenu);
        this.add(drawOptionMenu);
    }
}
