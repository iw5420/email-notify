package Ian.svc.email.notify.dto.mail;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
@Schema(description = "信件本文")
public class ContentValue {

  @NotEmpty(message = "Subject 不能為空")
  @Schema(title = "信件主旨", required = true)
  @JsonProperty("Subject")
  private String subject;

  @Schema(title = "稱謂", required = true)
  @JsonProperty("Salutation")
  private String salutation;
  
  @NotEmpty(message = "Content 不能為空")
  @Schema(title = "內容", required = true)
  @JsonProperty("Content")
  private String content;

  @Schema(title = "內容訊息樣本代碼", required = true)
  @JsonProperty("ContentTemplateID")
  private String contentTemplateID;

  @ArraySchema(schema = @Schema(title = "Items", example = "列表"))
  @JsonProperty("Items")
  private List<ContentValueItem> items;

}
