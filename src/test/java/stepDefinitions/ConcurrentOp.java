package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class ConcurrentOp {

    RequestSpecification request;
    Response response;

    @Given("^User sets the base API to request this (.+)$")
    public void user_sets_the_base_api_to_request_this(String url) throws Throwable {
        request= given().baseUri(url).config(RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().dontReuseHttpClientInstance()));
    }

    @When("^User sends the request to this (.+) with UUid that is valid concurrently$")
    public void user_sends_the_request_to_this_with_uuid_that_is_valid_concurrently(String endpoint) throws Throwable {

        response= request.post(endpoint).then().extract().response();

        ExecutorService executorService = Executors.newFixedThreadPool(10);// Java to allocate 10 threads

        //do while loop that will create 10 of the same threads
        do{
            executorService.execute(new Runnable() {
                @Override
                public void run(){
                    try {
                        request= given().baseUri("http://localhost:8088");
                        response= request.post(endpoint).then().extract().response();
                        System.out.println(response.getStatusCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }while(response.getStatusCode()!=423);

        // Wait for all the threads to gracefully shutdown
        try {
            executorService.awaitTermination(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ie) {
            System.out.println("Shutdown interrupted. Will try to shutdown again.");
            executorService.shutdownNow();
        }

    }

    @Then("^User should be presented with  (.+) as response on one of the transactions$")
    public void user_should_be_presented_with_as_response_on_one_of_the_transactions(String statuscode) throws Throwable {
        int code =new Integer(statuscode);
        Assert.assertEquals(code,response.getStatusCode());
    }

}
