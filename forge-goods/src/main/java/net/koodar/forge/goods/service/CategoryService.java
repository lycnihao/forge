package net.koodar.forge.goods.service;

import net.koodar.forge.common.dto.Response;
import net.koodar.forge.goods.domain.dto.CategoryParamDTO;
import net.koodar.forge.goods.domain.dto.CategoryQueryDTO;
import org.springframework.data.domain.Pageable;

/**
 * 商品分类接口
 * <p>
 * @author liyc
 */
public interface CategoryService {

    Response listTree();

    Response getCategoryById(Long id);

    Response pageCategory(CategoryQueryDTO categoryQueryDTO, Pageable pageable);

    Response addCategory(CategoryParamDTO categoryParamDTO);

    Response updateCategory(CategoryParamDTO categoryParamDTO);

    Response deleteCategory(Long id);

    Response disableCategory(Long id);

}
