package net.zyc.store.service.impl;

import net.zyc.store.entity.Product;
import net.zyc.store.mapper.ProductMapper;
import net.zyc.store.service.IProductService;
import net.zyc.store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> findHotProducts() {
        List<Product> list = productMapper.findHotProducts();

        for (Product product: list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setModifiedUser(null);
            product.setCreatedTime(null);
            product.setModifiedTime(null);
        }

        return list;
    }

    @Override
    public Product findProductById(Integer id) {
        Product product = productMapper.findById(id);
        if(product == null) {
            throw new ProductNotFoundException("商品未找到");
        }
        return product;
    }
}
