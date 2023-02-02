package net.zyc.store.service;

import net.zyc.store.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findHotProducts();

    Product findProductById(Integer id);

}
