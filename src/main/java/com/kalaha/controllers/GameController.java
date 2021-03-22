package com.kalaha.controllers;

import com.kalaha.services.GameService;
import static com.kalaha.services.Validation.CHOSEN_HOME_PIT;
import static com.kalaha.services.Validation.CHOSEN_PIT_WITHOUT_STONE;
import static com.kalaha.services.Validation.GAME_NOT_FOUND;
import static com.kalaha.services.Validation.NOT_YOUR_TURN;
import com.kalaha.services.dto.AfterMove;
import com.kalaha.services.dto.AfterMoveResponse;
import com.kalaha.services.dto.GameDetails;
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
	public ResponseEntity<AfterMove> makeMove(@PathVariable long gameId, @PathVariable int pitId) {
		AfterMoveResponse afterMoveResponse = gameService.makeMove(gameId, pitId);

		if (GAME_NOT_FOUND == afterMoveResponse.getResult()) {
			return ResponseEntity.notFound().build();
		}
		if (Set.of(NOT_YOUR_TURN, CHOSEN_HOME_PIT, CHOSEN_PIT_WITHOUT_STONE).contains(afterMoveResponse.getResult())) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(afterMoveResponse.getAfterMove());
	}
}
