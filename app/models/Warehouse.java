package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * A {@link Model} for a Warehouse that stores multiple items.
 * 
 * @author Philip Johnson (copied by Christopher Foo)
 * 
 */
@Entity
public class Warehouse extends Model {

  /**
   * Serial ID number for the {@link Warehouse}.
   */
  private static final long serialVersionUID = -5901301729351151823L;

  /**
   * The primary key used to identify each {@link Warehouse}.
   */
  @Id
  private long primaryKey;

  @Required
  private String warehouseId;

  /**
   * The name of this {@link Warehouse}.
   */
  @Required
  private String name;

  /**
   * The {@link StockItem}s stored in this {@link Warehouse}.
   */
  @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
  private List<StockItem> stockItems = new ArrayList<>();

  /**
   * The {@link Address} of this {@link Warehouse}.
   */
  @OneToOne(mappedBy = "warehouse", cascade = CascadeType.ALL)
  private Address address;

  /**
   * Creates a new {@link Warehouse} with the given name and {@link Address}.
   * 
   * @param name The name of the Warehouse. TODO UPDATE
   */
  public Warehouse(String warehouseId, String name) {
    this.warehouseId = warehouseId;
    this.name = name;
  }

  /**
   * Gets a {@link Finder} that can be used to query the {@link Warehouse} table.
   * 
   * @return A Finder to be used with the Warehouse table.
   */
  public static Finder<Long, Warehouse> find() {
    return new Finder<Long, Warehouse>(Long.class, Warehouse.class);
  }

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();
    if (Warehouse.find().where().eq("warehouseId", this.warehouseId).findUnique() != null) {
      errors.add(new ValidationError("AlreadyExists", "A warehouse with ID '" + this.warehouseId
          + "' already exists."));
    }
    return (errors.size() == 0) ? null : errors;
  }

  @Override
  public String toString() {
    return String.format("[Warehouse %s %s %s]", this.warehouseId, this.name, (this.address == null) ? "NULL" : this.address);
  }

  /**
   * @return the primaryKey
   */
  public long getPrimaryKey() {
    return this.primaryKey;
  }

  /**
   * @param primaryKey the primaryKey to set
   */
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * @return the warehouseId
   */
  public String getWarehouseId() {
    return this.warehouseId;
  }

  /**
   * @param warehouseId the warehouseId to set
   */
  public void setWarehouseId(String warehouseId) {
    this.warehouseId = warehouseId;
  }

  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the stockItems
   */
  public List<StockItem> getStockItems() {
    return this.stockItems;
  }

  /**
   * @param stockItems the stockItems to set
   */
  public void setStockItems(List<StockItem> stockItems) {
    this.stockItems = stockItems;
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return this.address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(Address address) {
    this.address = address;
  }
}
