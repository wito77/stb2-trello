package com.trello.test.lists;

import com.trello.TrelloUrl;
import com.trello.test.base.BaseRequest;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateListRequest {

    public static Response createListRequest(Map<String, String> queryParams) {

        return given()
                .spec(BaseRequest.requestSetup())
                .queryParams(queryParams)
                .when()
                .post(TrelloUrl.getListsUrl())
                .then()
                .extract()
                .response();
    }
}
