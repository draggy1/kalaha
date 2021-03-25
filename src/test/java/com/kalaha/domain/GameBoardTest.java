package com.kalaha.domain;

import static com.kalaha.domain.Fixtures.checkIfBoardIsAsExpected;
import static com.kalaha.domain.Fixtures.prepareBoard;
import static com.kalaha.domain.Fixtures.prepareFinishingBoardWithEmptyAllPitsPlayerOne;
import static com.kalaha.domain.Fixtures.prepareFinishingBoardWithEmptyAllPitsPlayerTwo;
import static com.kalaha.domain.Fixtures.prepareInitialHead;
import static com.kalaha.domain.Player.PLAYER_1;
import static com.kalaha.domain.Player.PLAYER_2;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Test suite for {@link GameBoard} class
 */
class GameBoardTest {
	int givenOrdinaryPitsSize = 6;
	int givenStones = 6;

	@Test
	void shouldCreateGameBoard() {
		Pit expected = prepareInitialHead(givenStones);
		GameBoard board = GameBoard.of(givenOrdinaryPitsSize, givenStones);

		checkIfBoardIsAsExpected(expected, board.getHead());
	}

	@Test
	void shouldFindPitById() {
		Pit expected = Pit.createOrdinary(11, 6, PLAYER_2);
		GameBoard gameBoard = prepareBoard();
		Pit actual = gameBoard.findPitById(11);

		assertEquals(expected, actual);
	}

	@Test
	void shouldFindHomePitById() {
		Pit expected = Pit.createHomeForPlayerTwo(14, 0);
		GameBoard gameBoard = prepareBoard();
		Pit actual = gameBoard.findPitById(14);

		assertEquals(expected, actual);
	}

	@Test
	void shouldMoveStonesAroundBoard() {
		GameBoard given = prepareBoard();
		Pit expectedLast = given.findPitById(14);
		GameBoard expectedBoard = Fixtures.prepareExpectedBoardAfterMoveChosenPitWithNumberEightLandInHome();

		Pit movedStonesFromPit = given.findPitById(8);
		Pit actual = GameBoard.scatterStonesAroundBoard(movedStonesFromPit, PLAYER_2);

		assertEquals(expectedLast, actual);
		checkIfBoardIsAsExpected(expectedBoard.getHead(), given.getHead());
	}

	@Test
	void shouldFindHomeForPlayerOneAsPlayerWithTurn() {
		GameBoard given = prepareBoard();
		Pit last = given.findPitById(9);
		Pit expected = given.findPitById(7);
		Pit actual = given.findHomeForPlayerWithTurn(last, PLAYER_1);

		assertEquals(expected, actual);
	}

	@Test
	void shouldFindHomeForPlayerTwoAsPlayerWithTurn() {
		GameBoard given = prepareBoard();
		Pit last = given.findPitById(1);
		Pit expected = given.findPitById(14);
		Pit actual = given.findHomeForPlayerWithTurn(last, PLAYER_2);

		assertEquals(expected, actual);
	}

	@Test
	void shouldPrepareStatus() {
		GameBoard given = prepareBoard();
		Map<String, String> expected = Fixtures.prepareStatus();

		assertEquals(expected, given.prepareStatus());
	}

	@Test
	void shouldPlayersCanNotMakeMoveBecauseOfPlayerOneHasEmptyAllPits() {
		GameBoard given = prepareFinishingBoardWithEmptyAllPitsPlayerOne();
		assertThat(given.canPlayersMakeMove()).isFalse();
	}

	@Test
	void shouldPlayersCanNotMakeMoveBecauseOfPlayerTwoHasEmptyAllPits() {
		GameBoard given = prepareFinishingBoardWithEmptyAllPitsPlayerTwo();
		assertThat(given.canPlayersMakeMove()).isFalse();
	}

	@Test
	void shouldPlayersMakeMove() {
		GameBoard given = prepareBoard();
		assertThat(given.canPlayersMakeMove()).isTrue();
	}
}