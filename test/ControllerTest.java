import java.util.HashMap;
import java.util.Map;
import models.Address;
import models.Product;
import models.Tag;
import models.Warehouse;
import models.StockItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static play.test.Helpers.status;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;

public class ControllerTest {
  private FakeApplication application;

  @Before
  public void startApp() {
    this.application = fakeApplication(inMemoryDatabase());
    start(this.application);
  }

  @After
  public void stopApp() {
    stop(this.application);
  }

  @Test
  public void testProductController() {
    // Test GET /products on an empty database.
    Result result = callAction(controllers.routes.ref.Product.index());
    assertTrue("Empty products", contentAsString(result).contains("No products"));

    // Test GET /products on a database containing a single product.
    String productId = "Product-01";
    Product product = new Product(productId, "French Press", "Coffee Maker");
    product.save();
    result = callAction(controllers.routes.ref.Product.index());
    assertTrue("One product", contentAsString(result).contains(productId));

    // Test GET /products/Product-01
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertTrue("Product detail", contentAsString(result).contains(productId));

    // Test GET /products/BadProductId and make sure we get a 404
    result = callAction(controllers.routes.ref.Product.details("BadProductId"));
    assertEquals("Product detail (bad)", NOT_FOUND, status(result));

    // Test POST /products (with simulated, valid form data).
    Map<String, String> productData = new HashMap<>();
    productData.put("productId", "Product-02");
    productData.put("name", "Baby Gaggia");
    productData.put("description", "Espresso machine");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(productData);
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create new product", OK, status(result));

    // Test POST /products (with simulated, invalid form data)
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create bad product fails", BAD_REQUEST, status(result));

    // Test DELETE /products/Product-01 (a valid productId)
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete current product OK", OK, status(result));
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertEquals("Deleted product gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete missing product also OK", OK, status(result));
  }

  @Test
  public void testTagController() {
    // Test GET /tags on an empty database.
    Result result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("Empty tags", contentAsString(result).contains("No Tags"));

    // Test GET /tags on a database containing a single product.
    String tagId = "Tag-01";
    Tag tag = new Tag(tagId);
    tag.save();
    result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("One tag", contentAsString(result).contains(tagId));

    // Test GET /tags/Tag-01
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertTrue("Tag detail", contentAsString(result).contains(tagId));

    // Test GET /tags/BadTagId and make sure we get a 404
    result = callAction(controllers.routes.ref.Tag.details("BadTagId"));
    assertEquals("Tag detail (bad)", NOT_FOUND, status(result));

    // Test POST /tags (with simulated, valid form data).
    Map<String, String> tagData = new HashMap<>();
    tagData.put("tagId", "Tag-02");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create new tag", OK, status(result));

    // Test POST /tags (with simulated, invalid form data)
    request = fakeRequest();
    tagData.put("tagId", "Tag");
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create bad tag fails", BAD_REQUEST, status(result));

    // Test DELETE /tags/Tag-01 (a valid tagId)
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete current tag OK", OK, status(result));
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertEquals("Deleted tag gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete missing tag also OK", OK, status(result));
  }

  @Test
  public void testStockItemController() {
    // Test GET /stockitems on an empty database.
    Result result = callAction(controllers.routes.ref.StockItem.index());
    assertTrue("Empty stock item", contentAsString(result).contains("No stock items"));

    // Test GET /stockitems on a database containing a single product.
    String stockItemId = "StockItem-01";

    String warehouseId = "Warehouse-01";
    Warehouse warehouse = new Warehouse(warehouseId, "Warehouse 1");
    Address address = new Address("11-1111", "Honolulu", "HI", 1234, warehouse);
    String productId = "Product-01";
    Product product = new Product(productId, "Test Product", "Test!");
    StockItem stockItem = new StockItem(stockItemId, warehouse, product, 5);
    stockItem.save();
    address.save();
    result = callAction(controllers.routes.ref.StockItem.index());
    assertTrue("One stock item", contentAsString(result).contains(stockItemId));

    // Test GET /stockitems/StockItem-01
    result = callAction(controllers.routes.ref.StockItem.details(stockItemId));
    assertTrue("StockItem detail", contentAsString(result).contains(stockItemId));

    // Test GET /stockitems/BadStockItemId and make sure we get a 404
    result = callAction(controllers.routes.ref.Tag.details("BadStockItemId"));
    assertEquals("StockItem detail (bad)", NOT_FOUND, status(result));

    // Test POST /stockitems (with simulated, valid form data).
    Map<String, String> stockItemData = new HashMap<>();
    stockItemData.put("stockItemId", "StockItem-02");
    stockItemData.put("quantity", "5");
    stockItemData.put("warehouse", warehouseId);
    stockItemData.put("product", productId);
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(stockItemData);
    result = callAction(controllers.routes.ref.StockItem.newStockItem(), request);
    assertEquals("Create new stock item", OK, status(result));

    // Test POST /stockitems (with simulated, invalid form data)
    request = fakeRequest();
    stockItemData.put("warehouse", "FAKE");
    request.withFormUrlEncodedBody(stockItemData);
    result = callAction(controllers.routes.ref.StockItem.newStockItem(), request);
    assertEquals("Create bad stock item fails", BAD_REQUEST, status(result));

    // Test DELETE /stockitems/StockItem-01 (a valid tagId)
    result = callAction(controllers.routes.ref.StockItem.delete(stockItemId));
    assertEquals("Delete current stock item OK", OK, status(result));
    result = callAction(controllers.routes.ref.StockItem.details(stockItemId));
    assertEquals("Deleted stock item gone", NOT_FOUND, status(result));
    assertEquals("Other side of stock item relationship", Product.find().findList().get(0)
        .getStockItems().size(), 1);
    result = callAction(controllers.routes.ref.StockItem.delete(stockItemId));
    assertEquals("Delete missing stock item also OK", OK, status(result));
  }
  
  @Test
  public void testWarehouseController() {
    // Test GET /warehouses on an empty database.
    Result result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("Empty warehouses", contentAsString(result).contains("No warehouses"));

    // Test GET /warehouses on a database containing a single product.
    String warehouseId = "Warehouse-01";
    Warehouse warehouse = new Warehouse(warehouseId, "Test Warehouse");
    warehouse.save();
    result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("One warehouse", contentAsString(result).contains(warehouseId));

    // Test GET /warehouses/Warehouse-01
    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
    assertTrue("Warehouse detail", contentAsString(result).contains(warehouseId));

    // Test GET /warehouses/BadWarehouseId and make sure we get a 404
    result = callAction(controllers.routes.ref.Warehouse.details("BadWarehouseId"));
    assertEquals("Warehouse detail (bad)", NOT_FOUND, status(result));

    // Test POST /warehouses (with simulated, valid form data).
    Map<String, String> warehouseData = new HashMap<>();
    warehouseData.put("warehouseId", "Warehouse-02");
    warehouseData.put("name", "Test Warehouse 2");
    warehouseData.put("streetAddress", "111-1111 Some Pl.");
    warehouseData.put("city", "Nowhere");
    warehouseData.put("state", "NW");
    warehouseData.put("zipcode", "10234");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(warehouseData);
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create new warehouse", OK, status(result));
    assertEquals("Check address", Address.find().findList().size(), 1);

    // Test POST /warehouses (with simulated, invalid form data)
    request = fakeRequest();
    warehouseData.clear();
    request.withFormUrlEncodedBody(warehouseData);
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create bad tag fails", BAD_REQUEST, status(result));

    // Test DELETE /warehouses/Warehouse-02 (a valid tagId)
    result = callAction(controllers.routes.ref.Warehouse.delete("Warehouse-02"));
    assertEquals("Delete current warehouse OK", OK, status(result));
    result = callAction(controllers.routes.ref.Warehouse.details("Warehouse-02"));
    assertEquals("Deleted warehouse gone", NOT_FOUND, status(result));
    assertEquals("Check address", 0, Address.find().findList().size());
    result = callAction(controllers.routes.ref.Warehouse.delete("Warehouse-02"));
    assertEquals("Delete missing Warehouse also OK", OK, status(result));
  }

}
