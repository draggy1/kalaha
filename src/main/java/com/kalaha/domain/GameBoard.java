package com.kalaha.domain;

import static com.kalaha.domain.Player.PLAYER_1;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Builder
public class GameBoard {
	private static final int FIRST_PIT_NUMBER = 1;
	private final int ordinaryPitsSize;
	private final int homePitNumberOfPlayerOne;
	private final int homePitNumberOfPlayerTwo;
	private final int stones;
	private final Pit head;

	public static GameBoard of(int ordinaryPitsSize, int stones) {
		GameBoardParameters parameters = GameBoardParameters.builder()
				.ordinaryPitsSize(ordinaryPitsSize)
				.stones(stones)
				.homePitNumberOfPlayerOne(ordinaryPitsSize + 1)
				.homePitNumberOfPlayerTwo(calculateHomePitNumberOfPlayerTwo(ordinaryPitsSize))
				.build();

		return GameBoard.builder()
				.homePitNumberOfPlayerOne(parameters.homePitNumberOfPlayerOne)
				.homePitNumberOfPlayerTwo(parameters.homePitNumberOfPlayerTwo)
				.stones(stones)
				.ordinaryPitsSize(ordinaryPitsSize)
				.head(GameBoardCreator.initBoard(parameters))
				.build();
	}

	Pit findPitById(int pitId) {
		Pit current = head;
		while (current.getNumber() != pitId) {
			current = current.getNext();
		}
		return current;
	}

	static Pit moveStones(Pit pit, Player playerWithMove) {
		Stones stones = pit.getStones();
		int stonesNumber = stones.getStonesNumber();
		stones.setZero();

		Pit current = pit.getNext();
		while (stonesNumber > 1) {
			if (current.isOrdinaryOrHome(playerWithMove)) {
				current.getStones().putStone();
				stonesNumber--;
			}
			current = current.getNext();
		}
		current.getStones().putStone();
		return current;
	}

	Pit findHomeForPlayerWithTurn(Pit last, Player playerWithTurn) {
		int number = playerWithTurn == PLAYER_1 ?  homePitNumberOfPlayerOne : homePitNumberOfPlayerTwo;

		Pit current = last.getNext();
		while (current.getNumber() != number){
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
		while(current.getStones().isPitEmpty() && current.getNumber() != homePitNumberOfPlayerOne){
			current = current.getNext();
		}
		if (current.getNumber() == homePitNumberOfPlayerOne){
			return false;
		}

		current = findPitById(homePitNumberOfPlayerOne + 1);

		while(current.getStones().isPitEmpty() && current.getNumber() != homePitNumberOfPlayerTwo){
			current = current.getNext();
		}
		return current.getNumber() != homePitNumberOfPlayerTwo;
	}

	private static int calculateHomePitNumberOfPlayerTwo(int ordinaryPitsSizeOnePlayer) {
		return 2 * ordinaryPitsSizeOnePlayer + 2;
	}

	@Value
	@Builder
	public static class GameBoardParameters{
		int ordinaryPitsSize;
		int homePitNumberOfPlayerOne;
		int homePitNumberOfPlayerTwo;
		int stones;
	}
}
