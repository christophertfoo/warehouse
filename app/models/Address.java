package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
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
  private Long primaryKey;

  /**
   * The {@link Warehouse} that this {@link Address} is for. The Address is owned by the Warehouse.
   */
  @OneToOne(cascade = CascadeType.PERSIST)
  private Warehouse warehouse;

  /**
   * The street address of the {@link Warehouse}.
   */
  @Required
  private String streetAddress;

  /**
   * The city that the {@link Warehouse} is in.
   */
  @Required
  private String city;

  /**
   * The state that the {@link Warehouse} is in.
   */
  @Required
  @MinLength(2)
  @MaxLength(2)
  private String state;

  /**
   * The zipcode of the {@link Warehouse}.
   */
  @Required
  @Min(0)
  private int zipcode;

  /**
   * Creates an {@link Address} with the given values.
   * 
   * @param streetAddress The street address of the {@link Warehouse}.
   * @param city The city that the Warehouse is in.
   * @param state The state that the Warehouse is in.
   * @param zipcode The zipcode of the Warehouse.
   */
  public Address(String streetAddress, String city, String state, int zipcode, Warehouse warehouse) {
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
    this.warehouse = warehouse;
  }

  /**
   * Returns a {@link Finder} that can be used to execute queries on the {@link Address} table.
   * 
   * @return A Finder to be used with the Address table.
   */
  public static Finder<Long, Address> find() {
    return new Finder<Long, Address>(Long.class, Address.class);
  }

  @Override
  public String toString() {
    return String.format("[Address %s %s %s %s %d]", (this.warehouse == null) ? "NULL"
        : this.warehouse.getWarehouseId(), this.streetAddress, this.city, this.state, this.zipcode);
  }

  public Long getPrimaryKey() {
    return this.primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public Warehouse getWarehouse() {
    return this.warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  public String getStreetAddress() {
    return this.streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getZipcode() {
    return this.zipcode;
  }

  public void setZipcode(int zipcode) {
    this.zipcode = zipcode;
  }
}
