package com.hunko.algostack.algorithm;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Map;


public final class AlgorithmAcceptance {

    public static ExtractableResponse<Response> 알고리즘_풀이_내역_등록(Map<String, String> body) {
        return RestAssured
                .given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/algorithm")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 월별_풀이_내역조회(int year, int month) {
        return RestAssured
                .given()
                .param("year", year)
                .param("month", month)
                .contentType(ContentType.JSON)
                .when()
                .get("/algorithm")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 일별_풀이_내역(int year, int month, int day) {
        return RestAssured
                .given()
                .param("year", year)
                .param("month", month)
                .param("day", day)
                .when()
                .get("/algorithm")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 특정_알고리즘_풀의내역_조회(String platform,Long id) {
        return  RestAssured
                .when()
                .get("/algorithm/{platform}/{id}", platform, id)
                .then()
                .log().all()
                .extract();
    }
}
