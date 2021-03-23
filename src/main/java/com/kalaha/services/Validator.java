package com.kalaha.services;

import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import com.kalaha.domain.Player;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;

public enum Validator {
	GAME_NOT_FOUND((game, pitId) -> Objects.isNull(game), "Game not found"),
	NOT_CORRECT_PIT_NUMBER((game, pitId) -> isNotCorrectPitNumber(pitId, game), "Pit not found - too high or low number"),
	CHOSEN_HOME_PIT((game, pitId) -> isItHomePit(game.getBoard(), pitId), "Chosen home pit"),
	NOT_YOUR_TURN(Validator::isNotYourTurn, "Not your turn"),
	CHOSEN_PIT_WITHOUT_STONE(Game::isPitEmpty, "Chosen pit without stone"),
	FINISHED((game, pitId) -> game.isGameFinished(), "Game finished"),
	SUCCESS((game, pitId) -> true, "Move performed successfully");

	private final BiPredicate<Game, Integer> validation;
	private final String message;

	Validator(BiPredicate<Game, Integer> validation, String message) {
		this.validation = validation;
		this.message = message;
	}

	static Validator validate(Game game, int pitId) {
		return Arrays.stream(values())
				.filter(el -> el.validation.test(game, pitId))
				.findAny()
				.orElse(SUCCESS);
	}

	public String getMessage() {
		return message;
	}

	private static boolean isNotYourTurn(Game game, int pitId) {
		int ordinaryPitsSize = game.getBoard().getOrdinaryPitsSize();
		Player whoMadeMove = pitId <= ordinaryPitsSize ? Player.PLAYER_1 : Player.PLAYER_2;
		return whoMadeMove != game.whoseTurn();
	}

	private static boolean isNotCorrectPitNumber(int pitId, Game game) {
		int homePitNumberOfPlayerTwo = game.getBoard().getHomePitNumberOfPlayerTwo();
		return pitId > homePitNumberOfPlayerTwo || pitId <= 0;
	}

	private static boolean isItHomePit(GameBoard board, int pitId) {
		return board.getHomePitNumberOfPlayerOne() == pitId || board.getHomePitNumberOfPlayerTwo() == pitId;
	}
}
