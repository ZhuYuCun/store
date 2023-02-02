package net.zyc.store.service.impl;

import net.zyc.store.entity.District;
import net.zyc.store.mapper.DistrictMapper;
import net.zyc.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> districts = districtMapper.findByParent(parent);
        for (District d: districts) {
            d.setId(null);
            d.setParent(null);
        }
        return districts;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
