import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RideTest {

    @Test
    public void testEquals(){
        Ride r1 = new Ride(
                2,
                new Position(0,0),
                new Position(0,5),
                5, 9);
        Ride r2 = new Ride(
                2,
                new Position(0,0),
                new Position(0,5),
                5, 9);
        assertEquals(r1,r2);

    }

    @Test
    public void testStepsRequired1(){
        Ride r1 = new Ride(
                2,
                new Position(0,0),
                new Position(0,5),
                5, 9);

        assertEquals(5,r1.stepsRequired);
    }

    @Test
    public void testStepsRequired2(){
        Ride r1 = new Ride(
                2,
                new Position(5,5),
                new Position(10,10),
                5, 9);

        assertEquals(10,r1.stepsRequired);
    }

    public List<Ride> getTestList(){
        Ride r1 = new Ride(
                1,
                new Position(0,5),
                new Position(0,10),
                1, 15);
        Ride r2 = new Ride(
                2,
                new Position(0,0),
                new Position(0,10),
                4, 9);
        Ride r3 = new Ride(
                3,
                new Position(0,9),
                new Position(0,10),
                5, 26);
        List<Ride> l = new LinkedList<>();
        l.add(r1);
        l.add(r2);
        l.add(r3);
        return l;
    }

    @Test
    public void testSortByStart1(){
        List<Ride> input = getTestList();
        List<Ride> expected = new LinkedList<>();
        expected.add(input.get(0));
        expected.add(input.get(1));
        expected.add(input.get(2));
        Ride.sortByStartStep(input);
        assertEquals(expected,input);
    }

    @Test
    public void testSortByStart2(){
        List<Ride> input = getTestList();
        List<Ride> expected = new LinkedList<>();
        expected.add(input.get(0));
        expected.add(input.get(1));
        expected.add(input.get(2));
        Ride.sortByStartStep(input);
        assertEquals(expected,input);

    }

    @Test
    public void testSortByStepsReq(){
        List<Ride> input = getTestList();
        List<Ride> expected = new LinkedList<>();
        expected.add(input.get(2));
        expected.add(input.get(0));
        expected.add(input.get(1));
        Ride.sortByStepsRequired(input);
        assertEquals(expected,input);
    }

    @Test
    public void testSortByEndStep(){
        List<Ride> input = getTestList();
        List<Ride> expected = new LinkedList<>();
        expected.add(input.get(1));
        expected.add(input.get(0));
        expected.add(input.get(2));
        Ride.sortByEndStep(input);
        assertEquals(expected,input);
    }


}