package net.koodar.forge.goods.repository;

import net.koodar.forge.goods.domain.entity.CategoryAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 商品分类属性值仓储接口
 * @author liyc
 */
public interface CategoryAttributeValueRepository extends JpaRepository<CategoryAttributeValue, Long>, JpaSpecificationExecutor<CategoryAttributeValue> {

    List<CategoryAttributeValue> findByAttributeId(Long categoryId);

    List<CategoryAttributeValue> findByAttributeIdIn(List<Long> attributeIds);

}
