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
    public void locationTest() {
        // Check response body for correct response
        RestAssured.get("/Location/4").then().assertThat().body("locationName", equalTo("Carver"));

    }

    @Test
    public void locationTestCoordinates() {
        // Check response body for correct response
        RestAssured.get("/Location/4").then().assertThat().body("latitude", equalTo(42.02569F), "longitude", equalTo(-93.64845F));

    }

    @Test
    public void userTest(){
        RestAssured.get("/users/25").then().assertThat().body("username", equalTo("Noah"));

    }

    @Test
    public void statTest(){
        RestAssured.get("/Stats/18").then().assertThat().body("username", equalTo("Noah"));

    }

}
