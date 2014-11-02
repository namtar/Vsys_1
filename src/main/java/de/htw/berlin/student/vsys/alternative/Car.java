package de.htw.berlin.student.vsys.alternative;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

/**
 * Class that represents a car which will be run in a thread.
 *
 * @author by Matthias Drummer on 02.11.2014
 */
public class Car implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Car.class.getName());
    private boolean parkingDone = false;

    private String kennzeichen;
    private final int startDelay; // start delay in millis
    private final int parkingDuration; // duration of the parkint in millis

    private final int max = 50;
    private final int min = 20;

    private final List<ParkingDeck> parkingDecksToUse;

    public Car(String kennzeichen, List<ParkingDeck> parkingDecksToUse) {

        if (parkingDecksToUse == null || parkingDecksToUse.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one parkhaus given.");
        }

        this.kennzeichen = kennzeichen;
        this.parkingDecksToUse = parkingDecksToUse;

        Random rand = new Random();
        startDelay = rand.nextInt((max - min) + 1) + min;
        parkingDuration = rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void run() {

        try {
            Thread.sleep((long) startDelay);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }

        while (!parkingDone) {
            for (ParkingDeck parkingDeck : parkingDecksToUse) {

                synchronized (this) {
                    if (parkingDeck.hasFreeSlots()) {
                        parkingDeck.enter(this);

                        try {
                            Thread.sleep((long) parkingDuration);
                        } catch (InterruptedException e) {
                            LOGGER.error(e.getMessage());
                        }

                        parkingDeck.leave(this);
                        parkingDone = true;
                        break; // leave parkhaus loop.
                    } else {
                        try {
                            Thread.sleep(startDelay);
                        } catch (InterruptedException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }
}
