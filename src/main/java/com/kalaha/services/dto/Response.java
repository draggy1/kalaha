package com.kalaha.services.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import lombok.Value;

@ApiModel(value = "Response", description = "Response returned after made move")
@Value(staticConstructor = "of")
public class Response {
	@ApiModelProperty(notes = "Unique id of created game", name = "gameId", example = "1")
	long id;
	@ApiModelProperty(notes = "URI address of created game", name = "uri", example = "http://localhost:8080/games/1/pits/1")
	String url;
	@ApiModelProperty(notes = "Describe how stones are located on board", name = "uri",
			example = "{\"1\":\"4\",\"2\":\"4\",\"3\":\"4\",\"4\":\"4\",\"5\":\"4\",\"6\":\"4\",\"7\":\"0\",\"8\":\"4\",\"9\":\"4\"," +
			"\"10\":\"4\",\"11\":\"4\",\"12\":\"4\",\"13\":\"4\",\"14\":\"0\"}")
	Map<String, String> status;
}
