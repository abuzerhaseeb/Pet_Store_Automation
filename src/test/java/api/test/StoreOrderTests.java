package api.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.StoreOrderEndPoints;
import api.payload.StoreOrder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class StoreOrderTests {

	Faker faker;
	StoreOrder store;

	// Setup test data before running the test methods.
	@BeforeClass
	public void setupData() {

		// Initializing Faker and creating random data for the StoreOrder object
		faker = new Faker();
		store = new StoreOrder();

		// Set random values for StoreOrder properties
		store.setId(faker.number().randomNumber());
		store.setPetId(faker.number().randomNumber());
		store.setQuantity(faker.number().randomNumber());

		// Get the current date and time
		LocalDateTime now = LocalDateTime.now();
		// Define the date and time format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		// Format the LocalDateTime object
		String formattedDateTime = now.format(formatter);
		// Set formatted date and time to the shipDate property
		store.setShipDate(formattedDateTime);

		// Set default values for other properties
		store.setStatus("placed");
		store.setComplete(true);
	}

	// Test method to create a store order
	@Test(priority = 1)
	public void testCreateStoreOrder() {

		// Send request to create a store order and log response details
		Response response = StoreOrderEndPoints.createStoreOrderPet(store);
		response.then().log().all();

		// Validate response body against JSON schema
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("StoreOrderSchema.json"));

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to create a store order");
	}

	// Test method to get a store order
	@Test(priority = 2, dependsOnMethods = { "testCreateStoreOrder" })
	public void testGetStoreOrder() {

		// Send request to get a store order by ID and log response details
		Response response = StoreOrderEndPoints.getStoreOrderPet(this.store.getId());
		// Log response details
		response.then().log().all();
		// Convert response body to JSON object
		JSONObject data = new JSONObject(response.asString());

		// Assert the ID from the response matches the ID set in the test data
		long idFromResponse = Long.parseLong(data.get("id").toString());
		Assert.assertEquals(idFromResponse, this.store.getId(), "Store order ID mismatch");

		// Assert the 'complete' flag is true
		Assert.assertEquals(data.get("complete").toString(), "true", "Store order completion status mismatch");

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to get the store order details");
	}

	// Test method to delete a store order
	@Test(priority = 3, dependsOnMethods = { "testCreateStoreOrder" })
	public void testDeleteStoreOrder() {

		// Send request to delete a store order by ID and log response details
		Response response = StoreOrderEndPoints.deleteStoreOrderPet(this.store.getId());
		response.then().log().all();

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete the store order");
	}

	// Test method to get store inventory
	@Test(priority = 4)
	public void testGetStoreInventory() {

		// Send request to get store inventory and log response details
		Response response = StoreOrderEndPoints.getStoreInventory();
		response.then().log().all();

		// Log response headers
		Headers myHeaders = response.getHeaders();
		for (Header header : myHeaders) {
			System.out.println(
					"Header name is :- " + header.getName() + " and the value of header is :- " + header.getValue());
		}

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to get store inventory");
	}
}
