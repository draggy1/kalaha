package com.kalaha.domain;

import com.kalaha.domain.GameBoard.GameBoardParameters;
import static com.kalaha.domain.Pit.linkOppositePits;
import static com.kalaha.domain.Pit.linkWithNextPit;
import static com.kalaha.domain.Player.PLAYER_1;
import static com.kalaha.domain.Player.PLAYER_2;

public class GameBoardCreator {
	private static final int FIRST_PIT_NUMBER = 1;
	static Pit initBoard(GameBoardParameters parameters) {
		int homePitNumberOfPlayerTwo = parameters.getHomePitNumberOfPlayerTwo();
		int ordinaryPitsSize = parameters.getOrdinaryPitsSize();
		int stones = parameters.getStones();

		Pit homePlayerOne = Pit.createHomeForPlayerOne(parameters.getHomePitNumberOfPlayerOne());
		Pit homePlayerTwo = Pit.createHomeForPlayerTwo(homePitNumberOfPlayerTwo);

		Pit homePlayerOnePrevious = Pit.createOrdinary(ordinaryPitsSize, stones, PLAYER_1);
		Pit homePlayerOnePreviousOpposite = Pit.createOrdinary(homePitNumberOfPlayerTwo - ordinaryPitsSize, stones, PLAYER_2);

		linkOppositePits(homePlayerOnePrevious, homePlayerOnePreviousOpposite);
		linkWithNextPit(homePlayerOnePrevious, homePlayerOne);
		linkWithNextPit(homePlayerOne, homePlayerOnePreviousOpposite);

		Pit firstPit = createBoard(parameters, homePlayerOnePrevious, homePlayerOnePreviousOpposite);

		linkWithNextPit(homePlayerTwo, firstPit);
		linkWithNextPit(firstPit.getOpposite(), homePlayerTwo);

		return firstPit;
	}

	private static Pit createBoard(GameBoardParameters parameters, Pit next, Pit nextOpposite) {
		int homePitNumberOfPlayerTwo = parameters.getHomePitNumberOfPlayerTwo();
		int stones = parameters.getStones();
		int currentNumber = next.getNumber() - 1;

		Pit current = Pit.createOrdinary(currentNumber, stones, PLAYER_1);
		Pit currentOpposite = Pit.createOrdinary(homePitNumberOfPlayerTwo - currentNumber, stones, PLAYER_2);

		linkOppositePits(current, currentOpposite);
		linkWithNextPit(current, next);
		linkWithNextPit(nextOpposite, currentOpposite);

		if (currentNumber == FIRST_PIT_NUMBER) {
			return current;
		}
		return createBoard(parameters, current, currentOpposite);
	}
}
