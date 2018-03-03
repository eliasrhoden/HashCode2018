import org.junit.Test;
import static org.junit.Assert.*;

public class RideOptimizerTest {

    @Test
    public void testBooking(){
        Ride r1 = new Ride(
                2,
                new Position(0,0),
                new Position(0,5),
                1,10);
        RideOptimizer opt = new RideOptimizer(2,11);

        opt.bookCar(0,r1,2);

        assertEquals(null,opt.schedules[0][0]);
        assertEquals(null,opt.schedules[0][6]);
        assertEquals(null,opt.schedules[1][3]);

        for(int i = 1;i<5;i++){
            Ride br = opt.schedules[0][i];
            assertEquals(r1,br);
        }
    }

    @Test
    public void testAnalyzer(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,2),
                new Position(0,5),
                1, 8);

        for(int i = 0;i<2;i++){
            schedule[i] = r1;
        }

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r2);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(2, analyzer.getStartStep());

    }

    @Test
    public void testAnalyzer2(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                4, 8);

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r1);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(4, analyzer.getStartStep());
    }

    @Test
    public void testAnalyzer3(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,8),
                new Position(0,5),
                10, 13);
        Ride r3 = new Ride(
                3,
                new Position(0,3),
                new Position(0,8),
                1, 8);

        for(int i = 0;i<2;i++){
            schedule[i] = r1;
        }

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r2);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(2, analyzer.getStartStep());

    }


}