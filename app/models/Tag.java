package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
   * The ID number and primary key of the {@link Tag}.
   */
  @Id
  public Long id;

  /**
   * The name of the {@link Tag}.
   */
  public String name;

  /**
   * The {@link Product}s associated with the {@link Tag}.
   */
  @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
  public List<Product> products = new ArrayList<>();

  /**
   * Creates a new {@link Tag} with the given name.
   * 
   * @param name The name of the Tag.
   */
  public Tag(String name) {
    this.name = name;
  }

  /**
   * Gets a {@link Finder} that can be used to query the {@link Tag} table.
   * 
   * @return A Finder to be used with the Tag table.
   */
  public static Finder<Long, Tag> find() {
    return new Finder<Long, Tag>(Long.class, Tag.class);
  }
}
