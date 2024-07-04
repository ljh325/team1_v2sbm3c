package dev.mvc.history;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.follows.FollowsProcInter;
import dev.mvc.follows.FollowsVO;
import dev.mvc.goals.GoalsProcInter;
import dev.mvc.goals.GoalsVO;
import dev.mvc.likesyesno.LikesyesnoProcInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.mh.MhProcInter;
import dev.mvc.mh.MhVO;
import dev.mvc.recordImage.RecordImage;
import dev.mvc.recordImage.RecordImageProcInter;
import dev.mvc.recordImage.RecordImageVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;






@RequestMapping("/history")
@Controller
public class HistoryCont {
  
  @Autowired
  @Qualifier("dev.mvc.goals.GoalsProc")
  private GoalsProcInter goalsProc;
  
  @Autowired
  @Qualifier("dev.mvc.history.HistoryProc")  // @Service("dev.mvc.member.MemberProc")
  private HistoryProcInter historyProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.mh.MhProc")
  private MhProcInter mhProc;
  
  @Autowired
  @Qualifier("dev.mvc.recordImage.RecordImageProc")
  private RecordImageProcInter recordImageProc;
  
  @Autowired
  @Qualifier("dev.mvc.follows.FollowsProc")
  private FollowsProcInter followsProc;
  
  @Autowired
  @Qualifier("dev.mvc.likesyesno.LikesyesnoProc")
  private LikesyesnoProcInter likesyesnoProc;
  
  public HistoryCont() {
    System.out.println("-> HistoryCont created.");  
  }
  
  
  /***************************************************************************************/
  /**
   * 운동 시작 체크 등록
   * @param model
   * @param historyVO 
   * @return
   */
//  @PostMapping(value="/history_start")
//  public String history_start(HttpSession session, Model model, Integer memberno , HistoryVO historyVO) {
//    
////    Integer memberno = (Integer)session.getAttribute("memberno"); // session에서 memberno가져오기
//    model.addAttribute("memberno", memberno);
//    System.out.println(memberno);
//    if (memberno != null) { // 세션에 memberno가 있으면(로그인이 되어있으면)
//      
//      int cnt = this.historyProc.history_start(historyVO); // 운동 시작시간 등록
//      model.addAttribute("cnt", cnt);
//      if (cnt == 1) { // 운동 시작시간 등록 성공시
//        session.setAttribute("workoutStarted", true); // 운동 시작 플래그 설정
//        return "redirect:/"; // 메인화면 template/index.html
//      } else { // 등록 실패시
//          return "member/login"; // /templates/member/login.html
//      }
//    } else {
//      return "member/login"; // /templates/member/login.html
//  }
//
//  }
  /***************************************************************************************/
  /**
   * 운동 기록 메인 화면
   * @param model
   * @return
   */
  @GetMapping(value="/history_form")
  public String history_form(Model model, HttpSession session, int memberno) {
    
    if (this.memberProc.isMember(session)) {
      //int memberno = (int)session.getAttribute("memberno");  
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
      return "history/history_form";
      }
      else {
        return "member/login";
      } 
  }
  
  
  /***************************************************************************************/
  /**
   * 운동 기록 폼
   * @param model
   * @return
   */
  @GetMapping(value="/history_record_form")
  public String history_record_form(Model model, HttpSession session) {
    
    if (this.memberProc.isMember(session)) {
      
      return "history/history_record_form";
    }
    else {
      return "member/login";
    } 
  }
  
  /**
   * 운동 정보 기록
   * @param model
   * @param historyVO
   * @return
   */
  @PostMapping(value="/history_record_proc")
  @ResponseBody
  public String history_record_proc(Model model, 
                                    @RequestBody HistoryVO historyVO,
                                    HttpSession session) {
    
    JSONObject obj = new JSONObject();
    
    int cnt = this.historyProc.insert_history(historyVO);
    
    System.out.println("cnt->" + cnt);
    obj.put("cnt", cnt);
  
    if (cnt == 1) {
      
    }
    return obj.toString(); 
  }
  /***************************************************************************************/
  
  
  
  @GetMapping(value="/activity_record") // http://localhost:9093/history/activity_record
  public String activity_record(Model model, HttpSession session) {
    if (this.memberProc.isMember(session)) {
      
      return "/history/activity_record";
    }
    else {
      return "member/login";
    } 
  }
  
  
  
  @PostMapping(value="/activity_record_proc")
  public String activity_record_proc(Model model, 
                                     RecordImageVO recordImageVO,
                                     RedirectAttributes ra,
                                     HttpSession session) {
    
    
  int memberno = (int) session.getAttribute("memberno");
  
  System.out.println("Image: memberno ---->" + memberno);
// ------------------------------------------------------------------------------
// 파일 전송 코드 시작
// ------------------------------------------------------------------------------
  String profile = "";          // 원본 파일명 image
  String profilesaved = "";   // 저장된 파일명, image
  String thumbs = "";     // preview image

  String upDir =  RecordImage.getUploadDir(); // 파일을 업로드할 폴더 준비
  System.out.println("-> upDir: " + upDir);
  
  // 전송 파일이 없어도 files1MF 객체가 생성됨.
  // <input type='file' class="form-control" name='files1MF' id='files1MF' 
  //           value='' placeholder="파일 선택">
  ArrayList<MultipartFile> mf = recordImageVO.getFiles1MF();
  
  int count = mf.size(); // 전송 파일 갯수
  
  if(count > 0) {
    for (MultipartFile multipartFile:mf) { // 파일 추출, 1개이상 파일 처리
      profile = multipartFile.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + profile);
      long sizes = multipartFile.getSize(); // 파일 크기
      if (sizes > 0) { // 파일 크기 체크, 파일을 올리는 경우
        if (Tool.checkUploadFile(profile) == true) { // 업로드 가능한 파일인지 검사
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
          profilesaved = Upload.saveFileSpring(multipartFile, upDir); 
          
          if (Tool.isImage(profilesaved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumbs = Tool.preview(upDir, profilesaved, 200, 150); 
          }

          recordImageVO.setRecprofile(profile);   // 순수 원본 파일명
          recordImageVO.setRecprofilesaved(profilesaved); // 저장된 파일명(파일명 중복 처리)
          recordImageVO.setRecthumbs(thumbs);      // 원본이미지 축소판
          recordImageVO.setRecsizes(sizes);  // 파일 크기
          
          // 이미지 외 나머지 데이터 입력
          int exrecordno = this.historyProc.exrecordno_max(); // 운동기록번호중 가장 최대값불러오기
          System.out.println("exrecordno->" + exrecordno);
          
          recordImageVO.setExrecordno(exrecordno);
          recordImageVO.setMemberno(memberno);
          
          // 이미지정보를 디비에 저장하는 코드
          int cnt = this.recordImageProc.rec_images_insert(recordImageVO);
          model.addAttribute("cnt", cnt);
          
          System.out.println("디비에 이미지 저장 성공?" + cnt);
          
          
          if (cnt == 1) { // 저장성공했을경우 1
            System.out.println("이미지 등록 성공");
          } else { // 실패: 0 
            System.out.println("이미지 등록 실패");
          }
        }else { // 전송 못하는 파일 형식
          ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
          ra.addFlashAttribute("cnt", 0); // 업로드 실패
          ra.addFlashAttribute("url", "/member/msg"); // msg.html, redirect parameter 적용
           // Post -> Get - param...
        }
      } else { // 글만 등록하는 경우
        System.out.println("-> 글만 등록");
      }
    }
  }
  // ------------------------------------------------------------------------------
  // 파일 전송 코드 종료
  // ------------------------------------------------------------------------------
    return "redirect:/history/recored_ssuccess?memberno=" + memberno;
  }
  
  
  
  /**
   * 프로필 활동
   * @param model
   * @return
   */
  @GetMapping(value="/profile_activity")
  public String profile_activity(Model model, HttpSession session, int memberno) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      int follower_no = (int)session.getAttribute("memberno");
      
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
      
      // 회원별 이미지 리스트
      ArrayList<RecordImageVO> list = this.recordImageProc.one_images_read(memberno);
      model.addAttribute("list", list);
      //System.out.println("list-->><><>" + list);
      // 회원 별 이미지글 총 수
      int cnt = this.recordImageProc.rec_images_cnt(memberno);
      model.addAttribute("cnt", cnt);
      
      FollowsVO followsVO = new FollowsVO();
      
      followsVO.setFollower_no(follower_no);
      followsVO.setFollowed_no(memberno);
      int exit_cnt = this.followsProc.exist_cnt(followsVO);
      
      int follower_cnt = this.followsProc.follower_cnt(memberno);
      int following_cnt = this.followsProc.following_cnt(memberno);
      // 현재 내 팔로우 수
      model.addAttribute("follower_cnt", follower_cnt);
      //현재 내가 팔로잉 한 수
      model.addAttribute("following_cnt", following_cnt);
      
      // 팔로우 했는지 여부
      model.addAttribute("exit_cnt", exit_cnt);
      
      return "history/profile_activity";
    }else{
    return "member/login"; // /member/login.html
    }
  }
  
  
  @GetMapping(value="/profile_detail")
  public String profile_detail(@RequestParam int exrecordno,
                               @RequestParam int memberno,
                               Model model, HttpSession session) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      System.out.println("memberno: >" + memberno);
      System.out.println("exrecordno: >" + exrecordno);
      //int memberno = (int)session.getAttribute("memberno");
      
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
      
      HashMap<String, Object> map = new HashMap<String, Object>();
      
      map.put("memberno", memberno);
      map.put("exrecordno", exrecordno);
      
      HistoryVO histortyVO = this.historyProc.record_read(map);
      model.addAttribute("histortyVO", histortyVO);
      
      System.out.println("map--> " + map);
      ArrayList<RecordImageVO> list = this.recordImageProc.rec_images_read(map);
      model.addAttribute("list", list);
      System.out.println("list ===> " + list);
      return "history/profile_detail";
    }else{
    return "member/login"; // /member/login.html
    }
  }
  
  
  @GetMapping(value="/profile_followng")
  public String profile_followng(Model model, HttpSession session, int memberno) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      //int memberno = (int)session.getAttribute("memberno");
      
      int follower_cnt = this.followsProc.follower_cnt(memberno);
      int following_cnt = this.followsProc.following_cnt(memberno);
      
      ArrayList<FollowsVO> list = this.followsProc.following_member_read(memberno);
      model.addAttribute("list", list);
      System.out.println("lsit" + list);
      //ArrayList<MemberVO> memberVO = this.memberProc.list();
      // 현재 내 팔로우 수
      model.addAttribute("follower_cnt", follower_cnt);
      //현재 내가 팔로잉 한 수
      model.addAttribute("following_cnt", following_cnt);
      
      return "history/profile_followng";
    }else{
      return "member/login"; // /member/login.html
    }
  }
  
  @GetMapping(value="/profile_follower")
  public String profile_follower(Model model, HttpSession session, int memberno) {
    
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      //model. memberno = (int)session.getAttribute("memberno");
      
      int follower_cnt = this.followsProc.follower_cnt(memberno);
      int following_cnt = this.followsProc.following_cnt(memberno);
      
      ArrayList<FollowsVO> list = this.followsProc.follower_member_read(memberno);
      model.addAttribute("list", list);
      System.out.println("lsit" + list);
      
      // 현재 내 팔로우 수
      model.addAttribute("follower_cnt", follower_cnt);
      //현재 내가 팔로잉 한 수
      model.addAttribute("following_cnt", following_cnt);
      
      return "history/profile_follower";
    }else{
      return "member/login"; // /member/login.html
    }
  }
  
  /**
   * 이미지 삭제
   * @param model
   * @param payload
   * @return
   */
  @PostMapping(value="/profile_delete_proc")
  @ResponseBody
  public String profile_delete_proc(Model model,  @RequestBody Map<String, Integer> payload) {
    int exrecordno = payload.get("exrecordno");
    System.out.println("exrecordno가 뭘까?? ->" + exrecordno);
    JSONObject obj = new JSONObject();
    
    int cnt = this.recordImageProc.rec_images_delete(exrecordno);
    System.out.println("cnt ---> 삭제 될까??" + cnt);
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  
  
  @GetMapping(value="/sns_comunity_form")
  public String sns_comunity_form(Model model, HttpSession session) {
   
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      //int memberno = (int)session.getAttribute("memberno");
      int cnt = this.recordImageProc.all_image_cnt();
      if (cnt == 0) {
        model.addAttribute("msg", "0");
      } else {
        ArrayList<RecordImageVO> list = this.recordImageProc.sns_image_read();
        System.out.println("list:  " + list);
        model.addAttribute("list", list);
        model.addAttribute("msg", "1");
        
        //ArrayList<RecordImageVO> newList = new ArrayList<>();
        HashMap<Integer, ArrayList<RecordImageVO>> imageMap = new HashMap<>();
        // 좋아요수
        HashMap<Integer, Integer> likeCountMap = new HashMap<>();
        
        for (RecordImageVO item : list) {
          HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("memberno", item.getMemberno());
          map.put("exrecordno", item.getExrecordno());
          
          // 여러 exrecordno의 값들이 존재
          int exrecordno = item.getExrecordno();
          int like_cnt = this.likesyesnoProc.like_cnt(exrecordno);
          likeCountMap.put(exrecordno, like_cnt);
          
          ArrayList<RecordImageVO> imageList = this.recordImageProc.rec_images_read(map);
          imageMap.put(exrecordno, imageList);
        }
        
        model.addAttribute("likeCountMap", likeCountMap); 
        model.addAttribute("imageMap", imageMap);         
      }
      return "history/sns_comunity_form";
    }else{
    return "member/login"; // /member/login.html
    } 
  }
  
  @GetMapping(value="/sns_following_form")
  public String sns_following_form(Model model, HttpSession session) {
   
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      int memberno = (int)session.getAttribute("memberno");
      int count = this.followsProc.following_cnt(memberno);
      System.out.println("count " + count);
      if(count == 0) {
        model.addAttribute("msg", "0");
      } else {    
        ArrayList<FollowsVO> list = this.followsProc.sns_follow_member_read(memberno);
        model.addAttribute("list", list);
        model.addAttribute("msg", "1");
      
        HashMap<Integer, ArrayList<RecordImageVO>> imageMap = new HashMap<>();
           
        for (FollowsVO item : list) {
          HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("memberno", item.getMemberno());
          map.put("exrecordno", item.getExrecordno());
         
          ArrayList<RecordImageVO> imageList = this.recordImageProc.rec_images_read(map);
          imageMap.put(item.getExrecordno(), imageList);
        }
       
        model.addAttribute("imageMap", imageMap);
        }
      return "history/sns_following_form";
    }else{
    return "redirect:/member/login"; // /member/login.html
    } 
  }
  
  /**
   * 게시글 공개
   * @param model
   * @param exrecordno
   * @return
   */
  @PostMapping(value="/rec_recvisible_update")
  @ResponseBody
  public String rec_recvisible_update(Model model,@RequestBody Map<String, Integer> request) {
    int exrecordno = request.get("exrecordno");
    
    System.out.println("exrecordno-->" + exrecordno);
    JSONObject obj = new JSONObject();
    int cnt = this.recordImageProc.rec_recvisible_update(exrecordno);
    obj.put("cnt", cnt);
    System.out.println("cnt-->" + cnt);
    
    return obj.toString();
  }
  
  /**
   * 게시글 비공개
   * @param model
   * @param exrecordno
   * @return
   */
  @PostMapping(value="/rec_norecvisible_update")
  @ResponseBody
  public String rec_norecvisible_update(Model model,@RequestBody Map<String, Integer> request) {
    int exrecordno = request.get("exrecordno");
    
    System.out.println("exrecordno-->" + exrecordno);
    JSONObject obj = new JSONObject();
    int cnt = this.recordImageProc.rec_norecvisible_update(exrecordno);
    obj.put("cnt", cnt);
    System.out.println("cnt-->" + cnt);
    
    return obj.toString();
  }
  
  /**
   * 운동 기록 성공 폼
   * @param model
   * @return
   */
  @GetMapping(value="/recored_ssuccess")
  public String recored_ssuccess(Model model) {
    
    return "history/recored_ssuccess";
  }
  
  /**
   * 신체 분석 그래프
   * @param model
   * @return
   */
  @GetMapping(value="/history_analyze")
  public String history_analyze(Model model, HttpSession session,MhVO mhVO) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      
    int memberno = (int)session.getAttribute("memberno"); 
    ArrayList<MhVO> list = this.mhProc.list_all(memberno); // 로그인한 계정에 대한 건강정보 리스트 전부 출력
    
    model.addAttribute("list", list); 
    
    mhVO = this.mhProc.read_n(memberno);  // 최근 건강정보 출력
    model.addAttribute("mhVO",mhVO );
    
    //가장 최근에 만들어진 목표 회원별 조회기능
    GoalsVO goalsVO = this.goalsProc.recent_read(memberno);
    model.addAttribute("goalsVO",goalsVO );
    
    return "/history/history_analyze"; // /mh/list_all.html
    }else{
      
    return "member/login"; // /mh/list_all.html
    
    }
  }
  
  /**
   * 신체 정보 그래프
   * @param session
   * @return
   */
  @GetMapping(value="/history_analyze_json")
  @ResponseBody
  public String history_analyze_json(HttpSession session) {
    System.out.println("지나갑니다");
    JSONObject obj = new JSONObject();
    
    int memberno = (int)session.getAttribute("memberno");
    System.out.println("memberno->"  + memberno);
    
    // 운동 신체 정보 리스트
    ArrayList<MhVO> list = this.mhProc.list_all(memberno); // 로그인한 계정에 대한 건강정보 리스트 전부 출력
    System.out.println("list->"  + list);
    obj.put("list", list);
    
    
    JSONArray jsonArray = new JSONArray();
    for (MhVO mhVO : list) {
        JSONObject mhJson = new JSONObject();
        mhJson.put("insertdate", mhVO.getInsertdate());
        mhJson.put("kg", mhVO.getKg());
        mhJson.put("cm", mhVO.getCm());
        mhJson.put("ckg", mhVO.getCkg());
        mhJson.put("muscle", mhVO.getMuscle());
        jsonArray.put(mhJson);
    }
    obj.put("list", jsonArray);
    
    
    //가장 최근에 만들어진 목표 회원별 조회기능
    GoalsVO goalsVO = this.goalsProc.recent_read(memberno);
    
    // 확인을 위해 GoalsVO 객체를 JSON으로 변환하여 추가
    if (goalsVO != null) {
        JSONObject goalsJson = new JSONObject();
        goalsJson.put("goalsno", goalsVO.getGoalsno());
        goalsJson.put("kg", goalsVO.getKg());
        goalsJson.put("ckg", goalsVO.getCkg());
        goalsJson.put("cm", goalsVO.getCm());
        goalsJson.put("muscle", goalsVO.getMuscle());
        goalsJson.put("memberno", goalsVO.getMemberno());
        goalsJson.put("gdate", goalsVO.getGdate());
        obj.put("goalsVO", goalsJson);
    } else {
        obj.put("goalsVO", JSONObject.NULL);
    }
    return obj.toString();
  }
  
  
  /**
   * 운동 기록 리스트 조회
   * @param request
   * @param model
   * @param session
   * @return
   */
  @PostMapping(value="/read_history")
  @ResponseBody
  public String read_history(@RequestBody Map<String, String> request, Model model, HttpSession session) {
    String recorddate = request.get("recorddate"); // request에서 startdate를 가져옴
    System.out.println("시작--->"  + recorddate);    
    JSONObject obj = new JSONObject();
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면
      int memberno = (int)session.getAttribute("memberno"); // 세션에서 memberno를 꺼내옴
      HistoryVO historyVO = new HistoryVO();
      historyVO.setMemberno(memberno);
      historyVO.setRecorddate(recorddate);
      
      ArrayList<HistoryVO> list = this.historyProc.read_history(historyVO);
      System.out.println("list:--->" + list);
           
      obj.put("list", list);
      
      
      return obj.toString();
    } else { // 로그인이 안되어있으면
      return "/member/login";
    }        
  }
  
  
  /**
   * 상세 운동 기록 조회
   * @param exrecordno
   * @param session
   * @param model
   * @param historyVO
   * @return
   */
  @GetMapping(value="record_read")
  public String record_read(@RequestParam String exrecordno, HttpSession session, Model model, HistoryVO historyVO) {
    //System.out.println("스타또 데이트" + startdate);
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면
      int memberno = (int)session.getAttribute("memberno"); // 세션에서 memberno를 꺼내옴
      System.out.println("memberno-->" + memberno);
      HashMap<String, Object> map = new HashMap<String, Object>();
      
      map.put("memberno", memberno);
      map.put("exrecordno", exrecordno);
      
      HistoryVO histortyVO = this.historyProc.record_read(map);
      model.addAttribute("histortyVO", histortyVO);
      
      System.out.println("map--> " + map);
      ArrayList<RecordImageVO> list = this.recordImageProc.rec_images_read(map);
      model.addAttribute("list", list);
      System.out.println("list ===> " + list);
      return "history/record_read";
    
    } else { // 로그인이 안되어있으면
      return "/member/login";
    }    
  }
  
  
  
  @GetMapping(value="record_read_update")
  public String record_read_update(@RequestParam int exrecordno, Model model, HttpSession session) {
    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");
    
    HistoryVO historyVO = new HistoryVO();
    HashMap<String, Object> map = new HashMap<String, Object>();
    
    map.put("memberno", memberno);
    map.put("exrecordno", exrecordno);
    historyVO.setExrecordno(exrecordno);
    
    System.out.println("historyVO==>" + historyVO);
    historyVO = this.historyProc.record_read(map);
    model.addAttribute("historyVO", historyVO);


    return "history/record_read_update";
    }
    else {
      return "/member/login";
    }
  }
  
  /**
   * 운동 정보 기록
   * @param model
   * @param historyVO
   * @return
   */
  @PostMapping(value="/record_read_update_proc")
  @ResponseBody
  public String record_read_update_proc(Model model,
                                    @RequestParam int exrecordno,
                                    @RequestBody HistoryVO historyVO,
                                    HttpSession session) {
    //int memberno = (int)session.getAttribute("memberno");
    JSONObject obj = new JSONObject();
    
    historyVO.setExrecordno(exrecordno);
    int cnt = this.historyProc.history_update(historyVO);
    System.out.println("cnt->" + cnt);
    
    obj.put("cnt", cnt);

    return obj.toString(); 
  }
  
//  if (this.memberProc.isMember(session)) {
//    int memberno = (int)session.getAttribute("memberno");  
//   
//    
//    return "mh/read";
//    }
//    else {
//      return "index";
//    }
  
  /**
   * 운동기록 삭제
   * @param session
   * @param model
   * @param historyVO
   * @return
   */
  @PostMapping(value="delete_sectoin_history")
  @ResponseBody
  public String delete_sectoin_history(HttpSession session, Model model,@RequestBody HistoryVO historyVO) {
    
    JSONObject obj = new JSONObject();
    HashMap<String, Object> map = new HashMap<>();
    
    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");
    System.out.println("memberno -> " + memberno);
    map.put("memberno", memberno);
    map.put("exrecordno", historyVO.getExrecordno());
    System.out.println("Exrecordno() -> " + historyVO.getExrecordno());
    int cnt = this.historyProc.delete_sectoin_history(map);
    System.out.println("cnt --> 삭제가 됬을까 : " + cnt);
    obj.put("cnt", cnt);
    
    return obj.toString();
    } else {
      return "member/login";
    }
  }
  /***************************************************************************************/
  /***************************************************************************************/
  
  /**
   * 테스트
   * @param model
   * @return
   */
  @GetMapping(value="/test")
  public String test(Model model) {
    
    return "history/test";
  }
  
  /**
   * 테스트
   * @param model
   * @return
   */
  @GetMapping(value="/test1")
  public String test1(Model model) {
    
    return "history/test1";
  }
  
  @GetMapping(value="/test2")
  public String test2(Model model) {
    
    return "history/test2";
  }
  @GetMapping(value="/fotorama")
  public String fotorama(Model model) {
    
    return "history/fotorama";
  }
  @GetMapping(value="/date")
  public String date(Model model) {
    
    return "history/date";
  }
  /**
   * ajax 실습
   * @param model
   * @return
   */
  @GetMapping(value="/cycle_ajax") // http://localhost:9091/history/cycle_ajax
  public String cycle_ajax(Model model) {
    
    return "history/cycle_ajax";
  }
  
  @GetMapping(value = "/cycle_ajax_json")
  @ResponseBody
  public String interval_ajax() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    JSONObject json = new JSONObject();
    json.put("cnt", 1);
    
    return json.toString();   
  }
  
  

  /***************************************************************************************/
  
  
  // 달력 불러오기
  @GetMapping("/calendar") // http://localhost:9093/history/calendar
  public String getCalendarPage() {
      return "history/calendar";
  }

  

  // 이벤트 값
  @GetMapping("/api/events")
  @ResponseBody
  public List<Map<String, Object>> getEvents(HttpSession session, int memberno) {
    
      //int memberno = (int)session.getAttribute("memberno");
      ArrayList<HistoryVO> historyVO = this.historyProc.count_history(memberno);
      
      List<Map<String, Object>> events = new ArrayList<>();
      
      for (HistoryVO history : historyVO) {
        Map<String, Object> event = new HashMap<>();
        event.put("title", " " +  history.getTotal_exercises() + " 개");
        System.out.println("운동 갯수" + history.getTotal_exercises());
        String recordDateStr = history.getRecorddate().toString();
        
        if (recordDateStr.length() >= 10) {
          recordDateStr = recordDateStr.substring(0, 10); // 첫 10글자만 추출
            System.out.println("날짜" + recordDateStr.substring(0, 10));
        }
        event.put("start", recordDateStr);
        events.add(event);
 
    }
      return events;
  }
}
