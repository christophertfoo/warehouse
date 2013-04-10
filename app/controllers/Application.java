package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Default application {@link Controller}. Will be changed later!
 * 
 * @author Christopher Foo
 * 
 */
public class Application extends Controller {

  /**
   * Renders the default index page for Play.
   * 
   * @return The OK {@link Result} status.
   */
  public static Result index() {
    return ok(index.render("Your new application is ready."));
  }

}
