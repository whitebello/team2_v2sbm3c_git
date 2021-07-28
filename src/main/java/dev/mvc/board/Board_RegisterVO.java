package dev.mvc.board;

public class Board_RegisterVO {

  /** 게시판 번호 */
  private int b_boardno;
  /** 회원 번호 */
  private int b_memberno;
  /** 커뮤니티 그룹 번호 */
  private int b_commgrpno;
  /** 전공도서 번호 */
  private int b_bookno;
  /** 제목 */
  private String b_title;
  /** 내용 */
  private String b_bcon;
  /** 등록일자 */
  private String b_brdate = "";
  /** 수정일자 */
  private String b_budate = "";
  /** 검색어 */
  private String b_word = "";
  /** 메인 이미지 */
  private String b_file1 = "";  
  /** 메인 이미지 preview */
  private String b_thumb = "";
  /** 메인 이미지 크기 */
  private long b_bsize;
  /** 조회수 */
  private int b_bcnt;
  /** 게시판 작성자 */
  private String b_writer;
  
  /** 회원 번호 */
  private int memberno;  
  /** 아이디 */
  private String id = "";
  /** 비밀번호 */
  private String passwd = "";
  /** 회원이름 */
  private String mname = "";
  /** 전화번호 */
  private String tel = "";
  /** 우편번호 */
  private String zipcode = "";
  /** 이메일 */
  private String email = "";
  /** SNS */
  private String oauth = "";
  /** 주소1 */
  private String address1 = "";
  /** 주소2 */
  private String address2 = "";
  /** 가입일 */
  private String mdate = "";
  /** 회원등급 */
  private int grade = 0;
  
  public int getB_boardno() {
    return b_boardno;
  }
  public void setB_boardno(int b_boardno) {
    this.b_boardno = b_boardno;
  }
  public int getB_memberno() {
    return b_memberno;
  }
  public void setB_memberno(int b_memberno) {
    this.b_memberno = b_memberno;
  }
  public int getB_commgrpno() {
    return b_commgrpno;
  }
  public void setB_commgrpno(int b_commgrpno) {
    this.b_commgrpno = b_commgrpno;
  }
  public int getB_bookno() {
    return b_bookno;
  }
  public void setB_bookno(int b_bookno) {
    this.b_bookno = b_bookno;
  }
  public String getB_title() {
    return b_title;
  }
  public void setB_title(String b_title) {
    this.b_title = b_title;
  }
  public String getB_bcon() {
    return b_bcon;
  }
  public void setB_bcon(String b_bcon) {
    this.b_bcon = b_bcon;
  }
  public String getB_brdate() {
    return b_brdate;
  }
  public void setB_brdate(String b_brdate) {
    this.b_brdate = b_brdate;
  }
  public String getB_budate() {
    return b_budate;
  }
  public void setB_budate(String b_budate) {
    this.b_budate = b_budate;
  }
  public String getB_word() {
    return b_word;
  }
  public void setB_word(String b_word) {
    this.b_word = b_word;
  }
  public String getB_file1() {
    return b_file1;
  }
  public void setB_file1(String b_file1) {
    this.b_file1 = b_file1;
  }
  public String getB_thumb() {
    return b_thumb;
  }
  public void setB_thumb(String b_thumb) {
    this.b_thumb = b_thumb;
  }
  public long getB_bsize() {
    return b_bsize;
  }
  public void setB_bsize(long b_bsize) {
    this.b_bsize = b_bsize;
  }
  public int getB_bcnt() {
    return b_bcnt;
  }
  public void setB_bcnt(int b_bcnt) {
    this.b_bcnt = b_bcnt;
  }
  public String getB_writer() {
    return b_writer;
  }
  public void setB_writer(String b_writer) {
    this.b_writer = b_writer;
  }
  public int getMemberno() {
    return memberno;
  }
  public void setMemberno(int memberno) {
    this.memberno = memberno;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  public String getMname() {
    return mname;
  }
  public void setMname(String mname) {
    this.mname = mname;
  }
  public String getTel() {
    return tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }
  public String getZipcode() {
    return zipcode;
  }
  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getOauth() {
    return oauth;
  }
  public void setOauth(String oauth) {
    this.oauth = oauth;
  }
  public String getAddress1() {
    return address1;
  }
  public void setAddress1(String address1) {
    this.address1 = address1;
  }
  public String getAddress2() {
    return address2;
  }
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  public String getMdate() {
    return mdate;
  }
  public void setMdate(String mdate) {
    this.mdate = mdate;
  }
  public int getGrade() {
    return grade;
  }
  public void setGrade(int grade) {
    this.grade = grade;
  }
  
  
}
