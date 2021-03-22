package com.kalaha.services;

import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import com.kalaha.domain.Player;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;

public enum Validation {
	GAME_NOT_FOUND((game, pitId) -> Objects.isNull(game)),
	NOT_YOUR_TURN(Validation::isNotYourTurn),
	CHOSEN_HOME_PIT((game, pitId) -> isItHomePit(game.getBoard(), pitId)),
	CHOSEN_PIT_WITHOUT_STONE(Game::isPitEmpty),
	FINISHED((game, pitId) -> game.isGameFinished()),
	SUCCESS((game, pitId) -> true);

	private final BiPredicate<Game, Integer> validation;

	Validation(BiPredicate<Game, Integer> validation) {
		this.validation = validation;
	}

	static Validation validate(Game game, int pitId){
		return Arrays.stream(values())
				.filter(el -> el.validation.test(game, pitId))
				.findAny()
				.orElse(SUCCESS);
	}

	private static boolean isNotYourTurn(Game game, int pitId){
		int ordinaryPitsSize = game.getBoard().getOrdinaryPitsSize();
		Player whoMadeMove = pitId <= ordinaryPitsSize ? Player.PLAYER_1 : Player.PLAYER_2;
		return whoMadeMove != game.whoseTurn();
	}

	private static boolean isItHomePit(GameBoard board, int pitId) {
		return board.getHomePitNumberOfPlayerOne() == pitId || board.getHomePitNumberOfPlayerTwo() == pitId;
	}
}
