package de.htw.berlin.student.vsys;

import org.apache.log4j.Logger;

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

	private final Parkhaus parkhausToUse;

	public Auto(String kennzeichen, Parkhaus parkhaus) {
		this.kennzeichen = kennzeichen;
		this.parkhausToUse = parkhaus;

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

//        while(!parkingDone) {
            if (parkhausToUse.hasFreeSlots()) {
                parkhausToUse.enter(this);

                try {
                    Thread.sleep((long) parkingDuration);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }

                parkhausToUse.leave(this);
                parkingDone = true;
            } else {
                try {
                    Thread.sleep(startDelay);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
//        }
	}

	public String getKennzeichen() {
		return kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	@Override public String toString() {
		return "de.htw.berlin.student.vsys.Auto{" +
				"kennzeichen='" + kennzeichen + '\'' +
				", startDelay=" + startDelay +
				", parkingDuration=" + parkingDuration +
				", max=" + max +
				", min=" + min +
				", parkhausToUse=" + parkhausToUse +
				'}';
	}
}
