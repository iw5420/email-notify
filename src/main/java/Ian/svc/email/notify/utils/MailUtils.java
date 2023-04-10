package Ian.svc.email.notify.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Ian.svc.email.notify.dto.mail.MailRq;
import Ian.svc.email.notify.enums.SendEmailReturnEnum;
import Ian.svc.email.notify.exception.NotifyException;
import Ian.svc.email.notify.handler.AbstractTemplateHandler;


public class MailUtils {
  
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  private AbstractTemplateHandler abstractTemplateHandler;
  
  private static List<String> imageFileNames = new ArrayList<>();

  public void setAbstractTemplateHandler(AbstractTemplateHandler abstractTemplateHandler) {
    this.abstractTemplateHandler = abstractTemplateHandler;
  }

  static {
    imageFileNames.add("mobile_edmlogo.jpg");
    imageFileNames.add("edm_footer.jpg");
    imageFileNames.add("s_banner.png");
  }
  
  public MailUtils(AbstractTemplateHandler abstractTemplateHandler) {
    this.abstractTemplateHandler = abstractTemplateHandler;
  }
  /**
   * 發送信件主流程
   *
   * @throws NotifyException
   *
   */
  public void send(String sender, String senderName, String[]receiver, String url, String port, MailRq tranrq, String templatePath) throws NotifyException {

    StringBuffer sb = new StringBuffer();

    sb.append("mail server = ").append(url);

    try {
        
        Properties prop = new Properties();
        // 指定 SMTP server
        prop.put("mail.smtp.host", url);    
        prop.put("mail.smtp.auth", "false");
        prop.put("mail.smtp.starttls.enable", "false");
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.connectiontimeout", 500);
        prop.put("mail.smtp.timeout", 2000);

        // 根據 Properties 建立一個 Instance
        Session session = Session.getInstance(prop);       
        // 從 Session 建立一個 Message
        MimeMessage message = new MimeMessage(session);
        // 設定傳送郵件的發信人
        message.setFrom(new InternetAddress(sender, senderName, "UTF-8"));
        // 設定傳送郵件的收件者
        InternetAddress[] address = getEmails(receiver);
        message.setRecipients(Message.RecipientType.TO, address);
        // 設定主題
        message.setSubject(tranrq.getContentValue().getSubject(), "UTF-8");
        // 文字部份，注意 img src 部份要用 cid:接下面附檔的header36.
        MimeBodyPart textPart = new MimeBodyPart();
        String content = abstractTemplateHandler.generateTemplate(tranrq.getContentValue(), templatePath);
 
        textPart.setContent(content, "text/html; charset=UTF-8");
        Multipart email = new MimeMultipart();
        email.addBodyPart(textPart);
        // 掛載圖檔
        setPicturePart(email);
        // 設定內容
        message.setContent(email);
        // 發送郵件
        logger.info("[MailUtils][send] send email");
        Transport.send(message);        
        logger.info("[MailUtils][success] send email success!!");

    }
    catch (MessagingException| UnsupportedEncodingException e) {   
      throw new NotifyException("cannot send notification, " + sb.toString(), SendEmailReturnEnum.FAIL, e);
    }
}

  /**
   * 取得email address list
   *
   * @throws AddressException
   *
   */
  private InternetAddress[] getEmails(String... emails) throws AddressException {
    InternetAddress[] addressList = new InternetAddress[emails.length];
    for (int i = 0; i < emails.length; i++) {
        addressList[i] = new InternetAddress(emails[i]);
    }
    return addressList;
  }

  /**
   * 設定圖形
   *
   * @return
   * @throws NotifyException
   */
  private Multipart setPicturePart(Multipart email) throws NotifyException{

      for (String imageFileName : imageFileNames) {
          try {
              MimeBodyPart picturePart = getPicturePart(imageFileName);
              email.addBodyPart(picturePart);
          }
          catch (MessagingException e) {
            throw new NotifyException("cannot get image resource: " + imageFileName, SendEmailReturnEnum.FAIL, e);
          }
      }
      return email;
  }

  /**
   * 取得圖形
   *
   * @return
   * @throws NotifyException
   */
  private MimeBodyPart getPicturePart(String imageFileName) throws NotifyException {
      MimeBodyPart picturePart = new MimeBodyPart();
      try {
          FileDataSource fds = new FileDataSource("template/img/" + imageFileName);

          picturePart.setDataHandler(new DataHandler(fds));
          picturePart.setFileName(fds.getName());
          picturePart.setHeader("Content-ID", "<" + imageFileName + ">");
      }
      catch (MessagingException e) {
        throw new NotifyException("cannot get image resource: " + imageFileName, SendEmailReturnEnum.FAIL, e);
      }
      return picturePart;
  }
}
