package controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import play.data.Form;
import play.data.format.Formatters;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;

public class StockItem extends Controller {
  
  public static Result index() {
    List<models.StockItem> stockItems = models.StockItem.find().findList();
    return ok(stockItems.isEmpty() ? "No stock items" : stockItems.toString());
  }

  public static Result details(String stockItemId) {
    models.StockItem stockitem = models.StockItem.find().where().eq("stockItemId", stockItemId).findUnique();
    return (stockitem == null) ? notFound("No stock item found") : ok(stockitem.toString());
  }
  
  public static Result newStockItem() {
    
    Formatters.register(models.Warehouse.class, new Formatters.SimpleFormatter<models.Warehouse>(){

      @Override
      public models.Warehouse parse(String text, Locale locale) throws ParseException {
        return models.Warehouse.find().where().eq("warehouseId", text).findUnique();
      }

      @Override
      public String print(models.Warehouse t, Locale locale) {
        return t.toString();
      }
    });
    
    Formatters.register(models.Product.class, new Formatters.SimpleFormatter<models.Product>() {

      @Override
      public models.Product parse(String text, Locale locale) throws ParseException {
        return models.Product.find().where().eq("productId", text).findUnique();
      }

      @Override
      public String print(models.Product t, Locale locale) {
        return t.toString();
      }
    });
    
    // Create a StockItem form and bind the request variables to it.
    Form<models.StockItem> stockitemForm = Form.form(models.StockItem.class).bindFromRequest();
    
    // Validate the form values.
    if(stockitemForm.hasErrors()) {
      Map<String, List<ValidationError>> errors = stockitemForm.errors();
      StringBuilder errorString = new StringBuilder();
      for(String errorKey : errors.keySet()) {
        List<ValidationError> errorList = errors.get(errorKey);
        for(ValidationError error : errorList) {
          errorString.append(error);
        }
      }
      return badRequest(errorString.toString());
    }
    
    // form is OK, so make a stock item and save it.
    models.StockItem stockitem = stockitemForm.get();
    stockitem.save();
    
    return ok(stockitem.toString());
  }
  
  public static Result delete(String stockItemId) {
    models.StockItem stockitem = models.StockItem.find().where().eq("stockItemId", stockItemId).findUnique();
    if(stockitem != null) {
      stockitem.delete();
    }
    return ok();
  }
}
