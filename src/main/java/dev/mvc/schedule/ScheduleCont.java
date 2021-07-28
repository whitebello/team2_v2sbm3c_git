package dev.mvc.schedule;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScheduleCont {
  @Autowired // CategrpProcInter를 구현한 CategrpProc.java의 객체가 할당
  @Qualifier("dev.mvc.schedule.ScheduleProc")
  private ScheduleProcInter scheduleProc;
  
  public ScheduleCont() {
    System.out.println("-> ScheduleCont created.");
  }
  
  /**
   * 시간표 폼
   * http://localhost:9091/schedule/list.do
   * @return
   */
  @RequestMapping(value="/schedule/schedule.do", method=RequestMethod.GET )
  public ModelAndView schedule(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("id") == null) {  // 로그인을 안했으면 로그인페이지로 이동하라
      mav.setViewName("/register/login_form");
      
      return mav;
    } else {
      
      int memberno = (int)session.getAttribute("memberno");
      
      // 출력 순서별 출력
      List<ScheduleVO> list = this.scheduleProc.list(memberno);
      
      mav.addObject("list", list); // request.setAttribute("list", list);
      
      mav.setViewName("/schedule/schedule"); // /WEB-INF/views/schedule/list.jsp
      
      return mav;
    }

  }
  
  /**
   * 스케줄 불러오기
   * http://localhost:9091/schedule/load.do?memberno=2
   * 
   * @return
   */
  @RequestMapping(value = "/schedule/load.do", method = RequestMethod.GET)
  @ResponseBody
  public List load(int memberno) {
      
    List<ScheduleVO> list = this.scheduleProc.list(memberno);
    
    return list;

  }
  
  /**
   * 시간표 목록
   * http://localhost:9091/schedule/list.do
   * @return
   */
  @RequestMapping(value="/schedule/list.do", method=RequestMethod.GET )
  public ModelAndView list(ScheduleVO scheduleVO, HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("id") == null) {  // 로그인을 안했으면 로그인페이지로 이동하라
      mav.setViewName("/register/login_form");
      
      return mav;
    } else {
      
      int memberno = (int)session.getAttribute("memberno");
      
      List<ScheduleVO> list = this.scheduleProc.list(memberno);
      
      mav.addObject("list", list);
      
      mav.setViewName("/schedule/list");
      
      return mav;
    }
  }
  
  /**
   * 등록 처리
   * @param scheduleVO  
   * @return
   */
  @RequestMapping(value="/schedule/create.do", method=RequestMethod.POST )
  public ModelAndView create(ScheduleVO scheduleVO) { 
     
    ModelAndView mav = new ModelAndView();
     
    int cnt = this.scheduleProc.create(scheduleVO);
     
    if (cnt == 1) {
      mav.setViewName("redirect:/schedule/list.do");   
    } else {
      mav.addObject("code", "create");
      mav.setViewName("/schedule/error_msg");
    }
  
    return mav; // forward
  }
 
 
   /**
  * 조회 + 수정폼/삭제폼 + Ajax
  * http://localhost:9091/schedule/read_ajax.do?classno=1
  * @param classno 
  * @return
  */
  @RequestMapping(value="/schedule/read_ajax.do", 
                               method=RequestMethod.GET )
  @ResponseBody
  public String read_ajax(int classno) {
    ScheduleVO scheduleVO = this.scheduleProc.read_by_classno(classno);
     
    JSONObject json = new JSONObject();
    json.put("classno", scheduleVO.getClassno());
    json.put("classname", scheduleVO.getClassname());
    json.put("starttime", scheduleVO.getStarttime());
    json.put("endtime", scheduleVO.getEndtime());
    json.put("professor", scheduleVO.getProfessor());
    json.put("textbook", scheduleVO.getTextbook());
    json.put("cday", scheduleVO.getCday());
    json.put("memberno", scheduleVO.getMemberno());
     
    return json.toString();
  }
 
 
  /**
  * 수정 처리
  * @param scheduleVO
  * @return
  */
  @RequestMapping(value="/schedule/update.do", method=RequestMethod.POST )
  public ModelAndView update(ScheduleVO scheduleVO) {
    ModelAndView mav = new ModelAndView();
   
    int cnt = this.scheduleProc.update(scheduleVO);
    mav.addObject("cnt", cnt); // request에 저장
   
    if (cnt == 1) {
      mav.setViewName("redirect:/schedule/list.do");   
    } else {
      mav.setViewName("/schedule/update_msg"); 
    } 
   
    return mav;
  }
 
  /**
  * 삭제
  * @param classno 조회할 카테고리 번호
  * @return
  */
  @RequestMapping(value="/schedule/delete.do", method=RequestMethod.POST )
  public ModelAndView delete(int classno) {
    ModelAndView mav = new ModelAndView();
   
    ScheduleVO scheduleVO = this.scheduleProc.read_by_classno(classno); // 삭제 정보
    mav.addObject("scheduleVO", scheduleVO);  // request 객체에 저장
   
    int cnt = this.scheduleProc.delete(classno); // 삭제 처리
    mav.addObject("cnt", cnt);  // request 객체에 저장
   
    mav.setViewName("redirect:/schedule/list.do");   

  
    return mav;
  }
  
  /**
  *  시간표 상세
  * @param classno 조회할 카테고리 번호
  * @return
  */
  @RequestMapping(value="/schedule/read_by_classno.do", method=RequestMethod.GET )
  public ModelAndView read_by_classno(int classno) {
    ModelAndView mav = new ModelAndView();
   
    ScheduleVO scheduleVO = this.scheduleProc.read_by_classno(classno); // 삭제 정보
    mav.addObject("scheduleVO", scheduleVO);  // request 객체에 저장
   
    mav.setViewName("/schedule/read");   

  
    return mav;
  }
  
}

