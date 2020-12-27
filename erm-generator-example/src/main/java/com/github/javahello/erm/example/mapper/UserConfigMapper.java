package com.github.javahello.erm.example.mapper;

import com.github.javahello.erm.example.model.UserConfig;
import com.github.javahello.erm.example.model.UserConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    long countByExample(UserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int deleteByExample(UserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int deleteByPrimaryKey(String confId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int insert(UserConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int insertSelective(UserConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    List<UserConfig> selectByExample(UserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    UserConfig selectByPrimaryKey(String confId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByExampleSelective(@Param("record") UserConfig record, @Param("example") UserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByExample(@Param("record") UserConfig record, @Param("example") UserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByPrimaryKeySelective(UserConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_CONFIG
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByPrimaryKey(UserConfig record);
}