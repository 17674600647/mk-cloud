package com.glm.entity.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: mk-cloud
 * @description: 搜索文章的DTO
 * @author: lizhiyong
 * @create: 2022-03-19 15:14
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchNotesDTO {
    @NotBlank(message = "查询的内容不能为空")
    private String searchContent;
    @NotNull(message = "查询的pageSize不能为空")
    private Integer pageSize;
    @NotNull(message = "查询的currentPage不能为空")
    private Integer currentPage;

    public IPage getPage() {
        return new Page(this.currentPage, this.pageSize);
    }
}
