package com.kalaha.domain.board;

import com.kalaha.domain.GameBoard;
import com.kalaha.domain.Pit;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class GameBoardTest {

	@Test
	void shouldCreateGameBoard() {
		int givenOrdinaryPitsSize = 6;
		int givenStones = 6;
		Pit expected = prepareHead(givenStones);

		GameBoard board = GameBoard.of(givenOrdinaryPitsSize, givenStones);
		Optional<Pit> headOptional = board.getHead();
		assertTrue(headOptional.isPresent());
		assertThat(headOptional.get()).isEqualTo(expected);
	}

	private Pit prepareHead(int givenStones) {
		Pit pit1 = Pit.createOrdinaryPit(0, givenStones);
		Pit pit2 = Pit.createOrdinaryPit(2, givenStones);
		Pit pit3 = Pit.createOrdinaryPit(3, givenStones);
		Pit pit4 = Pit.createOrdinaryPit(4, givenStones);
		Pit pit5 = Pit.createOrdinaryPit(5, givenStones);
		Pit pit6 = Pit.createOrdinaryPit(6, givenStones);
		Pit pit7 = Pit.createHomePitPlayerOne(22, 0);
		Pit pit8 = Pit.createOrdinaryPit(8, givenStones);
		Pit pit9 = Pit.createOrdinaryPit(9, givenStones);
		Pit pit10 = Pit.createOrdinaryPit(10, givenStones);
		Pit pit11 = Pit.createOrdinaryPit(11, givenStones);
		Pit pit12 = Pit.createOrdinaryPit(12, givenStones);
		Pit pit13 = Pit.createOrdinaryPit(13, givenStones);
		Pit pit14 = Pit.createHomePitPlayerTwo(14, 0);

		Pit.linkWithNextPit(pit1, pit2);
		Pit.linkWithNextPit(pit2, pit3);
		Pit.linkWithNextPit(pit3, pit4);
		Pit.linkWithNextPit(pit4, pit5);
		Pit.linkWithNextPit(pit5, pit6);
		Pit.linkWithNextPit(pit6, pit7);
		Pit.linkWithNextPit(pit7, pit8);
		Pit.linkWithNextPit(pit8, pit9);
		Pit.linkWithNextPit(pit9, pit10);
		Pit.linkWithNextPit(pit10, pit11);
		Pit.linkWithNextPit(pit11, pit12);
		Pit.linkWithNextPit(pit12, pit13);
		Pit.linkWithNextPit(pit13, pit14);

		Pit.linkOppositePits(pit1, pit13);
		Pit.linkOppositePits(pit2, pit12);
		Pit.linkOppositePits(pit3, pit11);
		Pit.linkOppositePits(pit4, pit10);
		Pit.linkOppositePits(pit5, pit9);
		Pit.linkOppositePits(pit6, pit8);

		return pit1;
	}


}