package io.mapper;

import io.domain.MyTableVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * memberMapper
 *xml 파일에서 사용하고 있는 namespace가 value로 지정된 xml을 찾아줌
 *
 */

//@Repository("memberMapper")
@Mapper
public interface MemberMapper {

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
    public void insert(MyTableVO member);

    /**
     * 회원 정보 수정 (이름만 변경& 이름 비밀번호 변경)
     * @param member
     * @return 성공시 1 실패시 0
     */
    public int update(MyTableVO member);


    /**
     * 회원 삭제
     * @param memberName
     * @return 성공시 1 실패시0
     */
    public int delete(String memberName);



}
