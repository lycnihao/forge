package net.koodar.forge.goods.application;

import net.koodar.forge.common.dto.Response;
import net.koodar.forge.goods.adapter.dto.CategoryParamDTO;
import net.koodar.forge.goods.adapter.dto.CategoryQueryDTO;
import org.springframework.data.domain.Pageable;

/**
 * 商品分类接口
 * <p>
 * @author liyc
 */
public interface CategoryAppService {

    Response listTree();

    Response getCategoryById(Long id);

    Response pageCategory(CategoryQueryDTO categoryQueryDTO, Pageable pageable);

    Response addCategory(CategoryParamDTO categoryParamDTO);

    Response updateCategory(CategoryParamDTO categoryParamDTO);

    Response deleteCategory(Long id);

    Response disableCategory(Long id);

}
