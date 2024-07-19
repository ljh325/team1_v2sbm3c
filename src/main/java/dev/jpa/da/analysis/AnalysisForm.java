package dev.jpa.da.analysis;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnalysisForm {
  private Long analysisno;
  
  @NotEmpty(message="분석 제목은 필수 입력 항목입니다.")
  private String title="영업 시간대별 자동차 판매량";
  
  @NotEmpty(message="분석 결과 내용은 필수 입력 항목입니다.")
  @Column(columnDefinition = "CLOB")
  private String content="오후에 매장을 방문하는 손님이 증가되는 것으로 분석됨.";

  @NotNull(message="챠트 종류는 필수 입력 항목입니다.")
  private int chart=1;
  
  @NotEmpty(message="챠트 데이터는 필수 입력 항목입니다.")
  private String data="";
  
  private String xlabel="영업 시간";
  
  private String ylabel="판매량";

  private String legend="판매량";
  
  private int page;

}

