package io.dao;

import io.domain.MyTableVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Repository : 스프링 컨테이너가 component-scan에 의해 지정한 클래스를 DAO빈으로 자동 변환
 */
@Repository("memberDAO")
public class MemberDAOImp implements MemberDAO {

    @Autowired
    private SqlSession sqlSession;


    @Override
    public List<MyTableVO> selectAll() {
        return sqlSession.selectList("member.selectAll");
    }

    @Override
    public String get(String memberName) {

        return sqlSession.selectOne("member.get", memberName);
    }

    @Override
    public int insert(MyTableVO member)
    {

        sqlSession.insert("member.insert",member);
        return member.getNum();
    }

    @Override
    public boolean update(MyTableVO member) {

        return (Integer)sqlSession.update("member.update",member)==1;
    }

    @Override
    public boolean patch(MyTableVO member) {
        return (Integer)sqlSession.update("member.patch",member)==1;
    }


    @Override
    public boolean delete(int num) {

        return (Integer)sqlSession.delete("member.delete",num)==1;
    }
}
