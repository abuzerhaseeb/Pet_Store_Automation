package api.endpoints;

public class StoreOrderRoutes {

	// Base URL for the store endpoints
	public static final String base_url = "https://petstore.swagger.io/v2";

	// Endpoint URLs for various store order operations

	// Endpoint for creating a new store order
	public static final String store_order_pet_post_url = base_url + "/store/order";

	// Endpoint for retrieving a store order by its ID
	public static final String store_order_pet_get_url = base_url + "/store/order/{orderId}";

	// Endpoint for deleting a store order by its ID
	public static final String store_order_pet_delete_url = base_url + "/store/order/{orderId}";

	// Endpoint for retrieving store inventory
	public static final String store_get_inventory = base_url + "/store/inventory";
}
