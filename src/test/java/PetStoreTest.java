import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class PetStoreTest {

    String requestURL = "https://petstore.swagger.io/v2/pet/";

    @Test
    public void CRUDPet() {
        long id =
        given().
                body("{\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 10,\n" +
                        "    \"name\": \"cat\"\n" +
                        "  },\n" +
                        "  \"name\": \"Tomas\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"tut.by\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 10,\n" +
                        "      \"name\": \"bengal\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post(requestURL).
        then().
                log().all().
                statusCode(200).
                body("category.id", equalTo(10),
                        "category.name", equalTo("cat"),
                        "name", equalTo("Tomas"),
                        "photoUrls", hasItems("tut.by"),
                        "tags.id", hasItems(10),
                        "tags.name", hasItems("bengal"),
                        "status", equalTo("available")).
                extract().body().jsonPath().getLong("id");
        when().
                get(requestURL + id).
        then().
                log().all().
                statusCode(200).
                body("category.id", equalTo(10),
                        "category.name", equalTo("cat"),
                        "name", equalTo("Tomas"),
                        "photoUrls", hasItems("tut.by"),
                        "tags.id", hasItems(10),
                        "tags.name", hasItems("bengal"),
                        "status", equalTo("available"));
        given().
                body("{\n" +
                        "\"id\": " + id +", \n" +
                        "  \"category\": {\n" +
                        "    \"id\": 10,\n" +
                        "    \"name\": \"cat\"\n" +
                        "  },\n" +
                        "  \"name\": \"Masha\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"onliner.by\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 10,\n" +
                        "      \"name\": \"bengal\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                put(requestURL).
        then().
                log().all().
                statusCode(200).
                body("name", equalTo("Masha"),
                        "photoUrls", hasItems("onliner.by"));
        when().
                delete(requestURL + id).
        then().
                log().all().
                statusCode(200);
    }
}




