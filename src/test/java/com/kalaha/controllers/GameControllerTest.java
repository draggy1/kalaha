package com.kalaha.controllers;

import com.kalaha.services.GameService;
import static com.kalaha.services.Validation.CHOSEN_HOME_PIT;
import static com.kalaha.services.Validation.GAME_NOT_FOUND;
import static com.kalaha.services.Validation.NOT_YOUR_TURN;
import com.kalaha.services.dto.AfterMoveResponse;
import com.kalaha.services.dto.GameDetails;
import java.net.URI;
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


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GameController.class)
class GameControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameService service;

	@Test
	void shouldCreateGameSuccessfully() throws Exception {
		when(service.createGame()).thenReturn(GameDetails.of(1, URI.create("http://localhost:8080/games/1")));

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
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(AfterMoveResponse.createFailedResponse(GAME_NOT_FOUND));

		mockMvc.perform(put(String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	void shouldReturnBadRequestWhenNotYourTurn() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(AfterMoveResponse.createFailedResponse(NOT_YOUR_TURN));

		mockMvc.perform(put(String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	void shouldReturnBadRequestWhenChosenHomePit() throws Exception {
		long givenGameId = 1;
		int givenPitId = 1;
		when(service.makeMove(givenGameId, givenPitId)).thenReturn(AfterMoveResponse.createFailedResponse(CHOSEN_HOME_PIT));

		mockMvc.perform(put(String.format("http://localhost:8080/games/%d/pits/%d", givenGameId, givenPitId))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
}