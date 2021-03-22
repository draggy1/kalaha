package com.kalaha.services;

import com.kalaha.config.GameConfig;
import com.kalaha.domain.Game;
import com.kalaha.domain.Game.Player;
import com.kalaha.domain.GameBoard;
import com.kalaha.services.dto.AfterMoveResponse;
import com.kalaha.services.dto.GameDetails;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	private final Map<Long, Game> GAMES_CONTAINER;
	private long GAME_COUNTER = 1;
	private final GameConfig config;

	public GameService(GameConfig config) {
		this.config = config;
		GAMES_CONTAINER = new HashMap<>();
	}

	public GameDetails createGame(){
		URI uri = URI.create(config.getUrl())
				.resolve("games/")
				.resolve(String.valueOf(GAME_COUNTER));

		GameBoard board = GameBoard.of(config.getOrdinaryPitsSize(), config.getStones());
		GAMES_CONTAINER.put(GAME_COUNTER, new Game(board));

		return GameDetails.of(GAME_COUNTER++, uri);
	}

	public AfterMoveResponse makeMove(long gameId, int pitId) {
		URI uri = URI.create(config.getUrl())
				.resolve(String.format("games/%d/", gameId))
				.resolve(String.format("pits/%d", pitId));

		Game game = GAMES_CONTAINER.get(gameId);
		if(Objects.isNull(game)){
			return AfterMoveResponse.createGameNotFoundResponse();
		}
		if(!isYourTurn(game.whoseTurn(), pitId)){
			return AfterMoveResponse.createNotYourTurnResponse();
		}
		if(isChosenHomePit(game.getBoard(), pitId)){
			return AfterMoveResponse.createChosenHomePitResponse();
		}
		if(game.hasPitZeroStones(pitId)){
			return AfterMoveResponse.createChosenPitWithoutStoneResponse();
		}
		game.makeMove(pitId);
		return AfterMoveResponse.createSuccessResponse(game.prepareResponse(gameId, uri));
	}

	private boolean isChosenHomePit(GameBoard board, int pitId) {
		return board.getHomePitNumberOfPlayerOne() == pitId || board.getHomePitNumberOfPlayerTwo() == pitId;
	}

	private boolean isYourTurn(Player player, int pitId){
		int ordinaryPitsSize = config.getOrdinaryPitsSize();
		Player whoMadeMove = pitId <= ordinaryPitsSize ? Player.PLAYER_1 : Player.PLAYER_2;
		return whoMadeMove == player;
	}
}
