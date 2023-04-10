package Ian.svc.email.notify.exception;

import Ian.svc.email.notify.enums.SendEmailReturnEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotifyException extends Exception{
  
  private static final long serialVersionUID = 1L;
  
  private String errorMessage; 
  SendEmailReturnEnum returnEnum;
  private Exception e;
  
  public NotifyException(String errorMessage, SendEmailReturnEnum returnEnum, Exception e) {
    this.errorMessage = errorMessage;
    this.returnEnum = returnEnum;
    this.e = e;
  }
  
}
