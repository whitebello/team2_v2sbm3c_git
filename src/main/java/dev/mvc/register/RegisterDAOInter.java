package dev.mvc.register;

public interface RegisterDAOInter {
  /**
   * 회원 등록
   * @param RegisterVO
   * @return 등록된 갯수
   */
  public int create(RegisterVO registerVO);

}
