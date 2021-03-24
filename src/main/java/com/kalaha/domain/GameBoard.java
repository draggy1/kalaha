package com.kalaha.domain;

import static com.kalaha.domain.Player.PLAYER_1;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameBoard {
	private static final int FIRST_PIT_NUMBER = 1;
	private final int ordinaryPitsSize;
	private final int homePitNumberOfPlayerOne;
	private final int homePitNumberOfPlayerTwo;
	private final Pit head;

	public static GameBoard of(int ordinaryPitsSize, int stones) {
		int homePitNumberOfPlayerOne = ordinaryPitsSize + 1;
		int homePitNumberOfPlayerTwo = calculateHomePitNumberOfPlayerTwo(ordinaryPitsSize);

		GameBoardCreator creator = GameBoardCreator.of(stones, homePitNumberOfPlayerOne,
				homePitNumberOfPlayerTwo);

		return GameBoard.builder()
				.homePitNumberOfPlayerOne(homePitNumberOfPlayerOne)
				.homePitNumberOfPlayerTwo(homePitNumberOfPlayerTwo)
				.ordinaryPitsSize(ordinaryPitsSize)
				.head(creator.initBoard())
				.build();
	}

	Pit findPitById(int pitId) {
		Pit current = head;
		while (current.getNumber() != pitId) {
			current = current.getNext();
		}
		return current;
	}

	static Pit moveStonesAroundBoard(Pit pit, Player playerWithMove) {
		Stones stones = pit.getStones();
		int stonesNumber = stones.getStonesNumber();
		stones.setZero();

		Pit current = pit;
		while (stonesNumber > 0) {
			current = current.getNext();
			if (current.isOrdinaryOrHome(playerWithMove)) {
				current.getStones().putStone();
				stonesNumber--;
			}
		}
		return current;
	}

	Pit findHomeForPlayerWithTurn(Pit last, Player playerWithTurn) {
		int number = playerWithTurn == PLAYER_1 ? homePitNumberOfPlayerOne : homePitNumberOfPlayerTwo;

		Pit current = last;
		while (current.getNumber() != number) {
			current = current.getNext();
		}
		return current;
	}

	Map<Integer, Integer> prepareStatus() {
		Map<Integer, Integer> status = new HashMap<>();
		Pit current = head;
		while (current.getNumber() != homePitNumberOfPlayerTwo) {
			status.put(current.getNumber(), current.getStones().getStonesNumber());
			current = current.getNext();
		}
		status.put(current.getNumber(), current.getStones().getStonesNumber());
		return status;
	}

	boolean canPlayersMakeMove() {
		Pit current = head;
		while (current.getStones().isPitEmpty() && current.getNumber() != homePitNumberOfPlayerOne) {
			current = current.getNext();
		}
		if (current.getNumber() == homePitNumberOfPlayerOne) {
			return false;
		}

		current = findPitById(homePitNumberOfPlayerOne + 1);

		while (current.getStones().isPitEmpty() && current.getNumber() != homePitNumberOfPlayerTwo) {
			current = current.getNext();
		}
		return current.getNumber() != homePitNumberOfPlayerTwo;
	}

	private static int calculateHomePitNumberOfPlayerTwo(int ordinaryPitsSizeOnePlayer) {
		return 2 * ordinaryPitsSizeOnePlayer + 2;
	}

	/*@Value
	@Builder
	public static class GameBoardParameters {
		int ordinaryPitsSize;
		int homePitNumberOfPlayerOne;
		int homePitNumberOfPlayerTwo;
		int stones;
	}*/
}
