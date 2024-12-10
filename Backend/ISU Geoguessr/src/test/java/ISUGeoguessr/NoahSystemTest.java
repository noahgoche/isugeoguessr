package ISUGeoguessr;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class NoahSystemTest {


    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void locationNameTest() {
        // Check response body for correct response
        RestAssured.get("/Location/4").then().assertThat().body("locationName", equalTo("Carver"));

    }

    @Test
    public void locationTestCoordinates() {
        // Check response body for correct response
        RestAssured.get("/Location/4").then().assertThat().body("latitude", equalTo(42.02569F), "longitude", equalTo(-93.64845F));

    }

    @Test
    public void usernameTest(){
        RestAssured.get("/users/64").then().assertThat().body("username", equalTo("Noah"));

    }

    @Test
    public void statUserTest(){
        RestAssured.get("/Stats/53").then().assertThat().body("username", equalTo("Noah"));
    }

    @Test
    public void changeEmailTest(){
        RestAssured.put("/users/email/64?newEmail=empty");
        RestAssured.put("/users/email/64?newEmail=testEmail@gmail.com");
        RestAssured.get("/users/64").then().assertThat().body("userEmail", equalTo("testEmail@gmail.com"));

    }

    @Test
    public void changeScoreTest(){
        RestAssured.put("/Stats/53/totalScore/0");
        RestAssured.put("/Stats/53/totalScore/5000");
        RestAssured.get("/Stats/53").then().assertThat().body("totalScore", equalTo(5000));

    }


    @Test
    public void checkWinsTest(){
        RestAssured.put("/Stats/53/wins/0");
        RestAssured.put("/Stats/53/wins/20");
        String res = RestAssured.get("/users/Wins/64").asPrettyString();
        int expectedResult = Integer.parseInt(res);
        RestAssured.get("/Stats/53").then().assertThat().body("wins", equalTo(expectedResult));

    }

    @Test
    public void imageFileNameTest(){
        RestAssured.get("/Location/4").then().assertThat().body("imageFileName", equalTo("carver.JPEG"));

    }

}
