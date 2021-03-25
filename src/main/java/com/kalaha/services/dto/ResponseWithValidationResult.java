package com.kalaha.services.dto;

import com.kalaha.services.Validator;
import lombok.Value;

/**
 * Wrapper class for response and validation result
 */
@Value(staticConstructor = "of")
public class ResponseWithValidationResult {
	Response response;
	Validator result;
}
