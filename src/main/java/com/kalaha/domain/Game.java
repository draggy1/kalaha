package com.kalaha.domain;

import static com.kalaha.domain.Player.PLAYER_1;
import com.kalaha.services.Validator;
import com.kalaha.services.dto.Response;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public class Game {
	private final long gameId;
	private final GameBoard board;
	private Player playerWithTurn;

	public Game(long gameId, GameBoard board, Player whoseTurn) {
		this.gameId = gameId;
		this.board = board;
		this.playerWithTurn = whoseTurn;
	}

	public void makeMove(int pitId) {
		Boolean isMovedToHomePit = Optional.of(board.findPitById(pitId))
				.map(pit -> GameBoard.moveStonesAroundBoard(pit, playerWithTurn))
				.map(this::moveStonesIfLastWasEmpty)
				.map(this::isMovedToHomePit)
				.orElse(false);

		playerWithTurn = isMovedToHomePit ? playerWithTurn : changeTurnToNextPlayer();
	}

	public Response prepareResponse(URI uri, Validator result) {
		return Response.of(gameId, uri, board.prepareStatus(), playerWithTurn, result.getMessage());
	}

	public boolean isPitEmpty(int pidId) {
		return Optional.of(board.findPitById(pidId))
				.map(pit -> pit.getStones().isPitEmpty())
				.orElse(true);
	}

	public Player whoseTurn() {
		return playerWithTurn;
	}

	public boolean isGameFinished() {
		return !board.canPlayersMakeMove();
	}

	public void handleFinishedGame() {
		Pit current = board.getHead();
		Pit homePlayerOne = board.findPitById(board.getHomePitNumberOfPlayerOne());

		while (current.getNumber() != board.getHomePitNumberOfPlayerOne()) {
			if (current.getStones().isPitNotEmpty()) {
				Pit.moveStonesBetweenPits(current, homePlayerOne);
			}
			current = current.getNext();
		}

		current = current.getNext();
		Pit homePlayerTwo = board.findPitById(board.getHomePitNumberOfPlayerTwo());

		while (current.getNumber() != board.getHomePitNumberOfPlayerTwo()) {
			if (current.getStones().isPitNotEmpty()) {
				Pit.moveStonesBetweenPits(current, homePlayerTwo);
			}
			current = current.getNext();
		}
	}

	private boolean isMovedToHomePit(Pit last) {
		return board.findHomeForPlayerWithTurn(last, playerWithTurn) == last;
	}

	private Pit moveStonesIfLastWasEmpty(Pit last) {
		if (last.canGrabStonesFromOpposite(playerWithTurn)) {
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
}
