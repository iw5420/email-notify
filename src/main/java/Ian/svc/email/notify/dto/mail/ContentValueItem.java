package Ian.svc.email.notify.dto.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通知信API(msgId -> DGT-C-MAILNOTFOSDM)的上行中，ContentValue裡的Items陣列物件。
 */
@Getter
@Setter
@ToString
@Schema(description = "列表項目")
public class ContentValueItem {

  public ContentValueItem(int ranking, String attribute, String value) {
    this.ranking = ranking;
    this.attribute = attribute;
    this.value = value;
  }

  @JsonProperty("Ranking")
  @Schema(title = "順序", example = "1, 2, 3",  required = true)
  int ranking;

  @Schema(title = "欄位名稱",  required = true)
  @JsonProperty("Attribute")
  String attribute;

  @Schema(title = "欄位名稱 對應的值",  required = true)
  @JsonProperty("Value")
  String value;

}
