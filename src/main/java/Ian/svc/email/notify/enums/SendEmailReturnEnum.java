package Ian.svc.email.notify.enums;

import lombok.Getter;

@Getter
public enum SendEmailReturnEnum {
  SUCCESS(200, "0000","交易成功"),
  FAIL(200, "9999", "發送失敗"),
  INVALIDARGUMENTS(200, "E101","檢核錯誤"),
  UNKNOWNERROR(200, "E999", "不明或未定義的錯誤");
  
  private int statusCode; // http status code
  private String returnCode; // mwheader return code
  private String desc; // mwheader return desc
  
  private SendEmailReturnEnum() {}

  private SendEmailReturnEnum(int statusCode, String returnCode, String desc) {
    this.statusCode = statusCode;
    this.returnCode = returnCode;
    this.desc = desc;
  }

}
