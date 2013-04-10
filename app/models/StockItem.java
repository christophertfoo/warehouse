package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
  public Long id;

  /**
   * The {@link Warehouse} that this {@link StockItem} is stored at.
   */
  @ManyToOne(cascade = CascadeType.ALL)
  public Warehouse warehouse;

  /**
   * The {@link Product} stored at the {@link Warehouse} that is tracked by this {@link StockItem}.
   */
  @ManyToOne(cascade = CascadeType.ALL)
  public Product product;

  /**
   * The number of the item at the {@link Warehouse}.
   */
  public long quantity;

  /**
   * Creates a new {@link StockItem} with the given values.
   * 
   * @param warehouse The {@link Warehouse} that this StockItem is stored at.
   * @param product The {@link Product} that is being stored.
   * @param quantity The number of the Product that is being stored.
   */
  public StockItem(Warehouse warehouse, Product product, long quantity) {
    this.warehouse = warehouse;
    this.product = product;
    this.quantity = quantity;
  }

  /**
   * Gets a {@link Finder} that can be used to query the {@link StockItem} table.
   * 
   * @return A Finder to be used with the StockItem table.
   */
  public static Finder<Long, StockItem> find() {
    return new Finder<Long, StockItem>(Long.class, StockItem.class);
  }
}
