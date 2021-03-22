package com.kalaha.services.dto;

import java.net.URI;
import lombok.Value;

@Value(staticConstructor="of")
public class GameDetails {
	long id;
	URI uri;
}
