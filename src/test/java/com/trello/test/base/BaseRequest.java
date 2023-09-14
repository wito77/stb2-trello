package com.trello.test.base;

import com.trello.secrets.TrelloSecrets;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseRequest {
    protected static RequestSpecBuilder requestBuilder;

    public static RequestSpecification requestSetup() {
        requestBuilder = new RequestSpecBuilder();
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.addQueryParam("key", TrelloSecrets.getKey());
        requestBuilder.addQueryParam("token", TrelloSecrets.getToken());
        requestBuilder.addFilter((new RequestLoggingFilter()));
        requestBuilder.addFilter((new ResponseLoggingFilter()));

        return requestBuilder.build();
    }
}
