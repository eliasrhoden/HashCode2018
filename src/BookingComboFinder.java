import java.util.LinkedList;
import java.util.List;

public class BookingComboFinder {

    public static int noOfCars;
    public static boolean first = true;
    public static boolean second = true;
    public static int nrOfRIdes;
    public static int ind = 0;
    public static int tot;

    public static List<List<Ride>[]> getAllCombosOfBookingBetweenCars(int nrOfCars, List<Ride> rideIds){
        noOfCars = nrOfCars;
        nrOfRIdes = rideIds.size();
        List<Ride>[] scenarioSoFar = createEmptyScenario(nrOfCars);
        List<List<Ride>[]> result = new LinkedList<>();
        LinkedList<Ride> rides = new LinkedList<>(rideIds);
        recursive(rides,scenarioSoFar,result);
        return result;
    }

    private static List<Ride>[] createEmptyScenario(int nrOfCars) {
        List<Ride>[] emptyScenario = new List[nrOfCars];
        for(int i = 0;i<nrOfCars;i++){
            emptyScenario[i] = new LinkedList<>();
        }
        return emptyScenario;
    }

    public static void recursive(LinkedList<Ride> rides, List<Ride>[] scenarioSoFar, List<List<Ride>[]> result){
            for(Ride r:rides){
                for(int c = 0;c<noOfCars;c++){
                    List<Ride>[] scenario = cloneSenario(scenarioSoFar);
                    scenario[c].add(r);
                    LinkedList<Ride> ridesForNextLvl = new LinkedList<>(rides);
                    ridesForNextLvl.remove( r);
                    recursive(ridesForNextLvl,scenario,result);
                }
                /*
                if(rides.size() == nrOfRIdes){
                    ind++;
                    printProgress(ind,tot);
                }
                */

            }
        List<Ride>[] endScenario = cloneSenario(scenarioSoFar);
        result.add(endScenario);
    }

    private static void printProgress(int t, int nrOfTimeSteps) {
        System.out.println(t*100.0/nrOfTimeSteps + " %");
    }


    private static List<Ride>[] cloneSenario(List<Ride>[] senario){
        List<Ride>[] result = new List[senario.length];
        for(int i = 0;i<result.length;i++){
            result[i] = new LinkedList<>(senario[i]);
        }
        return result;
    }


}
