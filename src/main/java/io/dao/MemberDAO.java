package io.dao;

import io.domain.MyTableVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberDAO {


    /**
     * 회원 전체 목록
     * @return 모델객체 리스트
     */
    public List<MyTableVO> selectAll();

    /**
     * 회원 이름으로 비밀번호 찾기
     * @param memberName
     * @return 비밀번호
     */

    public String get(String memberName);

    /**
     * 회원등록
     * @param member
     */
    public int insert(MyTableVO member);

    /**
     * 회원 정보 수정 (전부변경)
     * @param member
     * @return 성공시 1 실패시 0
     */
    public boolean update(MyTableVO member);

    /**
     * 회원 정보 수정 (이름만 변경)
     * @param member
     * @return
     */
    public boolean patch(MyTableVO member);

    /**
     * 회원 삭제
     * @param num
     * @return 성공시 1 실패시0
     */
    public boolean delete(int num);

}
