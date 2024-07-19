package dev.jpa.da.analysis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("analysis/chart")
public class ChartCont {
  
  /**
   * 컬럼 챠트 simple, http://localhost:9101/analysis/chart/column_simple1
   * @param model
   * @return
   */
  @GetMapping("/column_simple1")
  public String column_simple(Model model) {
    return "/analysis/chart/column_simple1.html";
  }
  
  /**
   * 컬럼 챠트 simple, http://localhost:9101/analysis/chart/column_simple_data1
   * @param model
   * @return
   */
  @GetMapping("/column_simple_data1")
  public String column_simple_data(Model model) {
    model.addAttribute("title", "노트북 매출액 Data 이용");
    model.addAttribute("xlabel", "년도");
    model.addAttribute("ylabel", "판매량");
    
    String chart_data = "[\r\n"
        + "          ['판매년도', '판매량'],\r\n"
        + "          ['2014',  1000],\r\n"
        + "          ['2015',  1170],\r\n"
        + "          ['2016',  660],\r\n"
        + "          ['2017',  1030]\r\n"
        + "        ]";
    model.addAttribute("chart_data", chart_data);
    
    return "/analysis/chart/column_simple_data1.html";
  }
  
  /**
   * 컬럼 챠트 simple, http://localhost:9101/analysis/chart/column_simple2
   * @param model
   * @return
   */
  @GetMapping("/column_simple2")
  public String column_simple2(Model model) {
    return "/analysis/chart/column_simple2.html";
  }
  
  /**
   * 컬럼 챠트 simple, http://localhost:9101/analysis/chart/column_simple_data2
   * @param model
   * @return
   */
  @GetMapping("/column_simple_data2")
  public String column_simple_data2(Model model) {
    model.addAttribute("title", "노트북 매출액 Data 이용");
    model.addAttribute("xlabel", "년도");
    model.addAttribute("ylabel", "판매량");
    
    String chart_data = "[\r\n"
        + "          ['판매년도', '판매량', '영업이익'],\r\n"
        + "          ['2014',  1000,      400],\r\n"
        + "          ['2015',  1170,      460],\r\n"
        + "          ['2016',  660,       1120],\r\n"
        + "          ['2017',  1030,      540]\r\n"
        + "        ]";
    model.addAttribute("chart_data", chart_data);
    
    return "/analysis/chart/column_simple_data2.html";
  }
  

  /**
   * 컬럼 챠트, http://localhost:9101/analysis/chart/column_time
   * @param model
   * @return
   */
  @GetMapping("/column_time")
  public String column_time(Model model) {
    return "/analysis/chart/column_time.html";
  }
  
  /**
   * 컬럼 챠트, http://localhost:9101/analysis/chart/column_time_data
   * @param model
   * @return
   */
  @GetMapping("/column_time_data")
  public String column_time_data(Model model) {
    model.addAttribute("title", "영업 시간대별 자동차 판매량 Data 이용");
    model.addAttribute("xlabel", "영업 시간");
    model.addAttribute("ylabel", "판매량");
    model.addAttribute("legend", "판매량");
    
    String chart_data = "["
        + "              [{v: [8, 0, 0], f: '8 am'}, 1],"
        + "              [{v: [9, 0, 0], f: '9 am'}, 2],"
        + "              [{v: [10, 0, 0], f:'10 am'}, 3],"
        + "              [{v: [11, 0, 0], f: '11 am'}, 4],"
        + "              [{v: [12, 0, 0], f: '12 pm'}, 5],"
        + "              [{v: [13, 0, 0], f: '1 pm'}, 6],"
        + "              [{v: [14, 0, 0], f: '2 pm'}, 7],"
        + "              [{v: [15, 0, 0], f: '3 pm'}, 8],"
        + "              [{v: [16, 0, 0], f: '4 pm'}, 9],"
        + "              [{v: [17, 0, 0], f: '5 pm'}, 10],"
        + "            ]";
    model.addAttribute("chart_data", chart_data);
    
    return "/analysis/chart/column_time_data.html";
  }

  /**
   * 원형 챠트, http://localhost:9101/analysis/chart/pie_data
   * @param model
   * @return
   */
  @GetMapping("/pie_data")
  public String pie_data(Model model) {
    model.addAttribute("title", "판매된 자동차 분포 Data 이용");
//    model.addAttribute("xlabel", "영업 시간");
//    model.addAttribute("ylabel", "판매량");
//    model.addAttribute("legend", "판매량");
    
    String chart_data = "[\r\n"
        + "          ['Task', 'Hours per Day'],\r\n"
        + "          ['Work',     11],\r\n"
        + "          ['Eat',      2],\r\n"
        + "          ['Commute',  2],\r\n"
        + "          ['Watch TV', 2],\r\n"
        + "          ['Sleep',    7]\r\n"
        + "        ]";
    model.addAttribute("chart_data", chart_data);
    
    return "/analysis/chart/pie_data.html";
  }

  /**
   * 선 챠트, http://localhost:9101/analysis/chart/line_data
   * @param model
   * @return
   */
  @GetMapping("/line_data")
  public String line_data(Model model) {
    model.addAttribute("title", "애완견 인기 추세 Data 이용");
    model.addAttribute("xlabel", "시간");
    model.addAttribute("ylabel", "인기도");
//    model.addAttribute("legend", "판매량");
    
    String chart_data = "[\r\n"
        + "        [0, 0],   [1, 10],  [2, 23],  [3, 17],  [4, 18],  [5, 9],\r\n"
        + "        [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],\r\n"
        + "        [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],\r\n"
        + "        [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],\r\n"
        + "        [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],\r\n"
        + "        [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],\r\n"
        + "        [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],\r\n"
        + "        [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],\r\n"
        + "        [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],\r\n"
        + "        [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],\r\n"
        + "        [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],\r\n"
        + "        [66, 70], [67, 72], [68, 75], [69, 80]\r\n"
        + "      ]";
    model.addAttribute("chart_data", chart_data);
    
    return "/analysis/chart/line_data.html";
  }
  
  /**
   * 멀티 선 챠트, http://localhost:9101/analysis/chart/line_multi
   * @param model
   * @return
   */
  @GetMapping("/line_multi")
  public String line_multi(Model model) {
    return "/analysis/chart/line_multi.html";
  }
  
  /**
   * 멀티 선 챠트, http://localhost:9101/analysis/chart/line_multi_data
   * @param model
   * @return
   */
  @GetMapping("/line_multi_data")
  public String line_multi_data(Model model) {
    model.addAttribute("title", "활동별 칼로리 소모량");
    model.addAttribute("xlabel", "시간");
    model.addAttribute("ylabel", "칼로리");
//    model.addAttribute("legend", "판매량");
    
    String chart_data = "[\r\n"
        + "          ['시간', '자전거', '등산'],\r\n"
        + "          [1, 50, 200],\r\n"
        + "          [2, 120, 450],\r\n"
        + "          [3, 130, 660],\r\n"
        + "          [4, 170, 750],\r\n"
        + "          [5, 250, 800]\r\n"
        + "        ]";
    model.addAttribute("chart_data", chart_data);
    
    return "/analysis/chart/line_multi_data.html";
  }
  
}
