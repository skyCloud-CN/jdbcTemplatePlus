/**
 * @(#)MybatisMapper.java, 10月 09, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.demo.dao;

import io.github.skycloud.fastdao.demo.model.Model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yuntian
 */
@Mapper
public interface MybatisMapper {

    @Select("SELECT * FROM db_test where id = ${id}")
    public Model selectList(@Param("id") Integer id);
}