package com.kalaha.controllers;

import com.kalaha.services.GameService;
import static com.kalaha.services.Validator.CHOSEN_HOME_PIT;
import static com.kalaha.services.Validator.CHOSEN_PIT_WITHOUT_STONE;
import static com.kalaha.services.Validator.GAME_NOT_FOUND;
import static com.kalaha.services.Validator.NOT_YOUR_TURN;
import static com.kalaha.services.Validator.NOT_CORRECT_PIT_NUMBER;
import com.kalaha.services.dto.GameDetails;
import com.kalaha.services.dto.Response;
import com.kalaha.services.dto.ResponseWithValidationResult;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@ResponseBody
	@PostMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameDetails> createGame() {
		return new ResponseEntity<>(gameService.createGame(), HttpStatus.CREATED);
	}

	@ResponseBody
	@PutMapping(value = "/games/{gameId}/pits/{pitId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> makeMove(@PathVariable long gameId, @PathVariable int pitId) {
		ResponseWithValidationResult response = gameService.makeMove(gameId, pitId);

		if (GAME_NOT_FOUND == response.getResult()) {
			return ResponseEntity.notFound().build();
		}
		if (Set.of(NOT_YOUR_TURN, CHOSEN_HOME_PIT, CHOSEN_PIT_WITHOUT_STONE, NOT_CORRECT_PIT_NUMBER).contains(response.getResult())) {
			return ResponseEntity.badRequest().body(response.getResponse());
		}
		return ResponseEntity.ok(response.getResponse());
	}
}
