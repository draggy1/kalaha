package com.kalaha.services.dto;

import com.kalaha.services.Validation;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class AfterMoveResponse {
	private AfterMove afterMove;
	private final Validation result;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		AfterMoveResponse that = (AfterMoveResponse) o;

		return new EqualsBuilder()
				.append(afterMove, that.afterMove)
				.append(result, that.result)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(afterMove)
				.append(result)
				.toHashCode();
	}
}
