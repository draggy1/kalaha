package com.kalaha.services;

import com.kalaha.config.GameConfig;
import static com.kalaha.domain.Fixtures.getUri;
import static com.kalaha.domain.Fixtures.prepareBoard;
import static com.kalaha.domain.Fixtures.prepareFinishingBoardWithEmptyAllPitsPlayerOne;
import com.kalaha.domain.Game;
import com.kalaha.domain.GameBoard;
import static com.kalaha.domain.Player.PLAYER_1;
import static com.kalaha.domain.Player.PLAYER_2;
import static com.kalaha.services.Validator.CHOSEN_HOME_PIT;
import static com.kalaha.services.Validator.CHOSEN_PIT_WITHOUT_STONE;
import static com.kalaha.services.Validator.FINISHED;
import static com.kalaha.services.Validator.NOT_CORRECT_PIT_NUMBER;
import static com.kalaha.services.Validator.NOT_YOUR_TURN;
import static com.kalaha.services.Validator.SUCCESS;
import com.kalaha.services.dto.GameDetails;
import com.kalaha.services.dto.Response;
import com.kalaha.services.dto.ResponseWithValidationResult;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test suite for {@link GameService} class
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
	private final int givenOrdinaryPitsSize = 6;
	private final int givenStones = 6;
	private final int givenGameId = 1;
	private final GameConfig givenConfig = new GameConfig("http://test:8080/", givenOrdinaryPitsSize, givenStones);
	private final GameContainer givenContainer = new GameContainer();

	@Test
	public void shouldCreateGame() {
		GameDetails expected = GameDetails.of(1, "http://test:8080/games/1");
		GameService tested = new GameService(givenConfig, new GameContainer());
		assertEquals(expected, tested.createGame());
	}

	@Test
	public void shouldMakeSuccessfulMove() {
		int givenPitId = 2;
		String uri = getUri(givenPitId, givenGameId);

		GameBoard givenGameBoard = prepareBoard();
		givenContainer.addGame(new Game(givenGameId, givenGameBoard, PLAYER_1));

		Map<String, String> status = prepareStatusSuccessfulMove();
		ResponseWithValidationResult expected = ResponseWithValidationResult.of(Response.of(givenGameId, uri, status), SUCCESS);

		GameService tested = new GameService(givenConfig, givenContainer);

		ResponseWithValidationResult responseWithValidationResult = tested.makeMove(givenGameId, givenPitId);
		assertEquals(expected, responseWithValidationResult);
	}

	@Test
	public void shouldMakeFinishingMove() {
		int givenPitId = 9;
		String uri = getUri(givenPitId, givenGameId);

		GameBoard givenGameBoard = prepareFinishingBoardWithEmptyAllPitsPlayerOne();
		givenContainer.addGame(new Game(givenGameId, givenGameBoard, PLAYER_2));

		Map<String, String> status = prepareFinishingStatus();
		ResponseWithValidationResult expected = ResponseWithValidationResult.of(Response.of(givenGameId, uri, status), FINISHED);

		GameService tested = new GameService(givenConfig, givenContainer);

		ResponseWithValidationResult responseWithValidationResult = tested.makeMove(givenGameId, givenPitId);
		assertEquals(expected, responseWithValidationResult);
	}

	@Test
	public void shouldNotLetMakeMoveBecauseOfNotCorrectTurn() {
		int givenPitId = 8;

		String uri = getUri(givenPitId, givenGameId);

		GameBoard givenGameBoard = prepareBoard();
		givenContainer.addGame(new Game(givenGameId, givenGameBoard, PLAYER_1));

		Map<String, String> status = prepareStatus();
		ResponseWithValidationResult expected = ResponseWithValidationResult.of(Response.of(givenGameId, uri, status), NOT_YOUR_TURN);

		GameService tested = new GameService(givenConfig, givenContainer);

		ResponseWithValidationResult responseWithValidationResult = tested.makeMove(givenGameId, givenPitId);
		assertEquals(expected, responseWithValidationResult);
	}

	@Test
	public void shouldNotLetMakeMoveBecauseOfChosenHomePit() {
		int givenPitId = 7;

		String uri = getUri(givenPitId, givenGameId);

		GameBoard givenGameBoard = prepareBoard();
		givenContainer.addGame(new Game(givenGameId, givenGameBoard, PLAYER_1));

		Map<String, String> status = prepareStatus();
		ResponseWithValidationResult expected = ResponseWithValidationResult.of(Response.of(givenGameId, uri, status), CHOSEN_HOME_PIT);

		GameService tested = new GameService(givenConfig, givenContainer);

		ResponseWithValidationResult responseWithValidationResult = tested.makeMove(givenGameId, givenPitId);
		assertEquals(expected, responseWithValidationResult);
	}

	@Test
	public void shouldNotLetMakeMoveBecauseOfChosenPitWithoutStone() {
		int givenPitId = 1;

		String uri = getUri(givenPitId, givenGameId);

		GameBoard givenGameBoard = prepareBoard();
		givenContainer.addGame(new Game(givenGameId, givenGameBoard, PLAYER_1));

		Map<String, String> status = prepareStatus();
		ResponseWithValidationResult expected = ResponseWithValidationResult.of(Response.of(givenGameId, uri, status), CHOSEN_PIT_WITHOUT_STONE);

		GameService tested = new GameService(givenConfig, givenContainer);

		ResponseWithValidationResult responseWithValidationResult = tested.makeMove(givenGameId, givenPitId);
		assertEquals(expected, responseWithValidationResult);
	}

	@Test
	public void shouldNotLetMakeMoveBecauseOfNotCorrectChosenStone() {
		int givenPitId = 28;

		String uri = getUri(givenPitId, givenGameId);

		GameBoard givenGameBoard = prepareBoard();
		givenContainer.addGame(new Game(givenGameId, givenGameBoard, PLAYER_1));

		Map<String, String> status = prepareStatus();
		ResponseWithValidationResult expected = ResponseWithValidationResult.of(Response.of(givenGameId, uri, status), NOT_CORRECT_PIT_NUMBER);

		GameService tested = new GameService(givenConfig, givenContainer);

		ResponseWithValidationResult responseWithValidationResult = tested.makeMove(givenGameId, givenPitId);
		assertEquals(expected, responseWithValidationResult);
	}

	private Map<String, String> prepareStatusSuccessfulMove() {
		HashMap<String, String> status = new HashMap<>();
		for (int i = 1; i < 3; i++) {
			status.put(String.valueOf(i), "0");
		}
		for (int i = 3; i < 7; i++) {
			status.put(String.valueOf(i), "8");
		}
		status.put("7", "2");
		status.put("8", "7");
		status.put("9", "7");
		for (int i = 10; i < 14; i++) {
			status.put(String.valueOf(i), "6");
		}
		status.put("14", "0");
		return status;
	}

	private Map<String, String> prepareStatus() {
		HashMap<String, String> status = new HashMap<>();
		status.put("1", "0");
		for (int i = 2; i < 7; i++) {
			status.put(String.valueOf(i), "7");
		}
		status.put("7", "1");
		for (int i = 8; i < 14; i++) {
			status.put(String.valueOf(i), "6");
		}
		status.put("14", "0");
		return status;
	}

	private Map<String, String> prepareFinishingStatus() {
		HashMap<String, String> status = new HashMap<>();
		for (int i = 1; i < 7; i++) {
			status.put(String.valueOf(i), "0");
		}
		status.put("7", "34");
		for (int i = 8; i < 14; i++) {
			status.put(String.valueOf(i), "0");
		}
		status.put("14", "32");
		return status;
	}
}