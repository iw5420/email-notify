package Ian.svc.email.notify.component;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import Ian.svc.email.notify.dto.base.MwHeader;
import lombok.Data;

@Component
@Data
@RequestScope
public class WebRequestContext {
  
  private MwHeader mwHeader = new MwHeader();

  public MwHeader getMwHeader() {
      return Optional.ofNullable(mwHeader).orElse(new MwHeader(mwHeader));
  }

}
