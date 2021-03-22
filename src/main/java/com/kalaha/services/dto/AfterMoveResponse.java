package com.kalaha.services.dto;

import com.kalaha.services.Validation;
import lombok.Getter;

@Getter
public class AfterMoveResponse {
	private AfterMove afterMove;
	private Validation result;

	private AfterMoveResponse(Validation result) {
		this.result = result;
	}

	private AfterMoveResponse(AfterMove afterMove, Validation result) {
		this.afterMove = afterMove;
		this.result = result;
	}

	public static AfterMoveResponse createFailedResponse(Validation validation) {
		return new AfterMoveResponse(validation);
	}

	public static AfterMoveResponse createSuccessResponse(AfterMove afterMove, Validation result) {
		return new AfterMoveResponse(afterMove, result);
	}
}
