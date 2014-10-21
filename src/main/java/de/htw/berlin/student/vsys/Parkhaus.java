package de.htw.berlin.student.vsys;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Das de.htw.berlin.student.vsys.Parkhaus
 * <p/>
 * Created by Matthias Drummer on 21.10.14.
 */
public class Parkhaus {

    private static final Logger LOGGER = Logger.getLogger(Parkhaus.class.getName());

    private short freeSlots = 10;

    public synchronized void enter(Auto auto) {

        if (freeSlots == 0) {
            throw new IllegalStateException("There are no slots left....");
        }

        LOGGER.info("Car enters: " + new Date() + ", " + auto.getKennzeichen());
        freeSlots++;
    }

    public synchronized void leave(Auto auto) {

        if (freeSlots == 10) {
            throw new IllegalStateException("The parking deck is already empty.");
        }

        LOGGER.info("Car leaves: " + new Date() + ", " + auto.getKennzeichen());
        freeSlots--;
    }

    public synchronized boolean hasFreeSlots() {
        if (freeSlots > 0) {
            //			this.notify();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "de.htw.berlin.student.vsys.Parkhaus{" +
                "freeSlots=" + freeSlots +
                '}';
    }
}
