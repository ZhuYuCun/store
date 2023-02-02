package net.zyc.store.mapper;


import net.zyc.store.entity.Product;
import java.util.List;

public interface ProductMapper {
    List<Product> findHotProducts();

    Product findById(Integer id);
}
