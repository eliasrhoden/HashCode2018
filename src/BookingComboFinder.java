import java.util.LinkedList;
import java.util.List;

public class BookingComboFinder {

    public static int noOfCars;

    public static List<List<Integer>[]> getAllCombosOfBookingBetweenCars(int nrOfCars, List<Integer> rideIds){
        noOfCars = nrOfCars;
        List<Integer>[] scenarioSoFar = createEmptyScenario(nrOfCars);
        List<List<Integer>[]> result = new LinkedList<>();
        LinkedList<Integer> rides = new LinkedList<>(rideIds);
        recursive(rides,scenarioSoFar,result);
        return result;
    }

    private static List<Integer>[] createEmptyScenario(int nrOfCars) {
        List<Integer>[] emptyScenario = new List[nrOfCars];
        for(int i = 0;i<nrOfCars;i++){
            emptyScenario[i] = new LinkedList<>();
        }
        return emptyScenario;
    }

    public static void recursive(LinkedList<Integer> rides, List<Integer>[] scenarioSoFar, List<List<Integer>[]> result){
            for(int r:rides){
                for(int c = 0;c<noOfCars;c++){
                    List<Integer>[] scenario = cloneSenario(scenarioSoFar);
                    scenario[c].add(r);
                    LinkedList<Integer> ridesForNextLvl = new LinkedList<>(rides);
                    ridesForNextLvl.remove((Integer) r);
                    recursive(ridesForNextLvl,scenario,result);
                }
            }
        List<Integer>[] endScenario = cloneSenario(scenarioSoFar);
        result.add(endScenario);
    }

    private static List<Integer>[] cloneSenario(List<Integer>[] senario){
        List<Integer>[] result = new List[senario.length];
        for(int i = 0;i<result.length;i++){
            result[i] = new LinkedList<>(senario[i]);
        }
        return result;
    }


}
