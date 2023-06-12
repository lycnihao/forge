package net.koodar.forge.goods.application.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.MultiResponse;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import net.koodar.forge.goods.adapter.dto.CategoryParamDTO;
import net.koodar.forge.goods.adapter.dto.CategoryQueryDTO;
import net.koodar.forge.goods.application.CategoryAppService;
import net.koodar.forge.goods.domain.entity.Category;
import net.koodar.forge.goods.domain.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类接口实现类
 * <p>
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class CategoryAppServiceImpl implements CategoryAppService {

    private final CategoryRepository categoryRepository;

    @Override
    public Response listTree() {
        List<Category> categoryList = categoryRepository.findAll();

        // 获取根节点
        List<Category> rootCategoryList = categoryList
                .stream()
                .filter(category -> category.getParentId() == null)
                .toList();

        // 转换为Tree结构
        List<Category> toTree = toTree(rootCategoryList, categoryList);

        return MultiResponse.ok(toTree);
    }

    @Override
    public Response getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("分类不存在"));

        List<Category> children = categoryRepository.findByParentId(id);
        category.setChildren(children);
        return SingleResponse.ok(category);
    }

    @Override
    public Response pageCategory(CategoryQueryDTO categoryQueryDTO, Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (categoryQueryDTO.getParentId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("parentId"), categoryQueryDTO.getParentId()));
            }
            if (StringUtils.hasLength(categoryQueryDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(root.get("name"), categoryQueryDTO.getName() + "%"));
            }
            query.where(predicates.toArray(new Predicate[0]));
            return query.getRestriction();
        }, pageable);
        return SingleResponse.ok(categoryPage);
    }

    @Override
    public Response addCategory(CategoryParamDTO categoryParamDTO) {
        boolean exists = categoryRepository.existsByParentIdAndCode(categoryParamDTO.getParentId(), categoryParamDTO.getCode());
        if (exists) {
            return SingleResponse.error(UserErrorCode.ALREADY_EXIST, "分类编码已存在");
        }
        Category category = categoryParamDTO.toEntity();
        categoryRepository.save(category);
        return Response.ok();
    }

    @Override
    public Response updateCategory(CategoryParamDTO categoryParamDTO) {
        Category dbCategory = categoryRepository.findByParentIdAndCode(categoryParamDTO.getParentId(), categoryParamDTO.getCode());
        if (dbCategory != null && !dbCategory.getId().equals(categoryParamDTO.getId())) {
            return SingleResponse.error(UserErrorCode.ALREADY_EXIST, "分类编码已存在");
        }
        Category category = categoryParamDTO.toEntity();
        categoryRepository.save(category);
        return Response.ok();
    }

    @Override
    public Response deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return Response.ok();
    }

    @Override
    public Response disableCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("分类不存在"));
        category.setStatusFlag(false);
        categoryRepository.save(category);
        return Response.ok();
    }

    private List<Category> toTree(List<Category> rootCategoryList, List<Category> categoryList) {

        for (Category category : rootCategoryList) {
            List<Category> children = categoryList.stream().filter(c -> c.getParentId() != null && c.getParentId().equals(category.getId())).toList();
            category.setChildren(children);
            toTree(children, categoryList);
        }

        return rootCategoryList;
    }
}
