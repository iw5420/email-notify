package Ian.svc.email.notify.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "SYSTEM_PARA_SETTING_CATEGORY")
@Entity
@Schema(description = "系統參數設定資料表")
public class SystemParaSettingCategory {
  
  @Id
  @Column(name = "ID")
  @Schema(title = "Id流水號", required = true)
  private Long id;
  
  @Column(name = "CATEGORY_NAME")
  @Schema(title = "參數設定類別", required = true)
  private String categoryName;

  @Column(name = "CATEGORY_VALUE")
  @Schema(title = "參數設定內容", required = true)
  private String categoryValue;
  
  @Column(name = "STATUS")
  @Schema(title = "自訂項目狀態", required = true)
  private String status;
  
  @Column(name = "CREATE_DATE")
  @Schema(title = "主檔建立日期時間", required = true)
  private Date createDate;
  
  @Column(name = "CREATE_USER")
  @Schema(title = "主檔建立者", required = true)
  private String createUser;
  
  @Column(name = "UPDATE_DATE")
  @Schema(title = "主檔更新日期時間", required = true)
  private Date updateDate;
  
  @Column(name = "UPDATE_USER")
  @Schema(title = "主檔更新者", required = true)
  private String updateUser;
  
}
