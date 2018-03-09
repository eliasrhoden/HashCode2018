import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliasr on 2018-03-01.
 */
public class RideOptimizer {

    public Ride[][] schedules;
    private int bonus;
    private final int DEEP_LVL = 5;
    private final int CARS_SIMULATNIOUSLY = 3;

    public RideOptimizer(int nrOfCars, int nrOfTimeSteps, int bonus){
        schedules = new Ride[nrOfCars][nrOfTimeSteps];
        this.bonus = bonus;
        System.out.println("No of cars: " + nrOfCars);
        System.out.println("No of timesteps: " + nrOfTimeSteps);
    }

    public List<Car> optimzeCarRoutes(List<Ride> rides){
        System.out.println("No of rides: " + rides.size());
        System.out.println("Starting rides optimizing!");

        Ride.sortByStartStep(rides);

        for(int car = 0;car < schedules.length;car++){

            List<BookingSequence> possibleBookings;
            List<Ride> ridesToUse = new LinkedList<>(rides);

            possibleBookings = findPossibleBookingSequnces(ridesToUse);
            Collections.sort(possibleBookings);

            bookSequence(possibleBookings.get(0),car);

        }
        return createCarsFromSchedule(schedules);
    }

    private void bookSequence(BookingSequence bookingSequence, int car) {
        for(Ride r:bookingSequence.rides){

        }
    }

    private List<BookingSequence> findPossibleBookingSequnces(List<Ride> ridesToUse) {

        return null;
    }


    public List<Car> createCarsFromSchedule(Ride[][] schedule){
        List<Car> result = new LinkedList<>();
        for(int car = 0;car <schedule.length;car++){
            Car c = new Car();
            int id = -1;
            for(int i = 0;i<schedule[0].length;i++){
                Ride r = schedule[car][i];
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

    public void bookCar(int car, Ride ride, int startOfRide){
        for(int i =startOfRide;i<startOfRide + ride.stepsRequired;i++){
            schedules[car][i] = ride;
        }
    }

    public static class BookingSequence implements Comparable{
        public List<Ride> rides;
        public int timeSteps;
        public int bonus;

        public BookingSequence(int timeSteps, int bonus){
            rides = new LinkedList<>();
            this.timeSteps = timeSteps;
            this.bonus = bonus;
        }

        public void addRide(Ride r){
            rides.add(r);
        }

        public int getPoints(){
            LinkedList<Ride> ridesLeftToStart = new LinkedList<>(rides);
            Ride current = ridesLeftToStart.pollFirst();
            int points = 0;
            for(int i = 0;i<timeSteps;i++){

                if(current == null)
                    break;
                if(current.START_STEP <= i){
                    if(current.START_STEP == i){
                        points+= bonus;
                    }
                    int end = i+current.stepsRequired;
                    if(end < current.END_STEP){
                        points+= current.stepsRequired;
                    }
                    i+= current.stepsRequired - 1;
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

    public static class CarScheduleAnalyzer{
        private Ride[] schedule;
        private boolean fits;
        private int firstStep;
        private List<TimeBlock> freeTime;


        public CarScheduleAnalyzer(Ride[] carShedule, Ride r){
            if(r.END_STEP - r.START_STEP < r.stepsRequired){
                fits = false;
                return;
            }
            fits = false;
            this.schedule = carShedule;
            freeTime = getFreeTime(carShedule);
            for(TimeBlock tb:freeTime){
                Position prePos = prevPos(tb.start);
                Position postPos = postPos(tb.end);
                int stepsToMoveToStartPos = prePos.stepsTo(r.startPosition);
                int stepToMoveToNextPos = 0;
                if(postPos != null){
                    stepToMoveToNextPos = r.endPosition.stepsTo(postPos);
                }
                int earliestBooking = tb.start + stepsToMoveToStartPos;
                int latestEnd = tb.end - stepToMoveToNextPos;
                if(earliestBooking <= r.START_STEP && r.START_STEP + r.stepsRequired + stepToMoveToNextPos <= tb.end){
                    fits = true;
                    firstStep = r.START_STEP;
                }
                if(earliestBooking >= r.START_STEP && earliestBooking + r.stepsRequired - 1 + stepToMoveToNextPos <= tb.end){
                    fits = true;
                    firstStep = earliestBooking;
                }
            }
        }

        private Position postPos(int end) {
            for(int i = end;i<schedule.length;i++){
                if(schedule[i] != null){
                    return schedule[i].startPosition;
                }
            }
            return null;
        }

        private Position prevPos(int start) {
            for(int i = start;i>=0;i--){
                if(schedule[i] != null){
                    return schedule[i].endPosition;
                }
            }
            return new Position(0,0);
        }

        public boolean rideFitsInschedule(){
            return fits;
        }

        public int getStartStep(){
            if(fits)
                return firstStep;
            else
                throw new RuntimeException("Ride doesn't fit in schedule, can't retrieve start step");
        }

        public static List<TimeBlock> getFreeTime(Ride[] schedule){
            List<TimeBlock> res = new LinkedList<>();
            int inARow = 0;
            int start = 0;
            for(int i = 0;i<schedule.length;i++){
                if(schedule[i]== null){
                    if(inARow == 0){
                        start = i;
                    }
                    inARow++;
                }else{
                    if(inARow > 1){
                        TimeBlock block = new TimeBlock(start, i-1);
                        res.add(block);
                    }
                    inARow = 0;
                }
            }
            if(inARow > 1){
                TimeBlock block = new TimeBlock(start, schedule.length-1);
                res.add(block);
            }

            return res;
        }

        static class TimeBlock{
            public int start;
            public int end;
            public TimeBlock(int start, int end){
                this.start = start; this.end = end;
            }

            @Override
            public boolean equals(Object o){
                if(o instanceof TimeBlock){
                    TimeBlock tb = (TimeBlock) o;
                    return start == tb.start && end == tb.end;
                }
                return false;
            }
        }
    }
}
