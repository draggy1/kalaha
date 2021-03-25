package com.kalaha.domain;

import static com.kalaha.domain.Player.PLAYER_1;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * Class represent board of play
 */
@Getter
@Builder
public class GameBoard {
	private static final int FIRST_PIT_NUMBER = 1;
	private final int ordinaryPitsNumber;
	private final int homePitNumberOfPlayerOne;
	private final int homePitNumberOfPlayerTwo;
	private final Pit head;

	public static GameBoard of(int ordinaryPitsNumber, int stones) {
		int homePitNumberOfPlayerOne = ordinaryPitsNumber + 1;
		int homePitNumberOfPlayerTwo = calculateHomePitNumberOfPlayerTwo(ordinaryPitsNumber);

		GameBoardCreator creator = GameBoardCreator.of(stones, homePitNumberOfPlayerOne,
				homePitNumberOfPlayerTwo);

		return GameBoard.builder()
				.homePitNumberOfPlayerOne(homePitNumberOfPlayerOne)
				.homePitNumberOfPlayerTwo(homePitNumberOfPlayerTwo)
				.ordinaryPitsNumber(ordinaryPitsNumber)
				.head(creator.initBoard())
				.build();
	}

	/**
	 * Method finds {@link Pit} by provided pit id
	 *
	 * @param pitId unique pit id
	 * @return Pit with providee pit id number
	 */
	Pit findPitById(int pitId) {
		Pit current = head;
		while (current.getNumber() != pitId) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Method gets stones from provided pit and scattered them around the board
	 *
	 * @param pit            from where are get stones to scatter
	 * @param playerWithMove {@link Player} who the turn belongs to
	 * @return last pit where land the last stone
	 */
	static Pit scatterStonesAroundBoard(Pit pit, Player playerWithMove) {
		Stones stones = pit.getStones();
		int stonesNumber = stones.getStonesNumber();
		stones.setZero();

		Pit current = pit;
		while (stonesNumber > 0) {
			current = current.getNext();
			if (current.isOrdinaryOrOwnHome(playerWithMove)) {
				current.getStones().putStone();
				stonesNumber--;
			}
		}
		return current;
	}

	/**
	 * Method finds home pit for player who the turn belongs to
	 *
	 * @param start          pit from which is started looking for
	 * @param playerWithTurn {@link Player} who the turn belongs to
	 * @return home pit provided player
	 */
	Pit findHomeForPlayerWithTurn(Pit start, Player playerWithTurn) {
		int number = playerWithTurn == PLAYER_1 ? homePitNumberOfPlayerOne : homePitNumberOfPlayerTwo;

		Pit current = start;
		while (current.getNumber() != number) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Method prepare status - actual situation on board for {@link com.kalaha.services.dto.Response}
	 *
	 * @return board status as a map
	 */
	Map<String, String> prepareStatus() {
		Map<String, String> status = new HashMap<>();
		Pit current = head;
		while (current.getNumber() != homePitNumberOfPlayerTwo) {
			status.put(String.valueOf(current.getNumber()), String.valueOf(current.getStones().getStonesNumber()));
			current = current.getNext();
		}
		status.put(String.valueOf(current.getNumber()), String.valueOf(current.getStones().getStonesNumber()));
		return status;
	}

	/**
	 * Method checks if both players can make move - are both players have at least one non-home pit with stone
	 *
	 * @return true if both players can make move, false when at least one player has all non-home pits empty
	 */
	boolean canPlayersMakeMove() {
		Pit current = head;
		boolean hasPlayerOneNonEmptyPit = containsNotEmptyPit(current, homePitNumberOfPlayerOne);

		current = findPitById(homePitNumberOfPlayerOne + 1);
		boolean hasPlayerTwoNonEmptyPit = containsNotEmptyPit(current, homePitNumberOfPlayerTwo);

		return hasPlayerOneNonEmptyPit && hasPlayerTwoNonEmptyPit;
	}

	private boolean containsNotEmptyPit(Pit current, int homePlayerNumber) {
		while (current.getStones().isPitEmpty() && current.getNumber() != homePlayerNumber) {
			current = current.getNext();
		}
		return current.getNumber() != homePlayerNumber;
	}

	private static int calculateHomePitNumberOfPlayerTwo(int ordinaryPitsSizeOnePlayer) {
		return 2 * ordinaryPitsSizeOnePlayer + 2;
	}
}
