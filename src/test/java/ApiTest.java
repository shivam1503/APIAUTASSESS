

import base.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Test;
import utils.JsonPathUtil;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiTest extends BaseTest {

    public String userID;

    @Test
    public void getUserList() throws Exception {

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/api/users?page=2")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        JsonNode responseJson = new ObjectMapper().readTree(response.getBody().asString());
        assertEquals("12", JsonPathUtil.getValueByPath(responseJson, "total"));

        //System.out.println("Response: " + response.getBody().asString());
    }

    @Test
    public void createUser() throws IOException {
        String filePath = "src/test/resources/request.json";
        JsonNode jsonNode = JsonPathUtil.readJsonFile(filePath);

        JsonPathUtil.updateFieldByPath(jsonNode, "name", "Shivam Rai");
        JsonPathUtil.updateFieldByPath(jsonNode, "job", "Tester");

        String updatedJsonPayload = jsonNode.toString();

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .post("/api/users")
                .then()
                .extract()
                .response();

        assertEquals(201, response.getStatusCode());

        JsonNode responseJson = new ObjectMapper().readTree(response.getBody().asString());
        //System.out.println("Response: " + response.getBody().asString());
        userID = JsonPathUtil.getValueByPath(responseJson, "id");
    }

    // the api is not working for newly created users might be bug in the api
    @Test
    public void getUserByID() throws IOException {
        createUser();
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/api/users/" + 10)
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        JsonNode responseJson = new ObjectMapper().readTree(response.getBody().asString());
        assertEquals("Byron", JsonPathUtil.getValueByPath(responseJson, "data.first_name"));
    }
}