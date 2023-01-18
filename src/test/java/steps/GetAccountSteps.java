package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.RestAssuredExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetAccountSteps {

    private static ResponseOptions<Response> response;

    @Given("I perform GET operation for {string}")
    public void iPerformGETOperationForWithIdParameter(String url) {
        response = RestAssuredExtension.GetOps(url);
        assertThat(Long.parseLong("3000"), greaterThanOrEqualTo(response.getTime()));
    }

    @Then("I should see the body has name as {string}")
    public void iShouldSeeTheBodyHasNameAs(String name) {
        assertThat(response.getBody().jsonPath().get("name").toString(), equalTo(name));
        assertThat(response.statusCode(), equalTo(200));
    }

    @Then("I should see bad request")
    public void iShouldSeeBadRequest() {
        assertThat(response.statusCode(), equalTo(400));
    }

     @Then("I need see the body has message error as {string} with status code as {int}")
    public void iNeedSeeTheBodyHasMessageErrorAsWithStatusCodeAs(String errorName, int statusCode) {
        assertThat(response.getBody().jsonPath().get("error").toString(), equalTo(errorName));
        assertThat(response.statusCode(), equalTo(statusCode));
    }

    @And("I also should see the body has amount as {int}")
    public void iAlsoShouldSeeTheBodyHasAmountAs(int amount) {
        assertThat(Integer.parseInt(response.getBody().jsonPath().get("amount").toString()), equalTo(amount));
    }
}