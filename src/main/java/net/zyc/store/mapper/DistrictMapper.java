package net.zyc.store.mapper;

import net.zyc.store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据父区域获取子区域列表
     * @param parent 副区域带好
     * @return 区域列表
     */
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
