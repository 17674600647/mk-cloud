package com.glm.feign.fallback;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.feign.MkBaseFeign;
import org.springframework.stereotype.Component;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-03-22 22:47
 **/

@Component
public class MkBaseFeignFallBack implements MkBaseFeign {
    @Override
    public ResponseResult getANote(GetOneNoteDTO getOneNoteDTO) {
        return ResponseResult.error("mk-base服务出小差啦");
    }
}
