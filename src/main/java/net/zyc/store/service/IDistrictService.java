package net.zyc.store.service;
import net.zyc.store.entity.District;
import java.util.List;

public interface IDistrictService {
    /**
     * 根据父区域的代号获取结果集
     * @param parent
     * @return
     */
    List<District> getByParent(String parent);

    String getNameByCode(String code);

}
