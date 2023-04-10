package Ian.svc.email.notify.dto.mail;

import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "信件Rq", required = true)
public class MailRq {

  @NotEmpty(message = "TxnDateTime 不能為空")
  @Schema(title = "交易日期", required = true)
  @JsonProperty("TxnDateTime")
  private String txnDateTime;

  @Schema(title = "地點", example = "TW, HK",required = true)
  @JsonProperty("Location")
  private String location;

  @Schema(title = "寄件者", example = "預設值: “XXX@XXX.com.tw”", required = true)
  @JsonProperty("SenderAddress")
  private String senderAddress;

  @Schema(title = "寄件者顯示名稱", example = "預設值: “...", required = true)
  @JsonProperty("SenderName")
  private String senderName = "...";

  @NotEmpty(message = "ReceiverAddress 不能為空")
  @Schema(title = "收件者", example = "需為本行domain", required = true)
  @JsonProperty("ReceiverAddress")
  private String receiverAddress;

  @Schema(title = "副本email", required = true)
  @JsonProperty("CarbonCopyAddress")
  private String carbonCopyAddress;
  
  @Schema(title = "信件本文", required = true)
  @JsonProperty("ContentValue")
  private ContentValue contentValue;
  
  @JsonProperty("ThemeID")
  private String themeId;
}
