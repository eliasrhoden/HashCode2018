import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RideOptimizerTest {

    @Test
    public void testBooking(){
        Ride r1 = new Ride(
                2,
                new Position(0,0),
                new Position(0,5),
                1,10);
        RideOptimizer opt = new RideOptimizer(2,11,1);

        opt.bookCar(0,r1,2);

        /**
         * Booked
         * Step : Booking
         * s0: null
         * s1: null
         * s2: 2
         * s3: 2
         * s4: 2
         * s5: 2
         * s6: 2
         * s7: null
         * s8: null
         * s9: null
         * s10: null
         * */


        assertEquals(null,opt.schedules[0][0]);
        assertEquals(null,opt.schedules[0][7]);
        assertEquals(null,opt.schedules[1][3]);

        for(int i = 2;i<7;i++){
            Ride br = opt.schedules[0][i];
            assertEquals(r1,br);
        }
    }

    @Test
    public void testFitsInSchedule1(){
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
    public void testFitsInSchedule2(){
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
    public void testFitsInSchedule3(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,2),
                new Position(0,0),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,2),
                new Position(0,3),
                1, 8);

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[6] = r2;
        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(2, analyzer.getStartStep());
    }

    @Test
    public void testFitsInSchedule4(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,5),
                new Position(0,7),
                1, 8);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(5, analyzer.getStartStep());
    }

    @Test
    public void testFitsInSchedule5(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,2),
                new Position(0,7),
                1, 8);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(2, analyzer.getStartStep());
    }

    @Test
    public void testFitsInSchedule6(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,2),
                new Position(0,8),
                1, 8);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(false, analyzer.rideFitsInschedule());
    }

    @Test
    public void testFitsInSchedule7(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,7),
                new Position(0,2),
                1, 8);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(false, analyzer.rideFitsInschedule());

    }

    @Test
    public void testFitsInSchedule8(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,5),
                new Position(0,7),
                5, 8);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(5, analyzer.getStartStep());
    }

    @Test
    public void testFitsInSchedule9(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,4),
                new Position(0,5),
                2, 8);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(true, analyzer.rideFitsInschedule());
        assertEquals(4, analyzer.getStartStep());
    }

    @Test
    public void testFitsInSchedule10(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,0),
                new Position(0,5),
                2, 6);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(false, analyzer.rideFitsInschedule());

    }

    @Test
    public void testFitsInSchedule11(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,0),
                new Position(0,3),
                0, 4);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;

        RideOptimizer.CarScheduleAnalyzer analyzer = new RideOptimizer.CarScheduleAnalyzer(schedule,r3);

        assertEquals(false, analyzer.rideFitsInschedule());

    }

    @Test
    public void testGetFreeTime1(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5:
         * s6:
         * s7: 7 : 2
         * s8: 6 : 2
         * s9: 5
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[7] = r2;
        schedule[8] = r2;


        RideOptimizer.CarScheduleAnalyzer.TimeBlock b1 = new RideOptimizer.CarScheduleAnalyzer.TimeBlock(2,6);

        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> exp;
        exp = List.of(b1);
        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> res;
        res = RideOptimizer.CarScheduleAnalyzer.getFreeTime(schedule);

        assertEquals(exp,res);

    }



    @Test
    public void testGetFreeTime2(){
        Ride[] schedule = new Ride[10];
        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 1);
        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);

        /**
         * Booked
         * Step : Col : Booking
         * s0: 0 : 1
         * s1: 1 : 1
         * s2: 2 :
         * s3:
         * s4:
         * s5: : 2
         * s6: : 2
         * s7:
         * s8:
         * s9:
         * */

        schedule[0] = r1;
        schedule[1] = r1;

        schedule[5] = r2;
        schedule[6] = r2;


        RideOptimizer.CarScheduleAnalyzer.TimeBlock b1 = new RideOptimizer.CarScheduleAnalyzer.TimeBlock(2,4);
        RideOptimizer.CarScheduleAnalyzer.TimeBlock b2 = new RideOptimizer.CarScheduleAnalyzer.TimeBlock(7,9);

        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> exp;
        exp = List.of(b1,b2);
        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> res;
        res = RideOptimizer.CarScheduleAnalyzer.getFreeTime(schedule);

        assertEquals(exp,res);

    }

    @Test
    public void testGetFreeTime3(){
        Ride[] schedule = new Ride[10];


        RideOptimizer.CarScheduleAnalyzer.TimeBlock b1 = new RideOptimizer.CarScheduleAnalyzer.TimeBlock(0,9);

        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> exp;
        exp = List.of(b1);
        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> res;
        res = RideOptimizer.CarScheduleAnalyzer.getFreeTime(schedule);

        assertEquals(exp,res);

    }

    @Test
    public void testGetFreeTime4(){
        Ride[] schedule = new Ride[10];

        Ride r2 = new Ride(
                2,
                new Position(0,7),
                new Position(0,5),
                5, 9);

        /**
         * Booked
         * Step : Col : Booking
         * s0:
         * s1:
         * s2:
         * s3:
         * s4:
         * s5: : 2
         * s6: : 2
         * s7:
         * s8:
         * s9:
         * */

        schedule[5] = r2;
        schedule[6] = r2;


        RideOptimizer.CarScheduleAnalyzer.TimeBlock b1 = new RideOptimizer.CarScheduleAnalyzer.TimeBlock(0,4);
        RideOptimizer.CarScheduleAnalyzer.TimeBlock b2 = new RideOptimizer.CarScheduleAnalyzer.TimeBlock(7,9);

        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> exp;
        exp = List.of(b1,b2);
        List<RideOptimizer.CarScheduleAnalyzer.TimeBlock> res;
        res = RideOptimizer.CarScheduleAnalyzer.getFreeTime(schedule);

        assertEquals(exp,res);

    }

    @Test
    public void testPoints(){
        RideOptimizer opt = new RideOptimizer(1,10,5);

        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 4);
        Ride r2 = new Ride(
                2,
                new Position(0,3),
                new Position(0,5),
                5, 9);

        RideOptimizer.BookingSequence s1 = new RideOptimizer.BookingSequence(10,5);

        s1.addRide(r1);
        s1.addRide(r2);

        assertEquals(14,s1.getPoints());


    }

    @Test
    public void testBookSeqSort(){
        RideOptimizer opt = new RideOptimizer(1,10,5);

        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 4);
        Ride r2 = new Ride(
                2,
                new Position(0,3),
                new Position(0,5),
                5, 9);

        RideOptimizer.BookingSequence s1 = new RideOptimizer.BookingSequence(10,5);
        RideOptimizer.BookingSequence s2 = new RideOptimizer.BookingSequence(5,5);
        s1.addRide(r1);
        s1.addRide(r2);

        LinkedList<RideOptimizer.BookingSequence> lis = new LinkedList<>();

        lis.add(s1);
        lis.add(s2);

        Collections.sort(lis);

        RideOptimizer.BookingSequence first = lis.getFirst();

        assertEquals(10,first.timeSteps);


    }

}