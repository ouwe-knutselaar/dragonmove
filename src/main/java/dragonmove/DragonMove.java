package dragonmove;

import dragonmove.config.Config;
import dragonmove.data.ServoTable;
import dragonmove.move.HeadOnlyMove;
import dragonmove.timer.TableExecuter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DragonMove {

    Logger log = Logger.getLogger("DragonMove");
    Config config;

    public DragonMove(String configfile) {
        config = new Config(configfile);
        log.info("Load config from "+configfile);

        if(config.getDebug().equals("DEBUG"))config.setLevel(Level.DEBUG);
        if(config.getDebug().equals("TRACE"))config.setLevel(Level.TRACE);

        log.setLevel(config.getLevel());
        log.debug("Loglevel is at debug");
        log.trace("Loglevel is at trace");
    }

    public static void main(String []argv){
        if(argv.length == 0)throw new IllegalArgumentException("Missing config file as argument");
        DragonMove dragonMove = new DragonMove(argv[0]);
        dragonMove.start();
    }

    private void start() {
        log.info("Start dragon");
        HeadOnlyMove headOnlyMove = new HeadOnlyMove(config);
        while(true){
            log.info("Start new move");
            ServoTable tempTable = headOnlyMove.makeNewMovement();
            TableExecuter tableExecuter = new TableExecuter(tempTable,config);
            tableExecuter.run();
        }
    }


}
