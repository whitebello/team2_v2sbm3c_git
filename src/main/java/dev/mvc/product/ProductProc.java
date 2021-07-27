package dev.mvc.product;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.product.Product;
import dev.mvc.product.ProductVO;
import dev.mvc.tool.Tool;

@Component("dev.mvc.product.ProductProc")
  public class ProductProc implements ProductProcInter {
    @Autowired
    private ProductDAOInter productDAO;

    @Override
    public int create(ProductVO productVO) {
      int cnt=this.productDAO.create(productVO);
      return cnt;
    }
    
    @Override
    public List<ProductVO> list_by_bookno(int bookno) {
      List<ProductVO> list = this.productDAO.list_by_bookno(bookno);
      return list;
    }
    
    @Override
    public List<ProductVO> list_by_bookno_search(HashMap<String, Object> hashMap) {
      List<ProductVO> list = productDAO.list_by_bookno_search(hashMap);
      
      for (ProductVO productVO : list) { // 내용이 160자 이상이면 160자만 선택
        String content = productVO.getContent();
        if (content.length() > 100) {
          content = content.substring(0, 100) + "...";
          productVO.setContent(content);
        }
      }
      
      return list;
    }

    @Override
    public int search_count(HashMap<String, Object> hashMap) {
      int count = productDAO.search_count(hashMap);
      return count;
    }
    
    /** 
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
     * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
     *
     * @param list_file 목록 파일명 
     * @param bookno 카테고리번호 
     * @param search_count 검색(전체) 레코드수 
     * @param now_page     현재 페이지
     * @param word 검색어
     * @return 페이징 생성 문자열
     */ 
    @Override
    public String pagingBox(String list_file, int bookno, int search_count, int now_page, String word){ 
      int total_page = (int)(Math.ceil((double)search_count/Product.RECORD_PER_PAGE)); // 전체 페이지 수 
      int total_grp = (int)(Math.ceil((double)total_page/Product.PAGE_PER_BLOCK)); // 전체 그룹  수
      int now_grp = (int)(Math.ceil((double)now_page/Product.PAGE_PER_BLOCK));  // 현재 그룹 번호
      
      // 1 group: 1 2 3
      // 2 group: 4 5 6
      // 3 group: 7 8 9
      int start_page = ((now_grp - 1) * Product.PAGE_PER_BLOCK) + 1; // 특정 그룹의 시작  페이지  
      int end_page = (now_grp * Product.PAGE_PER_BLOCK);               // 특정 그룹의 마지막 페이지   
       
      StringBuffer str = new StringBuffer(); 
       
      str.append("<style type='text/css'>"); 
      str.append("  #paging {text-align: center; margin-top: 5px; font-size: 1em;}"); 
      str.append("  #paging A:link {text-decoration:none; color:black; font-size: 1em;}"); 
      str.append("  #paging A:hover{text-decoration:none; background-color: #FFFFFF; color:black; font-size: 1em;}"); 
      str.append("  #paging A:visited {text-decoration:none;color:black; font-size: 1em;}"); 
      str.append("  .span_box_1{"); 
      str.append("    text-align: center;");    
      str.append("    font-size: 1em;"); 
      str.append("    border: 1px;"); 
      str.append("    border-style: solid;"); 
      str.append("    border-color: #cccccc;"); 
      str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("  }"); 
      str.append("  .span_box_2{"); 
      str.append("    text-align: center;");    
      str.append("    background-color: #668db4;"); 
      str.append("    color: #FFFFFF;"); 
      str.append("    font-size: 1em;"); 
      str.append("    border: 1px;"); 
      str.append("    border-style: solid;"); 
      str.append("    border-color: #cccccc;"); 
      str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/"); 
      str.append("  }"); 
      str.append("</style>"); 
      str.append("<DIV id='paging'>"); 
//      str.append("현재 페이지: " + nowPage + " / " + totalPage + "  "); 
   
      // 이전 10개 페이지로 이동
      // now_grp: 1 (1 ~ 10 page)
      // now_grp: 2 (11 ~ 20 page)
      // now_grp: 3 (21 ~ 30 page) 
      // 현재 2그룹일 경우: (2 - 1) * 10 = 1그룹의 마지막 페이지 10
      // 현재 3그룹일 경우: (3 - 1) * 10 = 2그룹의 마지막 페이지 20
      int _now_page = (now_grp - 1) * Product.PAGE_PER_BLOCK;  
      if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 이전 그룹으로 갈수 있는 링크 생성 
        str.append("<span class='span_box_1'><A href='"+list_file+"?&word="+word+"&now_page="+_now_page+"&bookno="+bookno+"'>이전</A></span>"); 
      } 
   
      // 중앙의 페이지 목록
      for(int i=start_page; i<=end_page; i++){ 
        if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이 출력 종료
          break; 
        } 
    
        if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
          str.append("<span class='span_box_2'>"+i+"</span>"); // 현재 페이지, 강조 
        }else{
          // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
          str.append("<span class='span_box_1'><A href='"+list_file+"?word="+word+"&now_page="+i+"&bookno="+bookno+"'>"+i+"</A></span>");   
        } 
      } 
   
      // 10개 다음 페이지로 이동
      // nowGrp: 1 (1 ~ 10 page),  nowGrp: 2 (11 ~ 20 page),  nowGrp: 3 (21 ~ 30 page) 
      // 현재 1그룹일 경우: (1 * 10) + 1 = 2그룹의 시작페이지 11
      // 현재 2그룹일 경우: (2 * 10) + 1 = 3그룹의 시작페이지 21
      _now_page = (now_grp * Product.PAGE_PER_BLOCK)+1;  
      if (now_grp < total_grp){ 
        str.append("<span class='span_box_1'><A href='"+list_file+"?&word="+word+"&now_page="+_now_page+"&bookno="+bookno+"'>다음</A></span>"); 
      } 
      str.append("</DIV>"); 
       
      return str.toString(); 
    }
 
    @Override
    public List<ProductVO> list_by_bookno_search_paging(HashMap<String, Object> map) {
      /* 
      페이지에서 출력할 시작 레코드 번호 계산 기준값, nowPage는 1부터 시작
      1 페이지 시작 rownum: now_page = 1, (1 - 1) * 10 --> 0 
      2 페이지 시작 rownum: now_page = 2, (2 - 1) * 10 --> 10
      3 페이지 시작 rownum: now_page = 3, (3 - 1) * 10 --> 20
      */
      int begin_of_page = ((Integer)map.get("now_page") - 1) * Product.RECORD_PER_PAGE;
     
      // 시작 rownum 결정
      // 1 페이지 = 0 + 1, 2 페이지 = 10 + 1, 3 페이지 = 20 + 1 
      int start_num = begin_of_page + 1;
      
      //  종료 rownum
      // 1 페이지 = 0 + 10, 2 페이지 = 0 + 20, 3 페이지 = 0 + 30
      int end_num = begin_of_page + Product.RECORD_PER_PAGE;   
      /*
      1 페이지: WHERE r >= 1 AND r <= 10
      2 페이지: WHERE r >= 11 AND r <= 20
      3 페이지: WHERE r >= 21 AND r <= 30
      */
      map.put("start_num", start_num);
      map.put("end_num", end_num);
     
      List<ProductVO> list = this.productDAO.list_by_bookno_search_paging(map);
      
      for (ProductVO productVO : list) { // 내용이 160자 이상이면 160자만 선택
        String content = productVO.getContent();
        if (content.length() > 160) {
          content = content.substring(0, 160) + "...";
          productVO.setContent(content);
        }
        
        String title = Tool.convertChar(productVO.getTitle());
        productVO.setTitle(title);
        
        // CKEditor 에서는 주석 처리 해야함----------------------.
        //content = Tool.convertChar(content);
        //productVO.setContent(content);
      }
      
      return list;
    }
    
    /**
     * 조회
     */
    @Override
    public ProductVO read(int productno) {
      ProductVO productVO = this.productDAO.read(productno);
      
      String title = productVO.getTitle();
      String content = productVO.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      //productVO.setTitle(title);
      //productVO.setContent(content);  
      
      long size1 = productVO.getSize1();
      productVO.setSize1_label(Tool.unit(size1));
      
      return productVO;
    }
    
    @Override
    public int product_update(ProductVO productVO) {
      int cnt = this.productDAO.product_update(productVO);
      return cnt;
    }
    
    @Override
    public ProductVO read_update_text(int productno) {
      ProductVO productVO = this.productDAO.read(productno);
      return productVO;
    }
    @Override
    public int update_text(ProductVO productVO) {
      int cnt = this.productDAO.update_text(productVO);
      return cnt;
    }
    
    @Override
    public int update_file(ProductVO productVO) {
      int cnt = this.productDAO.update_file(productVO);
      return cnt;
    }
    
    @Override
    public int delete(int productno) {
      int cnt = this.productDAO.delete(productno);
      System.out.println("productno" + productno);
      return cnt;
    }
    
    @Override
    public int update_stateno(int productno) {
      int cnt = this.productDAO.update_stateno(productno);
      return cnt;
    }

    @Override
    public int get_stateno(int productno) {
      int cnt = this.productDAO.get_stateno(productno);
      return cnt;
    }

    @Override
    public List<ProductVO> load() {
      List<ProductVO> list =this.productDAO.load();
      return list;
    }
    
}