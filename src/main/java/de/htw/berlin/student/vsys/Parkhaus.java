package de.htw.berlin.student.vsys;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

		LOGGER.log(Level.INFO, "Car enters: " + new Date() + ", " + auto.toString());
		freeSlots++;

		//		this.notify();
	}

	public synchronized void leave(Auto auto) {

		if (freeSlots == 10) {
			throw new IllegalStateException("The parking deck is already empty.");
		}

		LOGGER.log(Level.INFO, "Car leaves: " + new Date() + ", " + auto.toString());
		freeSlots--;

		//		this.notify();
	}

	public synchronized boolean hasFreeSlots() {
		if (freeSlots > 0) {
			//			this.notify();
			return true;
		}
		//		this.notify();
		return false;
	}

	@Override public String toString() {
		return "de.htw.berlin.student.vsys.Parkhaus{" +
				"freeSlots=" + freeSlots +
				'}';
	}
}
