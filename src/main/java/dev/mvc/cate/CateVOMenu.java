package dev.mvc.cate;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CateVOMenu {
  /** 중분류명 */
  private String name;

  /** 소분류명 */
  ArrayList<CateVO> list_namesub;

}
