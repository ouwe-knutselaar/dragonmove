import com.fasterxml.jackson.core.JsonProcessingException;
import dragonmove.data.ServoTable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class DataTest {

    @Test
    public void AddRecordTest()  {
        ServoTable testTable= new ServoTable();

        int test[] = {0,1,2,3,4,5,6,7};
        testTable.addRecord(test);

        int test2[]= {3,14,434,113,366,345,64,212};
        testTable.addRecord(test2);

        int test3[]={543,234,632,4,35,4,7,5,};
        testTable.addRecord(test3);

        assertEquals(3,testTable.getNumberOfRecord());
    }

    @Test
    public void fillTableUpTest(){
        ServoTable testTable= new ServoTable();
        Random rand = new Random();
        for(int tel=0;tel<100;tel++){
            int test[]=new int[16];
            for(int count=0;count<16;count++){
                test[count]=rand.nextInt(4096);
            }
            testTable.addRecord(test);
        }
        assertEquals(100,testTable.getNumberOfRecord());
    }

    @Test
    public void dumpTest() throws JsonProcessingException {
        ServoTable testTable= new ServoTable();
        Random rand = new Random();
        for(int tel=0;tel<100;tel++){
            int test[]=new int[16];
            for(int count=0;count<16;count++){
                test[count]=rand.nextInt(4096);
            }
            testTable.addRecord(test);
        }
        //testTable.dumpTable();
    }

    @Test
    public void saveTableTest() throws IOException {
        String saveFile = "/home/gebruiker/IdeaProjects/dragonmove/src/test/resources/testout.yaml";
        Files.delete(Paths.get(saveFile));
        ServoTable testTable= new ServoTable();
        Random rand = new Random();
        for(int tel=0;tel<100;tel++){
            int test[]=new int[16];
            for(int count=0;count<16;count++){
                test[count]=rand.nextInt(4096);
            }
            testTable.addRecord(test);
        }
        assertDoesNotThrow(()-> testTable.saveTable(saveFile));
    }

    @Test
    public void saveAndLoadTableTest() throws IOException {
        String saveFile = "/home/gebruiker/IdeaProjects/dragonmove/src/test/resources/testout.yaml";
        Files.delete(Paths.get(saveFile));
        ServoTable testTable= new ServoTable();
        Random rand = new Random();
        for(int tel=0;tel<100;tel++){
            int test[]=new int[16];
            for(int count=0;count<16;count++){
                test[count]=rand.nextInt(4096);
            }
            testTable.addRecord(test);
        }
        testTable.saveTable(saveFile);

        ServoTable loadTable= new ServoTable();
        assertDoesNotThrow(()->loadTable.loadTable(saveFile));
        assertEquals(100,loadTable.getNumberOfRecord());
    }
}
