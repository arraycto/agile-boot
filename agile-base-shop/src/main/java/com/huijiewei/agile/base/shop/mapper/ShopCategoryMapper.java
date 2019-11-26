package com.huijiewei.agile.base.shop.mapper;

import com.huijiewei.agile.base.shop.entity.ShopCategory;
import com.huijiewei.agile.base.shop.response.ShopCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ShopCategoryMapper {
    ShopCategoryMapper INSTANCE = Mappers.getMapper(ShopCategoryMapper.class);

    ShopCategoryResponse toShopCategoryResponse(ShopCategory category);

    List<ShopCategoryResponse> toShopCategoryResponses(List<ShopCategory> categories);
}
