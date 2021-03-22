package com.kalaha.services.dto;

public class AfterMoveResponse {
	private AfterMove afterMove;
	private Status response;

	private AfterMoveResponse(Status response) {
		this.response = response;
	}

	private AfterMoveResponse(AfterMove afterMove, Status response) {
		this.afterMove = afterMove;
		this.response = response;
	}

	public static AfterMoveResponse createGameNotFoundResponse(){
		return new AfterMoveResponse(Status.GAME_NOT_FOUND);
	}

	public static AfterMoveResponse createNotYourTurnResponse(){
		return new AfterMoveResponse(Status.NOT_YOUR_TURN);
	}

	public static AfterMoveResponse createChosenHomePitResponse(){
		return new AfterMoveResponse(Status.CHOSEN_HOME_PIT);
	}

	public static AfterMoveResponse createChosenPitWithoutStoneResponse(){
		return new AfterMoveResponse(Status.CHOSEN_PIT_WITHOUT_STONE);
	}

	public static AfterMoveResponse createSuccessResponse(AfterMove afterMove){
		return new AfterMoveResponse(afterMove, Status.SUCCESS);
	}

	public Status getResponse() {
		return response;
	}

	public AfterMove getAfterMove() {
		return afterMove;
	}
}
