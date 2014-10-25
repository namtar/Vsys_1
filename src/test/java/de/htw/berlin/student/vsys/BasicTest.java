package de.htw.berlin.student.vsys;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.lang.Thread.State;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Does the basic tests.
 * <p/>
 * Created by Matthias Drummer on 21.10.14.
 */
public class BasicTest {

    private Logger LOGGER = Logger.getLogger(BasicTest.class);

    private Parkhaus parkhaus;

    @Before
    public void before() {
        this.parkhaus = new Parkhaus(1);
    }

    @Test
    public void testRun() {

        LOGGER.info("Start testRun");

        List<Thread> threadList = new ArrayList<Thread>();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Auto("B-BB " + (i + 1), Arrays.asList(parkhaus)));
            threadList.add(t);
//			System.out.println(i);
            t.start();
        }

        boolean done = false;
        while (!done) {
            done = true;
            for (Thread thread : threadList) {
                if (thread.getState() != State.TERMINATED) {
                    done = false;
                    break;
                }
            }
        }

        Assert.assertEquals(parkhaus.getFreeSlots(), 10);
        LOGGER.info("Finished testRun");
    }

    @Test
    public void testRunMultipleParkingDecks() throws InterruptedException {

        // @See: http://stackoverflow.com/questions/6546193/how-to-catch-an-exception-from-a-thread

        // create 5 parking decks.
        List<Parkhaus> parkingDecks = new ArrayList<Parkhaus>();
        for (int i = 0; i < 5; i++) {
            parkingDecks.add(new Parkhaus(i));
        }

        final boolean[] exceptionOccured = {false};
        // create 100 cars
        List<Thread> carThreads = new ArrayList<Thread>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Auto("B-BB " + i, parkingDecks));
            t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    exceptionOccured[0] = true;
                }
            });
            carThreads.add(t);
            t.start();
        }

        for (Thread t : carThreads) {
            t.join();
        }

        if (exceptionOccured[0]) {
            Assert.fail("Thread tried to enter / leave a parking deck when its is not possible.");
        }
    }

    @Test
    public void testCarDelays() throws NoSuchFieldException, IllegalAccessException {

        // nicht schön, aber ein gutes Beispiel zu Reflection.
        Field field = Auto.class.getDeclaredField("startDelay");
        field.setAccessible(true);
        Field parkingDurationField = Auto.class.getDeclaredField("parkingDuration");
        parkingDurationField.setAccessible(true);
        for (int i = 0; i < 10; i++) {
            Auto auto = new Auto("B-BB 1234", Arrays.asList(parkhaus));

            int startDelay = field.getInt(auto);
            LOGGER.info("StartDelay: " + startDelay);
            int parkingDuration = parkingDurationField.getInt(auto);
            LOGGER.info("ParkingDuration: " + parkingDuration);
        }

    }

    @Test(expected = IllegalStateException.class)
    public void testEnterParkhaus_noSlotsAvailable() {

        Assert.assertTrue(parkhaus.hasFreeSlots());

        // This should throw an IllegalStateExeption when the eleventh car trys to enter.
        for (int i = 0; i < 12; i++) {
            Auto auto = new Auto("B-BB " + i, Arrays.asList(parkhaus));
            parkhaus.enter(auto);

            // Assert that the parkhaus has no slots left
            if (i == 9) {
                Assert.assertFalse(parkhaus.hasFreeSlots());
            }
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testLeaveParkhaus_isEmpty() {

        Assert.assertTrue(parkhaus.hasFreeSlots());
        Auto auto = new Auto("B-BB 1234", Arrays.asList(parkhaus));

        parkhaus.leave(auto);
    }

    @Test
    public void testEnterMultipleParkingDecks() throws InterruptedException {

        List<Parkhaus> parkingDecks = new ArrayList<Parkhaus>();
        parkingDecks.add(new Parkhaus(1));
        parkingDecks.add(new Parkhaus(2));

        List<Auto> cars = new ArrayList<Auto>();
        for (int i = 0; i < 10; i++) {
            Auto auto = new Auto("B-BB " + i, parkingDecks);
            cars.add(auto);
            parkingDecks.get(0).enter(auto);
        }
        Assert.assertFalse("Parking Deck 1 may not have any slots left.", parkingDecks.get(0).hasFreeSlots());
        Assert.assertTrue("Parking Deck 2 must have free slots.", parkingDecks.get(1).hasFreeSlots());
        Assert.assertEquals("There must be 10 slots available for parking deck 2", parkingDecks.get(1).getFreeSlots(), 10);

        Thread t = new Thread(new Auto("B-BA 1", parkingDecks));
        t.start();

        while (t.getState() != State.TERMINATED) {
            Thread.sleep(1000);
        }
    }
}
