package api.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.PetEndPoints;
import api.payload.Pet;
import api.payload.Pet.Category;
import api.payload.Pet.Tag;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class PetTests {

	Faker faker;
	Pet pet;

	// Setup test data before running the test methods.
	@BeforeClass
	public void setData() {

		// Initializing Faker and creating random data for the Pet object
		faker = new Faker();
		pet = new Pet();

		// Setting a random ID for the pet
		pet.setId(faker.number().randomNumber());

		// Setting a random ID and name for the category
		Category category = new Category();
		category.setId(faker.idNumber().hashCode());
		category.setName("Category " + faker.animal().name());
		pet.setCategory(category);

		// Setting a random name for the pet
		pet.setName(faker.animal().name());

		// Creating a list of random photo URLs for the pet
		List<String> photoUrls = new ArrayList<>();
		photoUrls.add("photourl1");
		photoUrls.add("photourl2");
		photoUrls.add("photourl3");
		pet.setPhotoUrls(photoUrls);

		// Creating random tags for the pet
		Tag tag1 = new Tag();
		tag1.setId(faker.number().randomNumber());
		tag1.setName("Tag " + faker.animal().name());

		Tag tag2 = new Tag();
		tag2.setId(faker.number().randomNumber());
		tag2.setName("Tag " + faker.animal().name());

		// Adding the tags to the list
		List<Tag> tags = new ArrayList<>();
		tags.add(tag1);
		tags.add(tag2);
		pet.setTags(tags);

		// Setting the status of the pet as available
		pet.setStatus("available");
	}

	@Test(priority = 1)
	public void testCreatePet() {

		// Sending a request to create a pet and log response details
		Response response = PetEndPoints.createPet(pet);
		response.then().log().all();

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to create pet");

		// Validating JSON schema
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("PetSchema.json"));
	}

	@Test(priority = 2, dependsOnMethods = { "testCreatePet" })
	public void testGetPet() {

		// Sending a request to retrieve the pet and log response details
		Response response = PetEndPoints.getPet(this.pet.getId());
		response.then().log().all();

		// Parsing response JSON
		JSONObject data = new JSONObject(response.asString());

		// Validating photo URLs
		String photoUrl1FromResponse = data.getJSONArray("photoUrls").get(0).toString();
		String photoUrl1FromList = this.pet.getPhotoUrls().get(0);
		Assert.assertEquals(photoUrl1FromResponse, photoUrl1FromList, "Photo URL mismatch");

		// Validating tag names
		String tagName1FromResponse = data.getJSONArray("tags").getJSONObject(0).get("name").toString();
		List<Tag> tags = this.pet.getTags();
		String tagName1FromArray = tags.get(0).getName();
		Assert.assertEquals(tagName1FromResponse, tagName1FromArray, "Tag name mismatch");

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve pet");
	}

	@Test(priority = 3, dependsOnMethods = { "testCreatePet" })
	public void testGetPetByStatus() {

		// Sending a request to retrieve pets by status and log response details
		Response response = PetEndPoints.getPetByStatus(this.pet.getStatus());
		response.then().log().all();

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve pets by status");
	}

	@Test(priority = 4, dependsOnMethods = { "testCreatePet", "testGetPet" })
	public void testUpdatePet() {

		// Updating pet details
		pet.setName(faker.animal().name());
		pet.setStatus("unavailable");
		Response responseBeforeUpdate = PetEndPoints.updatePet(pet);
		responseBeforeUpdate.then().log().all();
		Assert.assertEquals(responseBeforeUpdate.getStatusCode(), 200, "Failed to update pet");

		// Retrieving updated pet details
		Response responseAfterUpdate = PetEndPoints.getPet(this.pet.getId());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200, "Failed to retrieve updated pet details");
	}

	@Test(priority = 5, dependsOnMethods = { "testCreatePet" })
	public void testUploadImage() {

		// Uploading an image for the pet and log response details
		String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\images\\testimage1.jpg";
		Response response = PetEndPoints.uploadImage(this.pet.getId(), filePath);
		response.then().log().all();

		// Retrieving the response message and verifying if the response message
		// contains the image name
		String message = response.jsonPath().get("message");
		Assert.assertTrue(message.contains("testimage1.jpg"),
				"Image upload failed: Response message does not contain expected image name");

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to upload image: Unexpected status code");
	}

	@Test(priority = 6, dependsOnMethods = { "testCreatePet" })
	public void testDeletePet() {

		// Deleting the pet and log response details
		Response response = PetEndPoints.deletePet(this.pet.getId());
		response.then().log().all();

		//// If cookies are present
		// Map<String, String> cookies = response.getCookies();
		// cookies.keySet();

		// Printing response headers
		Headers myHeaders = response.getHeaders();
		for (Header header : myHeaders) {
			System.out.println(
					"Header name is :- " + header.getName() + " and the value of header is :- " + header.getValue());
		}

		// Validating response status code
		Assert.assertEquals(response.getStatusCode(), 200, "Failed to delete pet");
	}
}
