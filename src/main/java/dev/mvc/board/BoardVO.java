package dev.mvc.board;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {
  
  /**
    boardno                           NUMBER(10)     NOT NULL    PRIMARY KEY,
    memberno                          NUMBER(10)     NULL ,
    commgrpno                         NUMBER(10)     NULL ,
    title                             VARCHAR2(100)    NOT NULL,
    bcon                              CLOB     NOT NULL,
    bcnt                              NUMBER(10)     DEFAULT 0     NOT NULL,
    breplycnt                         NUMBER(10)     DEFAULT 0     NOT NULL,
    brdate                            DATE     NOT NULL,
    budate                            DATE     NOT NULL,
    word                              VARCHAR2(300)    NOT NULL,
    file1                             VARCHAR2(100)    NULL ,
    thumb                             VARCHAR2(100)    NULL ,
    bsize                             NUMBER(10)     DEFAULT 0     NOT NULL,
    price                             NUMBER(10)     DEFAULT 0     NOT NULL,
   * 
   */
  
  /** 게시판 번호 */
  private int boardno;
  /** 회원 번호 */
  private int memberno;
  /** 커뮤니티 그룹 번호 */
  private int commgrpno;
  /** 제목 */
  private String title;
  /** 내용 */
  private String bcon;
  /** 조회수 */
  private int bcnt;
  /** 댓글수 */
  private int breplycnt;
  /** 등록일자 */
  private String brdate = "";
  /** 수정일자 */
  private String budate = "";
  /** 검색어 */
  private String word = "";
  /** 메인 이미지 */
  private String file1 = "";  
  /** 메인 이미지 preview */
  private String thumb = "";
  /** 메인 이미지 크기 */
  private long bsize;
  /** 작성자 */
  private String writer;
  
  /** 
  이미지 MultipartFile 
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
                   value='' placeholder="파일 선택">
  */
  private MultipartFile file1MF;
  /** 파일 크기 단위 출력 */
  private String size1_label;
  
  
  public int getBoardno() {
    return boardno;
  }
  public void setBoardno(int boardno) {
    this.boardno = boardno;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public int getCommgrpno() {
    return commgrpno;
  }
  public void setCommgrpno(int commgrpno) {
    this.commgrpno = commgrpno;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getBcon() {
    return bcon;
  }
  public void setBcon(String bcon) {
    this.bcon = bcon;
  }
  public int getBcnt() {
    return bcnt;
  }
  public void setBcnt(int bcnt) {
    this.bcnt = bcnt;
  }
  public int getBreplycnt() {
    return breplycnt;
  }
  public void setBreplycnt(int breplycnt) {
    this.breplycnt = breplycnt;
  }
  public String getBrdate() {
    return brdate;
  }
  public void setBrdate(String brdate) {
    this.brdate = brdate;
  }
  public String getBudate() {
    return budate;
  }
  public void setBudate(String budate) {
    this.budate = budate;
  }
  public String getWord() {
    return word;
  }
  public void setWord(String word) {
    this.word = word;
  }
  public String getFile1() {
    return file1;
  }
  public void setFile1(String file1) {
    this.file1 = file1;
  }
  public String getThumb() {
    return thumb;
  }
  public void setThumb(String thumb) {
    this.thumb = thumb;
  }
  public long getBsize() {
    return bsize;
  }
  public void setBsize(long bsize) {
    this.bsize = bsize;
  }
  public MultipartFile getFile1MF() {
    return file1MF;
  }
  public void setFile1MF(MultipartFile file1mf) {
    file1MF = file1mf;
  }
  public String getWriter() {
    return writer;
  }
  public void setWriter(String writer) {
    this.writer = writer;
  }
  public String getSize1_label() {
    return size1_label;
  }
  public void setSize1_label(String size1_label) {
    this.size1_label = size1_label;
  }
  
  
  
}
