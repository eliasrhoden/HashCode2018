import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliasr on 2018-03-01.
 */
public class RideOptimizer {

    public static List<Ride>[] schedules;
    private int bonus;
    private final int DEEP_LVL = 5;
    private final int CARS_SIMULATNIOUSLY = 5;
    private final int TIMESTEPS;
    private final int MAX_RIDE_SUB_LIST = 5;
    private final int NR_OF_CARS;


    public RideOptimizer(int nrOfCars, int nrOfTimeSteps, int bonus){
        schedules = new List[nrOfCars];
        NR_OF_CARS = nrOfCars;
        for(int i = 0;i<schedules.length;i++)
            schedules[i] = new LinkedList<>();
        TIMESTEPS =nrOfTimeSteps;
        this.bonus = bonus;
        System.out.println("No of cars: " + nrOfCars);
        System.out.println("No of timesteps: " + nrOfTimeSteps);
    }

    public List<Car> optimzeCarRoutes(List<Ride> rides){
        System.out.println("No of rides: " + rides.size());
        System.out.println("Starting rides optimizing!");


        boolean ridesLeft = true;

        while(ridesLeft){
            for(int i = 0;i<NR_OF_CARS;i++) {
                List<Scenario> possibleBookingScenarios;
                Ride.sortByStartStep(rides);
                int endSUbList = MAX_RIDE_SUB_LIST;
                int ridesSiz = rides.size();
                if (ridesSiz < MAX_RIDE_SUB_LIST)
                    endSUbList = ridesSiz;

                List<Ride> ridesToUse = new LinkedList<>(rides.subList(0, endSUbList));
                int subNrOfCars = CARS_SIMULATNIOUSLY;
                if (i + subNrOfCars >= NR_OF_CARS)
                    subNrOfCars = NR_OF_CARS - i;
                possibleBookingScenarios = generateBookingScenarios(ridesToUse, subNrOfCars, i);
                Collections.sort(possibleBookingScenarios);

                for (BookingSequence sec : possibleBookingScenarios.get(0).carsheduels)
                    bookSequence(sec, rides);
                i += subNrOfCars - 1;

                int noOfRides = rides.size();
                System.out.println("No of rides left: " + noOfRides);
                if (noOfRides <= 0)
                    ridesLeft = false;
            }

        }
        return createCarsFromSchedule(schedules);
    }

    private void bookSequence(BookingSequence bookingSequence, List<Ride> rideList) {
        int car = bookingSequence.car;
        for(Ride r:bookingSequence.rides){
            schedules[car].add(r);
            rideList.remove(r);
        }
    }


    private List<Scenario> generateBookingScenarios(List<Ride> ridesToUse, int nrOfCars, int carOffset) {
        List<Scenario> scenarios = new LinkedList<>();
        List<List<Ride>[]> rideCombos = BookingComboFinder.getAllCombosOfBookingBetweenCars(nrOfCars,ridesToUse);
        //int tot = rideCombos.size();
        int i = 0;
        for(List<Ride>[] scenInInteger:rideCombos){
            Scenario scenario = getScenarioFromRideList(scenInInteger, carOffset);
            scenarios.add(scenario);
            //i++;
            //printProgress(i,tot);
        }

        return scenarios;
    }

    private Scenario getScenarioFromRideList(List<Ride>[] scenInInteger, int carOffset) {
        Scenario scenario = new Scenario();
        for(int car = 0;car < scenInInteger.length;car++){
            LinkedList<Ride> carShedule = new LinkedList<>(scenInInteger[car]);
            BookingSequence bookSch = new BookingSequence(TIMESTEPS,bonus,car + carOffset);
            for(Ride r:carShedule){
                bookSch.addRide(r);
            }
            scenario.carsheduels.add(bookSch);
        }
        return scenario;
    }

    private List<Integer> getRideIds(List<Ride> ridesToUse) {
        List<Integer> result = new LinkedList<>();
        for(Ride r:ridesToUse){
            result.add(r.ID);
        }
        return result;
    }


    public List<Car> createCarsFromSchedule(List<Ride>[] schedule){
        List<Car> result = new LinkedList<>();
        for(int car = 0;car <schedule.length;car++){
            Car c = new Car();
            int id = -1;
            for(Ride r:schedule[car]){
                if(r != null && r.ID != id){
                    c.addRideToSchedule(r);
                    id = r.ID;
                }
            }
            result.add(c);
        }
        return result;
    }

    private void printProgress(int t, int nrOfTimeSteps) {
        System.out.println(t*100.0/nrOfTimeSteps + " %");
    }


    public static class Scenario implements Comparable{

        public List<BookingSequence> carsheduels;

        public Scenario(){
            carsheduels = new LinkedList<>();
        }

        public int getPoints(){
            int p = 0;
            for(BookingSequence b:carsheduels){
                p += b.getPoints();
            }
            return p;
        }

        @Override
        public int compareTo(Object o) {
            Scenario other = (Scenario) o;
            return other.getPoints() - getPoints();
        }
    }

    public static class BookingSequence implements Comparable{
        public List<Ride> rides;
        public int timeSteps;
        public int bonus;
        public int car;

        public BookingSequence(int timeSteps, int bonus, int car){
            rides = new LinkedList<>();
            this.timeSteps = timeSteps;
            this.bonus = bonus;
            this.car = car;
        }

        @Override
        public BookingSequence clone(){
            BookingSequence clone = new BookingSequence(timeSteps,bonus,car);
            clone.rides = new LinkedList<>(rides);
            return clone;
        }

        public void addRide(Ride r){
            rides.add(r);
        }

        public int getPoints(){
            LinkedList<Ride> ridesLeftToStart = new LinkedList<>();
            ridesLeftToStart.addAll(rides);

            Ride current = ridesLeftToStart.pollFirst();
            int points = 0;
            Position pos = new Position(0,0);
            boolean moved = false;
            for(int i = 0;i<timeSteps;i++){
                if(current == null)
                    break;
                if(!moved) {
                    int stepsToStart = pos.stepsTo(current.startPosition);
                    i += stepsToStart - 1;
                    moved = true;
                }
                if(current.START_STEP <= i){
                    if(current.START_STEP == i){
                        points+= bonus;
                    }
                    int end = i+current.stepsRequired;
                    if(end < current.END_STEP){
                        points+= current.stepsRequired;
                    }
                    i+= current.stepsRequired - 1;
                    moved = false;
                    pos = current.endPosition;
                    current = ridesLeftToStart.pollFirst();
                }
            }
            return points;
        }

        @Override
        public int compareTo(Object o) {
            BookingSequence other = (BookingSequence) o;
            return  other.getPoints()  -getPoints();
        }
    }

}
