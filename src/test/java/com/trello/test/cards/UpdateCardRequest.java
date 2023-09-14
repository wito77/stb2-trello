package com.trello.test.cards;

import com.trello.TrelloUrl;
import com.trello.test.base.BaseRequest;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateCardRequest {

    public static Response updateCardRequest(Map<String, String> queryParams, String cardId) {
        return given()
                .spec(BaseRequest.requestSetup())
                .queryParams(queryParams)
                .when()
                .put(TrelloUrl.getCardUrl(cardId))
                .then()
                .extract()
                .response();
    }
}
