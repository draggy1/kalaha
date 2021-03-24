package com.kalaha.domain;

import static com.kalaha.domain.Player.PLAYER_1;
import static com.kalaha.domain.Player.PLAYER_2;
import static com.kalaha.domain.Stones.of;
import static com.kalaha.domain.Type.HOME_PLAYER_1;
import static com.kalaha.domain.Type.HOME_PLAYER_2;
import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public final class Pit {
	private final int number;
	private final Stones stones;
	private final Type type;
	private final Player owner;
	private Pit next;
	private Pit opposite;

	private Pit(int number, Stones stones, Type type, Player owner) {
		this.number = number;
		this.stones = stones;
		this.type = type;
		this.owner = owner;
	}

	static Pit createOrdinary(int number, int stones, Player owner) {
		return new Pit(number, of(stones), Type.ORDINARY, owner);
	}

	static Pit createHomeForPlayerOne(int number, int stones) {
		return new Pit(number, of(stones), HOME_PLAYER_1, PLAYER_1);
	}

	static Pit createHomeForPlayerTwo(int number, int stones) {
		return new Pit(number, of(stones), HOME_PLAYER_2, PLAYER_2);
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

	static void moveStonesBetweenPits(Pit from, Pit to) {
		int stones = from.stones.getStonesNumber();
		from.stones.setZero();
		to.stones.putStones(stones);
	}

	boolean canGrabStonesFromOpposite(Player playerWithTurn) {
		return isOrdinary() && stones.wasLastPitEmpty() && isOwner(playerWithTurn);
	}

	boolean isOrdinaryOrHome(Player player) {
		return isOrdinary() || owner == player;
	}

	private boolean isOwner(Player playerWithTurn) {
		return playerWithTurn == owner;
	}

	private boolean isOrdinary() {
		return type == Type.ORDINARY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Pit pit = (Pit) o;

		return new EqualsBuilder()
				.append(number, pit.number)
				.append(stones, pit.stones)
				.append(type, pit.type)
				.append(owner, pit.owner)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(number)
				.append(stones)
				.append(type)
				.append(owner)
				.toHashCode();
	}
}

