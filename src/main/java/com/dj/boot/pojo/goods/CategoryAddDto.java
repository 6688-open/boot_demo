package com.dj.boot.pojo.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 商品分类添加
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryAddDto implements Serializable {

    /**
     * 租户编码
     */
    @NotBlank(message = "租户编码不能为空")
    private String tenantId;
    /**
     * 创建人
     */
    @NotBlank(message = "用户名不能为空")
    private String createUser;
    @NotEmpty
    @Valid
    private List<CateAddParamDto> cateAddParamDtoList;
}
