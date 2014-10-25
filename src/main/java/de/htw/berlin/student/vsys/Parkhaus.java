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
    private int parkhausNr;

    public Parkhaus(int parkhausNr) {
        this.parkhausNr = parkhausNr;
    }

    public synchronized void enter(Auto auto) {

        if (freeSlots == 0) {
            throw new IllegalStateException("There are no slots left....");
        }

        StringBuilder sb = new StringBuilder("Car enters: ");
        sb.append(new Date());
        sb.append(", ");
        sb.append(auto.getKennzeichen());
        sb.append(", Parkhaus: ");
        sb.append(parkhausNr);

        LOGGER.info(sb.toString());
        freeSlots--;
    }

    public synchronized void leave(Auto auto) {

        if (freeSlots == 10) {
            throw new IllegalStateException("The parking deck is already empty.");
        }

        LOGGER.info("Car leaves: " + new Date() + ", " + auto.getKennzeichen() + ", Parkhaus: " + parkhausNr);
        freeSlots++;
    }

    public synchronized boolean hasFreeSlots() {
        if (freeSlots > 0) {
            return true;
        }
        return false;
    }

    public synchronized short getFreeSlots() {
        return freeSlots;
    }

    @Override
    public String toString() {
        return "de.htw.berlin.student.vsys.Parkhaus{" +
                "freeSlots=" + freeSlots +
                '}';
    }
}
