package com.kalaha.domain;

import static com.kalaha.domain.Pit.linkOppositePits;
import static com.kalaha.domain.Pit.linkWithNextPit;
import static com.kalaha.domain.Player.PLAYER_1;
import static com.kalaha.domain.Player.PLAYER_2;
import lombok.Value;

/**
 * Class responsible for creating the game board
 */
@Value(staticConstructor = "of")
public class GameBoardCreator {
	private static final int FIRST_PIT_NUMBER = 1;
	int stones;
	int homePitNumberOfPlayerOne;
	int homePitNumberOfPlayerTwo;

	/**
	 * Method creates:
	 * 1. Two home pits
	 * 2. Ordinary pits in the recursion way
	 *
	 * @return first pit (with number one) and it has pointer to next one and so on
	 */
	Pit initBoard() {
		Pit homePlayerOne = Pit.createHomeForPlayerOne(homePitNumberOfPlayerOne, 0);
		Pit homePlayerTwo = Pit.createHomeForPlayerTwo(homePitNumberOfPlayerTwo, 0);

		int pitBeforeHomePlayerOne = homePitNumberOfPlayerOne - 1;
		Pit homePlayerOnePrevious = Pit.createOrdinary(pitBeforeHomePlayerOne, stones, PLAYER_1);
		Pit homePlayerOnePreviousOpposite =
				Pit.createOrdinary(homePitNumberOfPlayerTwo - pitBeforeHomePlayerOne, stones, PLAYER_2);

		linkOppositePits(homePlayerOnePrevious, homePlayerOnePreviousOpposite);
		linkWithNextPit(homePlayerOnePrevious, homePlayerOne);
		linkWithNextPit(homePlayerOne, homePlayerOnePreviousOpposite);

		Pit firstPit = createBoard(homePlayerOnePrevious, homePlayerOnePreviousOpposite);

		linkWithNextPit(homePlayerTwo, firstPit);
		linkWithNextPit(firstPit.getOpposite(), homePlayerTwo);

		return firstPit;
	}

	private Pit createBoard(Pit next, Pit nextOpposite) {
		int currentNumber = next.getNumber() - 1;

		Pit current = Pit.createOrdinary(currentNumber, stones, PLAYER_1);
		Pit currentOpposite = Pit.createOrdinary(homePitNumberOfPlayerTwo - currentNumber, stones, PLAYER_2);

		linkOppositePits(current, currentOpposite);
		linkWithNextPit(current, next);
		linkWithNextPit(nextOpposite, currentOpposite);

		if (currentNumber == FIRST_PIT_NUMBER) {
			return current;
		}
		return createBoard(current, currentOpposite);
	}
}
