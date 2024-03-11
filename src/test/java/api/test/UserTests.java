package api.test;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User user;

	// Setup test data before running the test methods.
	@BeforeClass
	public void setupData() {

		// Initializing Faker and creating random data for the User object
		faker = new Faker();
		user = new User();

		user.setId(faker.number().randomNumber());
		user.setUsername(faker.name().username());
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().safeEmailAddress());
		user.setPassword(faker.internet().password(5, 10));
		user.setPhone(faker.phoneNumber().cellPhone());
	}

	// Test method to create a new user.
	@Test(priority = 1)
	public void testCreateUser() {

		// Send a request to create a new user and log response details
		Response response = UserEndPoints.createUser(user);
		response.then().log().all();

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to create user.");
	}

	// Test method to retrieve user information.
	@Test(priority = 2, dependsOnMethods = { "testCreateUser" })
	public void testGetUser() {

		// Send a request to retrieve user information by username and log response
		// details
		Response response = UserEndPoints.getUser(this.user.getUsername());
		response.then().log().all();

		// Convert the response body to a JSON object
		JSONObject data = new JSONObject(response.asString());

		// Validate that the retrieved user's first name matches the expected first name
		String firstNameFromResponse = data.get("firstName").toString();
		Assert.assertEquals(firstNameFromResponse, this.user.getFirstName(), "First name mismatch.");

		// Validate that the retrieved user's last name matches the expected last name
		String lastNameFromResponse = data.get("lastName").toString();
		Assert.assertEquals(lastNameFromResponse, this.user.getLastName(), "Last name mismatch.");

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve user.");
	}

	// Test method to update user information.
	@Test(priority = 3, dependsOnMethods = { "testCreateUser", "testGetUser" })
	public void testUpdateUser() {

		// Update user information
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().safeEmailAddress());

		// Send a request to update user information and validate the response
		Response responseBeforeUpdate = UserEndPoints.updateUser(this.user.getUsername(), user);
		responseBeforeUpdate.then().statusCode(200);
		Assert.assertEquals(responseBeforeUpdate.getStatusCode(), 200, "Failed to update user.");

		// Retrieve updated user information and validate the response
		Response responseAfterUpdate = UserEndPoints.getUser(this.user.getUsername());
		responseAfterUpdate.then().log().all();

		// Validating response status code
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200, "Failed to retrieve updated user.");
	}

	// Test method to delete a user.
	@Test(priority = 4, dependsOnMethods = { "testCreateUser" })
	public void testDeleteUser() {
		// Send a request to delete a user and validate the response
		Response response = UserEndPoints.deleteUser(this.user.getUsername());
		response.then().log().all();

		// Validate the Transfer-Encoding header in the response
		Assert.assertEquals(response.getHeader("Transfer-Encoding"), "chunked", "Invalid Transfer-Encoding header.");

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete user.");
	}
}
