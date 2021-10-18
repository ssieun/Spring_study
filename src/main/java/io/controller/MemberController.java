package io.controller;


import io.domain.MyTableVO;
import io.service.MemberService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.PrintWriter;


@Controller
@RequestMapping("/member/*")
@Log4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    //메인페이지
    @RequestMapping(value = "/main")
    public String main(){ return "main"; }


    //전체 목록 출력
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String selectAll(Model model){
        log.info("전체목록 출력");
        model.addAttribute("list",memberService.selectAll());
        return "list";
    }


    //검색 페이지
    @RequestMapping(value = "/selectPage")
    public String selectPage(){
        return "select";
    }

    /**
     * 검색
     * getMethod select
     * 이름으로 비밀번호를 검색한다.
     * ajax로 결과값을 뿌려준다
     *
     * consumes : 클라이언트가 요청을 보낼 떄 받는 데이터 타입 처리
     * produces : 클라이언트에게 요청하는 데이터 타입 속성
     * @ResponseBody : 자바 객체를 Http로 변환 해주며 return을 url을 찾는게 아니라 객체로 반환
     * @RequestParam : url뒤에 붙는 파라미터 값을 가져올 때 사용한다.
     * @PathVariable : url에서 구분자에 들어오는 값을 가져올 때 사용한다.
     *
     * @param memberName
     * @return pw
     */
    @GetMapping(value="/select/{memberName}",
            produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String select(@PathVariable("memberName") String memberName){

        String pw = memberService.get(memberName);
        System.out.println(pw);
        return pw;
    }


    //등록 페이지
    @GetMapping("/insertPage")
    public String insertPage(){
        return "insert";
    }


    /**
     * 등록
     * PostMethod insert
     *
     * @param member
     * @return redirect로 정보가 남지 않도록 해준다.
     */
    @PostMapping(value="/insert")
    public String insert(MyTableVO member, RedirectAttributes rttr){

        memberService.insert(member);
        rttr.addFlashAttribute("num",member.getNum());

        return "redirect:/member/main";
    }



    //수정(put)페이지 이동
    @GetMapping("/putPage")
    public String putPage(String memberName,int num, Model model){

        model.addAttribute("memberName", memberName);
        model.addAttribute("num", num);



        return "put";
    }


    /**
     * ajax로 json 타입으로 받아
     * 모델객체로 파싱해준다.
     *
     * @param member
     * @return true & false
     */
    //수정(put)
    @PutMapping(value = "/put")
    @ResponseBody
    public String put(@RequestBody MyTableVO member){

        String result = "";

        if(memberService.update(member)==true){ //수정 성공시
            System.out.println("update");
             System.out.println(member);
            result = "ok";
        }else{
            result ="no";
        }
        return result;
    }


    //수정(patch)페이지 이동
    @GetMapping("/patchPage")
    public String patchPage(String memberName,int num, Model model){

        model.addAttribute("memberName",memberName);
        model.addAttribute("num", num);

        return "patch";
    }


    /**
     * 수정
     * patchMethod update
     * 회원의 번호를 uri로 받고 회원의 이름을  ajax를 사용해 받는다.
     *
     * @RequestBody : 클라이언트가 전송하는 json 형태의 내용을 java객체로 변환시켜준다.
     * @param memberName
     * @param num
     * @return true & false
     */
    @PatchMapping(value = "/patch/{num}")
    @ResponseBody
    public String patch(@RequestBody String memberName, @PathVariable("num") int num ){

        String result ="";
        MyTableVO member = new MyTableVO();
        member.setMemberName(memberName);
        member.setNum(num);

        if(memberService.patch(member)==true){ //수정 성공시
            result = "ok";
            System.out.println(member);
        }else{
            result ="no";
        }
        return result;
    }


    /**
     * 삭제
     * deleteMethod delete
     * 회원의 번호를 uri에서 받는다.
     *
     * @pram num
     * @return true & false
     */
    @DeleteMapping(value = "/delete/{num}" )
    @ResponseBody
    public String delete(@PathVariable("num") int num){

        String result = "";

        //삭제 성공
        if(memberService.delete(num)==true){
            result = "ok";
            System.out.println(num);
        }else{
            result ="no";
        }

        System.out.println(result);
        return result;
    }

}
