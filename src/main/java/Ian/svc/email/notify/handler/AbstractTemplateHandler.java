package Ian.svc.email.notify.handler;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import Ian.svc.email.notify.dto.mail.ContentValue;

public abstract class AbstractTemplateHandler { 
  
  public abstract String generateTemplate(Object contentValue, String templatePath);
  
  public VelocityEngine generateVelocityEngine() {
    VelocityEngine ve = new VelocityEngine();
    ve.setProperty(Velocity.RESOURCE_LOADERS, "class");
    ve.setProperty("resource.loader.class.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    ve.init();  
    return ve;
  }

}
