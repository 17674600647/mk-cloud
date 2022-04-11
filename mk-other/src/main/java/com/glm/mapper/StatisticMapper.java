package com.glm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glm.entity.pojo.MkStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StatisticMapper extends BaseMapper<MkStatistic> {
    /**
     * 获取全部用户
     *
     * @return
     */
    @Select("select count(mu.id)\n" +
            "from mk_user mu\n" +
            "where mu.deleted = 0;\n" +
            ";")
    public Integer queryTotalUser();

    /**
     * 查询所有的笔记
     *
     * @return
     */
    @Select("select count(mn.id)\n" +
            "from mk_notes mn\n" +
            "where mn.deleted = 0;")
    public Integer queryTotalNote();

    /**
     * 查询登录记录
     *
     * @return
     */
    @Select("select count(ml.id)\n" +
            "from mk_logs ml\n" +
            "where ml.action_mark = '12700' and create_time >=current_date-1")
    public Integer queryTotalLogin();

    /**
     * 查询分享的笔记记录
     *
     * @return
     */
    @Select("select count(mn.id)\n" +
            "from mk_notes mn\n" +
            "where mn.share_status = 1\n" +
            "  and mn.deleted = 0;")
    public Integer queryToTalShare();
}
