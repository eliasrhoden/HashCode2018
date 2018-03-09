import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RideOptimizerTest {


    @Test
    public void testPoints(){
        RideOptimizer opt = new RideOptimizer(1,50,5);

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

        RideOptimizer.BookingSequence s1 = new RideOptimizer.BookingSequence(50,5,0);

        s1.addRide(r1);
        s1.addRide(r2);

        assertEquals(14,s1.getPoints());
    }

    @Test
    public void testBookSeqSort(){
        RideOptimizer opt = new RideOptimizer(1,50,5);

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

        RideOptimizer.BookingSequence s1 = new RideOptimizer.BookingSequence(50,5,0);
        RideOptimizer.BookingSequence s2 = new RideOptimizer.BookingSequence(40,5,0);
        s1.addRide(r1);
        s1.addRide(r2);

        LinkedList<RideOptimizer.BookingSequence> lis = new LinkedList<>();

        lis.add(s1);
        lis.add(s2);

        Collections.sort(lis);

        RideOptimizer.BookingSequence first = lis.getFirst();

        assertEquals(50,first.timeSteps);


    }

    @Test
    public void testPoints3(){
        RideOptimizer opt = new RideOptimizer(1,50,5);

        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 4);
        Ride r2 = new Ride(
                2,
                new Position(0,10),
                new Position(0,5),
                5, 10);

        RideOptimizer.BookingSequence s1 = new RideOptimizer.BookingSequence(50,5,0);

        s1.addRide(r1);
        s1.addRide(r2);

        assertEquals(7,s1.getPoints());
    }

    @Test
    public void testPoints2(){
        RideOptimizer opt = new RideOptimizer(1,50,5);

        Ride r1 = new Ride(
                1,
                new Position(0,0),
                new Position(0,2),
                0, 4);
        Ride r2 = new Ride(
                2,
                new Position(0,10),
                new Position(0,5),
                0, 6);

        RideOptimizer.BookingSequence s1 = new RideOptimizer.BookingSequence(50,5,0);

        s1.addRide(r2);
        s1.addRide(r1);

        assertEquals(0,s1.getPoints());
    }

    @Test
    public void pointTest(){
        RideOptimizer opt = new RideOptimizer(1,10,2);
        Ride r1 = new Ride(
                0,
                new Position(0,0),
                new Position(1,3),
                2, 9);
        Ride r2 = new Ride(
                1,
                new Position(1,2),
                new Position(1,0),
                0, 9);
        Ride r3 = new Ride(
                2,
                new Position(2,0),
                new Position(2,2),
                0, 9);

        RideOptimizer.BookingSequence s1 = new RideOptimizer.BookingSequence(10,2,0);

        s1.addRide(r1);
        s1.addRide(r2);
        s1.addRide(r3);

        assertEquals(4 + 2 + 2,s1.getPoints());
    }


}