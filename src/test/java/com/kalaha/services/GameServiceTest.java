package com.kalaha.services;

import com.kalaha.config.GameConfig;
import com.kalaha.services.dto.GameDetails;
import java.net.URI;
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

		GameService tested = new GameService(givenConfig);
		assertEquals(expected, tested.createGame());
	}
}