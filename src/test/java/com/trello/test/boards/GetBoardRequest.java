package com.trello.test.boards;

import com.trello.TrelloUrl;
import com.trello.test.base.BaseRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetBoardRequest {

    public static Response getBoardRequest(String BOARD_ID) {
        return given()
                .spec(BaseRequest.requestSetup())
//                .pathParam("boardId", BOARD_ID)
                .when()
                .get(TrelloUrl.getBoardsUrl() + "/" + BOARD_ID)
                .then()
                .extract()
                .response();
    }
}
