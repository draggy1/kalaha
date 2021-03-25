package com.kalaha.services;

import com.kalaha.config.GameConfig;
import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import static com.kalaha.domain.Player.PLAYER_1;
import com.kalaha.services.dto.GameDetails;
import com.kalaha.services.dto.ResponseWithValidationResult;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
@AllArgsConstructor
public class GameService {
	private final GameConfig config;
	private final GameContainer container;

	public GameDetails createGame() {
		long gameId = container.getGameCounter();
		String uri = URI.create(config.getUrl())
				.resolve("games/")
				.resolve(String.valueOf(gameId))
				.toString();

		GameBoard board = GameBoard.of(config.getOrdinaryPitsNumber(), config.getStones());
		container.addGame(new Game(gameId, board, PLAYER_1));
		return GameDetails.of(gameId, uri);
	}

	public ResponseWithValidationResult makeMove(long gameId, int pitId) {
		Game game = container.getContainer().get(gameId);

		Validator validator = Validator.validate(game, pitId);
		return getResponse(pitId, game, validator);
	}

	private ResponseWithValidationResult getResponse(int pitId, Game game, Validator result) {
		return switch (result) {
			case SUCCESS -> handleSuccess(pitId, game, result);
			case FINISHED -> handleFinish(pitId, game, result);
			default -> ResponseWithValidationResult.of(game.prepareResponse(getUri(pitId, game)), result);
		};
	}

	private ResponseWithValidationResult handleSuccess(int pitId, Game game, Validator result) {
		String uri = getUri(pitId, game);
		game.makeMove(pitId);
		return ResponseWithValidationResult.of(game.prepareResponse(uri), result);
	}

	private ResponseWithValidationResult handleFinish(int pitId, Game game, Validator result) {
		String uri = getUri(pitId, game);
		game.handleFinishedGame();
		return ResponseWithValidationResult.of(game.prepareResponse(uri), result);
	}

	private String getUri(int pitId, Game game) {
		return URI.create(config.getUrl())
				.resolve(String.format("games/%d/", game.getGameId()))
				.resolve(String.format("pits/%d", pitId))
				.toString();
	}
}
