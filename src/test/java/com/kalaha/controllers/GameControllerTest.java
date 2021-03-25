package com.kalaha.controllers;

import com.kalaha.services.GameService;
import static com.kalaha.services.Validator.CHOSEN_HOME_PIT;
import static com.kalaha.services.Validator.CHOSEN_PIT_WITHOUT_STONE;
import static com.kalaha.services.Validator.FINISHED;
import static com.kalaha.services.Validator.GAME_NOT_FOUND;
import static com.kalaha.services.Validator.NOT_YOUR_TURN;
import static com.kalaha.services.Validator.SUCCESS;
import com.kalaha.services.dto.GameDetails;
import com.kalaha.services.dto.Response;
import com.kalaha.services.dto.ResponseWithValidationResult;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test suite for {@link GameController}
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GameController.class)
class GameControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameService service;

	@Test
	void shouldCreateGameSuccessfully() throws Exception {
		when(service.createGame()).thenReturn(GameDetails.of(1, "http://localhost:8080/games/1"));

		mockMvc.perform(post("http://localhost:8080/games")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.CREATED.value()))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.uri").value("http://localhost:8080/games/1"));
	}

	@Test
	void shouldReturnNotFoundWhenGameNotExist() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		String givenUrl = String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId);
		Response response = Response.of(givenGameId, givenUrl, Map.of());
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(ResponseWithValidationResult.of(response, GAME_NOT_FOUND));

		mockMvc.perform(put(String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	void shouldReturnBadRequestWhenNotYourTurn() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		String givenUrl = String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId);
		Response response = Response.of(givenGameId, givenUrl, Map.of());
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(ResponseWithValidationResult.of(response, NOT_YOUR_TURN));

		mockMvc.perform(put(String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	void shouldReturnBadRequestWhenChosenHomePit() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		String givenUrl = String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId);
		Response response = Response.of(givenGameId, givenUrl, Map.of());
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(ResponseWithValidationResult.of(response, CHOSEN_HOME_PIT));

		mockMvc.perform(put(String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	void shouldReturnBadRequestWhenChosenPitWithoutStone() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		String givenUrl = String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId);
		Response response = Response.of(givenGameId, givenUrl, Map.of());
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(ResponseWithValidationResult.of(response, CHOSEN_PIT_WITHOUT_STONE));

		mockMvc.perform(put(String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	void shouldReturnOkWhenGameIsFinished() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		String givenUrl = String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId);
		Response response = Response.of(givenGameId, givenUrl, Map.of());
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(ResponseWithValidationResult.of(response, FINISHED));

		mockMvc.perform(put(givenUrl)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.url").value(givenUrl));
	}

	@Test
	void shouldReturnOkWhenMadeMoveSuccessfully() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		String givenUrl = String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId);
		Response response = Response.of(givenGameId, givenUrl, Map.of());
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(ResponseWithValidationResult.of(response, SUCCESS));

		mockMvc.perform(put(givenUrl)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.url").value(givenUrl));
	}
}