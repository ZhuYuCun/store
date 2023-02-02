package net.zyc.store.controller;

import net.zyc.store.entity.District;
import net.zyc.store.service.IDistrictService;
import net.zyc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("district")
public class DistrictController extends BaseController {

    @Autowired
    IDistrictService districtService;

    @RequestMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent) {

        List<District> data = districtService.getByParent(parent);

        return new JsonResult<>(OK, data);
    }

    @RequestMapping("code")
    public JsonResult<Void> getNameByCode() {
        return new JsonResult<>();
    }


}
