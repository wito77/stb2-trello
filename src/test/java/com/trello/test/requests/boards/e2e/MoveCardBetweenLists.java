package com.trello.test.requests.boards.e2e;

import com.trello.test.boards.DeleteBoardRequest;
import com.trello.test.cards.CreateCardRequest;
import com.trello.test.cards.UpdateCardRequest;
import com.trello.test.lists.CreateListRequest;
import com.trello.test.requests.boards.CreateBoardRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoveCardBetweenListsTest {

    private final String boardName = "TEST E2E Board";
    private final String firstListName = "List no1";
    private final String secondListName = "List no2";
    private final String cardName = "Test card no1";
    private static String boardId;
    private static String firstListId;
    private static String secondListId;
    private static String cardId;

    @Test
    @Order(1)
    void createNewBoardTest() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", boardName);

        final Response createBoardResponse = CreateBoardRequest.createBoardRequest(queryParams);
        Assertions.assertThat(createBoardResponse.statusCode()).isEqualTo(200);

        JsonPath json = createBoardResponse.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(boardName);

        boardId = json.getString("id");
    }

    @Test
    @Order(2)
    void createFirstListTest() {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", firstListName);
        queryParams.put("idBoard", boardId);

        final Response response = CreateListRequest.createListRequest(queryParams);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(firstListName);

        firstListId = json.getString("id");
    }

    @Test
    @Order(3)
    void createSecondListTest() {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", secondListName);
        queryParams.put("idBoard", boardId);

        final Response response = CreateListRequest.createListRequest(queryParams);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(secondListName);

        secondListId = json.getString("id");
    }

    @Test
    @Order(4)
    void createCardOnFirstListTest() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("idList", firstListId);
        queryParams.put("name", cardName);

        final Response response = CreateCardRequest.createCardRequest(queryParams);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getString("name")).isEqualTo(cardName);

        cardId = json.getString("id");
    }

    @Test
    @Order(5)
    void moveCardToSecondListTest() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("idList", secondListId);

        final Response response = UpdateCardRequest.updateCardRequest(queryParams, cardId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("idList")).isEqualTo(secondListId);
    }

    @Test
    @Order(6)
    void deleteBoardTest() {
        final Response deleteResponse = DeleteBoardRequest.deleteBoardRequest(boardId);

        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
    }
}
