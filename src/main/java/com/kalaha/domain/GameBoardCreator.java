package com.kalaha.domain;

import static com.kalaha.domain.Pit.linkOppositePits;
import static com.kalaha.domain.Pit.linkWithNextPit;
import static com.kalaha.domain.Player.PLAYER_1;
import static com.kalaha.domain.Player.PLAYER_2;

public class GameBoardCreator {
	private static final int FIRST_PIT_NUMBER = 1;
	static Pit initBoard(GameBoardParameters parameters) {
		Pit homePlayerOne = Pit.createHomeForPlayerOne(parameters.getHomePitNumberOfPlayerOne(), 0);
		Pit homePlayerTwo = Pit.createHomeForPlayerTwo(parameters.getHomePitNumberOfPlayerTwo(), 0);

		Pit homePlayerOnePrevious = Pit.createOrdinary(parameters.getOrdinaryPitsSize(), parameters.getStones(), PLAYER_1);
		Pit homePlayerOnePreviousOpposite =
				Pit.createOrdinary(calculateNumber(parameters, parameters.getOrdinaryPitsSize()), parameters.getStones(), PLAYER_2);

		linkOppositePits(homePlayerOnePrevious, homePlayerOnePreviousOpposite);
		linkWithNextPit(homePlayerOnePrevious, homePlayerOne);
		linkWithNextPit(homePlayerOne, homePlayerOnePreviousOpposite);

		Pit firstPit = createBoard(parameters, homePlayerOnePrevious, homePlayerOnePreviousOpposite);

		linkWithNextPit(homePlayerTwo, firstPit);
		linkWithNextPit(firstPit.getOpposite(), homePlayerTwo);

		return firstPit;
	}

	private static Pit createBoard(GameBoardParameters parameters, Pit next, Pit nextOpposite) {
		int currentNumber = next.getNumber() - 1;

		Pit current = Pit.createOrdinary(currentNumber, parameters.getStones(), PLAYER_1);
		Pit currentOpposite = Pit.createOrdinary(calculateNumber(parameters, currentNumber), parameters.getStones(), PLAYER_2);

		linkOppositePits(current, currentOpposite);
		linkWithNextPit(current, next);
		linkWithNextPit(nextOpposite, currentOpposite);

		if (currentNumber == FIRST_PIT_NUMBER) {
			return current;
		}
		return createBoard(parameters, current, currentOpposite);
	}

	private static int calculateNumber(GameBoardParameters parameters, int currentNumber) {
		return parameters.getHomePitNumberOfPlayerTwo() - currentNumber;
	}
}
