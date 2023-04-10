package Ian.svc.email.notify.dto.base;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import Ian.svc.email.notify.enums.SendEmailReturnEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access =AccessLevel.PUBLIC)
@Schema(description = "標頭")
public class MwHeader {
  
  @NotBlank(message = "XXXID 不能為空")
  @Size(message = "XXXID 長度不得超過20", max = 20)
  @Schema(title = "代碼", example = "IAN-B-ABCDEFGH", required = true)
  @JsonProperty("MSGID")
  protected String msgId;

  @NotBlank(message = "SOURCECHANNEL 不能為空")
  @Schema(title = "來源端系統AP ID", example = "ABC-D-OOOOOO", required = true)
  @JsonProperty("SOURCECHANNEL")
  protected String sourceChannel;

  @NotBlank(message = "TXNSEQ 不能為空")
  @Schema(title = "交易序號", example = "1678346846710", required = true)
  @JsonProperty("TXNSEQ")
  protected String txnSeq;
  
  @Schema(title = "處理結果代碼", required = true)
  @JsonProperty("RETURNCODE")
  protected String returnCode;

  @Schema(title = "處理結果訊息", required = true)
  @JsonProperty("RETURNDESC")
  protected String returnDesc;
  
  public static MwHeader error(MwHeader rqMwHeader, String returnCode, String message) {
    MwHeader rsMwHeader = new MwHeader(rqMwHeader);
    rsMwHeader.setReturnCode(returnCode);
    rsMwHeader.setReturnDesc(message);
    return rsMwHeader;
  }
  
  public static MwHeader setCode(MwHeader rqMwHeader, SendEmailReturnEnum returnCode) {
    MwHeader rsMwHeader = new MwHeader(rqMwHeader);
    rsMwHeader.setReturnCode(returnCode.getReturnCode());
    rsMwHeader.setReturnDesc(returnCode.getDesc());
    return rsMwHeader;
  }
  
  public MwHeader(MwHeader header) {
    this.msgId = header.getMsgId();
    this.sourceChannel = header.getSourceChannel();
    this.txnSeq = header.getTxnSeq();
    }

}
