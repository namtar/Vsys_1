package de.htw.berlin.student.vsys;

import org.junit.Test;

/**
 * Does the basic tests.
 * <p/>
 * Created by Matthias Drummer on 21.10.14.
 */
public class BasicTest {

	@Test
	public void testRun() {

		Parkhaus parkhaus = new Parkhaus();

		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new Auto("B-BB " + (i + 1), parkhaus));
//			System.out.println(i);
			t.start();
		}
	}

}
