import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

class AllOperationsTest {

    static final String HOST = "http://localhost:8080";

    @Test
    void shouldGetCompanyContent() {
        given()
                .contentType(JSON)
                .body(new JSONObject()
                        .put("type", "getCompanyContent")
                        .put("companyName", "google")
                        .toString()
                )
        .when()
                .post(HOST + "/v1/all-operations")
        .then()
                .statusCode(OK.value())
                .contentType(JSON)
                .body("name", is("google"))
                .body("benefits.size()", is(3))
                .body("gallery.size()", is(2));
    }

    @Test
    void shouldAddCandidate() {
        given()
                .contentType(JSON)
                .body(new JSONObject()
                        .put("type", "addCandidate")
                        .put("candidate", markHamill())
                        .toString()
                )
                .when()
                .post(HOST + "/v1/all-operations")
                .then()
                .statusCode(CREATED.value())
                .body(isEmptyString());
    }

    @Test
    void shouldGetCandidate() {
        markHamillIsCreated();
        given()
                .contentType(JSON)
                .body(new JSONObject()
                        .put("type", "getCandidate")
                        .put("login", "mHamill")
                        .toString()
                )
        .when()
                .post(HOST + "/v1/all-operations")
        .then()
                .statusCode(OK.value())
                .body("firstName", is("Mark"))
                .body("lastName", is("Hamill"));
    }

    private void markHamillIsCreated() {
        given()
                .contentType(JSON)
                .body(new JSONObject()
                        .put("type", "addCandidate")
                        .put("candidate", markHamill())
                        .toString()
                )
                .when()
                .post(HOST + "/v1/all-operations");
    }

    private JSONObject markHamill() {
        JSONObject candidate = new JSONObject();
        candidate.put("login", "mHamill");
        candidate.put("firstName","Mark");
        candidate.put("lastName","Hamill");
        candidate.put("email","mark.hamill@gmail.com");
        return candidate;
    }

}
