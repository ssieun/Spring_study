package io.dao;

import io.domain.MyTableVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Member;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {"classpath:/mappers/MemberMapper.xml",
//        "classpath:/mybatis-config.xml"})
public class DaoTest {


    /**
     * 항상 동일한 테스트 결과를 보장해주도록 합니다.
     * ※ 테스트는 어떠한 경우에서라도 서로 의존해서는 안되고, 서로 절대 영향을 줘서도 안된다.
     */
    @Autowired
    private MemberDAO member;

    @Test
    public void selectList(){
        log.info(member.selectAll());
    }


    /*@Test
    public void select(){
        log.info(memberDAO.get("cc"));
    }
*/
   /* @Test
    public void insert(){
        MyTableVO member = new MyTableVO();
        member.setMemberName("dddd");
        member.setMemberPw("22222");

        log.info(memberDAO.insert(member));
    }*/


  /* @Test
    public void update(){
       MyTableVO member = new MyTableVO();
       member.setMemberName("dddd");
       member.setMemberPw("22222");
        member.setNum(71);

        log.info(memberDAO.update(member));
   }
*/
  /*
  @Test
    public void patch(){
      MyTableVO member = new MyTableVO();
      member.setMemberName("aaaa");
      member.setNum(71);

      log.info(memberDAO.patch(member));
  }
*/
/*
  @Test
    public void delete(){
      int num = 71;

      log.info(memberDAO.delete(num));
  }*/
}
