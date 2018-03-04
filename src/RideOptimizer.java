
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliasr on 2018-03-01.
 */
public class RideOptimizer {

    public Ride[][] schedules;

    public RideOptimizer(int nrOfCars, int nrOfTimeSteps){
        schedules = new Ride[nrOfCars][nrOfTimeSteps];
        System.out.println("No of cars: " + nrOfCars);
        System.out.println("No of time steps: " + nrOfTimeSteps);
    }

    public List<Car> optimzeCarRoutes(List<Ride> rides){
        System.out.println("Starting rides optimizing!");

        int nrOfTimeSteps = schedules[0].length;

        for(int t = 0;t<nrOfTimeSteps;t++){
            final int step = t;
            List<Ride> ridesAllowedToStart = new LinkedList<>(rides);
            ridesAllowedToStart.removeIf((Ride r)->r.START_STEP > step);

            Ride.sortByStepsRequired(ridesAllowedToStart);
            int tot = ridesAllowedToStart.size();
            int inner = 0;
            for(Ride r:ridesAllowedToStart){
                int bestStart = Integer.MAX_VALUE;
                int bestCar = -1;
                boolean foundBest = false;
                   for(int car = 0; car < schedules.length;car++){
                       CarScheduleAnalyzer can = new CarScheduleAnalyzer(schedules[car],r);
                       if(can.rideFitsInschedule()){
                           int startStep = can.getStartStep();
                           if(startStep == t) {
                               bookCar(car, r, startStep);
                               rides.remove(r);
                               foundBest = true;
                               break;
                           }
                           if(startStep <= bestStart){
                               bestStart = startStep;
                               bestCar = car;
                           }
                       }
                   }
                   if(!foundBest && bestCar != -1) {
                       bookCar(bestCar, r, bestStart);
                       rides.remove(r);
                   }

                   inner++;
                System.out.print("\t");
                printProgress(inner,tot);
            }
            printProgress(t,nrOfTimeSteps);
        }
        return createCarsFromSchedule(schedules);
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
