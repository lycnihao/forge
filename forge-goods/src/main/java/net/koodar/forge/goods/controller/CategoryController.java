package net.koodar.forge.goods.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.module.operatelog.OperateLog;
import net.koodar.forge.goods.domain.dto.CategoryParamDTO;
import net.koodar.forge.goods.domain.dto.CategoryQueryDTO;
import net.koodar.forge.goods.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author liyc
 */
@OperateLog
@Tag(name = "CategoryController", description = "商品管理-商品分类")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "获取Tree结构的分类列表")
    @GetMapping("/goods/category/tree")
    public Response deptTree() {
        return categoryService.listTree();
    }

    @Operation(summary = "获取分类列表")
    @GetMapping("/goods/category/list")
    public Response pageList(@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable, CategoryQueryDTO categoryQueryDTO){
        return categoryService.pageCategory(categoryQueryDTO, pageable);
    }

    @Operation(summary = "根据id获取分类")
    @GetMapping("/goods/category/{id}")
    public Response pageCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "添加分类")
    @PostMapping("/goods/category/add")
    public Response addCategory(@RequestBody CategoryParamDTO categoryParamDTO) {
        return categoryService.addCategory(categoryParamDTO);
    }

    @Operation(summary = "修改分类")
    @PostMapping("/goods/category/update")
    public Response updateCategory(@RequestBody CategoryParamDTO categoryParamDTO) {
        return categoryService.updateCategory(categoryParamDTO);
    }

    @Operation(summary = "删除分类")
    @PostMapping("/goods/category/delete/{id}")
    public Response deleteCategory(@PathVariable("id") Long id) {
        return categoryService.deleteCategory(id);
    }

    @Operation(summary = "禁用分类")
    @PostMapping("/goods/category/disable/{id}")
    public Response disableCategory(@PathVariable("id") Long id) {
        return categoryService.disableCategory(id);
    }
}
