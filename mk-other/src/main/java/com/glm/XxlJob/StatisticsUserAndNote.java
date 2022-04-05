package com.glm.XxlJob;


import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class StatisticsUserAndNote {


    @XxlJob("MkOtherStatisticJobHandler")
    public void updateDataToES() {
        System.out.println("shabi1");
    }
}
