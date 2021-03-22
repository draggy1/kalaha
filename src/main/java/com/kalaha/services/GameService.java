package com.kalaha.services;

import com.kalaha.config.GameConfig;
import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import com.kalaha.services.dto.AfterMoveResponse;
import com.kalaha.services.dto.GameDetails;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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

	public GameDetails createGame() {
		URI uri = URI.create(config.getUrl())
				.resolve("games/")
				.resolve(String.valueOf(GAME_COUNTER));

		GameBoard board = GameBoard.of(config.getOrdinaryPitsSize(), config.getStones());
		GAMES_CONTAINER.put(GAME_COUNTER, new Game(GAME_COUNTER, board));

		return GameDetails.of(GAME_COUNTER++, uri);
	}

	public AfterMoveResponse makeMove(long gameId, int pitId) {
		Game game = GAMES_CONTAINER.get(gameId);

		Validation result = Validation.validate(game, pitId);
		return getResponse(pitId, game, result);
	}

	private AfterMoveResponse getResponse(int pitId, Game game, Validation result) {
		return switch (result) {
			case SUCCESS -> handleSuccess(pitId, game, result);
			case FINISHED -> handleFinish(pitId, game, result);
			default -> AfterMoveResponse.createFailedResponse(result);
		};
	}

	private AfterMoveResponse handleSuccess(int pitId, Game game, Validation result) {
		URI uri = getUri(pitId,game);
		game.makeMove(pitId);
		return AfterMoveResponse.createSuccessResponse(game.prepareResponse(uri), result);
	}

	private AfterMoveResponse handleFinish(int pitId, Game game, Validation result) {
		URI uri = getUri(pitId,game);
		game.handleFinish();
		return AfterMoveResponse.createSuccessResponse(game.prepareResponse(uri), result);
	}

	private URI getUri(int pitId, Game game){
		return URI.create(config.getUrl())
				.resolve(String.format("games/%d/", game.getGameId()))
				.resolve(String.format("pits/%d", pitId));
	}
}
