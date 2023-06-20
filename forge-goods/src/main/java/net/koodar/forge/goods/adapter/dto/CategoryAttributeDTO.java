package net.koodar.forge.goods.adapter.dto;

import lombok.Data;
import net.koodar.forge.goods.domain.entity.CategoryAttribute;
import net.koodar.forge.goods.domain.entity.CategoryAttributeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyc
 */
@Data
public class CategoryAttributeDTO {

    private String name;

    private String code;

    private Integer formType;

    private String remark;

    private List<String> values;

    public CategoryAttribute toEntity() {
        CategoryAttribute categoryAttribute = new CategoryAttribute();
        categoryAttribute.setName(this.name);
        categoryAttribute.setCode(this.code);
        categoryAttribute.setFormType(this.formType);
        categoryAttribute.setRemark(this.remark);

        if (this.values != null) {
            categoryAttribute.setAttributeValueList(new ArrayList<>(this.values.size()));
            this.values.forEach(value -> {
                CategoryAttributeValue categoryAttributeValue = new CategoryAttributeValue();
                categoryAttributeValue.setValue(value);
                categoryAttributeValue.setSort((long) categoryAttribute.getAttributeValueList().size());
                categoryAttribute.getAttributeValueList().add(categoryAttributeValue);
            });
        }

        return categoryAttribute;
    }

}
