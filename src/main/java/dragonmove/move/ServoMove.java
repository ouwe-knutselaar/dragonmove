package dragonmove.move;

public class ServoMove {

    public static int[] makeMove(int begin, int end, int steps){
        int []result = new int[steps];
        double slope=(end-begin)/(double)(steps-1);
        for(int tel=0;tel<steps;tel++){
            result[tel]=(int)(begin+(tel*slope));
        }
        result[steps-1]=end;     // Correctie for round
        return result;
    }
}
