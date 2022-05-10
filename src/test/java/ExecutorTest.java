import dragonmove.config.Config;
import dragonmove.data.ServoTable;
import dragonmove.timer.TableExecuter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class ExecutorTest {

    @Test
    public void runThreadTest(){
        Config config = ConfigRead.readConfig();
        ServoTable testTable= new ServoTable();
        Random rand = new Random();
        for(int tel=0;tel<100;tel++){
            int test[]=new int[16];
            for(int count=0;count<16;count++){
                test[count]=rand.nextInt(4096);
            }
            testTable.addRecord(test);
        }
        TableExecuter tableExecuter = new TableExecuter(testTable,config);
        Thread testThread=new Thread(tableExecuter);
        testThread.start();
        try {
            Thread.sleep(2050);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test if thread is running");
        assertFalse(tableExecuter.isRunning(),"Thread stopped normally");
    }

    @Test
    public void runThreadTestWithBreaTest(){
        Config config = ConfigRead.readConfig();
        ServoTable testTable= new ServoTable();
        Random rand = new Random();
        for(int tel=0;tel<100;tel++){
            int test[]=new int[16];
            for(int count=0;count<16;count++){
                test[count]=rand.nextInt(4096);
            }
            testTable.addRecord(test);
        }
        TableExecuter tableExecuter = new TableExecuter(testTable,config);
        Thread testThread=new Thread(tableExecuter);
        testThread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tableExecuter.stop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test if thread is running");
        assertFalse(tableExecuter.isRunning(),"Thread stopped normally");
    }
}
