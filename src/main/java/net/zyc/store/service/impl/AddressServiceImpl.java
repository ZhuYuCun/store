package net.zyc.store.service.impl;

import net.zyc.store.entity.Address;
import net.zyc.store.mapper.AddressMapper;
import net.zyc.store.mapper.DistrictMapper;
import net.zyc.store.service.IAddressService;
import net.zyc.store.service.IDistrictService;
import net.zyc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 新增收货地址的实现类
 */
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;
    // 添加用户收获地址依赖 IDistrictService
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countById(uid);

        if(count >= maxCount) {
            throw new AddressCountLimitException("收货地址数量上线！");
        }

        String ProvinceName = districtService.getNameByCode(address.getProvinceCode());
        String CityName = districtService.getNameByCode(address.getCityCode());
        String AreaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(ProvinceName);
        address.setCityName(CityName);
        address.setAreaName(AreaName);

        address.setUid(uid);
        address.setIsDefault(count == 0 ? 1 : 0); // 设置默认地址 1 默认 0 不默认

        address.setCreatedTime(new Date());
        address.setCreatedUser(username);
        address.setModifiedTime(new Date());
        address.setModifiedUser(username);

        Integer row = addressMapper.insert(address);
        if(row != 1) {
            throw new InsertException("插入时候产生异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);

        for(Address address: list) {
            address.setUid(null);
            address.setZip(null);
        }

        return list;
    }

    /**
     * 设置默认地址
     * @param aid
     * @param uid
     * @param username
     */
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if(address == null) {
            throw new AddressNotFoundException("地址不存在");
        }
        if(!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        Integer rows = addressMapper.updateNoneDefault(uid);

        if(rows < 1) {
           throw new UpdateException("更新时产生异常");
        }

        Integer row = addressMapper.updateDefault(aid, username, new Date());

        if(row != 1) {
            throw new UpdateException("更新时产生异常!");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);

        if(result == null) {
            throw new AddressNotFoundException();
        }

        if(!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        Integer row = addressMapper.deleteByAid(aid);

        if(row != 1) {
            throw new DeleteException("删除时产生未知异常!");
        }

        if(result.getIsDefault() == 0) {
            return;
        }

        Integer count = addressMapper.countById(uid);
        if(count == 0) {
            return;
        }

        Address address = addressMapper.findLastModified(uid);
        Integer updateNum = addressMapper.updateDefault(address.getAid(), username, new Date());
        if(updateNum != 1) {
            throw new UpdateException("更新时产生异常!");
        }
    }
}
