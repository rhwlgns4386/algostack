package com.hunko.algostack.algorithm;

import com.hunko.algostack.algorithm.util.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.hunko.algostack.algorithm.AlgorithmAcceptance.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlgorithmAcceptanceTest {

    @LocalServerPort
    private int prot;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private CacheUpdateScheduler cacheUpdateScheduler;

    @BeforeEach
    void setup() {
        RestAssured.port = this.prot;
        databaseCleanup.execute();
    }

    @Test
    void 알고리즘_풀이_내역_등록_테스트() {
        Map<String, String> body = Map.of(
                "id", "1",
                "title", "Two Sum",
                "platform", "LEETCODE",
                "result", "SUCCESS",
                "url", "https://leetcode.com/problems/two-sum/description");

        ExtractableResponse<Response> response = 알고리즘_풀이_내역_등록(body);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
    }

    @Test
    void 월별_풀이_내역_조회_테스트() {
        Map<String, String> item1Failed = Map.of(
                "id", "1",
                "title", "Two Sum",
                "platform", "LEETCODE",
                "result", "FAIL",
                "url", "https://leetcode.com/problems/two-sum/description");
        알고리즘_풀이_내역_등록(item1Failed);

        Map<String, String> item1Success = Map.of(
                "id", "1",
                "title", "Two Sum",
                "platform", "LEETCODE",
                "result", "SUCCESS",
                "url", "https://leetcode.com/problems/two-sum/description");
        알고리즘_풀이_내역_등록(item1Success);

        Map<String, String> item2Failed = Map.of(
                "id", "2",
                "title", "Add Two Numbers",
                "platform", "LEETCODE",
                "result", "FAIL",
                "url", "https://leetcode.com/problems/add-two-numbers/description");
        알고리즘_풀이_내역_등록(item2Failed);

        processScadule();
        LocalDateTime now = LocalDateTime.now();

        ExtractableResponse<Response> response = 월별_풀이_내역조회(now.getYear(), now.getMonthValue());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertThat(response.body().jsonPath().getMap(now.format(formatter))).hasSize(1);
        List<Map> list = response.body().jsonPath().getList(now.format(formatter) + ".list", Map.class);
        assertThat(list).hasSize(2)
                .contains(
                        Map.of(
                                "id", 2,
                                "title", "Add Two Numbers",
                                "platform", "LEETCODE",
                                "result", "FAIL",
                                "url", "https://leetcode.com/problems/add-two-numbers/description")
                )
                .contains(
                        Map.of(
                                "id", 1,
                                "title", "Two Sum",
                                "platform", "LEETCODE",
                                "result", "SUCCESS",
                                "url", "https://leetcode.com/problems/two-sum/description"));


    }

    @Test
    void 해당_일_알고리즘_결과_조회() {
        Map<String, String> failed = Map.of(
                "id", "1",
                "title", "Two Sum",
                "platform", "LEETCODE",
                "result", "FAIL",
                "url", "https://leetcode.com/problems/two-sum/description");
        알고리즘_풀이_내역_등록(failed);

        Map<String, String> success = Map.of(
                "id", "1",
                "title", "Two Sum",
                "platform", "LEETCODE",
                "result", "SUCCESS",
                "url", "https://leetcode.com/problems/two-sum/description");
        알고리즘_풀이_내역_등록(success);

        Map<String, String> item2Failed = Map.of(
                "id", "2",
                "title", "Add Two Numbers",
                "platform", "LEETCODE",
                "result", "FAIL",
                "url", "https://leetcode.com/problems/add-two-numbers/description");
        알고리즘_풀이_내역_등록(item2Failed);

        processScadule();

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        int day = now.getDayOfMonth();

        ExtractableResponse<Response> response = 일별_풀이_내역(year, month, day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
        List<Map> list = response.body().jsonPath().getList(now.format(formatter) + ".list", Map.class);
        assertThat(list).hasSize(2)
                .contains(
                        Map.of(
                                "id", 2,
                                "title", "Add Two Numbers",
                                "platform", "LEETCODE",
                                "result", "FAIL",
                                "url", "https://leetcode.com/problems/add-two-numbers/description")
                )
                .contains(
                        Map.of(
                                "id", 1,
                                "title", "Two Sum",
                                "platform", "LEETCODE",
                                "result", "SUCCESS",
                                "url", "https://leetcode.com/problems/two-sum/description"));

    }

    @Test
    void 특정_알고리즘_풀이내역_조회_테스트() {
        Map<String, String> body = Map.of(
                "id", "1",
                "title", "Two Sum",
                "platform", "LEETCODE",
                "result", "SUCCESS",
                "url", "https://leetcode.com/problems/two-sum/description");

        알고리즘_풀이_내역_등록(body);
        processScadule();

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map first = 일별_풀이_내역(now.getYear(), now.getMonthValue(), now.getDayOfMonth())
                .body().jsonPath().getList(now.format(formatter) + ".list", Map.class).getFirst();

        ExtractableResponse<Response> response = 특정_알고리즘_풀의내역_조회((String) first.get("platform"),Long.parseLong(first.get("id").toString()));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
        List<Map> list = response.body().jsonPath().getList("list", Map.class);
        assertThat(list).hasSize(1);

        Map<String, Object> data = list.getFirst();
        assertThat(data)
                .containsEntry("id", 1)
                .containsEntry("title", "Two Sum")
                .containsEntry("platform", "LEETCODE")
                .containsEntry("result", "SUCCESS")
                .containsEntry("url", "https://leetcode.com/problems/two-sum/description");

    }

    private void processScadule() {
        cacheUpdateScheduler.updateCache();
    }
}
