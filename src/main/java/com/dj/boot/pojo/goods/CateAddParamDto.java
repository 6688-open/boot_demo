package com.dj.boot.pojo.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CateAddParamDto implements Serializable, Comparable<CateAddParamDto> {

    /**
     * 级别
     */
    @NotNull(message = "级别不能为空")
    private int level;
    /**
     * 外部分类编码
     */
    @NotBlank(message = "外部分类编码不能为空")
    private String outCategoryNo;
    /**
     * 分类名称
     */
    @NotBlank(message = "外部分类名称不能为空")
    private String categoryName;

    @Override
    public int compareTo(CateAddParamDto dto) {
        return  dto.getLevel()-this.getLevel();
    }
}
