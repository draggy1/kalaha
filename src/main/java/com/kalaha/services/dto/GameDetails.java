package com.kalaha.services.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(value = "Game details", description = "Details of created game")
@Value(staticConstructor = "of")
public class GameDetails {
	@ApiModelProperty(notes = "Unique id of created game", name = "gameId")
	long id;
	@ApiModelProperty(notes = "URI address of created game", name = "uri")
	String uri;
}
