package steps;

import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import model.Bank;
import model.Payment;
import model.Recipient;
import model.Sender;
import org.json.JSONException;
import org.json.JSONObject;
import utilities.RestAssuredExtension;

import java.util.Base64;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class PostPixPaymentSteps {

    private static ResponseOptions<Response> response;
    private static Recipient recipient = new Recipient();
    private static Bank recipientBank = new Bank();
    private static Sender sender = new Sender();
    private static Bank senderBank = new Bank();
    private static Payment payment = new Payment();



    @Given("I want perform GET operation for {string}")
    public void iWantPerformGETOperationForWithIdParameter(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument(response.getBody().jsonPath().getJsonObject("document").toString());
        senderBank.setName(response.getBody().jsonPath().getJsonObject("bank.name").toString());
        senderBank.setIspb(response.getBody().jsonPath().getJsonObject("bank.ispb").toString());
        sender.setBank(senderBank);
        payment.setSender(sender);
    }

    @Given("I want perform GET operation for {string} without bank")
    public void iWantPerformGETOperationForWithIdParameterWithoutBank(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument(response.getBody().jsonPath().getJsonObject("document").toString());
        payment.setSender(sender);
    }

    @Given("I want perform GET operation for {string} without sender")
    public void iWantPerformGETOperationForWithIdParameterWithoutSender(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument(response.getBody().jsonPath().getJsonObject("document").toString());
    }
    @And("I perform POST operations for {string} with body as")
    public void iPerformPOSTOperationsForWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} with negative amount and body as")
    public void iPerformPOSTOperationsForWithNegativeAmountAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(-3000.00);
        payment.setRecipient(recipient);
    }


    @And("I perform POST operations for {string} without amount and body as")
    public void iPerformPOSTOperationsForWithoutAmountAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setRecipient(recipient);
    }

    //TODO - parâmetro errado na api, endToEnd
    @Then("I want send payment for {string} with valid QR Code")
    public void iWantSendPaymentQRCodeToQRCode(String url) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(payment);
        jsonString = jsonString.replace("\"end_to_end\"", "\"endToEnd\"");
        response = RestAssuredExtension.PostOpsWithBody(url, jsonString);
        assertThat(Long.parseLong("3000"), greaterThanOrEqualTo(response.getTime()));

    }

    //TODO - Não está retornando status code 201 conforme documentação
    @And("I should see the body has authentication code and status code as {int}")
    public void iShouldSeeTheBodyHasAuthenticationCodeAsWithStatusCode(Integer status_code) {
        String authenticationCodeResponse = response.getBody().jsonPath().getJsonObject("authentication_code").toString();
        assertThat(authenticationCodeResponse,hasLength(authenticationCodeResponse.length()));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @Then("I want send invalid payment for {string} with valid QR Code")
    public void iWantSendInvalidPaymentForWithValidQRCode(String url) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(payment);
        response = RestAssuredExtension.PostOpsWithBody(url, jsonString);
    }



    @And("I should see the bad request as {string} with errors endToEnd as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsEndToEndAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.endToEnd").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @And("I should see the bad request as {string} with errors sender bank as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsSenderBankAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.sender.bank").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @And("I should see the bad request as {string} with errors recipient bank as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsRecipientBankAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.recipient.bank").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @Then("I want send payment for {string} with invalid end_to_end")
    public void iWantSendPaymentForWithInvalidEnd_to_end(String url) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(payment);
        jsonString = jsonString.replace("\"end_to_end\"", "\"endToEnd\"");
        response = RestAssuredExtension.PostOpsWithBody(url, jsonString);
    }

    @And("I perform POST operations for {string} with conciliation_id invalid and with body as")
    public void iPerformPOSTOperationsForWithConciliation_idInvalidAndWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id("conciliationIdInvalid");
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} with end_to_end invalid and with body as")
    public void iPerformPOSTOperationsForWithEnd_to_endInvalidAndWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end("e2eInvalid");
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} without recipient bank and body as")
    public void iPerformPOSTOperationsForWithoutRecipientBankAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} without recipient and body as")
    public void iPerformPOSTOperationsForWithoutRecipientAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
    }

    @Given("I want perform GET operation for {string} with document invalid")
    public void iWantPerformGETOperationForWithDocumentInvalid(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument("9999");
        senderBank.setName(response.getBody().jsonPath().getJsonObject("bank.name").toString());
        senderBank.setIspb(response.getBody().jsonPath().getJsonObject("bank.ispb").toString());
        sender.setBank(senderBank);
        payment.setSender(sender);
    }

    @And("I should see the bad request as {string} with errors document sender as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsDocumentSenderAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.sender.document").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @And("I perform POST operations for {string} sender and recipient are the same with body as")
    public void iPerformPOSTOperationsForSenderAndRecipientAreTheSameWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        String name = response.getBody().jsonPath().getJsonObject("holder.name").toString();
        String document = response.getBody().jsonPath().getJsonObject("holder.document").toString();
        recipient.setName(name);
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
        Sender sender = new Sender();
        sender.setName(name);
        sender.setDocument(document);
        sender.setBank(recipientBank);
        payment.setSender(sender);

    }

    @And("I should see the bad request as {string} with status code {int}")
    public void iShouldSeeTheBadRequestAsWithStatusCode(String error, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(response.statusCode(), equalTo(status_code));
    }
}
