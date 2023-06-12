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
@Table(name = "t_category")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 分类编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 分类名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 上级ID 默认null,代表一级
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 是否叶子类目
     */
    @Column(name = "subFlag")
    private Boolean subFlag;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Long sort;

    /**
     * 状态 0-无效 1-有效 默认1
     */
    @Column(name = "status_flag")
    private Boolean statusFlag;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 分类属性
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private List<CategoryAttribute> attributeList;

    /**
     * 下级分类
     */
    @Transient
    private List<Category> children;

}
