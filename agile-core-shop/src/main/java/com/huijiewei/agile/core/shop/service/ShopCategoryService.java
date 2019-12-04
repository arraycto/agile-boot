package com.huijiewei.agile.core.shop.service;

import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.service.TreeService;
import com.huijiewei.agile.core.shop.entity.ShopCategory;
import com.huijiewei.agile.core.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.core.shop.repository.ShopCategoryRepository;
import com.huijiewei.agile.core.shop.request.ShopCategoryRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ShopCategoryService extends TreeService<ShopCategory> {
    private static final String SHOP_CATEGORIES_CACHE_KEY = "shop-categories";
    private static final String SHOP_CATEGORY_TREE_CACHE_KEY = "shop-category-tree";

    private final ShopCategoryRepository shopCategoryRepository;

    @Autowired
    public ShopCategoryService(ShopCategoryRepository shopCategoryRepository) {
        this.shopCategoryRepository = shopCategoryRepository;
    }

    @Cacheable(value = SHOP_CATEGORIES_CACHE_KEY)
    public List<ShopCategory> findAll() {
        return this.shopCategoryRepository.findAll();
    }

    @Cacheable(value = SHOP_CATEGORY_TREE_CACHE_KEY)
    public List<ShopCategory> findTree() {
        return this.buildTree(this.findAll());
    }

    private List<ShopCategoryResponse> getParentsById(Integer id) {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.buildParents(id, this.findAll()));
    }

    public List<ShopCategoryResponse> getTree() {
        return ShopCategoryMapper.INSTANCE.toShopCategoryResponses(this.findTree());
    }

    public List<ShopCategoryResponse> getRoute(Integer id) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("分类不存在");
        }

        return this.getParentsById(id);
    }

    public ShopCategoryResponse getById(Integer id, Boolean withParents) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("分类不存在");
        }

        ShopCategory shopCategory = shopCategoryOptional.get();

        ShopCategoryResponse response = ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);

        if (withParents) {
            response.setParents(this.getParentsById(shopCategory.getParentId()));
        }

        return response;
    }

    @CacheEvict(value = {SHOP_CATEGORIES_CACHE_KEY, SHOP_CATEGORY_TREE_CACHE_KEY}, allEntries = true)
    public ShopCategoryResponse create(@Valid ShopCategoryRequest request) {
        ShopCategory shopCategory = ShopCategoryMapper.INSTANCE.toShopCategory(request);

        if (shopCategory.getParentId() > 0 && !this.shopCategoryRepository.existsById(shopCategory.getParentId())) {
            throw new NotFoundException("你选择的上级分类不存在");
        }

        this.shopCategoryRepository.save(shopCategory);

        return ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);
    }

    @CacheEvict(value = {SHOP_CATEGORIES_CACHE_KEY, SHOP_CATEGORY_TREE_CACHE_KEY}, allEntries = true)
    public ShopCategoryResponse edit(Integer id, @Valid ShopCategoryRequest request) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("商品分类不存在");
        }

        ShopCategory current = shopCategoryOptional.get();

        ShopCategory shopCategory = ShopCategoryMapper.INSTANCE.toShopCategory(request, current);

        if (shopCategory.getParentId() > 0 && !this.shopCategoryRepository.existsById(shopCategory.getParentId())) {
            throw new NotFoundException("你选择的上级分类不存在");
        }

        this.shopCategoryRepository.save(shopCategory);

        return ShopCategoryMapper.INSTANCE.toShopCategoryResponse(shopCategory);
    }

    @CacheEvict(value = {SHOP_CATEGORIES_CACHE_KEY, SHOP_CATEGORY_TREE_CACHE_KEY}, allEntries = true)
    public void delete(Integer id) {
        Optional<ShopCategory> shopCategoryOptional = this.shopCategoryRepository.findById(id);

        if (shopCategoryOptional.isEmpty()) {
            throw new NotFoundException("商品分类不存在");
        }

        ShopCategory shopCategory = shopCategoryOptional.get();

        this.shopCategoryRepository.delete(shopCategory);
    }
}
