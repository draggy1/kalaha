package com.kalaha.services;

import com.kalaha.config.GameConfig;
import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import com.kalaha.services.dto.AfterMoveResponse;
import com.kalaha.services.dto.GameDetails;
import java.net.URI;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class GameService {
	private final GameConfig config;
	private final GameContainer container;

	public GameService(GameConfig config, GameContainer container) {
		this.config = config;
		this.container = container;
	}

	public GameDetails createGame() {
		long gameId = container.getGameCounter();
		URI uri = URI.create(config.getUrl())
				.resolve("games/")
				.resolve(String.valueOf(gameId));

		GameBoard board = GameBoard.of(config.getOrdinaryPitsSize(), config.getStones());
		container.addGame(new Game(gameId, board));
		return GameDetails.of(gameId, uri);
	}

	public AfterMoveResponse makeMove(long gameId, int pitId) {
		Game game = container.getGameContainer().get(gameId);

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
