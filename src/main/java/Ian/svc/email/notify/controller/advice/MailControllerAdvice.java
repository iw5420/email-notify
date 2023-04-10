package Ian.svc.email.notify.controller.advice;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import Ian.svc.email.notify.component.WebRequestContext;
import Ian.svc.email.notify.dto.base.MwHeader;
import Ian.svc.email.notify.dto.base.ResponseTemplate;
import Ian.svc.email.notify.enums.SendEmailReturnEnum;
import Ian.svc.email.notify.exception.NotifyException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class MailControllerAdvice {
  
  private final WebRequestContext context;
  
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseTemplate<MwHeader>> handleValidationErrors(MethodArgumentNotValidException exception) {

      BindingResult bindingResult = exception.getBindingResult();
      List<FieldError> errorList = bindingResult.getFieldErrors();
      String errorMessage = errorList.size()>0?errorList.get(0).getDefaultMessage():"資料格式錯誤";
      logger.error("[ERROR][argument]:{}", errorMessage);
      MwHeader mwHeaderRq = context.getMwHeader();
      MwHeader rsMwHeader = MwHeader.error(mwHeaderRq, SendEmailReturnEnum.INVALIDARGUMENTS.getReturnCode(), errorMessage);
      ResponseTemplate<MwHeader> baseResponse = new ResponseTemplate<MwHeader>();
      baseResponse.setMwHeader(rsMwHeader);
      return new ResponseEntity<ResponseTemplate<MwHeader>>(baseResponse, HttpStatus.OK);
  }
  
  @ExceptionHandler(NotifyException.class)
  public ResponseEntity<ResponseTemplate<MwHeader>> handleNotifyException(NotifyException exception)  {
      logger.error("[ERROR][notify exception]:{}", exception.getErrorMessage());
      MwHeader rsMwHeader = MwHeader.setCode(context.getMwHeader(), exception.getReturnEnum());
      rsMwHeader.setReturnDesc(exception.getErrorMessage());
      ResponseTemplate<MwHeader> baseResponse = new ResponseTemplate<MwHeader>();
      baseResponse.setMwHeader(rsMwHeader);
      return new ResponseEntity<ResponseTemplate<MwHeader>>(baseResponse, HttpStatus.OK);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseTemplate<MwHeader>> handleException(Exception exception)  {
      logger.error("[ERROR][general]:{}", exception.getMessage());
      MwHeader rsMwHeader = MwHeader.setCode(context.getMwHeader(), SendEmailReturnEnum.UNKNOWNERROR);
      ResponseTemplate<MwHeader> baseResponse = new ResponseTemplate<MwHeader>();
      baseResponse.setMwHeader(rsMwHeader);
      return new ResponseEntity<ResponseTemplate<MwHeader>>(baseResponse, HttpStatus.OK);
  }
}
