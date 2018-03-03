
/**
 * Created by eliasr on 2018-03-01.
 */
public class Ride {

    public final Position startPosition;
    public final Position endPosition;
    public final int stepsRequired;
    public final int START_STEP;
    public final int END_STEP;
    public final int ID;

    public Ride(int ID, Position start, Position end, int startStep, int endStep){
        this.ID = ID;
        this.startPosition = start;
        this.endPosition = end;
        this.stepsRequired = start.stepsTo(end);
        this.START_STEP = startStep;
        this.END_STEP = endStep;
    }
}
