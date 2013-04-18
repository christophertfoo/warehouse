package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * A {@link Model} of a Tag that is associated with a set of {@link Products}.
 * 
 * @author Philip Johnson (copied by Christopher Foo)
 * 
 */
@Entity
public class Tag extends Model {

  /**
   * Automatically generated ID number.
   */
  private static final long serialVersionUID = 5476207418180200244L;

  /**
   * The ID number and primary key of this {@link Tag}.
   */
  @Id
  private Long primaryKey;

  @Required
  private String tagId;

  /**
   * The {@link Product}s associated with the {@link Tag}.
   */
  @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
  private List<Product> products = new ArrayList<>();

  /**
   * Creates a new {@link Tag} with the given name.
   * 
   * @param name The name of the Tag.
   * TODO UPDATE
   */
  public Tag(String tagId) {
    this.tagId = tagId;
  }

  /**
   * Validates the {@link #tagId} of this {@link Tag} to ensure that the tagId does not equal "Tag".
   * 
   * @return null if OK, error string if not OK.
   */
  public String validate() {
    return ("Tag".equals(this.tagId)) ? "Invalid tag name" : null;
  }

  /**
   * Gets a {@link Finder} that can be used to query the {@link Tag} table.
   * 
   * @return A Finder to be used with the Tag table.
   */
  public static Finder<Long, Tag> find() {
    return new Finder<Long, Tag>(Long.class, Tag.class);
  }

  @Override
  public String toString() {
    return String.format("[Tag %s ])", tagId);
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
   * @return the tagId
   */
  public String getTagId() {
    return this.tagId;
  }

  /**
   * @param tagId the tagId to set
   */
  public void setTagId(String tagId) {
    this.tagId = tagId;
  }

  /**
   * @return the products
   */
  public List<Product> getProducts() {
    return this.products;
  }

  /**
   * @param products the products to set
   */
  public void setProducts(List<Product> products) {
    this.products = products;
  }
}
