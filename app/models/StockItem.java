package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.db.ebean.Model;

/**
 * A {@link Model} of the stock of items available in the {@link Warehouse}.
 * 
 * @author Philip Johnson (copied by Christopher Foo)
 * 
 */
@Entity
public class StockItem extends Model {

  /**
   * Automatically generated ID number.
   */
  private static final long serialVersionUID = 705189973737179533L;

  /**
   * The ID number and primary key of the {@link StockItem}.
   */
  @Id
  private Long primaryKey;

  @Required
  private String stockItemId;

  /**
   * The {@link Warehouse} that this {@link StockItem} is stored at.
   */
  @Required
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Warehouse warehouse;

  /**
   * The {@link Product} stored at the {@link Warehouse} that is tracked by this {@link StockItem}.
   */
  @Required
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Product product;

  /**
   * The number of the item at the {@link Warehouse}.
   */
  @Required
  private long quantity;

  /**
   * Creates a new {@link StockItem} with the given values.
   * 
   * @param warehouse The {@link Warehouse} that this StockItem is stored at.
   * @param product The {@link Product} that is being stored.
   * @param quantity The number of the Product that is being stored. TODO UPDATE
   */
  public StockItem(String stockItemId, Warehouse warehouse, Product product, long quantity) {
    this.stockItemId = stockItemId;
    this.warehouse = warehouse;
    this.product = product;
    this.quantity = quantity;
  }

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (this.warehouse == null
        || Warehouse.find().where().eq("warehouseId", this.warehouse.getWarehouseId()).findUnique() == null) {
      errors.add(new ValidationError("BadWarehouse", "Given Warehouse does not exist."));
    }
    if (this.product == null
        || Product.find().where().eq("productId", this.product.getProductId()).findUnique() == null) {
      errors.add(new ValidationError("BadProduct", "Given Product does not exists."));
    }
    
    return (errors.size() == 0) ? null : errors;
  }

  /**
   * Gets a {@link Finder} that can be used to query the {@link StockItem} table.
   * 
   * @return A Finder to be used with the StockItem table.
   */
  public static Finder<Long, StockItem> find() {
    return new Finder<Long, StockItem>(Long.class, StockItem.class);
  }
  
  @Override
  public String toString() {
    return String.format("[StockItem %s %s %s %d]", this.stockItemId, this.getProduct().toString(), this.getWarehouse().toString(), this.quantity);
  }

  /**
   * @return the primaryKey
   */
  public Long getPrimaryKey() {
    return this.primaryKey;
  }
  
  /**
   * 
   * @param primaryKey
   */
  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * @return the stockItemId
   */
  public String getStockItemId() {
    return this.stockItemId;
  }

  /**
   * @param stockItemId the stockItemId to set
   */
  public void setStockItemId(String stockItemId) {
    this.stockItemId = stockItemId;
  }

  /**
   * @return the warehouse
   */
  public Warehouse getWarehouse() {
    return this.warehouse;
  }

  /**
   * @param warehouse the warehouse to set
   */
  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  /**
   * @return the product
   */
  public Product getProduct() {
    return this.product;
  }

  /**
   * @param product the product to set
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * @return the quantity
   */
  public long getQuantity() {
    return this.quantity;
  }

  /**
   * @param quantity the quantity to set
   */
  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }
}
