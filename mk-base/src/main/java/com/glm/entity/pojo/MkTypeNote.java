package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mk_type_note")
@Accessors(chain = true)
public class MkTypeNote {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "note_id")
    private Long noteId;
    @TableField(value = "type_id")
    private Long typeId;

    public MkTypeNote (Long userId, Long noteId, Long typeId) {
       this.userId=userId;
       this.noteId=noteId;
       this.typeId=typeId;
    }
}
