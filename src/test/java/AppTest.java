import model.Product;
import org.junit.Test;

import config.TestConfig;

import java.util.Collections;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestConfig {

    private final Product DUMMY_PRODUCT = new Product(
            "OnePlus 5T",
            "My cellphone",
            400,
            8.69f,
            5f,
            1,
            "OnePlus",
            "smartphones",
            "A very cool photo",
            Collections.emptyList());

    /**
     * Testing the response of getting all the products is correct
     */
    @Test
    public void testGetProducts() {
        given()
        .when()
                .get()
        .then()
                .log().all()
                // Checking for specific data fields
                .body("products.id[0]", equalTo(1))
                .body("products.title[0]", equalTo("iPhone 9"))
                .body("products.price[0]", lessThan(600))
                // Schema validation
                .body(matchesJsonSchemaInClasspath("productsSchema.json"));
    }

    /**
     * Testing the response of posting a new peoduct is correct
     */
    @Test
    public void testPostProducts() {
        given()
                .contentType("application/json")
                .body(DUMMY_PRODUCT)
        .when()
                .post("add")
        .then()
                .log().all()
                // Checking for specific data fields
                .body("id", is(101))
                .body("title", is("OnePlus 5T"))
                .body("description", is("My cellphone"))
                .body("price", is(400))
                .body("discountPercentage", is(8.69f))
                .body("rating", is(5))
                .body("stock", is(1))
                .body("brand", is("OnePlus"))
                .body("category", is("smartphones"))
                .body("thumbnail", is("A very cool photo"))
                .body("images", is(Collections.emptyList()));
    }

    /**
     * Testing the response of updating a product is correct
     */
    @Test
    public void testPutProducts() {
        given()
                .body(DUMMY_PRODUCT)
        .when()
                .put("1")
        .then()
                .log().all()
                // Checking for specific data fields
                .body("id", is(1))
                .body("title", is("OnePlus 5T"))
                .body("description", is("My cellphone"))
                .body("price", is(400))
                .body("discountPercentage", is(8.69f))
                .body("rating", is(5))
                .body("stock", is(1))
                .body("brand", is("OnePlus"))
                .body("category", is("smartphones"))
                .body("thumbnail", is("A very cool photo"))
                .body("images", is(Collections.emptyList()));
    }
}
