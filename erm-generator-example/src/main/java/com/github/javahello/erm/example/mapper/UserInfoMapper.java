package com.github.javahello.erm.example.mapper;

import com.github.javahello.erm.example.model.UserInfo;
import com.github.javahello.erm.example.model.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    long countByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int deleteByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int deleteByPrimaryKey(@Param("id") Long id, @Param("uname") String uname);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int insertSelective(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    List<UserInfo> selectByExample(UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    UserInfo selectByPrimaryKey(@Param("id") Long id, @Param("uname") String uname);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER_INFO
     *
     * @mbg.generated Sun Dec 27 18:16:24 CST 2020
     */
    int updateByPrimaryKey(UserInfo record);
}