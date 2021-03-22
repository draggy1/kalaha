package com.kalaha.domain;

import com.kalaha.services.dto.AfterMove;
import java.net.URI;
import java.util.Arrays;

public class Game {
	private final GameBoard board;
	private Player playerWithTurn;

	public Game(GameBoard board) {
		this.board = board;
		this.playerWithTurn = Player.PLAYER_1;
	}

	public void makeMove(int pitId) {
		Boolean isLastPitHome = board.findPitById(pitId)
				.map(pit -> board.moveStones(pit, playerWithTurn))
				.map(this::moveStonesIfLastWasEmpty)
				.map(pit -> board.findHomePitForPlayerWithTurn(pit, playerWithTurn) == pit)
				.orElse(false);

		playerWithTurn = isLastPitHome ? playerWithTurn : changeTurnToNextPlayer();
	}

	private Pit moveStonesIfLastWasEmpty(Pit last) {
		if(last.isPitOrdinary() && last.wasLastPitEmpty()){
			Pit homePitForPlayerWithTurn = board.findHomePitForPlayerWithTurn(last, playerWithTurn);
			Pit.moveStonesBetweenPits(last.getOpposite(), homePitForPlayerWithTurn);
		}
		return last;
	}

	private Player changeTurnToNextPlayer() {
		return Arrays.stream(Player.values())
				.filter(el -> el != playerWithTurn)
				.findAny()
				.orElse(Player.PLAYER_1);
	}

	public AfterMove prepareResponse(long gameId, URI uri) {
		return AfterMove.of(gameId, uri, board.prepareStatus(), playerWithTurn);
	}

	public boolean hasPitZeroStones(int pidId) {
		return getBoard().findPitById(pidId)
				.map(Pit::hasPitZeroStones)
				.orElse(true);
	}

	public enum Player {
		PLAYER_1,
		PLAYER_2
	}

	public Player whoseTurn() {
		return playerWithTurn;
	}

	public GameBoard getBoard() {
		return board;
	}
}
