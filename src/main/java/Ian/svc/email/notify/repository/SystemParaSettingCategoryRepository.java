package Ian.svc.email.notify.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import Ian.svc.email.notify.entity.SystemParaSettingCategory;

public interface SystemParaSettingCategoryRepository extends JpaRepository<SystemParaSettingCategory, Long>{
  @Query("SELECT categoryValue from SystemParaSettingCategory WHERE categoryName=:email and status = 'Y' ")
  List<String>findCategoryValueByEmail(String email);

}
