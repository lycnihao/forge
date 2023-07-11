package net.koodar.forge.goods.domain.dto;

import lombok.Data;
import net.koodar.forge.common.dto.DTO;
import net.koodar.forge.goods.domain.entity.Category;

import java.util.List;

/**
 * @author liyc
 */
@Data
public class CategoryParamDTO extends DTO {

    private Long id;

    private String code;

    private String name;

    private Long parentId;

    private Boolean subFlag = false;

    private Long sort;

    private Boolean statusFlag = true;

    private String remark;

    private List<CategoryAttributeDTO> categoryAttribute;

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

        if (this.categoryAttribute != null) {
            this.categoryAttribute.forEach(categoryAttributeDTO -> {
                if (category.getAttributeList() == null) {
                    category.setAttributeList(new java.util.ArrayList<>(this.categoryAttribute.size()));
                }
                category.getAttributeList().add(categoryAttributeDTO.toEntity());
            });
        }

        return category;
    }

}
