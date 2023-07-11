package net.koodar.forge.goods.domain.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

/**
 * @author liyc
 */
@Getter
@Setter
public class CategoryQueryDTO extends DTO {

    private Long parentId;

    private String name;

}
