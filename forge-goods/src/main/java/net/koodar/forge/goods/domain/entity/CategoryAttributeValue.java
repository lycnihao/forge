package net.koodar.forge.goods.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *  商品分类属性值表
 *
 * @author liyc
 */
@Data
@Entity
@Table(name = "t_category_attribute_value")
@ToString(callSuper = true)
public class CategoryAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 属性ID
     */
    @Column(name = "attribute_id")
    private Long attributeId;

    /**
     * 属性值
     */
    @Column(name = "value")
    private String value;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Long sort;

}
