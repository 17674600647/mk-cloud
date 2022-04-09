package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mk_url_auth")
public class MkUrlAuth {
  @TableId(value = "id")
  private Long id;
  @TableField(value = "url")
  private String url;
  @TableField(value = "auth_id")
  private Integer authId;
  @TableField(value = "state")
  private Integer state;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private Date createTime;
  @Field(type= FieldType.Short)
  @TableLogic
  private Integer deleted;
}