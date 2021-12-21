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
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class ConcurrentOp {

    RequestSpecification request;
    Response response;
    private final static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private int status;
    private int actualStatus;

    @Given("^User sets the base API to request this (.+)$")
    public void user_sets_the_base_api_to_request_this(String url) throws Throwable {
        request = given().baseUri(url).config(RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().dontReuseHttpClientInstance()));
        logr.log(Level.INFO, "Connection opened with the base URL");
    }

    @When("^User sends the request to this (.+) (.+) with UUid that is valid concurrently$")
    public void user_sends_the_request_to_this_with_uuid_that_is_valid_concurrently(String url, String endpoint) throws Throwable {

        response = request.post(endpoint).then().extract().response();
        ExecutorService executorService = Executors.newFixedThreadPool(10);// Java to allocate 10 threads

        //do while loop that will create 10 of the same threads
        do {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        request = given().baseUri(url);
                        response = request.post(endpoint).then().extract().response();

                        if (response.getStatusCode() == 201)//
                        {
                            status = response.getStatusCode();
                            logr.log(Level.INFO, "One of the threads come back with status :" + response.getStatusCode());
                        } else if (response.getStatusCode() == 423) {
                            actualStatus = response.getStatusCode();
                        }

                        logr.log(Level.INFO, "Request sent multi threaded");
                    } catch (Exception e) {
                        logr.log(Level.SEVERE, e.getMessage());
                    }
                }
            });
        } while (response.getStatusCode() != 423);

        // Wait for all the threads to gracefully shutdown
        try {


            executorService.awaitTermination(2000, TimeUnit.MILLISECONDS);
            logr.log(Level.INFO, "Threads shut down");
        } catch (InterruptedException ie) {
            System.out.println("Shutdown interrupted. Will try to shutdown again.");
            executorService.shutdownNow();
            logr.log(Level.SEVERE, ie.getMessage());
        }

    }


    @Then("^User should be presented with  (.+) as response on one of the transactions$")
    public void user_should_be_presented_with_as_response_on_one_of_the_transactions(String statuscode) throws Throwable {
        int code = new Integer(statuscode);
        Assert.assertEquals(code, actualStatus);
        logr.log(Level.INFO, "Assertion done");
    }

    @Then("^One of the request that was sent concurrently should return  the given status code (.+)$")
    public void one_of_the_request_that_was_sent_concurrently_should_return_the_given_status_code(String codestatus) throws Throwable {

        int code = new Integer(codestatus);
        Assert.assertEquals(code, status);
        logr.log(Level.INFO, "Assertion done");
    }


}
