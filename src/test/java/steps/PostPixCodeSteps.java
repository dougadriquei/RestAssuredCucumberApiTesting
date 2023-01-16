package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.json.JSONException;
import org.json.JSONObject;
import utilities.RestAssuredExtension;

import java.util.Base64;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class PostPixCodeSteps {

    private static ResponseOptions<Response> response;

    @Given("I perform POST operation for {string} with body as")
    public void iPerformPOSTOperationForWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        assertThat(Long.parseLong("3000"), greaterThanOrEqualTo(response.getTime()));
    }

    @Given("I perform POST operation for {string}, returning bad request and with body as with body as")
    public void iPerformPOSTOperationForReturningBadRequestAndWithBodyAsWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encode2d_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
    }

    @Given("I perform POST operation for {string}, returning bad request and with body as")
    public void iPerformPOSTBadRequestOperationForBadRequestWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encode2d_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
    }

    @Then("I should see the body has key as {string}")
    public void iShouldSeeTheBodyHasKeyAs(String key) {
        String keyFound = response.getBody().jsonPath().getJsonObject("holder.key").toString();
        assertThat(keyFound, equalTo(key));
        assertThat(response.statusCode(), equalTo(200));
    }

    @And("I also want to see the body has end_to_end as {string}")
    public void iAlsoWantToSeeTheBodyHasEnd_to_endAs(String end_to_end) {
        String end_to_endFound = response.getBody().jsonPath().getJsonObject("end_to_end").toString();
        assertThat(end_to_endFound, equalTo(end_to_end));
        assertThat(response.statusCode(), equalTo(200));
    }

    @And("I want to see the conciliation identifier is equal to {string}")
    public void iWantToSeeTheConciliationIdentifierIsEqualTo(String conciliation_id) {
        String conciliation_id_found = response.getBody().jsonPath().getJsonObject("conciliation_id").toString();
        assertThat(conciliation_id_found, equalTo(conciliation_id));
        assertThat(response.statusCode(), equalTo(200));
    }

    @And("Finally I want to see the total value is equal to {string}")
    public void finallyIWantToSeeTheConciliationIdentifierIsEqualTo(String conciliation_id) {
        String conciliation_id_found = response.getBody().jsonPath().getJsonObject("total_value").toString();
        assertThat(conciliation_id_found, equalTo(conciliation_id));
        assertThat(response.statusCode(), equalTo(200));
    }

    @Then("I should see the body has message error as {string} with status code as {int}")
    public void iShouldSeeTheBodyHasMessageErrorAsWithStatusCodeAs(String errorName, int statusCode) {
        assertThat(response.getBody().jsonPath().get("error").toString(), equalTo(errorName));
        assertThat(response.statusCode(), equalTo(statusCode));
    }
}
