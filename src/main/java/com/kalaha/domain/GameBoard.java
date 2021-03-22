package com.kalaha.domain;

import com.kalaha.domain.Game.Player;
import static com.kalaha.domain.Pit.linkOppositePits;
import static com.kalaha.domain.Pit.linkWithNextPit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameBoard {
	private static final int FIRST_PIT_NUMBER = 1;
	private final int ordinaryPitsSize;
	private final int homePitNumberOfPlayerOne;
	private final int homePitNumberOfPlayerTwo;
	private final int stones;
	private Pit head;

	public static GameBoard of(int ordinaryPitsSize, int stones) {
		int homePitNumberOfPlayerOne = ordinaryPitsSize + 1;
		int homePitNumberOfPlayerTwo = calculateHomePitNumberOfPlayerTwo(ordinaryPitsSize);
		GameBoard gameBoard = new GameBoard(ordinaryPitsSize, stones, homePitNumberOfPlayerOne, homePitNumberOfPlayerTwo);
		gameBoard.initBoard();
		return gameBoard;
	}

	private GameBoard(int ordinaryPitsSize, int stones, int homePitNumberOfPlayerOne, int homePitNumberOfPlayerTwo) {
		this.ordinaryPitsSize = ordinaryPitsSize;
		this.homePitNumberOfPlayerOne = homePitNumberOfPlayerOne;
		this.homePitNumberOfPlayerTwo = homePitNumberOfPlayerTwo;
		this.stones = stones;
	}

	public Optional<Pit> getHead() {
		return Optional.ofNullable(head);
	}

	public int getHomePitNumberOfPlayerOne() {
		return homePitNumberOfPlayerOne;
	}

	public int getHomePitNumberOfPlayerTwo() {
		return homePitNumberOfPlayerTwo;
	}

	public Optional<Pit> findPitById(int pitId) {
		return getHead().map(head -> {
			Pit current = head;
			while (current.getNumber() != pitId) {
				current = current.getNext();
			}
			return current;
		});
	}

	public Pit moveStones(Pit pit, Player playerWithMove) {
		int stones = pit.getStones();
		pit.setZero();
		Pit current = pit.getNext();
		while (stones > 1) {
			if (current.isOrdinaryOrHomePitOfPlayer(playerWithMove)) {
				current.putStone();
				stones--;
			}
			current = current.getNext();
		}
		current.putStone();
		return current;
	}

	public Map<Integer, Integer> prepareStatus() {
		Map<Integer, Integer> status = new HashMap<>();
		Pit current = head;
		while (current.getNumber() != homePitNumberOfPlayerTwo) {
			status.put(current.getNumber(), current.getStones());
			current = current.getNext();
		}
		status.put(current.getNumber(), current.getStones());
		return status;
	}

	private void initBoard() {
		Pit homePlayerOne = Pit.createHomePitPlayerOne(homePitNumberOfPlayerOne, 0);
		Pit homePlayerTwo = Pit.createHomePitPlayerTwo(homePitNumberOfPlayerTwo, 0);

		Pit homePlayerOnePrevious = Pit.createOrdinaryPit(ordinaryPitsSize, stones);
		Pit homePlayerOnePreviousOpposite = Pit.createOrdinaryPit(homePitNumberOfPlayerTwo - ordinaryPitsSize, stones);

		linkOppositePits(homePlayerOnePrevious, homePlayerOnePreviousOpposite);
		linkWithNextPit(homePlayerOnePrevious, homePlayerOne);
		linkWithNextPit(homePlayerOne, homePlayerOnePreviousOpposite);

		Pit firstPit = createBoard(homePlayerOnePrevious, homePlayerOnePreviousOpposite);

		linkWithNextPit(homePlayerTwo, firstPit);
		linkWithNextPit(firstPit.getOpposite(), homePlayerTwo);

		head = firstPit;
	}

	private Pit createBoard(Pit next, Pit nextOpposite) {
		int currentNumber = next.getNumber() - 1;
		Pit current = Pit.createOrdinaryPit(currentNumber, stones);
		Pit currentOpposite = Pit.createOrdinaryPit(homePitNumberOfPlayerTwo - currentNumber, stones);

		linkOppositePits(current, currentOpposite);
		linkWithNextPit(current, next);
		linkWithNextPit(nextOpposite, currentOpposite);

		if (currentNumber == FIRST_PIT_NUMBER) {
			return current;
		}
		return createBoard(current, currentOpposite);
	}

	private static int calculateHomePitNumberOfPlayerTwo(int ordinaryPitsSizeOnePlayer) {
		return 2 * ordinaryPitsSizeOnePlayer + 2;
	}

	public Pit findHomePitForPlayerWithTurn(Pit last, Player playerWithTurn) {
		int number = playerWithTurn == Player.PLAYER_1 ?  homePitNumberOfPlayerOne : homePitNumberOfPlayerTwo;

		Pit current = last.getNext();
		while (current.getNumber() != number){
			 current = current.getNext();
		}
		return current;
	}
}
