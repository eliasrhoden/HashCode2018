import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BookingComboFinderTest {


    @Test
    public void manualTest1(){
        LinkedList<Integer> rideIds = new LinkedList<>();
        rideIds.add(1);
        rideIds.add(2);
        List<List<Integer>[]> result = BookingComboFinder.getAllCombosOfBookingBetweenCars(2,rideIds);
        System.out.println("hallo");
    }

    @Test
    public void test1(){
        LinkedList<Integer> rideIds = new LinkedList<>();
        rideIds.add(1);
        rideIds.add(2);
        List<List<Integer>[]> result = BookingComboFinder.getAllCombosOfBookingBetweenCars(2,rideIds);



        List<Integer>[][] senario = new LinkedList[2][11];

        for(int i = 0;i<11;i++){
            senario[i] = new List[2];
            for(int j = 0;j<2;j++)
                senario[i][j] = new LinkedList<>();
        }

        /**
         * All combos:
         *
         * C1: 1
         * C2:
         */

        senario[0][0].add(1);

        /**
         * C1:
         * C2: 1
         */

        senario[1][1].add(1);

        /**
         * C1: 1 2
         * C2:
         */

        senario[2][0].add(1);
        senario[2][0].add(2);

        /**
         * C1: 1
         * C2: 2
         */

        senario[3][0].add(1);
        senario[3][1].add(2);

        /**
         * C1: 2
         * C2:
         */

        senario[4][0].add(2);

        /**
         * C1:
         * C2: 2
         */

        senario[5][1].add(2);

        /**
         * C1: 2 1
         * C2:
         */

        senario[6][0].add(2);
        senario[6][0].add(1);

        /**
         * C1: 2
         * C2: 1
         */

        senario[7][0].add(2);
        senario[7][1].add(1);

        /**
         * C1:
         * C2: 2 1
         */

        senario[8][1].add(2);
        senario[8][1].add(1);

        /**
         * C1:
         * C2: 1 2
         */

        senario[9][1].add(1);
        senario[9][1].add(2);

        /**
         * C1:
         * C2:
         *
         * */
    }



}