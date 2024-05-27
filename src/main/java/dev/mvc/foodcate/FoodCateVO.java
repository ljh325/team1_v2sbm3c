package dev.mvc.foodcate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class FoodCateVO {

    /** 운동 카테고리 번호(pk) */
    private int foodcateno=0;
    
    
    /** 식단 카테고리 참조 이미지 */
    private String image;

    /**카테고리 등록/수정한 관리자 번호 */
    private int adminsno;
    
    /**식품영양정보 DB의 식품코드 참조*/
    private int foodcode;
    
   
 
    // 파일 업로드 관련
    // -----------------------------------------------------------------------------------
    /**
    이미지 파일
   
    */
    private MultipartFile fileMF = null; 
    /** 메인 이미지 크기 단위, 파일 크기 */
    private String size_label = "";
    /** 메인 이미지 */
    private String fname = "";
    /** 실제 저장된 메인 이미지 */
    private String fupname = "";
    /** 메인 이미지 preview */
    private String thumb = "";
    /** 메인 이미지 크기 */
    private long fsize = 0;

   

    
    /** 등록일 */
    private String fdate;
}
    