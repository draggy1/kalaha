package com.kalaha.services.dto;

import com.kalaha.domain.Player;
import java.net.URI;
import java.util.Map;
import lombok.Value;

@Value(staticConstructor = "of")
public class AfterMove {
	long id;
	URI url;
	Map<Integer, Integer> status;
	Player player;
}
