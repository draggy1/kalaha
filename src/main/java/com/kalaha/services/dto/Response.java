package com.kalaha.services.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import lombok.Value;

@ApiModel(value = "Response", description = "Response returned after made move")
@Value(staticConstructor = "of")
public class Response {
	@ApiModelProperty(notes = "Unique id of created game", name = "gameId")
	long id;
	@ApiModelProperty(notes = "URI address of created game", name = "uri",
			example = "{\"1\":\"4\",\"2\":\"4\",\"3\":\"4\",\"4\":\"4\",\"5\":\"4\",\"6\":\"4\",\"7\":\"0\",\"8\":\"4\",\"9\":\"4\"," +
					"\"10\":\"4\",\"11\":\"4\",\"12\":\"4\",\"13\":\"4\",\"14\":\"0\"}")
	String url;
	Map<String, String> status;
}
