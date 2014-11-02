package de.htw.berlin.student.vsys;

import de.htw.berlin.student.vsys.alternative.Car;
import de.htw.berlin.student.vsys.alternative.ParkingDeck;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for the alternative implementation.
 *
 * @author by Matthias Drummer on 02.11.2014
 */
public class AlternativeTest {

    @Test
    public void test() throws InterruptedException {

        ParkingDeck parkingDeck = new ParkingDeck(1);

        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Car("B-BB " + i, Arrays.asList(parkingDeck)));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

}
