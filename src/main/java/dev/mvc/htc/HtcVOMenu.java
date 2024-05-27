package dev.mvc.htc;

import java.util.ArrayList;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Setter @Getter
public class HtcVOMenu {
  /** 중분류명 */
  private String name;
  
  /** 소분류명 */
  ArrayList<HtcVO> list_namesub;
  
}


