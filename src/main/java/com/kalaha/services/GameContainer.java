package com.kalaha.services;

import com.kalaha.domain.Game;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GameContainer {
	private long gameCounter = 1;
	private final Map<Long, Game> container = new HashMap<>();

	void addGame(Game game) {
		container.put(gameCounter, game);
		gameCounter++;
	}

}
