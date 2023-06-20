package net.koodar.forge.goods.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.koodar.forge.data.jpa.doman.BaseEntity;

import java.util.List;

/**
 * 商品分类表
 *
 * @author liyc
 */
@Data
@Entity
@Table(name = "t_category_attribute")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryAttribute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 属性名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 属性编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 分类ID
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * 表单方式 1单选2复选3文本4下拉 默认3
     */
    @Column(name = "form_type")
    private Integer formType;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;


    /**
     * 属性值
     */
    @Transient
    private List<CategoryAttributeValue> attributeValueList;

}
