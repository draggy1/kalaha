package com.kalaha.domain;

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
		Pit head = board.getHead();


		Pit expectedPit = expected;
		Pit actual = head;

		while(expectedPit.getNumber() < 14){
			assertThat(expectedPit).isEqualTo(actual);
			expectedPit = expectedPit.getNext();
			actual = actual.getNext();
		}
		assertThat(expectedPit).isEqualTo(actual);
	}

	private Pit prepareHead(int givenStones) {
		Pit pit1 = Pit.createOrdinary(1, givenStones, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, givenStones, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, givenStones, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, givenStones, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, givenStones, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, givenStones, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7);
		Pit pit8 = Pit.createOrdinary(8, givenStones, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, givenStones, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, givenStones, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, givenStones, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, givenStones, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, givenStones, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14);

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