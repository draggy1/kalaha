package com.kalaha.services;

import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import com.kalaha.domain.Player;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;

public enum Validator {
	FINISHED((game, pitId) -> game.isGameFinished()),
	GAME_NOT_FOUND((game, pitId) -> Objects.isNull(game)),
	NOT_CORRECT_PIT_NUMBER((game, pitId) -> isNotCorrectPitNumber(pitId, game)),
	CHOSEN_HOME_PIT((game, pitId) -> isItHomePit(game.getBoard(), pitId)),
	NOT_YOUR_TURN(Validator::isNotYourTurn),
	CHOSEN_PIT_WITHOUT_STONE(Game::isPitEmpty),
	SUCCESS((game, pitId) -> true);

	private final BiPredicate<Game, Integer> validation;

	Validator(BiPredicate<Game, Integer> validation) {
		this.validation = validation;
	}

	static Validator validate(Game game, int pitId) {
		return Arrays.stream(values())
				.filter(el -> el.validation.test(game, pitId))
				.findAny()
				.orElse(SUCCESS);
	}

	private static boolean isNotYourTurn(Game game, int pitId) {
		int ordinaryPitsSize = game.getBoard().getOrdinaryPitsNumber();
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
