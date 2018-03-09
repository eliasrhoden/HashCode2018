import java.util.Collections;
import java.util.List;

/**
 * Created by eliasr on 2018-03-01.
 */
public class Ride implements Comparable {

    public static SortBy sort;
    public final Position startPosition;
    public final Position endPosition;
    public final int stepsRequired;
    public final int START_STEP;
    public final int END_STEP;
    public final int ID;

    private enum SortBy{
        startStep, stepsRequired, endStep;
    }


    public Ride(int ID, Position start, Position end, int startStep, int endStep){
        this.ID = ID;
        this.startPosition = start;
        this.endPosition = end;
        this.stepsRequired = start.stepsTo(end);
        this.START_STEP = startStep;
        this.END_STEP = endStep;
    }


    public static void sortByStartStep(List<Ride> list){
        sort = SortBy.startStep;
        Collections.sort(list);
    }

    public static void sortByStepsRequired(List<Ride> list){
        sort = SortBy.stepsRequired;
        Collections.sort(list);
    }

    public static void sortByEndStep(List<Ride> list){
        sort = SortBy.endStep;
        Collections.sort(list);
    }


    @Override
    public int compareTo(Object o) {
        Ride other = (Ride) o;
        switch (sort){
            case startStep:
               return START_STEP - other.START_STEP;
            case stepsRequired:
                return  stepsRequired - other.stepsRequired;
            case endStep:
                return  END_STEP - other.END_STEP;
            default:
        }
        return START_STEP - other.START_STEP;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Ride))
            return false;
        Ride other = (Ride) o;

        return ID == other.ID &&
                startPosition.equals(other.startPosition) &&
                endPosition.equals(other.endPosition) &&
                stepsRequired == other.stepsRequired &&
                START_STEP == other.START_STEP &&
                END_STEP == other.END_STEP;
    }

    @Override
    public String toString(){
        String res = "Ride id:"+ ID + " ";
        res += "StartStep: " + startPosition.toString() + " ";
        res += "EndStep: " + endPosition.toString() + " ";
        res += "No of steps: " + stepsRequired + " ";

        return res;
    }
}
