package api.endpoints;

public class PetRoutes {

	// Base URL for the pet store API
	public static final String base_url = "https://petstore.swagger.io/v2";

	// Endpoint URLs for various pet operations

	// URL for creating a new pet
	public static final String pet_post_url = base_url + "/pet";

	// URL for retrieving pet information by ID
	public static final String pet_get_url = base_url + "/pet/{petId}";

	// URL for updating existing pet information
	public static final String pet_put_url = base_url + "/pet";

	// URL for deleting a pet by ID
	public static final String pet_delete_url = base_url + "/pet/{petId}";

	// URL for uploading an image for a pet
	public static final String pet_uploadImage_url = base_url + "/pet/{petId}/uploadImage";

	// URL for finding pets by status
	public static final String pet_findByStatus_url = base_url + "/pet/findByStatus";
}