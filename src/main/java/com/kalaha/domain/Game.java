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

	/**
	 * Method responsible for handling move provided by player. Method:
	 * 1. Finds pit with provided param
	 * 2. Gets stones and scatter around the board
	 * 3. Handle situation when last pit is empty
	 * 4. Handle situation when last pit is home pit
	 *
	 * @param pitId unique pit id
	 */
	public void makeMove(int pitId) {
		boolean isMovedToHomePit = Optional.of(board.findPitById(pitId))
				.map(pit -> GameBoard.scatterStonesAroundBoard(pit, playerWithTurn))
				.map(this::moveStonesIfLastWasEmpty)
				.map(this::isLastStoneScatteredToHomePit)
				.orElse(false);

		playerWithTurn = isMovedToHomePit ? playerWithTurn : changeTurnToNextPlayer();
	}

	/**
	 * Method prepares {@link Response} to return
	 *
	 * @param uri uri address used in response
	 * @return response of api call
	 */
	public Response prepareResponse(String uri) {
		return Response.of(gameId, uri, board.prepareStatus());
	}

	/**
	 * Checks if pit with provided id is empty
	 *
	 * @param pidId pitId unique pit id
	 * @return true when pit is empty, false when pit contains stones
	 */
	public boolean isPitEmpty(int pidId) {
		return Optional.of(board.findPitById(pidId))
				.map(pit -> pit.getStones().isPitEmpty())
				.orElse(true);
	}

	/**
	 * Checks who the turn belongs to
	 *
	 * @return Player who the turn belongs to
	 */
	public Player whoseTurn() {
		return playerWithTurn;
	}

	/**
	 * Checks who the turn belongs to
	 *
	 * @return Player who the turn belongs to
	 */
	public boolean isGameFinished() {
		return !board.canPlayersMakeMove();
	}

	/**
	 * Method handles board when one player has empty all pits
	 */
	public void handleFinishedGame() {
		Pit homePlayerOne = board.findPitById(board.getHomePitNumberOfPlayerOne());
		Pit homePlayerTwo = board.findPitById(board.getHomePitNumberOfPlayerTwo());

		Pit current = board.getHead();
		current = moveAllStonesToHome(homePlayerOne, current);

		current = current.getNext();
		moveAllStonesToHome(homePlayerTwo, current);
	}

	private Pit moveAllStonesToHome(Pit homePlayer, Pit current) {
		while (current.getNumber() != homePlayer.getNumber()) {
			if (current.getStones().isPitNotEmpty()) {
				Pit.moveStonesToPit(current, homePlayer);
			}
			current = current.getNext();
		}
		return current;
	}

	private boolean isLastStoneScatteredToHomePit(Pit last) {
		return board.findHomeForPlayerWithTurn(last, playerWithTurn) == last;
	}

	private Pit moveStonesIfLastWasEmpty(Pit last) {
		if (last.canGrabStonesFromOpposite(playerWithTurn)) {
			Pit homePitForPlayerWithTurn = board.findHomeForPlayerWithTurn(last, playerWithTurn);
			Pit.moveStonesToPit(last.getOpposite(), homePitForPlayerWithTurn);
			Pit.moveStonesToPit(last, homePitForPlayerWithTurn);
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
