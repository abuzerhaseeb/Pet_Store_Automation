package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {

	// Method to create a new user
	public static Response createUser(User data) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(data).when()
				.post(UserRoutes.user_post_url);

		return response;
	}

	// Method to get user details by username
	public static Response getUser(String username) {
		Response response = given().pathParam("username", username).when().get(UserRoutes.user_get_url);

		return response;
	}

	// Method to update user details by username
	public static Response updateUser(String username, User data) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", username).body(data).when().put(UserRoutes.user_put_url);

		return response;
	}

	// Method to delete a user by username
	public static Response deleteUser(String username) {
		Response response = given().pathParam("username", username).when().delete(UserRoutes.user_delete_url);

		return response;
	}
}
