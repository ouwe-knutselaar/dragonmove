package dragonmove;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import dragonmove.calibrate.MainPanel;
import dragonmove.config.Config;

import java.io.IOException;

public class Calibrate {

    Config config;

    public static void main(String [] argv) throws IOException {
        if(argv.length == 0)throw new IllegalArgumentException("Missing config file as argument");
        Calibrate calibrate = new Calibrate();
        calibrate.run(argv[0]);
    }

    public void run(String configfile) throws IOException {
        config = new Config(configfile);

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

        screen.startScreen();
        gui.addWindowAndWait(new MainPanel(config));
        screen.stopScreen();
    }

}
