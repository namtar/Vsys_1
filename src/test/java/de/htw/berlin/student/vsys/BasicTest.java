package de.htw.berlin.student.vsys;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

/**
 * Does the basic tests.
 * <p/>
 * Created by Matthias Drummer on 21.10.14.
 */
public class BasicTest {

    private Logger LOGGER = Logger.getLogger(BasicTest.class);

    @Test
    public void testRun() {

        LOGGER.info("Start testRun");

        Parkhaus parkhaus = new Parkhaus();
        List<Thread> threadList = new ArrayList<Thread>();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Auto("B-BB " + (i + 1), parkhaus));
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

        LOGGER.info("Finished testRun");
    }

}
