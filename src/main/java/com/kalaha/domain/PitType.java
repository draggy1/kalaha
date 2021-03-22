package com.kalaha.domain;

import com.kalaha.domain.Game.Player;
import java.util.Set;

public enum PitType {
	ORDINARY(Set.of(Player.PLAYER_1, Player.PLAYER_2)),
	HOME_PLAYER_1(Set.of(Player.PLAYER_1)),
	HOME_PLAYER_2(Set.of(Player.PLAYER_2));

	Set<Player> access;

	PitType(Set<Player> access) {
		this.access = access;
	}
}
