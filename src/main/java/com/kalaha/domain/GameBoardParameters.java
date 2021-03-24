package com.kalaha.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GameBoardParameters {
	int ordinaryPitsSize;
	int homePitNumberOfPlayerOne;
	int homePitNumberOfPlayerTwo;
	int stones;
}
