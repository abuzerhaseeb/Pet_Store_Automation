package api.endpoints;

public class UserRoutes {

	// Base URL for the user endpoints
	public static final String base_url = "https://petstore.swagger.io/v2";

	// Endpoint URLs for various user operations

	// Endpoint for creating a new user
	public static final String user_post_url = base_url + "/user";

	// Endpoint for retrieving user information by username
	public static final String user_get_url = base_url + "/user/{username}";

	// Endpoint for updating existing user information by username
	public static final String user_put_url = base_url + "/user/{username}";

	// Endpoint for deleting a user by username
	public static final String user_delete_url = base_url + "/user/{username}";
}
