package Ian.svc.email.notify.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "RESPONSE樣板")
public class ResponseTemplate<S> {
  
  @Schema(title = "標頭", required = true)
  @JsonProperty("MWHEADER")
  private MwHeader mwHeader;

}
