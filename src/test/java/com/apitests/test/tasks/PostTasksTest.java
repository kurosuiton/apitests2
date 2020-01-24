package com.apitests.test.tasks;

import com.apitests.model.Task;
import com.google.gson.Gson;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class PostTasksTest {

    private static final String URI = "https://api.todoist.com/rest/v1/tasks";
    private static final String TOKEN = "Bearer 679314bcd0ae7c3b17591b3d71a022debe1dbe3b";
    private final RestAssuredConfig config = config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

    /**
     * Отправить 2 запроса с идентичными данными без заголовка X-Request-Id
     * Код ответа на 1 запрос: 200
     * Код ответа на 2 запрос: 200
     */
    @Test
    public void uniqueRequestCnt1Test() {
        String json = new Gson().toJson(new Task(UUID.randomUUID().toString()));
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
    }

    /**
     * Отправить 1 запрос без заголовка X-Request-Id, 2 запрос с идентичными данными и уникальным значением в заголовке X-Request-Id
     * Код ответа на 1 запрос: 200
     * Код ответа на 2 запрос: 200
     */
    @Test
    public void uniqueRequestCnt2Test() {
        String json = new Gson().toJson(new Task(UUID.randomUUID().toString()));
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", UUID.randomUUID().toString().substring(0, 8))
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
    }

    /**
     * Отправить 1 запрос с уникальным значением в заголовке X-Request-Id, 2 запрос с идентичными данными и без заголовка X-Request-Id
     * Код ответа на 1 запрос: 200
     * Код ответа на 2 запрос: 200
     */
    @Test
    public void uniqueRequestCnt3Test() {
        String json = new Gson().toJson(new Task(UUID.randomUUID().toString()));
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", UUID.randomUUID().toString().substring(0, 8))
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
    }

    /**
     * Отправить 2 запроса с уникальными значениями в заголовке X-Request-Id с идентичными данными
     * Код ответа на 1 запрос: 200
     * Код ответа на 2 запрос: 200
     */
    @Test
    public void uniqueRequestCnt4Test() {
        String json = new Gson().toJson(new Task(UUID.randomUUID().toString()));
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", UUID.randomUUID().toString().substring(0, 8))
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", UUID.randomUUID().toString().substring(0, 8))
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
    }

    /**
     * Отправить 2 запроса с не идентичными данными с неуникальным значением заголовка X-Request-Id
     * Код ответа на 1 запрос: 200
     * Код ответа на 2 запрос: 400
     */
    @Test
    public void uniqueRequestCnt5Test() {
        Gson gson = new Gson();
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", uuid)
                .body(gson.toJson(new Task(UUID.randomUUID().toString())))
                .when().post(URI)
                .then().statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", uuid)
                .body(gson.toJson(new Task(UUID.randomUUID().toString())))
                .when().post(URI)
                .then().statusCode(400);
    }

    /**
     * Отправить 2 запроса с идентичными данными с неуникальным значением заголовка X-Request-Id
     * Код ответа на 1 запрос: 200
     * Код ответа на 2 запрос: 400
     */
    @Test
    public void uniqueRequestCnt6Test() {
        Gson gson = new Gson();
        String json = gson.toJson(new Task(UUID.randomUUID().toString()));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", uuid)
                .body(json)
                .when().post(URI)
                .then().statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .header("X-Request-Id", uuid)
                .body(json)
                .when().post(URI)
                .then().statusCode(400);
    }

    @DataProvider
    public Object[][] getDataForCntFrom12To30Test() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            sb.append("01234567");
        }
        Gson gson = new Gson();
        return new Object[][]{
                {"{\"content\":1", 400}, // Другой тип данных, например, integer
                {gson.toJson(new Task("")), 400}, // Пустое поле
                {gson.toJson(new Task("  ")), 400}, // Несколько пробелов // TODO баг/фича
                {gson.toJson(new Task(" текст ")), 200}, // Пробелы до и после текста
                {gson.toJson(new Task("ТЕКСТ")), 200}, // Текст в верхнем регистре
                {gson.toJson(new Task("текст")), 200}, // Текст в нижнем регистре
                {gson.toJson(new Task("теКСт")), 200}, // Текст в верхнем и нижнем регистре
                {gson.toJson(new Task(",.!?:;")), 200}, // Знаки препинания
                {gson.toJson(new Task("0123456789")), 200}, // Цифры
                {gson.toJson(new Task(sb.toString())), 200}, // Максимально разрешенная длина строки: 2048
                {gson.toJson(new Task(sb.append("0").toString())), 200}, // Максимально разрешенная длина строки: 2049
                {gson.toJson(new Task("`~@#$%^&*()_+|-=\\{}[]:;’<>?,./®©£¥¢¦§«»€")), 200}, // Спецсимволы
                {gson.toJson(new Task("DROP TABLE user; SELECT * FROM blog WHERE code LIKE ‘a%’;")), 200}, // SQL-инъекции
                {gson.toJson(new Task("äöüÄÖÜß")), 200}, // немецкие буквы
                {gson.toJson(new Task("àâçéèêëîïôûùüÿ")), 200}, // французские буквы
                {gson.toJson(new Task("NÑO")), 200}, // испанские буквы
                {gson.toJson(new Task("éàòù ÀàÁáÈèÉéÌìÍíÒòÓóÙùÚú")), 200}, // итальянские буквы
                {gson.toJson(new Task("中国的")), 200}, // китайские иероглифы
                {gson.toJson(new Task("日本の")), 200}, // японские иероглифы
                {gson.toJson(new Task("한국의")), 200} // корейские иероглифы
        };
    }

    /**
     * Проверка на корректную обработку значений поля content
     */
    @Test(dataProvider = "getDataForCntFrom12To30Test")
    public void fieldContentCntFrom12To30Test(String json, Integer statusCode) {
        given()
                .contentType(ContentType.JSON)
                .config(config)
                .header("Authorization", TOKEN)
                .body(json)
                .when().post(URI)
                .then().statusCode(statusCode);
    }
}