package com.kalaha.domain;

import com.kalaha.domain.Game.Player;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Pit {
	private int number;
	private int stones;
	private PitType pitType;
	private Pit next;
	private Pit opposite;

	private Pit(int number, int stones, PitType pitType) {
		this.number = number;
		this.stones = stones;
		this.pitType = pitType;
	}

	static Pit createOrdinaryPit(int number, int stones) {
		return new Pit(number, stones, PitType.ORDINARY);
	}

	static Pit createHomePitPlayerOne(int number, int stones) {
		return new Pit(number, stones, PitType.HOME_PLAYER_1);
	}

	static Pit createHomePitPlayerTwo(int number, int stones) {
		return new Pit(number, stones, PitType.HOME_PLAYER_2);
	}

	static void linkOppositePits(Pit next, Pit opposite) {
		if (Objects.nonNull(next) && Objects.nonNull(opposite)) {
			next.opposite = opposite;
			opposite.opposite = next;
		}
	}

	static void linkWithNextPit(Pit current, Pit next) {
		if (Objects.nonNull(current)) {
			current.next = next;
		}
	}

	static void moveStonesBetweenPits(Pit from, Pit to){
		int numberFrom = from.getNumber();
		from.setZero();
		to.stones = to.stones + numberFrom;
	}

	int getNumber() {
		return number;
	}

	Pit getOpposite() {
		return opposite;
	}

	Pit getNext() {
		return next;
	}

	int getStones() {
		return stones;
	}

	void setZero() {
		stones = 0;
	}

	void putStone() {
		stones++;
	}

	boolean isOrdinaryOrHomePitOfPlayer(Player player){
		return isPitOrdinary() || pitType.access.contains(player);
	}

	boolean isPitOrdinary() {
		return pitType == PitType.ORDINARY;
	}

	boolean hasPitZeroStones(){
		return getStones() == 0;
	}

	boolean wasLastPitEmpty(){
		return getStones() == 1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Pit pit = (Pit) o;

		return new EqualsBuilder()
				.append(number, pit.number)
				.append(stones, pit.stones)
				.append(pitType, pit.pitType)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(number)
				.append(stones)
				.append(pitType)
				.toHashCode();
	}
}

