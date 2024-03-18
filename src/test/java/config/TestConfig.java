package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import io.restassured.RestAssured;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.lessThan;

/**
 * Base class for configurations and base assertions of requests to the API
 */
public class TestConfig {
  @BeforeClass
  public static void setup() {

    // Setting base configurations for requests
    RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://dummyjson.com/")
            .setContentType("application/json")
            .setBasePath("products/")
            .build();

    // Setting base assertions for responses
    RestAssured.responseSpecification = new ResponseSpecBuilder()
            // Checking status code
            .expectStatusCode(200)
            // Checking headers
            .expectContentType("application/json; charset=utf-8")
            // In a real project we would verify only the desired domains have access to the API
            .expectHeader("access-control-allow-origin", "*")
            // Checking response time
            .expectResponseTime(lessThan(5L), TimeUnit.SECONDS)
            .build();
  }
}
