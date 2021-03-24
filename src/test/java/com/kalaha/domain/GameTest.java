package com.kalaha.domain;

import static com.kalaha.domain.Fixtures.checkIfBoardIsAsExpected;
import static com.kalaha.domain.Fixtures.getUri;
import static com.kalaha.domain.Fixtures.prepareBoard;
import static com.kalaha.domain.Fixtures.prepareExpectedBoardAfterMoveChosenPitWithNumberEightLandInHome;
import static com.kalaha.domain.Fixtures.prepareExpectedBoardAfterMoveLandInEmpty;
import static com.kalaha.domain.Fixtures.prepareFinishingBoardWithEmptyAllPitsPlayerOne;
import static com.kalaha.domain.Fixtures.prepareFinishingBoardWithEmptyAllPitsPlayerOneAfterHandledFinish;
import static com.kalaha.domain.Fixtures.prepareFinishingBoardWithEmptyAllPitsPlayerTwo;
import static com.kalaha.domain.Fixtures.prepareFinishingBoardWithEmptyAllPitsPlayerTwoAfterHandledFinish;
import static com.kalaha.domain.Fixtures.prepareGivenBoardAfterMoveLandInEmpty;
import static com.kalaha.domain.Fixtures.prepareStatus;
import static com.kalaha.domain.Player.PLAYER_1;
import static com.kalaha.domain.Player.PLAYER_2;
import com.kalaha.services.dto.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

class GameTest {

	@Test
	void shouldMakeMove() {
		GameBoard expected = prepareExpectedBoardAfterMoveChosenPitWithNumberEightLandInHome();
		GameBoard board = prepareBoard();
		Game tested = new Game(1, board, PLAYER_2);

		tested.makeMove(8);

		assertEquals(1, tested.getGameId());
		assertEquals(PLAYER_2, tested.getPlayerWithTurn());
		checkIfBoardIsAsExpected(expected.getHead(), board.getHead());
	}

	@Test
	void shouldMakeMoveAfterLandInEmptyPit() {
		GameBoard board = prepareGivenBoardAfterMoveLandInEmpty();
		GameBoard expected = prepareExpectedBoardAfterMoveLandInEmpty();
		Game tested = new Game(1, board, PLAYER_2);

		tested.makeMove(9);

		assertEquals(1, tested.getGameId());
		assertEquals(PLAYER_1, tested.getPlayerWithTurn());
		checkIfBoardIsAsExpected(expected.getHead(), board.getHead());
	}

	@Test
	void shouldPrepareResponse() {
		GameBoard givenBoard = prepareBoard();
		Response expected = Response.of(1, getUri(8, 1), prepareStatus());
		Game tested = new Game(1, givenBoard, PLAYER_2);

		Response actual = tested.prepareResponse(getUri(8, 1));
		assertEquals(expected, actual);
	}

	@Test
	void shouldPitBeEmpty() {
		Game tested = new Game(1, prepareBoard(), PLAYER_2);
		boolean actual = tested.isPitEmpty(1);
		assertTrue(actual);
	}

	@Test
	void shouldPitNotBeEmpty() {
		Game tested = new Game(1, prepareBoard(), PLAYER_2);
		boolean actual = tested.isPitEmpty(2);
		assertFalse(actual);
	}

	@Test
	void shouldBePlayerOneTurn() {
		Game tested = new Game(1, prepareBoard(), PLAYER_1);
		assertEquals(PLAYER_1, tested.whoseTurn());
	}

	@Test
	void shouldBePlayerTwoTurn() {
		Game tested = new Game(1, prepareBoard(), PLAYER_2);
		assertEquals(PLAYER_2, tested.whoseTurn());
	}

	@Test
	void shouldGameNotBeFinished() {
		Game tested = new Game(1, prepareBoard(), PLAYER_2);
		assertFalse(tested.isGameFinished());
	}

	@Test
	void shouldGameBeFinished() {
		Game tested = new Game(1, prepareFinishingBoardWithEmptyAllPitsPlayerOne(), PLAYER_2);
		assertTrue(tested.isGameFinished());
	}

	@Test
	void shouldHandleFinishedGameEmptyPlayerOnePits() {
		GameBoard expected = prepareFinishingBoardWithEmptyAllPitsPlayerOneAfterHandledFinish();
		Game tested = new Game(1, prepareFinishingBoardWithEmptyAllPitsPlayerOne(), PLAYER_2);
		tested.handleFinishedGame();

		checkIfBoardIsAsExpected(expected.getHead(), tested.getBoard().getHead());
	}

	@Test
	void shouldHandleFinishedGameEmptyPlayerTwoPits() {
		GameBoard expected = prepareFinishingBoardWithEmptyAllPitsPlayerTwoAfterHandledFinish();
		Game tested = new Game(1, prepareFinishingBoardWithEmptyAllPitsPlayerTwo(), PLAYER_1);
		tested.handleFinishedGame();

		checkIfBoardIsAsExpected(expected.getHead(), tested.getBoard().getHead());
	}
}