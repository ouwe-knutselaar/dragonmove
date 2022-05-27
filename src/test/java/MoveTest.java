import dragonmove.config.Config;
import dragonmove.data.ServoTable;
import dragonmove.move.HeadOnlyMove;
import dragonmove.move.ServoMove;
import dragonmove.timer.TableExecuter;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

@Log
public class MoveTest {

    @Test
    public void makeSingleMoveTest() {
        Random rand = new Random();
        for (int tel = 0; tel < 1000; tel++) {
            int target = rand.nextInt(4096);
            int test[] = ServoMove.makeMove(2048, target, 100);
            assertEquals(100, test.length,"Size of the move is correct");
            assertEquals(target, test[99],"Values of the move is equal");
            //System.out.println("c:" + tel + "\ttarget " + target + "\tresult " + test[99] + "\tprev " + test[98]);
        }
    }

    @Test
    public void addMoveToTableTest() {
        ServoTable testTable = new ServoTable();
        Random rand = new Random();
        for (int tel = 0; tel < 10; tel++) {
            int test[] = new int[16];
            for (int count = 0; count < 16; count++) {
                test[count] = rand.nextInt(4096);
            }
            testTable.addRecord(test);
        }
        int test[] = ServoMove.makeMove(100, 4096, 15);
        for (int tel = 0; tel < 15; tel++) {
            testTable.modServo(tel, 0, test[tel]);
        }
        assertEquals(4096, testTable.getRecord(14).getServoValueList()[0].getValue());
    }

    @Test
    public void HeadOnlyLoadTest(){
        Config config = ConfigRead.readConfig();
        HeadOnlyMove headOnlyMove = new HeadOnlyMove(config);
        assertTrue(headOnlyMove.getServoNamesList().contains("nekturn"));
        assertTrue(headOnlyMove.getServoNamesList().contains("nekright"));
        assertTrue(headOnlyMove.getServoNamesList().contains("nekleft"));
    }

    @Test
    public void HeadOnlyMovementTestGetTableTest(){
        Config config = ConfigRead.readConfig();
        HeadOnlyMove headOnlyMove = new HeadOnlyMove(config);
        int nekturnNewPos = config.getServoByName("nekturn").generateNewRandomPosition();
        int nekleftNewPos = config.getServoByName("nekleft").generateNewRandomPosition();
        int nekrightNewPos = config.getServoByName("nekright").generateNewRandomPosition();
        ServoTable testTable = headOnlyMove.makeNewMovement(
                nekturnNewPos,
                nekleftNewPos,
                nekrightNewPos
        );
        assertEquals(nekturnNewPos,testTable.getValueFromSpecificRecord(99,1));
        assertEquals(nekleftNewPos,testTable.getValueFromSpecificRecord(99,2));
        assertEquals(nekrightNewPos,testTable.getValueFromSpecificRecord(99,3));
    }

    @Test
    public void HeadOnlyMovementExecuteRunTest(){
        Config config = ConfigRead.readConfig();
        HeadOnlyMove headOnlyMove = new HeadOnlyMove(config);

        int nekturnNewPos = config.getServoByName("nekturn").generateNewRandomPosition();
        int nekleftNewPos = config.getServoByName("nekleft").generateNewRandomPosition();
        int nekrightNewPos = config.getServoByName("nekright").generateNewRandomPosition();
        ServoTable testTable = headOnlyMove.makeNewMovement(
                nekturnNewPos,
                nekleftNewPos,
                nekrightNewPos
        );
        TableExecuter tableExecuter = new TableExecuter(testTable,config);
        tableExecuter.run();
        log.info("First run");
        log.info("nekturnNewPos:"+nekturnNewPos+" nekleftNewPos:"+nekleftNewPos+" nekrightNewPos:"+nekrightNewPos);
        for(int tel=0;tel<16;tel++)log.info(config.getServoByNumber(tel).toString());

        nekturnNewPos = config.getServoByName("nekturn").generateNewRandomPosition();
        nekleftNewPos = config.getServoByName("nekleft").generateNewRandomPosition();
        nekrightNewPos = config.getServoByName("nekright").generateNewRandomPosition();
        testTable = headOnlyMove.makeNewMovement(
                nekturnNewPos,
                nekleftNewPos,
                nekrightNewPos
        );
        tableExecuter = new TableExecuter(testTable,config);
        tableExecuter.run();

        assertEquals(nekturnNewPos,config.getServoByName("nekturn").getCurrentPosition());
        log.info("Second run");
        log.info("nekturnNewPos:"+nekturnNewPos+" nekleftNewPos:"+nekleftNewPos+" nekrightNewPos:"+nekrightNewPos);
        for(int tel=0;tel<16;tel++)log.info(config.getServoByNumber(tel).toString());
    }

    @Test
    public void HeadOnlyMoveToHomePositionTest(){
        Config config = ConfigRead.readConfig();
        HeadOnlyMove headOnlyMove = new HeadOnlyMove(config);
        ServoTable testTable = headOnlyMove.toHomePosition();
        assertNotEquals(0,testTable.getValueFromSpecificRecord(99,1));
        assertNotEquals(0,testTable.getValueFromSpecificRecord(99,2));
        assertNotEquals(0,testTable.getValueFromSpecificRecord(99,3));
    }

}
