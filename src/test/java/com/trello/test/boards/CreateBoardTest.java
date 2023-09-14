package com.trello.test.boards;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import org.junit.jupiter.api.Assertions;
import io.restassured.specification.Argument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.trello.test.requests.boards.CreateBoardRequest.createBoardRequest;

class CreateBoardTest {

//    private static final String BOARD_NAME = "Test Board z Javy";

    private static String BOARD_ID;

    @DisplayName("Create a new board with valid data")
    @ParameterizedTest(name = "Board name: {0}")
    @MethodSource("sampleBoardData")
    void createBoardTest(String BOARD_NAME) {

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("name", BOARD_NAME);

        // Create a new board
        final Response response = createBoardRequest(queryParams);
//        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        JsonPath json = response.jsonPath();
//        Assertions.assertEquals(BOARD_NAME, json.getString("name"));
        Assertions.assertThat(json.getString("name")).isEqualTo(BOARD_NAME);
        BOARD_ID = json.getString("id");
        System.out.println(BOARD_ID);

        // Get the board
        final Response getResponse = GetBoardRequest.getBoardRequest(BOARD_ID);
        Assertions.assertThat(getResponse.statusCode()).isEqualTo(200);
        String bodyNames = json.getString("name");
        System.out.println(bodyNames);

        // Delete the board
        final Response deleteResponse = DeleteBoardRequest.deleteBoardRequest(BOARD_ID);

//        Assertions.assertEquals(200, deleteResponse.statusCode());
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);

        // Get the not existing board
        final Response getDeletedBoardResponse = GetBoardRequest.getBoardRequest(BOARD_ID);
        Assertions.assertThat(getDeletedBoardResponse.statusCode()).isEqualTo(404);
        Assertions.assertThat(getDeletedBoardResponse.body().asString()).isEqualTo("The requested resource was not found.");

        // Delete deleted board
        final Response deleteDeletedResponse = DeleteBoardRequest.deleteBoardRequest(BOARD_ID);
        Assertions.assertThat(deleteDeletedResponse.statusCode()).isEqualTo(404);
    }

    private static Stream<Arguments> sampleBoardData() {
        return Stream.of(
                Arguments.of("Test Board z Javy z jupiter params"),
                Arguments.of("!"),
                Arguments.of("@"),
                Arguments.of("#"),
                Arguments.of("$"),
                Arguments.of("&"),
                Arguments.of("^"),
                Arguments.of("?"),
                Arguments.of("nazwa_____")
        );
    }
}
