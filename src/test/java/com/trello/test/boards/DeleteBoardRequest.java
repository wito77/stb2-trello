package com.trello.test.boards;

import com.trello.TrelloUrl;
import com.trello.secrets.TrelloSecrets;
import com.trello.test.base.BaseRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteBoardRequest {
    public static Response deleteBoardRequest(String BOARD_ID) {
        return given()
                .spec(BaseRequest.requestSetup())
//                .pathParam("boardId", BOARD_ID)
                .when()
                .delete(TrelloUrl.getBoardsUrl() + "/" + BOARD_ID)
                .then()
                .extract()
                .response();
    }
}
