package dragonmove.calibrate;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Window;
import java.util.Collections;

public abstract class BasePanel extends BasicWindow {

    CPanel panel = new CPanel();
    Button closePanel = new Button("close");


    public BasePanel(String title, int columns){
        setTitle(title);
        setHints(Collections.singletonList(Window.Hint.FULL_SCREEN));

        closePanel.addListener(button -> closePanel());
        panel.setLayoutManager(new GridLayout(columns));

        panel.addComponent(closePanel);
        setComponent(panel);
    }

    protected void closePanel(){
        close();
    }
}
