package dragonmove.calibrate;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;

public abstract class BasePanel extends BasicWindow {

    CPanel panel = new CPanel();
    Button closePanel = new Button("close");


    public BasePanel(String title, int columns, TerminalSize terminalSize){
        setTitle(title);
        setFixedSize(terminalSize);

        closePanel.addListener(button -> close());
        panel.setLayoutManager(new GridLayout(columns));

        panel.addComponent(closePanel);
        setComponent(panel);
    }
}
