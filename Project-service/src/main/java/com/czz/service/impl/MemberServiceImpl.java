package com.czz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.MemberDao;
import com.czz.health.pojo.Member;
import com.czz.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author czz
 * @create 2021-01-14 18:29
 **/
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findMemberByTel(String telephone) {
        Member member = memberDao.findMemberByTelephone(telephone);
        return member;
    }

    /**
     * 添加会员
     *
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
