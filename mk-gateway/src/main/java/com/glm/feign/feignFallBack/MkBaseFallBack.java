package com.glm.feign.feignFallBack;

import com.glm.feign.MkBaseFeign;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MkBaseFallBack implements MkBaseFeign {

    @Override
    public Map<String, ArrayList<Integer>> queryUrlAuth() {
        return null;
    }
}
