package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class InvalidUUID {

    RequestSpecification request;
    Response response;
    private final  static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Given("^User sets the base API to request  (.+)$")
    public void user_sets_the_base_api_to_request(String url) throws Throwable {
        request= given().baseUri(url);
        logr.log(Level.INFO,"Connection opened with the base URL");
    }

    @When("^User sends the request to this (.+) with UUid been Invalid$")
    public void user_sends_the_request_to_this_with_uuid_been_invalid(String endpoint) throws Throwable {
        response= request.post(endpoint).then().extract().response();
        logr.log(Level.INFO,"Request sent");
    }


    @Then("^User should be presented with  (.+) as response$")
    public void user_should_be_presented_with_as_response(String statuscode) throws Throwable {
        int code =new Integer(statuscode);
        Assert.assertEquals(code,response.getStatusCode());
        logr.log(Level.INFO,"Assertion done");
    }


}
