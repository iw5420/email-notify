package Ian.svc.email.notify.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import Ian.svc.email.notify.dto.base.MwHeader;
import Ian.svc.email.notify.dto.base.RequestTemplate;
import Ian.svc.email.notify.dto.mail.MailRq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import Ian.svc.email.notify.service.SendMailService;

@Tag(name = "Mail Controller")
@RestController
public class MailController {
  @Autowired
  private SendMailService SendMailService;
  
  @Operation(summary = "寄信功能")
  @PostMapping("/sendMail")
  public MwHeader sendMail(@Valid @RequestBody RequestTemplate<MailRq> request){
    MwHeader mwHeader =  SendMailService.sendMail(request.getTranrq());
    return mwHeader;
  }
  
}
