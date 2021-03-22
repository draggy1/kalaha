package com.kalaha.services;

import com.kalaha.config.GameConfig;
import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import com.kalaha.domain.Player;
import com.kalaha.services.dto.AfterMove;
import com.kalaha.services.dto.AfterMoveResponse;
import com.kalaha.services.dto.GameDetails;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

	@Test
	public void shouldCreateGame() {
		URI givenUri = URI.create("http://test:8080/");
		GameConfig givenConfig = new GameConfig(givenUri.toString(), 6, 6);
		GameDetails expected = GameDetails.of(1, URI.create("http://test:8080/games/1"));

		GameService tested = new GameService(givenConfig, new GameContainer());
		assertEquals(expected, tested.createGame());
	}

	@Test
	public void shouldMakeMove() {
		int givenOrdinaryPitsSize = 6;
		int givenStones = 6;
		int givenGameId = 1;
		int givenPitId = 1;

		URI givenUri = URI.create("http://test:8080/")
				.resolve(String.format("games/%d/", givenGameId))
				.resolve(String.format("pits/%d", givenPitId));

		GameConfig givenConfig = new GameConfig("http://test:8080/", givenOrdinaryPitsSize, givenStones);
		GameBoard givenGameBoard = GameBoard.of(givenOrdinaryPitsSize, givenStones);
		GameContainer givenContainer = new GameContainer();
		givenContainer.addGame(new Game(givenGameId, givenGameBoard));

		Map<Integer, Integer> status = prepareStatus();
		AfterMove givenAfterMove = AfterMove.of(givenGameId, givenUri, status, Player.PLAYER_1);
		AfterMoveResponse expected = AfterMoveResponse.createSuccessResponse(givenAfterMove, Validation.SUCCESS);

		GameService tested = new GameService(givenConfig,  givenContainer);

		AfterMoveResponse afterMoveResponse = tested.makeMove(givenGameId, givenPitId);
		Assert.assertEquals(expected, afterMoveResponse);
	}

	private Map<Integer, Integer> prepareStatus() {
		HashMap<Integer, Integer> status = new HashMap<>();
		status.put(1, 0);
		for (int i = 2; i < 7; i++) {
			status.put(i, 7);
		}
		status.put(7, 1);
		for (int i = 8; i < 14; i++) {
			status.put(i, 6);
		}
		status.put(14, 0);

		return status;
	}
}