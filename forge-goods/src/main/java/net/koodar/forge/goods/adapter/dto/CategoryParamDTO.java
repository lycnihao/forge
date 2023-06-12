package net.koodar.forge.goods.adapter.dto;

import lombok.Data;
import net.koodar.forge.goods.domain.entity.Category;

/**
 * @author liyc
 */
@Data
public class CategoryParamDTO {

    private Long id;

    private String code;

    private String name;

    private Long parentId;

    private Boolean subFlag = false;

    private Long sort;

    private Boolean statusFlag = true;

    private String remark;

    public Category toEntity() {
        Category category = new Category();
        category.setId(this.id);
        category.setCode(this.code);
        category.setName(this.name);
        category.setParentId(this.parentId);
        category.setSubFlag(this.subFlag);
        category.setSort(this.sort);
        category.setStatusFlag(this.statusFlag);
        category.setRemark(this.remark);
        return category;
    }

}
