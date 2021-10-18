package io.service;

import io.dao.MemberDAO;
import io.domain.MyTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberService")
public class MemberServiceImple implements MemberService {

    @Autowired
    private MemberDAO memberDAO;

    boolean falseCheck = false;
    boolean trueCheck =true;

    @Override
    public List<MyTableVO> selectAll()
    {
        return memberDAO.selectAll();
    }

    @Override
    public String get(String memberName) {

        return memberDAO.get(memberName);
    }

    @Override
    public int insert(MyTableVO member) {
        System.out.println("insert Service");
        memberDAO.insert(member);
        return member.getNum();
    }

    @Override
    public boolean update(MyTableVO member) {

        if(!memberDAO.update(member)){
            return falseCheck;
        }else{
            return trueCheck;
        }
    }

    @Override
    public boolean patch(MyTableVO member) {

        if(!memberDAO.patch(member)){
            return falseCheck;
        }else{
            return trueCheck;
        }
    }


    @Override
    public boolean delete(int num) {

        if(!memberDAO.delete(num)){
            return falseCheck;
        }else{
            System.out.println("delete Service");
            return trueCheck;
        }

    }


}
