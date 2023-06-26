package net.koodar.forge.goods.domain.repository;

import net.koodar.forge.goods.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 商品分类仓储接口
 * @author liyc
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    List<Category> findByParentId(Long parentId);

    boolean existsByParentIdAndCode(Long parentId, String code);

    Category findByParentIdAndCode(Long parentId, String code);

    long countByParentId(Long parentId);

}
