package Ian.svc.email.notify.handler;

import java.io.StringWriter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import Ian.svc.email.notify.dto.mail.ContentValue;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BasicTemplate extends AbstractTemplateHandler{

  @Override
  public String generateTemplate(Object contentValue, String templatePath) {
    VelocityContext context = new VelocityContext();
    VelocityEngine ve = generateVelocityEngine();
    context.put("contentValue", contentValue);
    Template t = ve.getTemplate(templatePath);
    StringWriter writer = new StringWriter();
    t.merge( context, writer );
    return writer.toString();
  }
  
}
