package io.controller;

import io.domain.MyTableVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import java.security.PrivateKey;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class) //테스트 코드와 spring을 연동
@WebAppConfiguration // WebApplicationContext를 생성할 수 있도록 하는 어노테이션 servlet의 ServletContext를 이용하기 위함
@ContextConfiguration("file:web/WEB-INF/dispatcher-servlet.xml") //location 경로에 file 또는 calssPath가 들어갈 수 있다.
public class MemberControllerTest {

    @Autowired
    private WebApplicationContext was;//spring에 있는 것을 사용하여 통합테스트 가능
    private ObjectMapper objectMapper; //객체를 json으로 변경시 사용

    /**
     * 가짜 MVC
     * 브라우저에서 사용하는 것처럼 만들어서 Controller를 실행해 볼 수 있다.
     */
    private static MockMvc mockMVC;


    /**
     *테스트 할 시 사용할 모델객체 데이터
     *
     */
    public  static MyTableVO getMemberData() {
        MyTableVO memberData = new MyTableVO();

        memberData.setMemberName("aaaa");
        memberData.setMemberPw("1234");
        return memberData;
    }


    /**
     * @Before : 반복되는 작업에 넣으면 테스트가 실행되면서 자동으로 실행되게 해준다.
     */
    @Before
    public void setUp(){
        //weApplicationContext를 통해서 ServletContext를 빌드한다.
        this.mockMVC = MockMvcBuilders.webAppContextSetup(was).build();
        objectMapper= new ObjectMapper();

    }

    /**
     * 클래스 내의 모든 테스트 완료시 1번 호출
     * @throws Exception
     */
   @AfterClass
    public static void tearDown() throws Exception {
        mockMVC.perform(MockMvcRequestBuilders.delete("/member/delete/"+getMemberData().getNum()));

    }


    @Test
    public void testList() throws Exception{
        log.info(mockMVC.perform(MockMvcRequestBuilders.get("/member/list")) //preform : controller 호충방식인 get.post.delet...등이 있다.
        .andReturn() //응답된 결과 값 출력
        .getModelAndView() //RedirectAttributes 의 flash attribute를 사용중이라면 getModelAndView() 대신 getFlashMap()를 사용할 수 있다.
        .getModelMap()); //Model 객체를 전달하고 있다면 getViewName() 대신 getModelMap() 을 사용하실 수 있다.
    }



    /**
     * insert
     * @throws Exception
     * num값을 가져와서 setData에 업데이트 해주기
     */
     @Test
    public void testInsert() throws  Exception{
       FlashMap result;
         result = mockMVC.perform(MockMvcRequestBuilders.post("/member/insert")
   /*     .param("memberName","qqqqq")
          .param("memberPw","11111"))*/

          .param("memberName", getMemberData().getMemberName())
          .param("memberPw",getMemberData().getMemberPw())).andReturn().getFlashMap();

         log.info(result);                  //String.valueOf :문자열로 바꿔준다
         getMemberData().setNum(Integer.parseInt(String.valueOf(result.get("num"))));
}


    /**
     * updata(put)
     *
     */

    @Test
    public void testUpdate()throws Exception{

     log.info(mockMVC.perform(MockMvcRequestBuilders.put("/member/put")
             //json 형식으로 데이터를 보낸다고 명시
            .contentType(APPLICATION_JSON)
             //json형식의 String으로 만들기 위해 objectMapper를 사용
             .content(objectMapper.writeValueAsString(getMemberData()))).andReturn());
    }

    /**
     *
     * update(patch)
     * @throws Exception
     */
    @Test
    public void testPatch()throws Exception{

        String name = "aaaa";

        log.info(mockMVC.perform(MockMvcRequestBuilders.patch("/member/patch/"+"74")
              .content(getMemberData().getMemberName()).contentType(APPLICATION_JSON)).andReturn());
    }

    /**
     * select
     * @throws Exception
     */

    @Test
    public void testSelect() throws  Exception{
        log.info(mockMVC.perform(MockMvcRequestBuilders.get("/member/select/"+getMemberData().getMemberName()))
                .andReturn());
    }



    /**
     * delete
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception{
        log.info(mockMVC.perform(MockMvcRequestBuilders.delete("/member/delete/"+getMemberData().getNum()))
        .andReturn());
    }




    /**
     *
     * .andDo(print()) // 결과를 print. MockMvcBuilders의 alwaysDo(print())로 대체 가능
     * .andExpect(status().isOk());// 호출 결과값이 OK가 나오면 정상처리
     *
     */

}
