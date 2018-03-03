import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliasr on 2018-03-01.
 */
public class RideOptimizer {

    public Ride[][] schedules;

    public RideOptimizer(int nrOfCars, int nrOfTimeSteps){
        schedules = new Ride[nrOfCars][nrOfTimeSteps];
    }

    public List<Car> optimzeCarRoutes(List<Ride> rides){
        System.out.println("Starting rides optimizing!");

        int nrOfTimeSteps = schedules[0].length;

        for(int t = 0;t<nrOfTimeSteps;t++){
            final int step = t;
            List<Ride> ridesAllowedToStart = new LinkedList<>(rides);
            ridesAllowedToStart.removeIf((Ride r)->r.START_STEP != step);

            Ride.sortByStepsRequired(ridesAllowedToStart);

            for(Ride r:ridesAllowedToStart){
                   for(int car = 0; car < schedules.length;car++){
                       CarScheduleAnalyzer can = new CarScheduleAnalyzer(schedules[car],r);

                       if(can.rideFitsInschedule()){
                           int startStep = can.getStartStep();
                           bookCar(car,r,startStep);
                           rides.remove(r);
                       }
                   }
            }
            printProgress(t,nrOfTimeSteps);
        }
        return null;
    }

    private void printProgress(int t, int nrOfTimeSteps) {
        System.out.println(t*100.0/nrOfTimeSteps + " %");
    }

    public void bookCar(int car, Ride ride, int startOfRide){

    }

    public static class CarScheduleAnalyzer{
        private Ride[] schedule;

        public CarScheduleAnalyzer(Ride[] carShedule, Ride r){

        }

        public boolean rideFitsInschedule(){
            return false;
        }

        public int getStartStep(){
            return 0;
        }
    }
}
