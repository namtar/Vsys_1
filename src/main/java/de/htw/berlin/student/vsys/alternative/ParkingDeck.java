package de.htw.berlin.student.vsys.alternative;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Class that represents a parking deck.
 *
 * @author by Matthias Drummer on 02.11.2014
 */
public class ParkingDeck {

    private static final Logger LOGGER = Logger.getLogger(ParkingDeck.class.getName());

    private short freeSlots = 10;
    private int parkingDeckNr;

    public ParkingDeck(int parkingDeckNr) {
        this.parkingDeckNr = parkingDeckNr;
    }

    public void enter(Car car) {

        if (freeSlots == 0) {
            throw new IllegalStateException("There are no slots left....");
        }

        StringBuilder sb = new StringBuilder("Car enters: ");
        sb.append(new Date());
        sb.append(", ");
        sb.append(car.getKennzeichen());
        sb.append(", Parkhaus: ");
        sb.append(parkingDeckNr);

        LOGGER.info(sb.toString());
        freeSlots--;
    }

    public void leave(Car car) {

        if (freeSlots == 10) {
            throw new IllegalStateException("The parking deck is already empty.");
        }

        LOGGER.info("Car leaves: " + new Date() + ", " + car.getKennzeichen() + ", Parkhaus: " + parkingDeckNr);
        freeSlots++;
    }

    public boolean hasFreeSlots() {
        if (freeSlots > 0) {
            return true;
        }
        return false;
    }

    public short getFreeSlots() {
        return freeSlots;
    }


    @Override
    public String toString() {
        return "ParkingDeck{" +
                "freeSlots=" + freeSlots +
                ", parkingDeckNr=" + parkingDeckNr +
                '}';
    }
}
