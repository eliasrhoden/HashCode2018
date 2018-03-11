import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;


public class OutputMaker {

    static String outPut = "";

    public static void createOutput(List<Car> cars, String fileName){
        PrintWriter out = null;
        try {
            out = new PrintWriter("output/" + fileName +".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(Car c:cars){
            outPut = Integer.toString(c.getRides().size()) + " ";
            for(Ride r:c.getRides()){
                outPut += r.ID + " ";
            }
            outPut += "\n";
            out.print(outPut);
        }
        out.flush();
    }

}
