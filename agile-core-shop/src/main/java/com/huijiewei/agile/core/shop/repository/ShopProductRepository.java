package com.huijiewei.agile.core.shop.repository;

import com.huijiewei.agile.core.shop.entity.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {
    public Boolean existsByShopCategoryIdIn(List<Integer> shopCategoryId);
}
