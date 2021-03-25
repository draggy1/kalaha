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

/**
 * Class represent single pit
 */
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

	/**
	 * Method links together two opposite pits
	 *
	 * @param current  handled pit
	 * @param opposite to current
	 */
	static void linkOppositePits(Pit current, Pit opposite) {
		if (Objects.nonNull(current) && Objects.nonNull(opposite)) {
			current.opposite = opposite;
			opposite.opposite = current;
		}
	}

	/**
	 * Method links together current pit with next
	 *
	 * @param current handled pit
	 * @param next    to current
	 */
	static void linkWithNextPit(Pit current, Pit next) {
		if (Objects.nonNull(current)) {
			current.next = next;
		}
	}

	/**
	 * Method moves stones from one pit to another
	 *
	 * @param from pit from which are moved stones
	 * @param to   pit to which are moved stones
	 */
	static void moveStonesToPit(Pit from, Pit to) {
		int stones = from.stones.getStonesNumber();
		from.stones.setZero();
		to.stones.putStones(stones);
	}

	/**
	 * Method checks if {@link Player} can grab stones from opposite pit
	 *
	 * @param playerWithTurn player who the turn belongs to
	 * @return true if player can grab the stones from opposite pit, false otherwise
	 */
	boolean canGrabStonesFromOpposite(Player playerWithTurn) {
		return isOrdinary() && stones.wasLastPitEmpty() && isOwner(playerWithTurn);
	}

	/**
	 * Method checks if pit is ordinary or own home pit
	 *
	 * @param player {@link Player} player who the turn belongs to
	 * @return true if current pit is ordinary or home pit which belongs to player with current turn, false otherwise
	 */
	boolean isOrdinaryOrOwnHome(Player player) {
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

