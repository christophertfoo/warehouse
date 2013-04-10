package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.db.ebean.Model;

/**
 * A {@link Model} that represents the address of a {@link Warehouse}.
 * 
 * @author Christopher Foo
 * 
 */
@Entity
public class Address extends Model {

  /**
   * Automatically generated serial ID.
   */
  private static final long serialVersionUID = -5999834051115543069L;

  /**
   * The ID number of the {@link Address} and primary key in the database table.
   */
  @Id
  public Long id;

  /**
   * The {@link Warehouse} that this {@link Address} is for. The Address is owned by the Warehouse.
   */
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
  public Warehouse warehouse;

  /**
   * The house / building number of the {@link Warehouse}.
   */
  public String number;

  /**
   * The city that the {@link Warehouse} is in.
   */
  public String city;

  /**
   * The state that the {@link Warehouse} is in.
   */
  public String state;

  /**
   * The zipcode of the {@link Warehouse}.
   */
  public int zipcode;

  /**
   * Creates an {@link Address} with the given values.
   * 
   * @param number The house / building number of the {@link Warehouse}.
   * @param city The city that the Warehouse is in.
   * @param state The state that the Warehouse is in.
   * @param zipcode The zipcode of the Warehouse.
   */
  public Address(String number, String city, String state, int zipcode) {
    this.number = number;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
  }

  /**
   * Returns a {@link Finder} that can be used to execute queries on the {@link Address} table.
   * @return A Finder to be used with the Address table.
   */
  public static Finder<Long, Address> find() {
    return new Finder<Long, Address>(Long.class, Address.class);
  }
}
