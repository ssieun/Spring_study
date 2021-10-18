package io.controller;

import io.domain.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {


    /**
     * model객체와 크게 다르지 않음
     * modelAndView객체로 리턴해주어야 한다.
     * 데이터를 보낼때는 addObject -->map형식
     * view의 경로를 설정할때는 setViewName
     */

   /* @RequestMapping("/")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView();
        Date date = new Date();
        //날짜형식 지정하기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);

        mv.setViewName("/test01");
        mv.addObject("date", time);

        return mv;
    }*/

    /**
     * 파일로 데이터를 전달하거나 전송할 시에는 파일로 부터 전달받는 길을 스트림이라고 한다.
     * 바이너리 형태로 데이터를 입출력
     * -(FileInputStream(파일 읽기) FileOutPutStream(파일 쓰기)
     * 문자 형태로 데이터를 입출력 으로 유니코드로 된 문자를 출력
     * - FileReader(텍스트 파일 읽기) , InputStreamReader(한글 텍스트파일 읽어오기),FileWrite(텍스트 파일에 키보드로 입력한 값 저장)
     * <p>
     * buffer 입출력
     * 데이터를 한 곳에서 다른 한 곳으로 전송하는 동안 일시적으로 그 데이터를 보관하는 임시 메모리 영역
     * 입출력 속도 향상을 위해 버퍼를 사용한다 -> 데이터가 스트링으로 고정
     */

    String filePath = "C:\\sieun\\data\\insert.txt"; //파일 경로

    @RequestMapping("/")
    public String main() {
        return "main";
    }

    //회원리스트
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String newPage(MemberVO member, Model model) {
        String line = "";
        List<MemberVO> memberList = new ArrayList<>();
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                member = new MemberVO();

                String[] data = line.split("-");
                member.setName(data[0]);
                member.setPw(data[1]);

                memberList.add(member);
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("list", memberList);

        return "list";
    }


    @GetMapping("/insert")
    public void insert() {
    }

    //insert
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(MemberVO member) {


        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));//이어쓰기

            bufferedWriter.write(member.getName() + "-" + member.getPw());
            bufferedWriter.write("\n");
            bufferedWriter.close();//버퍼객체 닫기

        } catch (IOException e) {

            e.printStackTrace();
        }

        return "main";
    }

    @RequestMapping(value = "/selectPage", method = RequestMethod.GET)
    public String select() {
        return "select";
    }

    //select(red)
    @ResponseBody
    @RequestMapping(value = "/select/{name}", method = RequestMethod.GET)
    public String select(@PathVariable("name") String name) {
        String line = "";
        String result = "";

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split("-");

                String memberName = data[0];
                String memberPw = data[1];

                if (memberName.equals(name)) { //파일에 입력한 이름이 있으면
                    result = memberPw;
                    System.out.println(memberPw);
                }
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return result;
    }

    //PUT페이지
    @GetMapping("/putPage")
    public String putPage(@RequestParam("name") String name, @RequestParam("pw") String pw, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("pw", pw);

        return "put";
    }

    /**
     * @PutMapping 또는 RequestMethod.put 사용 가능
     * @RequestBody를 통해 자바 객체로 변환할 때 HttpMessageConverter를 사용하여
     * 헤더에 담긴 컨텐츠 타입을 보고 어떤 것인지 판단하여
     * 요청 본문에 담긴 값을 자바 객체로 변환
     */

    //update(put)

    @ResponseBody
    @RequestMapping(value = "/put/{change}", consumes = "application/json", method = {RequestMethod.PUT, RequestMethod.GET})
    public String put(@RequestBody MemberVO member, @PathVariable("change") String change) {
        String result = "";
        System.out.println("들어오니");


        System.out.println(member.getName());
        System.out.println(member.getPw());
        System.out.println(change);

        try {
            String line = "";
            String temp = "";
            File file = new File(filePath); //파일 객체 생성

            if (file.exists()) { //경로에 파일이 있는지 체크
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("-");
                    String memberName = data[0];

                    if (memberName.equals(change)) { //파일에 기존의 이름이 있으면
                        temp += member.getName() + "-" + member.getPw() + "\n"; //그 라인은 바꿔줄 정보로 넣어준다.
                        continue;
                    }
                    temp += line + "\n";

                } //while end

                //파일 새로 만들기
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
                bufferedWriter.write(temp);
                bufferedReader.close();
                bufferedWriter.close();
                result = "ok";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/patch")
    public void page(@RequestParam("name") String name, @RequestParam("pw") String pw, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("pw", pw);
    }

    ;


    /**
     * (파일내용을 중간에 수정 불가능 파일을 다시 새로 만들어야함)
     *
     * @PatchMapping 또는 RequestMethod.PATCH 사용 가능
     */

    //patch
    @RequestMapping(value = "/patch", method = RequestMethod.POST)
    public String patch(MemberVO member, @RequestParam("change") String change) throws IOException {

        String line = "";
        String temp = "";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));


        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split("-");

                String memberName = data[0];

                if (memberName.equals(change)) {
                    temp += member.getName() + "-" + member.getPw() + "\n"; //내가 변경하고 싶은 데이터 저장
                    continue;
                }
                temp += line + "\n";
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
            bufferedWriter.write(temp);
            bufferedReader.close();
            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "main";
    }

    /**
     * @DeleteMapping 또는 RequestMethod.DELETE 사용 가능
     */

    //delete
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("name") String name, @RequestParam("pw") String pw) throws IOException {

        try {
            String line = "";
            String temp = "";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

            while ((line = bufferedReader.readLine()) != null) {

                String[] data = line.split("-");
                String memberName = data[0];
                String memberPw = data[1];

                if (memberName.equals(name) && memberPw.equals(pw)) { //파일에 입력한 이름과 패스워드가 있으면
                    temp += ""; //공백으로 채워주기
                    continue;
                }
                temp += line + "\n";
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
            bufferedWriter.write(temp);
            bufferedReader.close();
            bufferedWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "main";
    }

}
