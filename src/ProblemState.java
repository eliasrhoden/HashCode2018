import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johwenns on 2018-03-01.
 */
public class ProblemState {
    public int row;
    public int col;
    public int cars;
    public int rides;
    public int bonus;
    public int steps;
    public List<Ride> rideList;

    public static ProblemState readProblemState(String fileName) {

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("input/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] infoLine;
        infoLine = lines.get(0).split(" ");


        int Arow = Integer.parseInt(infoLine[0]);
        int Acol = Integer.parseInt(infoLine[1]);
        int Acar = Integer.parseInt(infoLine[2]);
        int Aride = Integer.parseInt(infoLine[3]);
        int Abonus = Integer.parseInt(infoLine[4]);
        int Asteps = Integer.parseInt(infoLine[5]);




        lines.remove(0);
        String[] posInfo;
        List<Ride> ArideList = new ArrayList<>();
        int counter = 0;
        for(String currentString : lines){
            posInfo = currentString.split(" ");
            Position start = new Position(Integer.parseInt(posInfo[0]),Integer.parseInt(posInfo[1]));
            Position finish = new Position(Integer.parseInt(posInfo[2]),Integer.parseInt(posInfo[3]));
            Ride ride = new Ride(counter, start,finish,Integer.parseInt(posInfo[4]),Integer.parseInt(posInfo[5]));
            ArideList.add(ride);
            counter++;
        }


        ProblemState problemState = new ProblemState(Arow, Acol, Acar, Aride, Abonus, Asteps, ArideList);
        return problemState;
    }

    public ProblemState(int a, int b, int c, int d, int e, int f, List<Ride> g) {
        row = a;
        col = b;
        cars = c;
        rides = d;
        bonus = e;
        steps = f;
        rideList = g;
    }

}
