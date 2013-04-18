package controllers;

import java.util.List;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Warehouse extends Controller {
  public static Result index() {
    List<models.Warehouse> warehouses = models.Warehouse.find().findList();
    return ok(warehouses.isEmpty() ? "No warehouses" : warehouses.toString());
  }

  public static Result details(String warehouseId) {
    models.Warehouse warehouse =
        models.Warehouse.find().where().eq("warehouseId", warehouseId).findUnique();
    return (warehouse == null) ? notFound("No warehouse found") : ok(warehouse.toString());
  }

  public static Result newWarehouse() {
    // Create a Warehouse form and bind the request variables to it.
    Form<models.Warehouse> warehouseForm = Form.form(models.Warehouse.class).bindFromRequest();
    Form<models.Address> addressForm = Form.form(models.Address.class).bindFromRequest();
    StringBuilder errorStringBuilder = new StringBuilder();
    
    if(addressForm.hasErrors()) {
      errorStringBuilder.append(Helpers.generateErrorString(addressForm));
    }
    
    // Validate the form values.
    if (warehouseForm.hasErrors()) {
      errorStringBuilder.append(Helpers.generateErrorString(warehouseForm));
    }

    if(errorStringBuilder.length() > 0) {
      return badRequest(errorStringBuilder.toString());
    }
    
    // form is OK, so make a Warehouse and save it.
    models.Warehouse warehouse = warehouseForm.get();
    models.Address address = addressForm.get();
    address.setWarehouse(warehouse);
    warehouse.save();
    address.save();
    return ok(warehouse.toString());
  }

  public static Result delete(String warehouseId) {
    models.Warehouse warehouse =
        models.Warehouse.find().where().eq("warehouseId", warehouseId).findUnique();
    if (warehouse != null) {
      System.out.println(warehouse);
      warehouse.delete();
    }
    return ok();
  }
}
