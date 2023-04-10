package Ian.svc.email.notify.service;

import Ian.svc.email.notify.dto.base.MwHeader;
import Ian.svc.email.notify.dto.mail.MailRq;

public interface SendMailService {

  MwHeader sendMail(MailRq tranrq);
}
