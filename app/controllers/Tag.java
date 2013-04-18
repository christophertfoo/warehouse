package controllers;

import java.util.List;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Tag extends Controller {
  public static Result index() {
    List<models.Tag> Tags = models.Tag.find().findList();
    return ok(Tags.isEmpty() ? "No Tags" : Tags.toString());
  }
  
  public static Result details(String tagId) {
    models.Tag tag = models.Tag.find().where().eq("tagId", tagId).findUnique();
    return (tag == null) ? notFound("No Tag found") : ok(tag.toString());
  }
  
  public static Result newTag() {
    Form<models.Tag> tagForm = Form.form(models.Tag.class).bindFromRequest();
    if(tagForm.hasErrors()) {
      return badRequest("Tag name cannot be 'Tag'.");
    }
    models.Tag tag = tagForm.get();
    tag.save();
    return ok(tag.toString());
  }
  
  public static Result delete(String tagId) {
    models.Tag tag = models.Tag.find().where().eq("tagId", tagId).findUnique();
    if(tag != null) {
      tag.delete();
    }
    return ok();
  }
}
