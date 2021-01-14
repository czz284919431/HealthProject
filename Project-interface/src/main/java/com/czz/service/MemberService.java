package com.czz.service;

import com.czz.health.pojo.Member;

public interface MemberService {

    /**
     * 查询用户
     * @param telephone
     * @return
     */
    Member findMemberByTel(String telephone);

    /**
     * 添加用户
     * @param member
     */
    void add(Member member);
}
