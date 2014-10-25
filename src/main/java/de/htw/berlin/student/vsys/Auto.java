package de.htw.berlin.student.vsys;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

/**
 * The car as thread.
 * <p/>
 * Created by Matthias Drummer on 21.10.14.
 */
public class Auto implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Auto.class.getName());
    private boolean parkingDone = false;

    private String kennzeichen;
    private final int startDelay; // start delay in millis
    private final int parkingDuration; // duration of the parkint in millis

    private final int max = 50;
    private final int min = 20;

    private final List<Parkhaus> parkhaeuserToUse;

    public Auto(String kennzeichen, List<Parkhaus> parkhaeuserToUse) {

        if (parkhaeuserToUse == null || parkhaeuserToUse.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one parkhaus given.");
        }

        this.kennzeichen = kennzeichen;
        this.parkhaeuserToUse = parkhaeuserToUse;

        Random rand = new Random();
        startDelay = rand.nextInt((max - min) + 1) + min;
        parkingDuration = rand.nextInt((max - min) + 1) + min;

//        LOGGER.info("Auto created: " + this.toString());
    }

    @Override
    public void run() {

        try {
            Thread.sleep((long) startDelay);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }

        while (!parkingDone) {
            for (Parkhaus parkhaus : parkhaeuserToUse) {

                if (parkhaus.hasFreeSlots()) {
                    parkhaus.enter(this);

                    try {
                        Thread.sleep((long) parkingDuration);
                    } catch (InterruptedException e) {
                        LOGGER.error(e.getMessage());
                    }

                    parkhaus.leave(this);
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

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "parkingDone=" + parkingDone +
                ", kennzeichen='" + kennzeichen + '\'' +
                ", startDelay=" + startDelay +
                ", parkingDuration=" + parkingDuration +
                ", max=" + max +
                ", min=" + min +
                ", parkhaeuserToUse=" + parkhaeuserToUse +
                '}';
    }
}
