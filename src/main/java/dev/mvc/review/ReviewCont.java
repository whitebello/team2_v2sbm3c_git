package dev.mvc.review;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.board.BoardVO;
import dev.mvc.commgrp.CommgrpVO;
import dev.mvc.product.ProductProcInter;
import dev.mvc.product.ProductVO;
import dev.mvc.register.RegisterProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class ReviewCont {
  
  @Autowired
  @Qualifier("dev.mvc.product.ProductProc")
  private ProductProcInter productProc=null;
  
  @Autowired
  @Qualifier("dev.mvc.register.RegisterProc")
  private RegisterProcInter registerProc=null;
  
  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")
  private ReviewProcInter reviewProc;
  
  public ReviewCont() {
    System.out.println("-> ReviewCont created.");
  }
  
  
  /**
   * 새로고침 방지
   * @return
   */
  @RequestMapping(value="/review/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  
  /**
   * 등록 폼
   * @return
   */
  @RequestMapping(value="/review/create.do", method=RequestMethod.GET )
  public ModelAndView create(HttpServletRequest request, int productno) {
    ModelAndView mav = new ModelAndView();
    
    ProductVO productVO = this.productProc.read(productno);
    mav.addObject("productVO", productVO);
    
    mav.setViewName("/review/create"); // webapp/WEB-INF/views/bookgrp/create.jsp
    return mav; // forward
  }
  
  /**
   * 등록 처리
   * @param reviewVO
   * @return
   */
  @RequestMapping(value="/review/create.do", method=RequestMethod.POST )
  public ModelAndView create(HttpServletRequest request, ReviewVO reviewVO) { 
   
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
    // 완성된 경로 /src/main/resources/static/reviews/storage
    String upDir =  user_dir + "/src/main/resources/static/review/storage/"; // 절대 경로
    
    // 전송 파일이 없어서도 fnamesMF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF' 
    //           value='' placeholder="파일 선택">
    MultipartFile mf = reviewVO.getFile1MF();
    
    file1 = mf.getOriginalFilename(); // 저장된 파일명
    long rsize = mf.getSize();  // 파일 크기
    
    if (rsize > 0) { // 파일 크기 체크
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      file1 = Upload.saveFileSpring(mf, upDir); 
      
      if (Tool.isImage(file1)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
        thumb = Tool.preview(upDir, file1, 200, 150); 
      }
    }    
    
    reviewVO.setFile1(file1);
    // reviewVO.setFile1saved(file1saved);
    reviewVO.setThumb(thumb);
    reviewVO.setRsize(rsize);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------
    
    // Call By Reference: 메모리 공유, Hashcode 전달
    int cnt = this.reviewProc.create(reviewVO); // 등록 처리
    
    mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
    mav.addObject("productno", reviewVO.getProductno());  
    mav.addObject("memberno", reviewVO.getMemberno());  
    
    mav.addObject("url", "/review/create_msg"); 
    
    
    mav.setViewName("redirect:/review/msg.do"); 

    return mav; // forward
  }
  
  /**
   * 관리자 기반 리뷰 전체 목록
   * http://localhost:9091/review/list.do
   * @return
   */
  @RequestMapping(value="/review/list.do", method=RequestMethod.GET )
  public ModelAndView list() {
    ModelAndView mav = new ModelAndView();
    
    // 등록 순서별 출력    
    // List<bookgrpVO> list = this.bookgrpProc.list_bookgrpno_asc();
    
    // 출력 순서별 출력
    List<ReviewVO> list = this.reviewProc.list_reviewno_asc();
    mav.addObject("list", list); // request.setAttribute("list", list);
    
    List<Product_ReviewVO> list2 = this.reviewProc.list_all_join();
    mav.addObject("list2", list2);
    
    mav.setViewName("/review/list"); // /WEB-INF/views/bookgrp/list_ajax.jsp
    return mav;
  }
 
  /**
   * 상품별 리뷰 목록
   * @param reviewVO
   * @return
   */
  @RequestMapping(value="/review/list_by_productno.do", method=RequestMethod.GET )
  public ModelAndView list_by_productno(@RequestParam(value = "productno", defaultValue = "1") int productno, 
                                        HttpServletRequest request) { 
    ModelAndView mav = new ModelAndView();
    

    // 숫자와 문자열 타입을 저장해야함으로 Obejct 사용
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("productno", productno); // #{productno}
    
    List<ReviewVO> list = this.reviewProc.list_by_productno(productno);
    mav.addObject("list", list);
     
    // 조인 목록
    List<Product_ReviewVO> list2 = this.reviewProc.list_all_join();
    mav.addObject("list2", list2); // request.setAttribute("list", list);
     
    ProductVO productVO = this.productProc.read(productno);
    mav.addObject("productVO", productVO);
    
    mav.setViewName("/review/list_by_productno"); // webapp/WEB-INF/views/bookgrp/create.jsp
   
    return mav; // forward
  }
  
  /**
   * 해당 멤버의 리뷰 목록
   * @param reviewVO
   * @return
   */
  @RequestMapping(value="/review/list_by_memberno.do", method=RequestMethod.GET )
  public ModelAndView list_by_memberno(HttpSession session) { 
    ModelAndView mav = new ModelAndView();
    
    if(session.getAttribute("memberno") != null) {
      int memberno = (int)session.getAttribute("memberno");

      List<ReviewVO> list = this.reviewProc.list_by_memberno(memberno);
      mav.addObject("list", list);
  
      // 조인 목록
      List<Product_ReviewVO> list2 = this.reviewProc.list_all_join();
      mav.addObject("list2", list2); // request.setAttribute("list", list);
  
      mav.setViewName("/review/list_by_memberno"); // webapp/WEB-INF/views/review/list_by_memberno.jsp
    } else {
      // System.out.println("로그인 페이지 이동");
      mav.addObject("return_url", "/review/list_by_memberno.do"); // 로그인 후 이동할 주소
      mav.setViewName("redirect:/register/login.do");
    }
    return mav; // forward
  }
  
  /**
   * Product + Review join 연결 목록
   * http://localhost:9091/review/list_all_join.do 
   * @return
   */
  @RequestMapping(value="/review/list_all_join.do", method=RequestMethod.GET )
  public ModelAndView list_all_join() {
    ModelAndView mav = new ModelAndView();
    
    List<Product_ReviewVO> list = this.reviewProc.list_all_join();
    mav.addObject("list", list); // request.setAttribute("list", list);

    mav.setViewName("/review/list_all_join"); // /review/list_all_join.jsp
    return mav;
  }
  
  /**
   * Product + Review productno join 연결 목록
   * http://localhost:9091/review/list_by_productno_join.do 
   * @return
   */
  @RequestMapping(value="/review/list_by_productno_join.do", method=RequestMethod.GET )
  public ModelAndView list_by_productno_join(@RequestParam(value = "productno", defaultValue = "1") int productno, 
                                             HttpServletRequest request) {
    ModelAndView mav = new ModelAndView();
    
    HashMap<String, Object> map = new HashMap<String, Object>();

    map.put("productno", productno); 
        
    List<Product_ReviewVO> list = this.reviewProc.list_by_productno_join(map);
    
    mav.addObject("list", list); // request.setAttribute("list", list);

    mav.setViewName("/review/list_by_productno_join"); 
    return mav;
  }
  
  /**
   * 조회
   * @return
   */
  @RequestMapping(value="/review/read.do", method=RequestMethod.GET )
  public ModelAndView read(int reviewno) throws Exception{
    ModelAndView mav = new ModelAndView();

    ReviewVO reviewVO = this.reviewProc.read(reviewno);
    reviewProc.rcnt_read(reviewVO);

    mav.addObject("reviewVO", reviewVO); // request.setAttribute("contentsVO", contentsVO);

    ProductVO productVO = this.productProc.read(reviewVO.getProductno());
    mav.addObject("productVO", productVO);
    
    mav.setViewName("/review/read"); // /WEB-INF/views/review/read.jsp
        
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9091/review/update.do?reviewno=1
   * 
   * @return
   */
  @RequestMapping(value = "/review/update.do", method = RequestMethod.GET)
  public ModelAndView update(int reviewno) {
    ModelAndView mav = new ModelAndView();
    
    ReviewVO reviewVO = this.reviewProc.read_update(reviewno);
    ProductVO productVO = this.productProc.read(reviewVO.getProductno());
    
    mav.addObject("reviewVO", reviewVO);
    mav.addObject("productVO", productVO);
    
    mav.setViewName("/review/update"); // /WEB-INF/views/review/update.jsp

    return mav; // forward
  }

  /**
   * 수정 처리
   * http://localhost:9091/review/update.do?reviewno=1
   * 
   * @return
   */
  @RequestMapping(value = "/review/update.do", method = RequestMethod.POST)
  public ModelAndView update(ReviewVO reviewVO, 
      @RequestParam(value = "now_page", defaultValue = "1") int now_page){
    ModelAndView mav = new ModelAndView();
   
    // -------------------------------------------------------------------
    // 파일 삭제 코드 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    ReviewVO vo = reviewProc.read(reviewVO.getReviewno());
    
    String file1 = vo.getFile1();
    String thumb = vo.getThumb();
    long rsize = 0;
    boolean sw = false;
    
    // 완성된 경로 F:/ai8/ws_frame/team2_v2sbm3c/src/main/resources/static/review/storage/
    String upDir =  System.getProperty("user.dir") + "/src/main/resources/static/review/storage/"; // 절대 경로

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
    // 완성된 경로 F:/ai8/ws_frame/resort_v1sbm3a/src/main/resources/static/review/storage
    upDir =  user_dir + "/src/main/resources/static/review/storage/"; // 절대 경로
    
    // 전송 파일이 없어서도 fnamesMF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF' 
    //           value='' placeholder="파일 선택">
    MultipartFile mf = reviewVO.getFile1MF();
    
    file1 = mf.getOriginalFilename(); // 저장된 파일명
    rsize = mf.getSize();  // 파일 크기
    
    if (rsize > 0) { // 파일 크기 체크
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      file1 = Upload.saveFileSpring(mf, upDir); 
      
      if (Tool.isImage(file1)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
        thumb = Tool.preview(upDir, file1, 200, 150); 
      }
    }    
    
    reviewVO.setFile1(file1);
    // reviewVO.setFile1saved(file1saved);
    reviewVO.setThumb(thumb);
    reviewVO.setRsize(rsize);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------
    
    // Call By Reference: 메모리 공유, Hashcode 전달
    int cnt = this.reviewProc.update(reviewVO); // 수정 처리
    
    mav.addObject("reviewno", reviewVO.getReviewno());
    
    mav.setViewName("redirect:/review/read.do"); 

    return mav; // forward
  }
  
  /**
   * 삭제 폼
   * @param reviewno
   * @return
   */
  @RequestMapping(value="/review/delete.do", method=RequestMethod.GET )
  public ModelAndView delete(int reviewno) { 
    ModelAndView mav = new  ModelAndView();
    
    // 삭제할 정보를 조회하여 확인
    ReviewVO reviewVO = this.reviewProc.read(reviewno);
    mav.addObject("reviewVO", reviewVO);     
    
    ProductVO productVO = this.productProc.read(reviewVO.getProductno());
    mav.addObject("productVO",productVO);
    
    mav.setViewName("/review/delete");  // review/delete.jsp
    
    return mav; 
  }
  
  /**
   * 삭제 처리 
   * http://localhost:9091/review/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/review/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(HttpServletRequest request,
                                             int productno,
                                             int reviewno) {
    // System.out.println("삭제 처리 POST");
    ModelAndView mav = new ModelAndView();

    // -------------------------------------------------------------------
    // 파일 삭제 코드 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    ReviewVO vo = reviewProc.read(reviewno);
    
    String file1 = vo.getFile1();
    String thumb = vo.getThumb();
    long rsize = 0;
    boolean sw = false;
    
    // 완성된 경로 F:/ai8/ws_frame/resort_v1sbm3a/src/main/resources/static/contents/storage/
    String upDir =  System.getProperty("user.dir") + "/src/main/resources/static/review/storage/"; // 절대 경로

    sw = Tool.deleteFile(upDir, file1);  // Folder에서 1건의 파일 삭제
    sw = Tool.deleteFile(upDir, thumb);     // Folder에서 1건의 파일 삭제
    // System.out.println("sw: " + sw);
    // -------------------------------------------------------------------
    // 파일 삭제 종료 시작
    // -------------------------------------------------------------------
    
    int cnt = this.reviewProc.delete(reviewno); // DBMS 삭제

    
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("productno", productno);
    
    mav.addObject("productno", productno);
    
    // grid에도 삭제 변경 사항 전달함.
    mav.setViewName("/review/list_by_commgrpno_grid_paging"); 
    mav.setViewName("redirect:/review/list_by_productno.do"); 
   
    return mav; // forward
  }   


}
