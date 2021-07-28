package dev.mvc.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.commgrp.CommgrpProcInter;
import dev.mvc.commgrp.CommgrpVO;
import dev.mvc.register.RegisterProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class BoardCont {
  
  @Autowired
  @Qualifier("dev.mvc.commgrp.CommgrpProc")
  private CommgrpProcInter commgrpProc;
  
  /*
   * @Autowired
   * 
   * @Qualifier("dev.mvc.book.BookProc") 
   * private BookProcInter bookProc;
   */
  @Autowired
  @Qualifier("dev.mvc.register.RegisterProc")
  private RegisterProcInter registerProc=null;
  
  @Autowired
  @Qualifier("dev.mvc.board.BoardProc")
  private BoardProcInter boardProc;
  
  public BoardCont() {
    System.out.println("-> BoardCont created.");
  }
  
  /**
   * 새로고침 방지
   * @return
   */
  @RequestMapping(value="/board/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  /**
   * 등록폼
   * 사전 준비된 레코드: 회원 1번, commgrpno 1번을 사용하는 경우 테스트 URL
   * http://localhost:9091/board/create.do?commgrpno=1
   * @return
   */
  @RequestMapping(value = "/board/create.do", method = RequestMethod.GET)
  public ModelAndView create(HttpServletRequest request, int commgrpno) {
    ModelAndView mav = new ModelAndView();
    
    CommgrpVO commgrpVO = this.commgrpProc.read(commgrpno);
    mav.addObject("commgrpVO", commgrpVO);
    
    mav.setViewName("/board/create");
    
    return mav; // forward
  }
  
  /**
   * 등록 처리 http://localhost:9090/board/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/board/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, BoardVO boardVO, String userid) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 전송 코드 시작
    // -------------------------------------------------------------------
    String file1 = "";     // 원본 파일명, image
    String thumb = "";     // preview image

    // 기준 경로 확인
    String user_dir = System.getProperty("user.dir");
    System.out.println("--> User dir: " + user_dir);
    //  --> User dir: F:\ai8\ws_frame\resort_v1sbm3a
    
    // 파일 접근임으로 절대 경로 지정, static 지정
    // 완성된 경로 F:/ai8/ws_frame/resort_v1sbm3a/src/main/resources/static/board/storage
    String upDir =  user_dir + "/src/main/resources/static/board/storage/"; // 절대 경로
    
    // 전송 파일이 없어서도 fnamesMF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF' 
    //           value='' placeholder="파일 선택">
    MultipartFile mf = boardVO.getFile1MF();
    
    file1 = mf.getOriginalFilename(); // 저장된 파일명
    long bsize = mf.getSize();  // 파일 크기
    
    if (bsize > 0) { // 파일 크기 체크
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      file1 = Upload.saveFileSpring(mf, upDir); 
      
      if (Tool.isImage(file1)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
        thumb = Tool.preview(upDir, file1, 200, 150); 
      }
    }    
    
    boardVO.setFile1(file1);
    // boardVO.setFile1saved(file1saved);
    boardVO.setThumb(thumb);
    boardVO.setBsize(bsize);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------
    
    // Call By Reference: 메모리 공유, Hashcode 전달
    int cnt = this.boardProc.create(boardVO); 
    
    // -------------------------------------------------------------------
    // PK의 return
    // -------------------------------------------------------------------
    // System.out.println("--> boardno: " + BoardVO.getContentsno());
    // mav.addObject("boardno", BoardVO.getContentsno()); // redirect parameter 적용
    // -------------------------------------------------------------------
    
//    if (cnt == 1) {
//      cateProc.increaseCnt(BoardVO.getCateno()); // 글수 증가
//    }
    /*
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    String userid = ck_id;
    

    boardVO.setUserid(userid); 
    */
    System.out.println("게시글 등록한 ID :" + boardVO.getWriter());
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
    mav.addObject("commgrpno", boardVO.getCommgrpno()); 
    
    mav.addObject("url", "/board/create_msg"); // create_msg.jsp, redirect parameter 적용
    
    mav.addObject("boardno", boardVO.getBoardno());
    mav.setViewName("redirect:/board/msg.do"); 
    
    return mav; // forward
  }

  /**
   * 커뮤니티 그룹별 목록 
   * http://localhost:9091/board/list_by_commgrpno.do?commgrpno=1
   * 
   * @return
   */
   @RequestMapping(value = "/board/list_by_commgrpno.do", method = RequestMethod.GET)
    public ModelAndView list_by_commgrpno(int commgrpno, int memberno) { 
      ModelAndView mav = new  ModelAndView();  
      
      CommgrpVO commgrpVO = this.commgrpProc.read(commgrpno);
      mav.addObject("commgrpVO", commgrpVO);
      
      List<BoardVO> list = this.boardProc.list_by_commgrpno(commgrpno);
      mav.addObject("list", list);

      mav.setViewName("/board/list_by_commgrpno");
      
      return mav; // forward 
   }
   
   /**
    * 목록 + 검색 지원
    * http://localhost:9091/board/list_by_commgrpno_search.do?commgrpno=1&word=스위스
    * @param commgrpno
    * @param word
    * @return
    */
     @RequestMapping(value = "/board/list_by_commgrpno_search.do", method = RequestMethod.GET)
     public ModelAndView list_by_commgrpno_search(@RequestParam(value="commgrpno", defaultValue="1") int commgrpno,
                                                  @RequestParam(value="word", defaultValue="") String word,
                                                  @RequestParam(value = "now_page", defaultValue = "1") int now_page) {
     ModelAndView mav = new ModelAndView(); 
          
     // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용 
     HashMap<String, Object> map = new HashMap<String, Object>(); 
     map.put("commgrpno", commgrpno); // #{commgrpno}
     map.put("word", word); // #{word}
     map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용
     
     // 검색 목록 
     List<BoardVO> list = boardProc.list_by_commgrpno_search(map);
     mav.addObject("list", list);
     
     // 검색된 레코드 갯수 
     int search_count = boardProc.search_count(map);
     mav.addObject("search_count", search_count);
     System.out.println("search_count: " + search_count);
     
     CommgrpVO commgrpVO = commgrpProc.read(commgrpno); 
     mav.addObject("commgrpVO", commgrpVO);     
     
     mav.setViewName("/board/list_by_commgrpno_search");   // /board/list_by_commgrpno_search.jsp
     
     return mav; 
   }
     
   /**
    * 자유게시판 목록 + 검색 + 페이징 지원
    * http://localhost:9091/board/list_by_commgrpno_search_paging.do?memberno=11&commgrpno=1&word=스위스&now_page=1
    * @RequestParam(value = "memberno", defaultValue = "11") int memberno,
    * @param commgrpno
    * @param word
    * @param now_page
    * @return
    */
   @RequestMapping(value = "/board/list_by_commgrpno_search_paging.do", method = RequestMethod.GET)
   public ModelAndView list_by_commgrpno_search_paging(@RequestParam(value = "commgrpno", defaultValue = "1") int commgrpno,
                                                       @RequestParam(value = "word", defaultValue = "") String word,
                                                       @RequestParam(defaultValue = "all") String search_option,
                                                       @RequestParam(value = "now_page", defaultValue = "1") int now_page,
                                                       HttpServletRequest request) {
     // System.out.println("-> list_by_commgrpno_search_paging now_page: " + now_page);

     ModelAndView mav = new ModelAndView();

     // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
     HashMap<String, Object> map = new HashMap<String, Object>();
     map.put("commgrpno", commgrpno); // #{commgrpno}
     map.put("word", word); // #{word}
     map.put("search_option", search_option); // #{word}
     map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

     // 검색 목록
     List<BoardVO> list = boardProc.list_by_commgrpno_search_paging(map);
     mav.addObject("list", list);

     // 조인 검색 목록
     List<Commgrp_BoardVO> list2 = this.boardProc.list_all_join();
     mav.addObject("list2", list2); // request.setAttribute("list", list);
     
     // 검색된 레코드 갯수
     int search_count = boardProc.search_count(map);
     mav.addObject("search_count", search_count);

     CommgrpVO commgrpVO = commgrpProc.read(commgrpno);
     mav.addObject("commgrpVO", commgrpVO);
     
     
     /*
      * RegisterVO registerVO = this.registerProc.read(memberno);
      * mav.addObject("registerVO", registerVO);
      */  

     /*
      * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
      * 18 19 20 [다음]
      * @param list_file 목록 파일명
      * @param commgrpno 카테고리번호
      * @param search_count 검색(전체) 레코드수
      * @param now_page 현재 페이지
      * @param word 검색어
      * @return 페이징 생성 문자열
      */
     String paging = boardProc.pagingBox("list_by_commgrpno_search_paging.do", commgrpno, search_count, search_option, word, now_page);
     mav.addObject("paging", paging);
     mav.addObject("word", word);
     mav.addObject("search_option", search_option);
     mav.addObject("now_page", now_page);

     // /board/list_by_commgrpno_table_img1_search_paging.jsp
     mav.setViewName("/board/list_by_commgrpno_search_paging");

     // -------------------------------------------------------------------------------
     // 게시글 등록전 로그인 폼 출력 관련 쿠기  
     // -------------------------------------------------------------------------------
     Cookie[] cookies = request.getCookies();
     Cookie cookie = null;

     String ck_id = ""; // id 저장
     String ck_id_save = ""; // id 저장 여부를 체크
     String ck_passwd = ""; // passwd 저장
     String ck_passwd_save = ""; // passwd 저장 여부를 체크

     if (cookies != null) {  // Cookie 변수가 있다면
       for (int i=0; i < cookies.length; i++){
         cookie = cookies[i]; // 쿠키 객체 추출
         
         if (cookie.getName().equals("ck_id")){
           ck_id = cookie.getValue();                                 // Cookie에 저장된 id
         }else if(cookie.getName().equals("ck_id_save")){
           ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
         }else if (cookie.getName().equals("ck_passwd")){
           ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
         }else if(cookie.getName().equals("ck_passwd_save")){
           ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
         }
       }
     }
     
     System.out.println("-> ck_id: " + ck_id);

     mav.addObject("ck_id", ck_id); 
     mav.addObject("ck_id_save", ck_id_save);
     mav.addObject("ck_passwd", ck_passwd);
     mav.addObject("ck_passwd_save", ck_passwd_save);
     // -------------------------------------------------------------------------------
     return mav;
   }
   
   /**
    * 공지사항 목록 + 검색 + 페이징 지원
    * http://localhost:9091/board/list_by_commgrpno_notice_search_paging.do?memberno=11&commgrpno=1&word=스위스&now_page=1
    * @RequestParam(value = "memberno", defaultValue = "11") int memberno,
    * @param commgrpno
    * @param word
    * @param now_page
    * @return
    */
   @RequestMapping(value = "/board/list_by_commgrpno_notice_search_paging.do", method = RequestMethod.GET)
   public ModelAndView list_by_commgrpno_notice_search_paging(@RequestParam(value = "commgrpno", defaultValue = "1") int commgrpno,
                                                       @RequestParam(value = "word", defaultValue = "") String word,
                                                       @RequestParam(defaultValue = "all") String search_option,
                                                       @RequestParam(value = "now_page", defaultValue = "1") int now_page,
                                                       HttpServletRequest request) {
     // System.out.println("-> list_by_commgrpno_notice_search_paging now_page: " + now_page);

     ModelAndView mav = new ModelAndView();

     // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
     HashMap<String, Object> map = new HashMap<String, Object>();
     map.put("commgrpno", commgrpno); // #{commgrpno}
     map.put("word", word); // #{word}
     map.put("search_option", search_option); // #{search_option}
     map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

     // 검색 목록
     List<BoardVO> list = boardProc.list_by_commgrpno_notice_search_paging(map);
     mav.addObject("list", list);

     // 조인 검색 목록
     List<Commgrp_BoardVO> list2 = this.boardProc.list_all_join();
     mav.addObject("list2", list2);
     
     // 검색된 레코드 갯수
     int search_count = boardProc.search_count(map);
     mav.addObject("search_count", search_count);

     CommgrpVO commgrpVO = commgrpProc.read(commgrpno);
     mav.addObject("commgrpVO", commgrpVO);

     /*
      * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
      * 18 19 20 [다음]
      * @param list_file 목록 파일명
      * @param commgrpno 카테고리번호
      * @param search_count 검색(전체) 레코드수
      * @param now_page 현재 페이지
      * @param word 검색어
      * @return 페이징 생성 문자열
      */
     String paging = boardProc.pagingBox("list_by_commgrpno_notice_search_paging.do", commgrpno, search_count, search_option, word, now_page);
     mav.addObject("paging", paging);
     mav.addObject("word", word);
     mav.addObject("search_option", search_option);
     mav.addObject("now_page", now_page);

     // /board/list_by_commgrpno_notice_search_paging.jsp
     mav.setViewName("/board/list_by_commgrpno_notice_search_paging");

     // -------------------------------------------------------------------------------
     // 게시글 등록전 로그인 폼 출력 관련 쿠기  
     // -------------------------------------------------------------------------------
     Cookie[] cookies = request.getCookies();
     Cookie cookie = null;

     String ck_id = ""; // id 저장
     String ck_id_save = ""; // id 저장 여부를 체크
     String ck_passwd = ""; // passwd 저장
     String ck_passwd_save = ""; // passwd 저장 여부를 체크

     if (cookies != null) {  // Cookie 변수가 있다면
       for (int i=0; i < cookies.length; i++){
         cookie = cookies[i]; // 쿠키 객체 추출
         
         if (cookie.getName().equals("ck_id")){
           ck_id = cookie.getValue();                                 // Cookie에 저장된 id
         }else if(cookie.getName().equals("ck_id_save")){
           ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
         }else if (cookie.getName().equals("ck_passwd")){
           ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
         }else if(cookie.getName().equals("ck_passwd_save")){
           ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
         }
       }
     }
     
     System.out.println("-> ck_id: " + ck_id);

     mav.addObject("ck_id", ck_id); 
     mav.addObject("ck_id_save", ck_id_save);
     mav.addObject("ck_passwd", ck_passwd);
     mav.addObject("ck_passwd_save", ck_passwd_save);
     // -------------------------------------------------------------------------------
     return mav;
   }
   
   /**
    * QnA 목록 + 검색 + 페이징 지원
    * http://localhost:9091/board/list_by_commgrpno_notice_search_paging.do?memberno=11&commgrpno=1&word=스위스&now_page=1
    * @RequestParam(value = "memberno", defaultValue = "11") int memberno,
    * @param commgrpno
    * @param word
    * @param now_page
    * @return
    */
   @RequestMapping(value = "/board/list_by_commgrpno_qna_search_paging.do", method = RequestMethod.GET)
   public ModelAndView list_by_commgrpno_qna_search_paging(@RequestParam(value = "commgrpno", defaultValue = "1") int commgrpno,
                                                       @RequestParam(value = "word", defaultValue = "") String word,
                                                       @RequestParam(defaultValue = "all") String search_option,
                                                       @RequestParam(value = "now_page", defaultValue = "1") int now_page,
                                                       HttpServletRequest request) {
     // System.out.println("-> list_by_commgrpno_qna_search_paging now_page: " + now_page);

     ModelAndView mav = new ModelAndView();

     // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
     HashMap<String, Object> map = new HashMap<String, Object>();
     map.put("commgrpno", commgrpno); // #{commgrpno}
     map.put("word", word); // #{word}
     map.put("search_option", search_option); // #{search_option}
     map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

     // 검색 목록
     List<BoardVO> list = boardProc.list_by_commgrpno_notice_search_paging(map);
     mav.addObject("list", list);
     
     // 조인 검색 목록
     List<Commgrp_BoardVO> list2 = this.boardProc.list_all_join();
     mav.addObject("list2", list2); // request.setAttribute("list", list);

     // 검색된 레코드 갯수
     int search_count = boardProc.search_count(map);
     mav.addObject("search_count", search_count);

     CommgrpVO commgrpVO = commgrpProc.read(commgrpno);
     mav.addObject("commgrpVO", commgrpVO);

     /*
      * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
      * 18 19 20 [다음]
      * @param list_file 목록 파일명
      * @param commgrpno 카테고리번호
      * @param search_count 검색(전체) 레코드수
      * @param now_page 현재 페이지
      * @param word 검색어
      * @return 페이징 생성 문자열
      */
     String paging = boardProc.pagingBox("list_by_commgrpno_qna_search_paging.do", commgrpno, search_count, search_option, word, now_page);
     mav.addObject("paging", paging);
     mav.addObject("word", word);
     mav.addObject("search_option", search_option);
     mav.addObject("now_page", now_page);

     // /board/list_by_commgrpno_qna_search_paging.jsp
     mav.setViewName("/board/list_by_commgrpno_qna_search_paging");

     // -------------------------------------------------------------------------------
     // 게시글 등록전 로그인 폼 출력 관련 쿠기  
     // -------------------------------------------------------------------------------
     Cookie[] cookies = request.getCookies();
     Cookie cookie = null;

     String ck_id = ""; // id 저장
     String ck_id_save = ""; // id 저장 여부를 체크
     String ck_passwd = ""; // passwd 저장
     String ck_passwd_save = ""; // passwd 저장 여부를 체크

     if (cookies != null) {  // Cookie 변수가 있다면
       for (int i=0; i < cookies.length; i++){
         cookie = cookies[i]; // 쿠키 객체 추출
         
         if (cookie.getName().equals("ck_id")){
           ck_id = cookie.getValue();                                 // Cookie에 저장된 id
         }else if(cookie.getName().equals("ck_id_save")){
           ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
         }else if (cookie.getName().equals("ck_passwd")){
           ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
         }else if(cookie.getName().equals("ck_passwd_save")){
           ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
         }
       }
     }
     
     System.out.println("-> ck_id: " + ck_id);

     mav.addObject("ck_id", ck_id); 
     mav.addObject("ck_id_save", ck_id_save);
     mav.addObject("ck_passwd", ck_passwd);
     mav.addObject("ck_passwd_save", ck_passwd_save);
     // -------------------------------------------------------------------------------
     return mav;
   }
   
   /**
    * Commgrp + Board join 연결 목록
    * http://localhost:9091/board/list_all_join.do 
    * @return
    */
   @RequestMapping(value="/board/list_all_join.do", method=RequestMethod.GET )
   public ModelAndView list_all_join() {
     ModelAndView mav = new ModelAndView();
     
     List<Commgrp_BoardVO> list = this.boardProc.list_all_join();
     mav.addObject("list", list); // request.setAttribute("list", list);

     mav.setViewName("/board/list_all_join"); // /cate/list_all_join.jsp
     return mav;
   }
   
   /**
    * Commgrp + Board join 연결, commgrp 선택 목록
    * Commgrp_BoardVO
    * http://localhost:9091/board/list_by_commgrpno_join.do 
    * @return
    */
   @RequestMapping(value="/board/list_by_commgrpno_join.do", method=RequestMethod.GET )
   public ModelAndView list_by_commgrpno_join(@RequestParam(value = "commgrpno", defaultValue = "1") int commgrpno, 
                                              HttpServletRequest request) {
     ModelAndView mav = new ModelAndView();
     
     HashMap<String, Object> map = new HashMap<String, Object>();

     map.put("commgrpno", commgrpno); // #{commgrpno}
         
     List<Commgrp_BoardVO> list = this.boardProc.list_by_commgrpno_join(map);
     
     mav.addObject("list", list); // request.setAttribute("list", list);

     mav.setViewName("/board/list_by_commgrpno_join"); // /board/list_by_commgrpno_join.jsp
     return mav;
   }
   
   /**
    * Grid 형태의 목록
    * http://localhost:9091/board/list_by_commgrpno_grid.do?commgrpno=1
    * 
    * @return
    */
   @RequestMapping(value = "/board/list_by_commgrpno_grid.do", method = RequestMethod.GET)
   public ModelAndView list_by_commgrpno_grid(int commgrpno) {
     
     ModelAndView mav = new ModelAndView();
    
     CommgrpVO commgrpVO = this.commgrpProc.read(commgrpno);
     mav.addObject("commgrpVO", commgrpVO);
     
     
     List<BoardVO> list = boardProc.list_by_commgrpno_grid(commgrpno);
     mav.addObject("list", list);

     // 테이블 이미지 기반, /webapp/board/list_by_commgrpno_grid.jsp
     mav.setViewName("/board/list_by_commgrpno_grid");

     return mav; // forward
   }
   
   /**
     * Grid 목록 + 페이징 지원
    * http://localhost:9091/board/list_by_commgrpno_grid_paging.do?commgrpno=1&now_page=1
    * 
    * @param commgrpno
    * @param now_page
    * @return
    */
   @RequestMapping(value = "/board/list_by_commgrpno_grid_paging.do", method = RequestMethod.GET)
   public ModelAndView list_by_commgrpno_grid_paging(@RequestParam(value = "commgrpno", defaultValue = "1") int commgrpno,
                                                       @RequestParam(value = "word", defaultValue = "") String word,
                                                       @RequestParam(defaultValue = "all") String search_option,
                                                       @RequestParam(value = "now_page", defaultValue = "1") int now_page,
                                                       HttpServletRequest request) {
     // System.out.println("-> list_by_commgrpno_grid_paging now_page: " + now_page);

     ModelAndView mav = new ModelAndView();

     // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
     HashMap<String, Object> map = new HashMap<String, Object>();
     map.put("commgrpno", commgrpno); // #{commgrpno}
     map.put("word", word); 
     map.put("search_option", search_option); // #{search_option}
     map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

     // 검색 목록
     List<BoardVO> list = boardProc.list_by_commgrpno_grid_paging(map);
     mav.addObject("list", list);
     
     // 조인 검색 목록
     List<Commgrp_BoardVO> list2 = this.boardProc.list_all_join();
     mav.addObject("list2", list2); // request.setAttribute("list", list);

     // 검색된 레코드 갯수
     int search_count = boardProc.search_count(map);
     mav.addObject("search_count", search_count);

     CommgrpVO commgrpVO = commgrpProc.read(commgrpno);
     mav.addObject("commgrpVO", commgrpVO);

     /*
      * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
      * 18 19 20 [다음]
      * @param list_file 목록 파일명
      * @param commgrpno 카테고리번호
      * @param search_count 검색(전체) 레코드수
      * @param now_page 현재 페이지
      * @param word 검색어
      * @return 페이징 생성 문자열
      */
     String paging2 = boardProc.pagingBox2("list_by_commgrpno_grid_paging.do", commgrpno, search_count, search_option, word, now_page);
     mav.addObject("paging2", paging2);
     mav.addObject("commgrpno", commgrpno);
     mav.addObject("word", word);
     mav.addObject("search_option", search_option);
     mav.addObject("now_page", now_page);

     mav.setViewName("/board/list_by_commgrpno_grid_paging");

     // -------------------------------------------------------------------------------
     // 게시글 등록전 로그인 폼 출력 관련 쿠기  
     // -------------------------------------------------------------------------------
     Cookie[] cookies = request.getCookies();
     Cookie cookie = null;

     String ck_id = ""; // id 저장
     String ck_id_save = ""; // id 저장 여부를 체크
     String ck_passwd = ""; // passwd 저장
     String ck_passwd_save = ""; // passwd 저장 여부를 체크

     if (cookies != null) {  // Cookie 변수가 있다면
       for (int i=0; i < cookies.length; i++){
         cookie = cookies[i]; // 쿠키 객체 추출
         
         if (cookie.getName().equals("ck_id")){
           ck_id = cookie.getValue();                                 // Cookie에 저장된 id
         }else if(cookie.getName().equals("ck_id_save")){
           ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
         }else if (cookie.getName().equals("ck_passwd")){
           ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
         }else if(cookie.getName().equals("ck_passwd_save")){
           ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
         }
       }
     }
     
     System.out.println("-> ck_id: " + ck_id);
     
     mav.addObject("ck_id", ck_id); 
     mav.addObject("ck_id_save", ck_id_save);
     mav.addObject("ck_passwd", ck_passwd);
     mav.addObject("ck_passwd_save", ck_passwd_save);
     // -------------------------------------------------------------------------------
     
     return mav;
   }
   
   /**
    * Grid 목록 + 검색 + 페이징 지원
   * http://localhost:9091/board/list_by_commgrpno_grid_search_paging.do?commgrpno=1&now_page=1
   * 
   * @param commgrpno
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/board/list_by_commgrpno_grid_search_paging.do", method = RequestMethod.GET)
  public ModelAndView list_by_commgrpno_grid_search_paging(@RequestParam(value = "commgrpno", defaultValue = "1") int commgrpno,
                                                      @RequestParam(value = "word", defaultValue = "") String word,
                                                      @RequestParam(defaultValue = "all") String search_option,
                                                      @RequestParam(value = "now_page", defaultValue = "1") int now_page,
                                                      HttpServletRequest request) {
    // System.out.println("-> list_by_commgrpno_grid_search_paging now_page: " + now_page);

    ModelAndView mav = new ModelAndView();

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("commgrpno", commgrpno); // #{commgrpno}
    map.put("word", word); // #{word}
    map.put("search_option", search_option); // #{search_option}
    map.put("now_page", now_page); // 페이지에 출력할 레코드의 범위를 산출하기위해 사용

    // 검색 목록
    List<BoardVO> list = boardProc.list_by_commgrpno_grid_search_paging(map);
    mav.addObject("list", list);
    
    // 조인 검색 목록
    List<Commgrp_BoardVO> list2 = this.boardProc.list_all_join();
    mav.addObject("list2", list2); // request.setAttribute("list", list);

    // 검색된 레코드 갯수
    int search_count = boardProc.search_count(map);
    mav.addObject("search_count", search_count);

    CommgrpVO commgrpVO = commgrpProc.read(commgrpno);
    mav.addObject("commgrpVO", commgrpVO);

    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param list_file 목록 파일명
     * @param commgrpno 카테고리번호
     * @param search_count 검색(전체) 레코드수
     * @param now_page 현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */
    String paging2 = boardProc.pagingBox2("list_by_commgrpno_grid_search_paging.do", commgrpno, search_count, search_option, word, now_page);
    mav.addObject("paging2", paging2);
    mav.addObject("commgrpno", commgrpno);
    mav.addObject("word", word);
    mav.addObject("search_option", search_option);
    mav.addObject("now_page", now_page);

    mav.setViewName("/board/list_by_commgrpno_grid_search_paging");

    // -------------------------------------------------------------------------------
    // 게시글 등록전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);

    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    return mav;
  }

  /**
   * 조회
   * http://localhost:9091/board/read.do
   * @return
   * @throws Exception 
   */
  @RequestMapping(value="/board/read.do", method=RequestMethod.GET )
  public ModelAndView read(HttpServletRequest request, int boardno,
                           @RequestParam(value = "now_page", defaultValue = "1") int now_page,
                           @RequestParam(value = "word", defaultValue = "") String word) throws Exception {
    ModelAndView mav = new ModelAndView();

    BoardVO boardVO = this.boardProc.read(boardno);
    mav.addObject("boardVO", boardVO); // request.setAttribute("boardVO", boardVO);
     
    CommgrpVO commgrpVO = this.commgrpProc.read(boardVO.getCommgrpno());
    mav.addObject("commgrpVO", commgrpVO);
    
    mav.addObject("now_page", now_page);

    // -------------------------------------------------------------------------------
    // 게시글 조회 전 로그인 폼 출력 관련 쿠기  
    // -------------------------------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) {  // Cookie 변수가 있다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
        }
      }
    }
    
    System.out.println("-> ck_id: " + ck_id);
    
    boardProc.bcnt_read(boardVO);
    
    mav.addObject("boardVO", boardVO);
    
    mav.addObject("ck_id", ck_id); 
    mav.addObject("ck_id_save", ck_id_save);
    mav.addObject("ck_passwd", ck_passwd);
    mav.addObject("ck_passwd_save", ck_passwd_save);
    // -------------------------------------------------------------------------------
    
    mav.setViewName("/board/read"); // /WEB-INF/views/board/read.jsp
        
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9091/board/update.do?boardno=1
   * 
   * @return
   */
  @RequestMapping(value = "/board/update.do", method = RequestMethod.GET)
  public ModelAndView update(int boardno) {
    ModelAndView mav = new ModelAndView();
    
    BoardVO boardVO = this.boardProc.read_update(boardno);
    CommgrpVO commgrpVO = this.commgrpProc.read(boardVO.getCommgrpno());
    
    mav.addObject("boardVO", boardVO);
    mav.addObject("commgrpVO", commgrpVO);
    
    mav.setViewName("/board/update"); // /WEB-INF/views/board/update.jsp

    return mav; // forward
  }

  /**
   * 수정 처리
   * http://localhost:9091/board/update.do?boardno=1
   * 
   * @return
   */
  @RequestMapping(value = "/board/update.do", method = RequestMethod.POST)
  public ModelAndView update(BoardVO boardVO, 
      @RequestParam(value = "now_page", defaultValue = "1") int now_page){
    ModelAndView mav = new ModelAndView();
   
    // -------------------------------------------------------------------
    // 파일 삭제 코드 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    BoardVO vo = boardProc.read(boardVO.getBoardno());
    //    System.out.println("contentsno: " + vo.getContentsno());
    //    System.out.println("file1: " + vo.getFile1());
        
    String file1 = vo.getFile1();
    String thumb = vo.getThumb();
    long bsize = 0;
    boolean sw = false;
    
    // 완성된 경로 F:/ai8/ws_frame/team2_v2sbm3c/src/main/resources/static/board/storage/
    String upDir =  System.getProperty("user.dir") + "/src/main/resources/static/board/storage/"; // 절대 경로

    sw = Tool.deleteFile(upDir, file1);  // Folder에서 1건의 파일 삭제
    sw = Tool.deleteFile(upDir, thumb);     // Folder에서 1건의 파일 삭제
    // System.out.println("sw: " + sw);
    // -------------------------------------------------------------------
    // 파일 삭제 종료 시작
    // -------------------------------------------------------------------
    
    // -------------------------------------------------------------------
    // 파일 전송 코드 시작
    // -------------------------------------------------------------------
    file1 = "";     // 원본 파일명, image

    // 기준 경로 확인
    String user_dir = System.getProperty("user.dir");
    System.out.println("--> User dir: " + user_dir);
    //  --> User dir: F:\ai8\ws_frame\resort_v1sbm3a
    
    // 파일 접근임으로 절대 경로 지정, static 지정
    // 완성된 경로 F:/ai8/ws_frame/resort_v1sbm3a/src/main/resources/static/board/storage
    upDir =  user_dir + "/src/main/resources/static/board/storage/"; // 절대 경로
    
    // 전송 파일이 없어서도 fnamesMF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF' 
    //           value='' placeholder="파일 선택">
    MultipartFile mf = boardVO.getFile1MF();
    
    file1 = mf.getOriginalFilename(); // 저장된 파일명
    bsize = mf.getSize();  // 파일 크기
    
    if (bsize > 0) { // 파일 크기 체크
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      file1 = Upload.saveFileSpring(mf, upDir); 
      
      if (Tool.isImage(file1)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
        thumb = Tool.preview(upDir, file1, 200, 150); 
      }
    }    
    
    boardVO.setFile1(file1);
    // boardVO.setFile1saved(file1saved);
    boardVO.setThumb(thumb);
    boardVO.setBsize(bsize);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------
    
    // Call By Reference: 메모리 공유, Hashcode 전달
    int cnt = this.boardProc.update(boardVO); // 수정 처리
    
    mav.addObject("commgrpno", boardVO.getCommgrpno());
    mav.addObject("boardno", boardVO.getBoardno());
    mav.addObject("now_page", now_page);
    
    mav.setViewName("redirect:/board/read.do"); 

    return mav; // forward
  }
  
  /**
   * 삭제 폼
   * @param boardno
   * @return
   */
  @RequestMapping(value="/board/delete.do", method=RequestMethod.GET )
  public ModelAndView delete(int boardno) { 
    ModelAndView mav = new  ModelAndView();
    
    // 삭제할 정보를 조회하여 확인
    BoardVO boardVO = this.boardProc.read(boardno);
    mav.addObject("boardVO", boardVO);     
    
    CommgrpVO commgrpVO = this.commgrpProc.read(boardVO.getCommgrpno());
    mav.addObject("commgrpVO",commgrpVO);
    
    mav.setViewName("/board/delete");  // board/delete.jsp
    
    return mav; 
  }
  
  /**
   * 삭제 처리 http://localhost:9091/contents/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/board/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(HttpServletRequest request,
                                             int commgrpno,
                                             int boardno,
                                             String word,
                                             int now_page ) {
    System.out.println("삭제 처리 POST");
    ModelAndView mav = new ModelAndView();

    // -------------------------------------------------------------------
    // 파일 삭제 코드 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    BoardVO vo = boardProc.read(boardno);
//    System.out.println("boardno: " + vo.getContentsno());
//    System.out.println("file1: " + vo.getFile1());
    
    String file1 = vo.getFile1();
    String thumb = vo.getThumb();
    long bsize = 0;
    boolean sw = false;
    
    // 완성된 경로 F:/ai8/ws_frame/resort_v1sbm3a/src/main/resources/static/contents/storage/
    String upDir =  System.getProperty("user.dir") + "/src/main/resources/static/board/storage/"; // 절대 경로

    sw = Tool.deleteFile(upDir, file1);  // Folder에서 1건의 파일 삭제
    sw = Tool.deleteFile(upDir, thumb);     // Folder에서 1건의 파일 삭제
    // System.out.println("sw: " + sw);
    // -------------------------------------------------------------------
    // 파일 삭제 종료 시작
    // -------------------------------------------------------------------
    
    int cnt = this.boardProc.delete(boardno); // DBMS 삭제

    if (cnt == 1) {
      // -------------------------------------------------------------------------------------
      // 마지막 페이지의 레코드 삭제시의 페이지 번호 -1 처리
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("commgrpno", commgrpno);
      map.put("word", word);
      // 10번째 레코드를 삭제후
      // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
      // 페이지수를 4 -> 3으로 감소 시켜야함.
      
      // Boards에 변수 2개 선언함.
      // List 목록 페이지당 출력할 레코드 갯수 : RECORD_PER_PAGE
      // Grid 목록 페이지당 출력할 레코드 갯수 : RECORD_PER_PAGE2
      if ((boardProc.search_count(map) % Boards.RECORD_PER_PAGE == 0) && 
          (boardProc.search_count(map) % Boards.RECORD_PER_PAGE2 == 0)) {
        now_page = now_page - 1;
        if (now_page < 1) {
          now_page = 1; // 시작 페이지
        }
      }
      // -------------------------------------------------------------------------------------
    }
    // System.out.println("-> delete now_page: " + now_page);
    mav.addObject("commgrpno", commgrpno);
    mav.addObject("word", word);
    mav.addObject("now_page", now_page);
    
    // grid에도 삭제 변경 사항 전달함.
    mav.setViewName("/board/list_by_commgrpno_grid_paging"); 
    if(commgrpno == 1) {
       mav.setViewName("redirect:/board/list_by_commgrpno_search_paging.do"); 
    }else if (commgrpno == 2){
      mav.setViewName("redirect:/board/list_by_commgrpno_notice_search_paging.do"); 
    }else {
      mav.setViewName("redirect:/board/list_by_commgrpno_qua_search_paging.do"); 
    }
   
  
    

    return mav; // forward
  }   

 
}
