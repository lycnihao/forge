package net.koodar.forge.goods.repository;

import net.koodar.forge.goods.domain.entity.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 商品分类属性仓储接口
 * @author liyc
 */
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Long>, JpaSpecificationExecutor<CategoryAttribute> {

    List<CategoryAttribute> findByCategoryId(Long categoryId);
}
