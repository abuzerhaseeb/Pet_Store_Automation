package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.StoreOrder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreOrderEndPoints {

	// Method for creating a new store order
	public static Response createStoreOrderPet(StoreOrder storeOrder) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(storeOrder).when()
				.post(StoreOrderRoutes.store_order_pet_post_url);

		return response;
	}

	// Method for retrieving a store order by order ID
	public static Response getStoreOrderPet(long orderId) {
		Response response = given().pathParam("orderId", orderId).when().get(StoreOrderRoutes.store_order_pet_get_url);

		return response;
	}

	// Method for deleting a store order by order ID
	public static Response deleteStoreOrderPet(long orderId) {
		Response response = given().pathParam("orderId", orderId).when()
				.delete(StoreOrderRoutes.store_order_pet_delete_url);

		return response;
	}

	// Method for retrieving store inventory
	public static Response getStoreInventory() {
		Response response = given().when().get(StoreOrderRoutes.store_get_inventory);

		return response;
	}
}
