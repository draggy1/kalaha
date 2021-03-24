package com.kalaha.domain;

import static com.kalaha.domain.Player.PLAYER_1;
import com.kalaha.services.dto.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
@ApiModel("Single game representation")
public class Game {
	@ApiModelProperty(notes = "Unique id of game", name = "gameId")
	private final long gameId;
	@ApiModelProperty(notes = "Representation of game board", name = "board")
	private final GameBoard board;
	@ApiModelProperty(notes = "Player who has current turn", name = "playerWithTurn")
	private Player playerWithTurn;

	public Game(long gameId, GameBoard board, Player whoseTurn) {
		this.gameId = gameId;
		this.board = board;
		this.playerWithTurn = whoseTurn;
	}

	public void makeMove(int pitId) {
		boolean isMovedToHomePit = Optional.of(board.findPitById(pitId))
				.map(pit -> GameBoard.moveStonesAroundBoard(pit, playerWithTurn))
				.map(this::moveStonesIfLastWasEmpty)
				.map(this::isMovedToHomePit)
				.orElse(false);

		playerWithTurn = isMovedToHomePit ? playerWithTurn : changeTurnToNextPlayer();
	}

	public Response prepareResponse(String uri) {
		return Response.of(gameId, uri, board.prepareStatus());
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
