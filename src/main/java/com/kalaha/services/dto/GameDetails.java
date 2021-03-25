package com.kalaha.services.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

/**
 * Class represents details about game
 */
@ApiModel(value = "Game details", description = "Details of created game")
@Value(staticConstructor = "of")
public class GameDetails {
	@ApiModelProperty(notes = "Unique id of created game", name = "gameId", example = "1")
	long id;
	@ApiModelProperty(notes = "URI address of created game", name = "uri", example = "http://localhost:8080/games/1/pits/1")
	String uri;
}
