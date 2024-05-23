package dev.mvc.foodrecom;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Setter @Getter
public class FoodrecomVO {

    /** 운동 목표 */
    private int foodrecomno=0;
    
    private int mhno = 0;
    
    private int goalsno = 0;
    

    private String frecom;

   

    
    /** 등록일 */
    private String rdate;
    


  




}
