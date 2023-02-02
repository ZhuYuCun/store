package net.zyc.store.controller;

import net.zyc.store.entity.Product;
import net.zyc.store.service.IProductService;
import net.zyc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("products")
@RestController
public class ProductController extends BaseController{

    @Autowired
    IProductService productService;

    @RequestMapping("hot")
    public JsonResult<List<Product>> findHotProducts() {
        List<Product> list = productService.findHotProducts();

        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/{id}")
    public JsonResult<Product> findById(@PathVariable("id") Integer id) {
        Product product = productService.findProductById(id);
        return new JsonResult<>(OK, product);
    }
}
