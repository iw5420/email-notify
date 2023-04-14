package Ian.svc.email.notify.service;

import java.util.List;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import Ian.svc.email.notify.component.WebRequestContext;
import Ian.svc.email.notify.dto.base.MwHeader;
import Ian.svc.email.notify.dto.mail.MailRq;
import Ian.svc.email.notify.repository.SystemParaSettingCategoryRepository;
import Ian.svc.email.notify.utils.MailUtils;
import lombok.RequiredArgsConstructor;
import Ian.svc.email.notify.enums.SendEmailReturnEnum;
import Ian.svc.email.notify.exception.NotifyException;
import Ian.svc.email.notify.handler.AbstractTemplateHandler;
import Ian.svc.email.notify.handler.BasicTemplate;

@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService{
 private Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private SystemParaSettingCategoryRepository SystemParaSettingCategoryRepository;
  
  @Autowired 
  private final WebRequestContext context;
  
  @Value("${mail.sender}")
  private String defaltSender;
  
  @Value("${mail.senderName}")
  private String defaltSenderName;
  
  @Value("${mail.url}")
  private String url;
  
  @Value("${mail.port}")
  private String port;
  
  @Value("${mail.category}")
  private String category;
  
  @Value("${mail.checkEmailStr}")
  private String checkEmailStr;
  
  @Value("${mail.template.MIPP00001}")
  private String MIPP00001;
  
  @Value("${mail.checkTemplate}")
  private String checkTemplate;
  
  @Override
  public MwHeader sendMail(MailRq tranrq) {
    logger.info("[email-notify][begin] 執行sendMail service : {}",tranrq.toString());
    //檢核收件人domain為本行
    MwHeader mwHeader = context.getMwHeader();
    List<String>emailCheckList = SystemParaSettingCategoryRepository.findCategoryValueByEmail(category);
    boolean checkCarbon = true;   
    String carbonCopy = tranrq.getCarbonCopyAddress();
    if(carbonCopy!=null) {
      if(!carbonCopy.isEmpty()) //附件只有在非空的時候才檢核
        checkCarbon = checkMails(tranrq.getCarbonCopyAddress(), emailCheckList);      
    }   
    if(!checkMails(tranrq.getReceiverAddress(), emailCheckList) || !checkCarbon) {
        mwHeader = mwHeader.error(mwHeader, SendEmailReturnEnum.INVALIDARGUMENTS.getReturnCode(), checkEmailStr);
        logger.error("[ERROR][SendMailService] 郵件欄位檢核錯誤 : {}",tranrq.getReceiverAddress());
        return mwHeader;
    }
    
    //若是沒值 採用預設值
    String sender = tranrq.getSenderAddress()!=null&&tranrq.getSenderAddress()!=""?tranrq.getSenderAddress():defaltSender;
    String senderName = tranrq.getSenderName()!=null&&tranrq.getSenderName()!=""?tranrq.getSenderName():defaltSenderName;
    String[] receiver = tranrq.getReceiverAddress().split(",");
    StringBuffer templatePath = new StringBuffer();

    AbstractTemplateHandler abstractTemplateHandler = new BasicTemplate();
    MailUtils mailUtils= new MailUtils(abstractTemplateHandler);
    
    //判斷模板是否存在
    if(StringUtils.equals(tranrq.getThemeId(), MIPP00001)) {
      templatePath.append("/template/").append(MIPP00001).append(".vm");
    }else {
        logger.error("[ERROR][argument]:{}", checkTemplate);
        mwHeader = mwHeader.error(mwHeader, SendEmailReturnEnum.INVALIDARGUMENTS.getReturnCode(), checkTemplate);
      return mwHeader;
    }
    
    //寄送信件主功能
    try {
      mailUtils.send(sender,senderName,receiver,url, port, tranrq, templatePath.toString());
    } catch (NotifyException e) {
        logger.error("[ERROR][sendMail]:{}", e.getErrorMessage());
        mwHeader = mwHeader.error(mwHeader, e.getReturnEnum().getReturnCode(), e.getErrorMessage());
      return mwHeader;
    }
    mwHeader = mwHeader.setCode(mwHeader, SendEmailReturnEnum.SUCCESS);
    logger.info("[email-notify][end] sendMail service 結束");
    return mwHeader;   
  }
  
  //檢核字尾是否同資料庫中domain
  public boolean checkMails(String mails, List<String>emailCheckList){  
    String[] mailsArr;
    String delimeter = ",";  // 指定分割字符,
    mailsArr = mails.split(delimeter); // 分割字符串
    for(String mail :  mailsArr){
      boolean verify = false;
     for(String emailCheck:emailCheckList) {
       if(mail.endsWith(emailCheck)) {
         verify = true;
         break;
       }
     }
     if(verify==false){
       return false;
     }
    }  
    return true;
  }
}
