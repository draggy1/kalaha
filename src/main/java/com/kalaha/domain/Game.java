package com.kalaha.domain;

import static com.kalaha.domain.Player.PLAYER_1;
import com.kalaha.services.dto.AfterMove;
import java.net.URI;
import java.util.Arrays;
import lombok.Getter;

@Getter
public class Game {
	private final long gameId;
	private final GameBoard board;
	private Player playerWithTurn;

	public Game(long gameId, GameBoard board) {
		this.gameId = gameId;
		this.board = board;
		this.playerWithTurn = PLAYER_1;
	}

	public void makeMove(int pitId) {
		Boolean isLastPitHome = board.findPitById(pitId)
				.map(pit -> GameBoard.moveStones(pit, playerWithTurn))
				.map(this::moveStonesIfLastWasEmpty)
				.map(pit -> board.findHomeForPlayerWithTurn(pit, playerWithTurn) == pit)
				.orElse(false);

		playerWithTurn = isLastPitHome ? playerWithTurn : changeTurnToNextPlayer();
	}

	private Pit moveStonesIfLastWasEmpty(Pit last) {
		if(last.canGrabStonesFromOpposite(playerWithTurn)){
			Pit homePitForPlayerWithTurn = board.findHomeForPlayerWithTurn(last, playerWithTurn);
			Pit.moveStonesBetweenPits(last.getOpposite(), homePitForPlayerWithTurn);
			Pit.moveStonesBetweenPits(last, homePitForPlayerWithTurn);
		}
		return last;
	}

	private Player changeTurnToNextPlayer() {
		return Arrays.stream(Player.values())
				.filter(el -> el != playerWithTurn)
				.findAny()
				.orElse(PLAYER_1);
	}

	public AfterMove prepareResponse(URI uri) {
		return AfterMove.of(gameId, uri, board.prepareStatus(), playerWithTurn);
	}

	public boolean isPitEmpty(int pidId) {
		return board.findPitById(pidId)
				.map(pit -> pit.getStones().isPitEmpty())
				.orElse(true);
	}

	public Player whoseTurn() {
		return playerWithTurn;
	}
}
