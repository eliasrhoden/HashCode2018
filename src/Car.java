import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliasr on 2018-03-01.
 */
public class Car {

    private List<Ride> schedule;
    
    public Car(int id){
        schedule = new LinkedList<>();
    }

    public List<Ride> getRides(){
        return new LinkedList<>(schedule);
    }

    public void addRideToSchedule(Ride ride){
        schedule.add(ride);
    }

}
