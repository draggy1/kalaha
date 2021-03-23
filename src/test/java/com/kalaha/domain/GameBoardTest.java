package com.kalaha.domain;

import static com.kalaha.domain.Fixtures.prepareInitialHead;
import static com.kalaha.domain.Player.PLAYER_1;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class GameBoardTest {
	int givenOrdinaryPitsSize = 6;
	int givenStones = 6;

	@Test
	void shouldCreateGameBoard() {
		Pit expected = prepareInitialHead(givenStones);

		GameBoard board = GameBoard.of(givenOrdinaryPitsSize, givenStones);

		Pit expectedPit = expected;
		Pit actual = board.getHead();
		while (expectedPit.getNumber() < 14) {
			assertThat(expectedPit).isEqualTo(actual);
			expectedPit = expectedPit.getNext();
			actual = actual.getNext();
		}
		assertThat(expectedPit).isEqualTo(actual);
		assertThat(expectedPit.getNext()).isEqualTo(actual.getNext());
	}

	@Test
	public void shouldFindByPit(){

	}

	@Test
	public void shouldMoveStones() {

	}
	@Test
	public void shouldFindHomeForPlayerWithTurn() {

	}
	@Test
	void shouldPrepareStatus() {

	}
	@Test
	void shouldCheckIfCanPlayersMakeMove() {

	}
}