package com.kalaha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stones {
	private int stonesNumber;

	static Stones createEmpty() {
		return new Stones(0);
	}

	public static Stones create(int stones) {
		return new Stones(stones);
	}

	void setZero() {
		stonesNumber = 0;
	}

	void putStone() {
		stonesNumber++;
	}

	void putStones(int stonesNumber) {
		this.stonesNumber =+ stonesNumber;
	}

	boolean wasLastPitEmpty() {
		return stonesNumber == 1;
	}

	boolean isPitEmpty() {
		return stonesNumber == 0;
	}
}
