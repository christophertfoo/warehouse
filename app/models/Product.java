package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import play.db.ebean.Model;

/**
 * The {@link Model} for a product description.
 * @author Philip Johnson (copied by Christopher Foo)
 *
 */
@Entity
public class Product extends Model {

  /**
   * Automatically generated serial ID number.
   */
  private static final long serialVersionUID = -7881391014358935700L;

  /**
   * The ID number and primary key of the {@link Product}.
   */
  @Id
  public Long id;
  
  /**
   * The name of the {@link Product}.
   */
  public String name;
  
  /**
   * The description of the {@link Product}.
   */
  public String description;
  
  /**
   * The {@link Tag}s associated with this {@link Product}.
   */
  @ManyToMany(cascade=CascadeType.ALL)
  public List<Tag> tags = new ArrayList<>();
  
  /**
   * The {@link StockItem}s associated with this {@link Product}.
   */
  @OneToMany(mappedBy="product", cascade=CascadeType.ALL)
  public List<StockItem> stockItems = new ArrayList<>();
  
  /**
   * Creates a new {@link Product} with the given name and description.
   * @param name The name of the Product.
   * @param description The description of the Product.
   */
  public Product(String name, String description) {
    this.name = name;
    this.description = description;
  }
  
  /**
   * Gets a {@link Finder} that can be used to query the {@link Product} table.
   * @return A Finder to be used with the Product table.
   */
  public static Finder<Long, Product> find() {
    return new Finder<Long, Product>(Long.class, Product.class);
  }
}
