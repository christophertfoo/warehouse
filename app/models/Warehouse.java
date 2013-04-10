package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * A {@link Model} for a Warehouse that stores multiple items.
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
  public long id;
  
  /**
   * The name of this {@link Warehouse}.
   */
  public String name;
  
  /**
   * The {@link StockItem}s stored in this {@link Warehouse}.
   */
  @OneToMany(mappedBy="warehouse", cascade=CascadeType.ALL)
  public List<StockItem> stockItems = new ArrayList<>();
  
  /**
   * The {@link Address} of this {@link Warehouse}.
   */
  @OneToOne(cascade=CascadeType.ALL)
  public Address address;
  
  /**
   * Creates a new {@link Warehouse} with the given name and {@link Address}.
   * @param name The name of the Warehouse.
   * @param address The Address of the Warehouse.
   */
  public Warehouse(String name, Address address) {
    this.name = name;
    this.address = address;
  }
  
  /**
   * Gets a {@link Finder} that can be used to query the {@link Warehouse} table.
   * 
   * @return A Finder to be used with the Warehouse table.
   */
  public static Finder<Long, Warehouse> find() {
    return new Finder<Long, Warehouse>(Long.class, Warehouse.class);
  }
}
