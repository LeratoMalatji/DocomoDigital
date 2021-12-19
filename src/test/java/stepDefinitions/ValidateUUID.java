package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class ValidateUUID {

    RequestSpecification request;
    Response response;


    @Given("^User sets the base API request (.+)$")
    public void user_sets_the_base_api_request(String url){
       request= given().baseUri(url);

    }

    @When("^User sends the request to this (.+) with id been valid$")
    public void user_sends_the_request_to_this_with_id_been_valid(String endpoint){

        response= request.post(endpoint).then().extract().response();

    }

    @Then("^User should be presented with  (.+) as a response$")
    public void user_should_be_presented_with_as_a_response(String status){

        int code =new Integer(status);
        Assert.assertEquals(code,response.getStatusCode());

    }
}
