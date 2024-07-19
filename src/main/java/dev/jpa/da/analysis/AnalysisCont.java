package dev.jpa.da.analysis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import dev.mvc.tool.Tool;
import jakarta.validation.Valid;

@Controller
@RequestMapping("analysis")
public class AnalysisCont {
  @Autowired
  AnalysisService service;
  
  public AnalysisCont() {
    System.out.println("-> AnalysisCont created.");
  }

  /**
   * 컬럼 챠트 등록폼(변수 1), http://localhost:9101/analysis/create_column_simple
   * @param model
   * @return
   */
  @GetMapping("/create_column_simple")
  public String create_column_simple(Model model) {
    model.addAttribute("analysisForm", new AnalysisForm());
    return "/analysis/create_column_simple";
  }

  /**
   * 컬럼 챠트 등록폼, http://localhost:9101/analysis/create_column
   * @param model
   * @return
   */
  @GetMapping("/create_column_time")
  public String create_form(Model model) {
    model.addAttribute("analysisForm", new AnalysisForm());
    return "/analysis/create_column_time";
  }

  /**
   * 파이 챠트 등록폼, http://localhost:9101/analysis/create_form_pie
   * @param model
   * @return
   */
  @GetMapping("/create_pie")
  public String create_form_pie(Model model) {
    model.addAttribute("analysisForm", new AnalysisForm());
    return "/analysis/create_pie";
  }
  
  /**
   * 라인 챠트 등록폼, http://localhost:9101/analysis/create_line
   * @param model
   * @return
   */
  @GetMapping("/create_line")
  public String create_form_line(Model model) {
    model.addAttribute("analysisForm", new AnalysisForm());
    return "/analysis/create_line";
  }
  
  /**
   * 등록 처리, http://localhost:9101/analysis/create_proc
   * @param model
   * @return
   */
  @PostMapping("/create_proc")
  public String create_proc(@Valid @ModelAttribute("analysisForm") AnalysisForm form,
                                       BindingResult bindingResult) {
    
    if (bindingResult.hasErrors()) {
      if (form.getChart() == 1) {
        return "/analysis/create_column_simple";  // templates/analysis/create_column_simple.html
      } else if (form.getChart() == 2) {
          return "/analysis/create_column_time";  // templates/analysis/create_column_time.html        
      } else if (form.getChart() == 3) {
        return "/analysis/create_pie";  // templates/analysis/create_pie.html
      } else if (form.getChart() == 4) {
        return "/analysis/create_line";  // templates/analysis/create_line.html
      }  
    }
    
    try {
      Analysis analysis = new Analysis(form.getTitle(), form.getContent(),
          form.getChart(), form.getData(), form.getXlabel(), form.getYlabel(), form.getLegend());
      
      this.service.save(analysis);
      
//    } catch (DataIntegrityViolationException e) { // unique 제약 조건 위배시 실행 블럭
//      e.printStackTrace();
//      bindingResult.reject("id", "이미 등록된 사용자 입니다.");
//      return "/damember/create_form";  // templates/damember/create_form.html
//      
    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("create_failed", e.getMessage());
      
      if (form.getChart() == 1) {
        return "/analysis/create_column_simple";  // templates/analysis/create_column_simple.html
      } else  if (form.getChart() == 2) {
        return "/analysis/create_column_time";  // templates/analysis/create_column_time.html        
      } else if (form.getChart() == 3) {
        return "/analysis/create_pie";  // templates/analysis/create_pie.html
      } else if (form.getChart() == 4) {
        return "/analysis/create_line";  // templates/analysis/create_line.html
      } 
    }
    
    return "redirect:/analysis/list";
  }
  
  /**
   * 전체 목록
   * @param model
   * @return
   */
  @GetMapping("/list_all")
  public String list_all(Model model) {
    List<Analysis> list = this.service.list_all();
    
    model.addAttribute("list", list);
    
    return "/analysis/list_all";  // templates/analysis/list_all.html
  }

  /**
   * 페이징 목록
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
    Page<Analysis> list = this.service.list(page);
    
    model.addAttribute("list", list);
    model.addAttribute("page", page);
    
    return "/analysis/list";  // templates/analysis/list.html
  }

  /**
   * 조회
   * @param model
   * @return
   */
  @GetMapping("/read")
  public String read(Model model, @RequestParam(value="page", defaultValue="0") int page,
                                                  @RequestParam(value="analysisno", defaultValue="0") int analysisno) {
    model.addAttribute("analysis", this.service.read(analysisno));
    model.addAttribute("page", page);
    
    return "/analysis/read"; 
  }
  
  /**
   * 챠트 조회
   * @param model
   * @return
   */
  @GetMapping("/read_chart")
  public String read_chart(Model model, @RequestParam(value="page", defaultValue="0") int page,
                                                  @RequestParam(value="analysisno", defaultValue="0") int analysisno) {
    System.out.println("-> analysisno: " + analysisno);
    Analysis analysis = this.service.read(analysisno);
    
    String content = Tool.convertChar(analysis.getContent());
    analysis.setContent(content);
    
    model.addAttribute("analysis", analysis);
    model.addAttribute("page", page);
    
    System.out.println("-> analysis.getChart(): " + analysis.getChart());
    
    if (analysis.getChart() == 1) {
      return "/analysis/read_column_simple";  // templates/analysis/read_column_simple.html
    } else if (analysis.getChart() == 2) { 
      return "/analysis/read_column_time";  // templates/analysis/read_column_time.html
    } else if (analysis.getChart() == 3) {
      return "/analysis/read_pie";  // templates/analysis/read_pie.html
    } else if (analysis.getChart() == 4) {
      return "/analysis/read_line";  // templates/analysis/read_line.html
    } else {
      return "/analysis/read_column_simple";  // templates/analysis/read_column_simple.html
    }
  }
  
  /**
   * 수정폼
   * @param model
   * @return
   */
  @GetMapping("/update_form")
  public String update_form(Model model, 
                                   @RequestParam(value="page", defaultValue="0") int page,
                                   @RequestParam(value="analysisno", defaultValue="0") int analysisno) {
    Analysis analysis = this.service.read(analysisno);
    
    System.out.println("-> 수정 analysisno: " + analysisno);
    System.out.println("-> analysis.getChart(): " + analysis.getChart());
    
    AnalysisForm analysisForm = new AnalysisForm();
    analysisForm.setAnalysisno(analysis.getAnalysisno());
    analysisForm.setTitle(analysis.getTitle());
    analysisForm.setContent(analysis.getContent());
    analysisForm.setChart(analysis.getChart());
    analysisForm.setData(analysis.getData());
    
    analysisForm.setPage(page);
    
    model.addAttribute("analysisForm", analysisForm);
    
    if (analysis.getChart() == 1) {
      analysisForm.setXlabel(analysis.getXlabel());
      analysisForm.setYlabel(analysis.getYlabel());
      
      return "/analysis/update_column_simple";  // templates/analysis/update_column_simple.html
    } else if (analysis.getChart() == 2) {
      analysisForm.setLegend(analysis.getLegend());
      
      return "/analysis/update_column_time";  // templates/analysis/update_column_time.html
    } else if (analysis.getChart() == 3) {
      return "/analysis/update_pie";  // templates/analysis/update_pie.html
    } else if (analysis.getChart() == 4) {
      analysisForm.setXlabel(analysis.getXlabel());
      analysisForm.setYlabel(analysis.getYlabel());
      analysisForm.setLegend(analysis.getLegend());
      
      return "/analysis/update_line";  // templates/analysis/update_line.html
    } else {
      return "/analysis/update_column_simple";  // templates/analysis/update_column_simple.html
    }
  }
  
  /**
   * 수정 처리, http://localhost:9101/analysis/update_proc
   * @param model
   * @return
   */
  @PostMapping("/update_proc")
  public String update_proc(@Valid @ModelAttribute("analysisForm") AnalysisForm form,
                                   @RequestParam(value="page", defaultValue="0") int page,
                                   BindingResult bindingResult) {
    
    if (bindingResult.hasErrors()) {
      if (form.getChart() == 1) {
        return "/analysis/update_column_simple";  // templates/analysis/update_column_simple.html
      } else if (form.getChart() == 2) {
          return "/analysis/update_column_time";  // templates/analysis/update_column_time.html        
      } else if (form.getChart() == 3) {
        return "/analysis/update_pie";  // templates/analysis/update_pie.html
      } else if (form.getChart() == 4) {
        return "/analysis/update_line";  // templates/analysis/update_line.html
      }  
    }
    
    try {
      Analysis analysis = this.service.read(form.getAnalysisno()); // 수정할 레코드 읽기
      analysis.setTitle(form.getTitle());
      analysis.setContent(form.getContent());
      analysis.setChart(form.getChart());
      analysis.setData(form.getData());
      
      if (form.getChart() == 1) {
        analysis.setXlabel(form.getXlabel());
        analysis.setYlabel(form.getYlabel());
        
      } else if (form.getChart() == 2) {
        analysis.setLegend(form.getLegend());
        
      } else if (form.getChart() == 3) {
      } else if (form.getChart() == 4) {
        analysis.setXlabel(form.getXlabel());
        analysis.setYlabel(form.getYlabel());
        analysis.setLegend(form.getLegend());
      }  
     
      this.service.save(analysis); // 수정된 내용 저장
      
//    } catch (DataIntegrityViolationException e) { // unique 제약 조건 위배시 실행 블럭
//      e.printStackTrace();
//      bindingResult.reject("id", "이미 등록된 사용자 입니다.");
//      return "/damember/create_form";  // templates/damember/create_form.html
//      
    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("create_failed", e.getMessage());
      
      if (form.getChart() == 1) {
        return "/analysis/update_column_simple";  // templates/analysis/update_column_simple.html
      } else if (form.getChart() == 2) {
          return "/analysis/update_column_time";  // templates/analysis/update_column_time.html        
      } else if (form.getChart() == 3) {
        return "/analysis/update_pie";  // templates/analysis/update_pie.html
      } else if (form.getChart() == 4) {
        return "/analysis/update_line";  // templates/analysis/update_line.html
      }  
    }
    
    return String.format("redirect:/analysis/read_chart?analysisno=%s&page=%s", form.getAnalysisno(), form.getPage());
  }
  
  /**
   * 삭제 처리, http://localhost:9101/analysis/delete_proc
   * @param model
   * @return
   */
  @PostMapping("/delete_proc")
  public String delete_proc(@RequestParam(value="page", defaultValue="0") int page,
                                  @RequestParam(value="analysisno", defaultValue="0") int analysisno) {
    
    System.out.println("-> 삭제할 챠트 번호: " + analysisno);
    this.service.delete((long)analysisno);
    
    return String.format("redirect:/analysis/list?page=%s", page);
  }
  
}








