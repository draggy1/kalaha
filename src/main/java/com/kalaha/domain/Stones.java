package com.kalaha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class represents stones which are located in pit
 */
@Data
@AllArgsConstructor
public class Stones {
	private int stonesNumber;

	public static Stones of(int stones) {
		return new Stones(stones);
	}

	/**
	 * Make pit with stones empty
	 */
	void setZero() {
		stonesNumber = 0;
	}

	/**
	 * Put stone into pit
	 */
	void putStone() {
		stonesNumber++;
	}

	/**
	 * Put stones to the pit
	 *
	 * @param stonesNumber stones which will be located to the pit
	 */
	void putStones(int stonesNumber) {
		this.stonesNumber += stonesNumber;
	}

	/**
	 * Used only after scattering stones. Checks if pit contains exactly one stone, because it means that it was empty before scattering
	 *
	 * @return true if pit was empty before scattering, false otherwise
	 */
	boolean wasLastPitEmpty() {
		return stonesNumber == 1;
	}

	/**
	 * Method checks if current pit is empty
	 *
	 * @return true if current pit is empty, false otherwise
	 */
	boolean isPitEmpty() {
		return stonesNumber == 0;
	}

	/**
	 * Method checks if current pit contains at least one stone
	 *
	 * @return true if current pit is not empty, false otherwise
	 */
	boolean isPitNotEmpty() {
		return !isPitEmpty();
	}
}
