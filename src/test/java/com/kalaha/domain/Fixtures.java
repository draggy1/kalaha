package com.kalaha.domain;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Fixtures {
	static Pit prepareInitialHead(int givenStones) {
		Pit pit1 = Pit.createOrdinary(1, givenStones, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, givenStones, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, givenStones, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, givenStones, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, givenStones, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, givenStones, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 0);
		Pit pit8 = Pit.createOrdinary(8, givenStones, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, givenStones, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, givenStones, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, givenStones, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, givenStones, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, givenStones, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 0);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static GameBoard prepareBoard() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareBoardHead())
				.build();
	}

	public static Pit prepareBoardHead() {
		Pit pit1 = Pit.createOrdinary(1, 0, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 7, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 7, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 7, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 7, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 7, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 1);
		Pit pit8 = Pit.createOrdinary(8, 6, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 6, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 6, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 6, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 6, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 6, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 0);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static GameBoard prepareExpectedBoardAfterMoveChosenPitWithNumberEightLandInHome() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareHeadAfterMove())
				.build();
	}

	private static Pit prepareHeadAfterMove() {
		Pit pit1 = Pit.createOrdinary(1, 0, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 7, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 7, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 7, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 7, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 7, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 1);
		Pit pit8 = Pit.createOrdinary(8, 0, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 7, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 7, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 7, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 7, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 7, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 1);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static GameBoard prepareFinishingBoardWithEmptyAllPitsPlayerOne() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareFinishingBoardHeadWithEmptyAllPitsPlayerOne())
				.build();
	}

	public static Pit prepareFinishingBoardHeadWithEmptyAllPitsPlayerOne() {
		Pit pit1 = Pit.createOrdinary(1, 0, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 0, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 0, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 0, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 0, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 0, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 34);
		Pit pit8 = Pit.createOrdinary(8, 1, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 1, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 1, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 1, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 1, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 1, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 26);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static GameBoard prepareFinishingBoardWithEmptyAllPitsPlayerOneAfterHandledFinish() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareFinishingBoardHeadWithEmptyAllPitsPlayerOneAfterHandledFinish())
				.build();
	}

	private static Pit prepareFinishingBoardHeadWithEmptyAllPitsPlayerOneAfterHandledFinish() {
		Pit pit1 = Pit.createOrdinary(1, 0, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 0, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 0, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 0, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 0, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 0, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 34);
		Pit pit8 = Pit.createOrdinary(8, 0, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 0, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 0, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 0, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 0, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 0, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 32);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static GameBoard prepareFinishingBoardWithEmptyAllPitsPlayerTwo() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareFinishingBoardHeadWithEmptyAllPitsPlayerTwo())
				.build();
	}

	public static Pit prepareFinishingBoardHeadWithEmptyAllPitsPlayerTwo() {
		Pit pit1 = Pit.createOrdinary(1, 0, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 0, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 0, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 0, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 0, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 1, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 0);
		Pit pit8 = Pit.createOrdinary(8, 0, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 0, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 0, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 0, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 0, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 0, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 26);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static GameBoard prepareFinishingBoardWithEmptyAllPitsPlayerTwoAfterHandledFinish() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareFinishingBoardHeadWithEmptyAllPitsPlayerTwoAfterHandledFinish())
				.build();
	}

	private static Pit prepareFinishingBoardHeadWithEmptyAllPitsPlayerTwoAfterHandledFinish() {
		Pit pit1 = Pit.createOrdinary(1, 0, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 0, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 0, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 0, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 0, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 0, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 1);
		Pit pit8 = Pit.createOrdinary(8, 0, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 0, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 0, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 0, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 0, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 0, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 26);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	private static void linkWithNextAndOpposite(Pit pit1, Pit pit2, Pit pit3, Pit pit4, Pit pit5, Pit pit6, Pit pit7, Pit pit8, Pit pit9, Pit pit10, Pit pit11, Pit pit12, Pit pit13, Pit pit14) {
		Pit.linkWithNextPit(pit1, pit2);
		Pit.linkWithNextPit(pit2, pit3);
		Pit.linkWithNextPit(pit3, pit4);
		Pit.linkWithNextPit(pit4, pit5);
		Pit.linkWithNextPit(pit5, pit6);
		Pit.linkWithNextPit(pit6, pit7);
		Pit.linkWithNextPit(pit7, pit8);
		Pit.linkWithNextPit(pit8, pit9);
		Pit.linkWithNextPit(pit9, pit10);
		Pit.linkWithNextPit(pit10, pit11);
		Pit.linkWithNextPit(pit11, pit12);
		Pit.linkWithNextPit(pit12, pit13);
		Pit.linkWithNextPit(pit13, pit14);
		Pit.linkWithNextPit(pit14, pit1);

		Pit.linkOppositePits(pit1, pit13);
		Pit.linkOppositePits(pit2, pit12);
		Pit.linkOppositePits(pit3, pit11);
		Pit.linkOppositePits(pit4, pit10);
		Pit.linkOppositePits(pit5, pit9);
		Pit.linkOppositePits(pit6, pit8);
	}

	public static GameBoard prepareGivenBoardAfterMoveLandInEmpty() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareGivenHeadAfterMoveLandInEmpty())
				.build();
	}

	public static Pit prepareGivenHeadAfterMoveLandInEmpty() {
		Pit pit1 = Pit.createOrdinary(1, 6, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 6, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 7, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 9, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 15, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 2, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 13);
		Pit pit8 = Pit.createOrdinary(8, 0, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 13, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 8, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 3, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 0, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 5, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 26);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static GameBoard prepareExpectedBoardAfterMoveLandInEmpty() {
		return GameBoard.builder()
				.homePitNumberOfPlayerOne(7)
				.homePitNumberOfPlayerTwo(14)
				.ordinaryPitsSize(6)
				.head(prepareExpectedHandAfterMoveLandInEmpty())
				.build();
	}

	public static Pit prepareExpectedHandAfterMoveLandInEmpty() {
		Pit pit1 = Pit.createOrdinary(1, 7, Player.PLAYER_1);
		Pit pit2 = Pit.createOrdinary(2, 7, Player.PLAYER_1);
		Pit pit3 = Pit.createOrdinary(3, 8, Player.PLAYER_1);
		Pit pit4 = Pit.createOrdinary(4, 10, Player.PLAYER_1);
		Pit pit5 = Pit.createOrdinary(5, 0, Player.PLAYER_1);
		Pit pit6 = Pit.createOrdinary(6, 3, Player.PLAYER_1);
		Pit pit7 = Pit.createHomeForPlayerOne(7, 13);
		Pit pit8 = Pit.createOrdinary(8, 1, Player.PLAYER_2);
		Pit pit9 = Pit.createOrdinary(9, 0, Player.PLAYER_2);
		Pit pit10 = Pit.createOrdinary(10, 9, Player.PLAYER_2);
		Pit pit11 = Pit.createOrdinary(11, 4, Player.PLAYER_2);
		Pit pit12 = Pit.createOrdinary(12, 1, Player.PLAYER_2);
		Pit pit13 = Pit.createOrdinary(13, 6, Player.PLAYER_2);
		Pit pit14 = Pit.createHomeForPlayerTwo(14, 44);

		linkWithNextAndOpposite(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);

		return pit1;
	}

	public static Map<String, String> prepareStatus() {
		HashMap<String, String> expectedStatus = new HashMap<>();
		expectedStatus.put("1", "0");
		expectedStatus.put("2", "7");
		expectedStatus.put("3", "7");
		expectedStatus.put("4", "7");
		expectedStatus.put("5", "7");
		expectedStatus.put("6", "7");
		expectedStatus.put("7", "1");
		expectedStatus.put("8", "6");
		expectedStatus.put("9", "6");
		expectedStatus.put("10", "6");
		expectedStatus.put("11", "6");
		expectedStatus.put("12", "6");
		expectedStatus.put("13", "6");
		expectedStatus.put("14", "0");

		return expectedStatus;
	}

	public static void checkIfBoardIsAsExpected(Pit expected, Pit actual) {
		Pit expectedPit = expected;
		while (expectedPit.getNumber() < 14) {
			assertThat(expectedPit).isEqualTo(actual);
			expectedPit = expectedPit.getNext();
			actual = actual.getNext();
		}
		assertThat(expectedPit).isEqualTo(actual);
		assertThat(expectedPit.getNext()).isEqualTo(actual.getNext());
	}

	public static String getUri(int givenPitId, int givenGameId) {
		return URI.create("http://test:8080/")
				.resolve(String.format("games/%d/", givenGameId))
				.resolve(String.format("pits/%d", givenPitId))
				.toString();
	}
}
