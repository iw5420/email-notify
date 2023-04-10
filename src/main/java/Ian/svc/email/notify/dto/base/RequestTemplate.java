package Ian.svc.email.notify.dto.base;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "REQUEST樣板")
public class RequestTemplate<Q> {

  @Valid
  @NotNull(message = "標頭不得為空")
  @JsonProperty("MWHEADER")
  @Schema(title = "標頭", required = true)
  private MwHeader mwHeader;

  @Valid
  @NotNull(message = "TRANRQ不得為空")
  @JsonProperty("TRANRQ")
  @Schema(title = "請求內容", required = true)
  private Q tranrq;
  
}
