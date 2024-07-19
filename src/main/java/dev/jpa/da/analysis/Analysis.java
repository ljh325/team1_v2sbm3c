package dev.jpa.da.analysis;


import groovy.transform.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@ToString
public class Analysis {

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analysis_seq")
  @SequenceGenerator(name="analysis_seq", sequenceName="ANALYSIS_SEQ", allocationSize=1)
    private Long analysisno;
    
    /** 분석된 데이터 고유 분류 코드 */
    private int code=1;
    
    /** 분석 제목 */
    private String title;
    
    /** 분석 내용 */
    @Column(columnDefinition = "CLOB")
    private String content;

    /** 분류, Chart 종류  */
    private int chart;
    
    /** 챠트 데이터 */
    @Column(length=4000)
    private String data;
    
    /** X label */
    private String xlabel;
    
    /** Y label */
    private String ylabel;
    
    /** 범례 */
    private String legend;
    
    /** 조회수 */
    private int cnt;

    /** 등록 날짜, Timestamp는 검색이 너무 불편함 */
    private String rdate;
    
    public Analysis() {
      
    }

    public Analysis(String title, String content, int chart, String data, String xlabel, String ylabel,
        String legend) {
      this.title = title;
      this.content = content;
      this.chart = chart;
      this.data = data;
      this.xlabel = xlabel;
      this.ylabel = ylabel;
      this.legend = legend;
    }

    // 사용자로부터 입력받는 필드만 명시


        
}

