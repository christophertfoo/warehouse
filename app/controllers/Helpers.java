package controllers;

import java.util.List;
import java.util.Map;
import play.data.Form;
import play.data.validation.ValidationError;

public class Helpers {
  private Helpers(){
    // Empty Private constructor to prevent instantiation.
  }
  
  public static String generateErrorString (Form<?> form) {
    Map<String, List<ValidationError>> errors = form.errors();
    StringBuilder errorString = new StringBuilder();
    for (String errorKey : errors.keySet()) {
      List<ValidationError> errorList = errors.get(errorKey);
      for (ValidationError error : errorList) {
        errorString.append(errorKey + ": " + error.toString() + "\n");
      }
    }
    return errorString.toString();
  }
}
