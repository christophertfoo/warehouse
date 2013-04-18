package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Required;
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
   * The ID number and primary key of this {@link Product}.
   */
  @Id
  private Long primaryKey;
  

  /**
   * The ID for this {@link Product}.
   */
  @Required
  private String productId;
  

  /**
   * The name of this {@link Product}.
   */
  @Required
  private String name;
  
  /**
   * The description of this {@link Product}.
   */
  private String description;
  
  /**
   * The {@link Tag}s associated with this {@link Product}.
   */
  @ManyToMany(cascade=CascadeType.PERSIST)
  private List<Tag> tags = new ArrayList<>();
  
  /**
   * The {@link StockItem}s associated with this {@link Product}.
   */
  @OneToMany(mappedBy="product", cascade=CascadeType.ALL)
  private List<StockItem> stockItems = new ArrayList<>();
  
  /**
   * Creates a new {@link Product} with the given name and description.
   * @param name The name of the Product.
   * @param description The description of the Product.
   * TODO UPDATE
   */
  public Product(String productId, String name, String description) {
    this.productId = productId;
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
  
  @Override
  public String toString() {
    return String.format("[Product %s %s %s]", this.productId, this.name, this.description);
  }

  /**
   * @return the primaryKey
   */
  public Long getPrimaryKey() {
    return this.primaryKey;
  }

  /**
   * @param primaryKey the primaryKey to set
   */
  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * @return the productId
   */
  public String getProductId() {
    return this.productId;
  }

  /**
   * @param productId the productId to set
   */
  public void setProductId(String productId) {
    this.productId = productId;
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
   * @return the description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the tags
   */
  public List<Tag> getTags() {
    return this.tags;
  }

  /**
   * @param tags the tags to set
   */
  public void setTags(List<Tag> tags) {
    this.tags = tags;
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
}
