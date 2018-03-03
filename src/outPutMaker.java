import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;


public class outPutMaker {

    static String outPut = "";

    public static void createOutput(List<Car> cars){
        PrintWriter out = null;
        try {
            out = new PrintWriter("output/carOutput.txt");
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

//    public static int getPoints(List<Car> cars) {
//        int points = 0;
//        for (Car c : cars) {
//            points += (c. + 1 - s.startRow) * (s.endColumn + 1 - s.startColumn);
//        }
//        return points;
//    }
}
