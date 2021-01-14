package com.czz.dao;

import com.czz.health.pojo.Member;

public interface MemberDao {


    /**
     * 根据号码查询用户
     * @param telephone
     * @return
     */
    Member findMemberByTelephone(String telephone);

    /**
     * 添加用户
     * @param member
     */
    void add(Member member);
}
