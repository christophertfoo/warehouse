import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import java.util.List;
import models.Address;
import models.Product;
import models.StockItem;
import models.Tag;
import models.Warehouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;

/**
 * Tests the models of the warehouse Play application.
 * 
 * @author Philip Johnson (copied by Christopher Foo)
 * 
 */
public class ModelTest {

  /**
   * The {@link FakeApplication} used to run the tests.
   */
  public FakeApplication application;

  /**
   * Start the application before each test.
   */
  @Before
  public void startApp() {
    this.application = fakeApplication(inMemoryDatabase());
    start(this.application);
  }

  /**
   * Stop the application after each test.
   */
  @After
  public void stopApp() {
    stop(this.application);
  }

  /**
   * Test each of the models.
   */
  @Test
  public void testModel() {
    // Create 1 tag that's associated with 1 product.
    Tag tag = new Tag("Tag");
    Product product = new Product("1", "Product", "Description");
    product.getTags().add(tag);
    tag.getProducts().add(product);

    // Create 1 warehouse that's associated with 1 StockItem for 1 Product
    Warehouse warehouse = new Warehouse("1", "Warehouse");
    Address address = new Address("11-1111", "Honolulu", "HI", 1234, warehouse);
    StockItem stockitem = new StockItem("1", warehouse, product, 100);
    warehouse.getStockItems().add(stockitem);
    stockitem.setWarehouse(warehouse);

    // Persist the sample model by saving all entities and relationships
    warehouse.save();
    tag.save();
    product.save();
    stockitem.save();
    address.save();

    // Retrieve the entire model from the database.
    List<Warehouse> warehouses = Warehouse.find().findList();
    List<Tag> tags = Tag.find().findList();
    List<Product> products = Product.find().findList();
    List<StockItem> stockitems = StockItem.find().findList();
    List<Address> addresses = Address.find().findList();

    // Check that we've recovered all our entities.
    assertEquals("Checking warehouse", warehouses.size(), 1);
    assertEquals("Checking tags", tags.size(), 1);
    assertEquals("Checking products", products.size(), 1);
    assertEquals("Checking stockitems", stockitems.size(), 1);
    assertEquals("Checking addresses", addresses.size(), 1);

    // Check that we've recovered all relationships.
    assertEquals("Warehouse-StockItem", warehouses.get(0).getStockItems().get(0), stockitems.get(0));
    assertEquals("StockItem-Warehouse", stockitems.get(0).getWarehouse(), warehouses.get(0));
    assertEquals("Product-StockItem", products.get(0).getStockItems().get(0), stockitems.get(0));
    assertEquals("StockItem-Product", stockitems.get(0).getProduct(), products.get(0));
    assertEquals("Product-Tag", products.get(0).getTags().get(0), tags.get(0));
    assertEquals("Tag-Product", tags.get(0).getProducts().get(0), products.get(0));
    assertEquals("Warehouse-Address", warehouses.get(0).getAddress(), addresses.get(0));
    assertEquals("Address-Warehouse", addresses.get(0).getWarehouse(), warehouses.get(0));

    // Some code to illustrate model manipulation with ORM.
    // Start in Java, Delete the tag from the (original) product instance.
    product.getTags().clear();

    // Persist the revised product instance.
    product.save();

    // Tags should be cleared.
    assertTrue("Previously retrieved product still has tag", !products.get(0).getTags().isEmpty());

    // The persisted product should have had its tags cleared as well.
    assertTrue("Fresh Product has no tag", Product.find().findList().get(0).getTags().isEmpty());

    // There are no tags associated with the products due to the clear.
    assertTrue("Fresh Tag has no Products", Tag.find().findList().get(0).getProducts().isEmpty());

    // Delete tag.
    tag.delete();
    assertTrue("No more tags in database", Tag.find().findList().isEmpty());
  }
}
