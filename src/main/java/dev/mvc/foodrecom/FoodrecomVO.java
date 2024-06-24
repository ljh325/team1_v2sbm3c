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
    
    /**건강정보 **/
    private String kg;
    private String ckg;

    private String cm;
    private String muscle;
    
    /**운동 목표 **/
    private String g_kg;
    private String g_ckg;

    private String g_cm;
    private String g_muscle;


  




}
