package api.endpoints;

import static io.restassured.RestAssured.given;

import java.io.File;

import api.payload.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndPoints {

	// Method to create a new pet
	public static Response createPet(Pet pet) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(pet).when()
				.post(PetRoutes.pet_post_url);
		return response;
	}

	// Method to retrieve pet information by ID
	public static Response getPet(long petId) {
		Response response = given().pathParam("petId", petId).when().get(PetRoutes.pet_get_url);
		return response;
	}

	// Method to retrieve pets by status
	public static Response getPetByStatus(String status) {
		Response response = given().queryParam("status", status).when().get(PetRoutes.pet_findByStatus_url);
		return response;
	}

	// Method to update existing pet information
	public static Response updatePet(Pet pet) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(pet).when()
				.put(PetRoutes.pet_put_url);
		return response;
	}

	// Method to upload an image for a pet
	public static Response uploadImage(long petId, String filePath) {
		File file = new File(filePath);
		Response response = given().multiPart("file", file).contentType(ContentType.MULTIPART).pathParam("petId", petId)
				.when().post(PetRoutes.pet_uploadImage_url);
		return response;
	}

	// Method to delete a pet by ID
	public static Response deletePet(long petId) {
		Response response = given().pathParam("petId", petId).when().delete(PetRoutes.pet_delete_url);
		return response;
	}
}
