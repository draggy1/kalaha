package com.kalaha.services.dto;

import com.kalaha.services.Validator;
import lombok.Value;

@Value(staticConstructor = "of")
public class ResponseWithValidationResult {
	Response response;
	Validator result;
}
