package net.koodar.forge.goods.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.module.operatelog.OperateLog;
import net.koodar.forge.goods.adapter.dto.CategoryParamDTO;
import net.koodar.forge.goods.adapter.dto.CategoryQueryDTO;
import net.koodar.forge.goods.application.CategoryAppService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author liyc
 */
@OperateLog
@Tag(name = "CategoryController", description = "订单管理-商品分类")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryAppService categoryAppService;

    @Operation(summary = "获取Tree结构的分类列表")
    @GetMapping("/goods/category/tree")
    public Response deptTree() {
        return categoryAppService.listTree();
    }

    @Operation(summary = "获取分类列表")
    @GetMapping("/goods/category/list")
    public Response pageList(@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable, CategoryQueryDTO categoryQueryDTO){
        return categoryAppService.pageCategory(categoryQueryDTO, pageable);
    }

    @Operation(summary = "根据id获取分类")
    @GetMapping("/goods/category/{id}")
    public Response pageCategoryById(@PathVariable("id") Long id) {
        return categoryAppService.getCategoryById(id);
    }

    @Operation(summary = "添加分类")
    @PostMapping("/goods/category/add")
    public Response addCategory(@RequestBody CategoryParamDTO categoryParamDTO) {
        return categoryAppService.addCategory(categoryParamDTO);
    }

    @Operation(summary = "修改分类")
    @PostMapping("/goods/category/update")
    public Response updateCategory(@RequestBody CategoryParamDTO categoryParamDTO) {
        return categoryAppService.updateCategory(categoryParamDTO);
    }

    @Operation(summary = "删除分类")
    @PostMapping("/goods/category/delete/{id}")
    public Response deleteCategory(@PathVariable("id") Long id) {
        return categoryAppService.deleteCategory(id);
    }

    @Operation(summary = "禁用分类")
    @PostMapping("/goods/category/disable/{id}")
    public Response disableCategory(@PathVariable("id") Long id) {
        return categoryAppService.disableCategory(id);
    }
}
