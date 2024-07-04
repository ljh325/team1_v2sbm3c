package dev.mvc.follows;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/follows")
@Controller
public class FollowsCont {
  
  @Autowired
  @Qualifier("dev.mvc.follows.FollowsProc")
  private FollowsProcInter followsProc;

  public FollowsCont() {
    System.out.println("-> FollowsCont created.");  
  }
  
  /***************************************************************************************/
  
  
  @PostMapping(value="/follower_insert")
  @ResponseBody
  public String follower_insert(Model model, HttpSession session, FollowsVO followsVO, @RequestBody Map<String, Integer> payload) {
    int followed_no = payload.get("memberno");
    System.out.println("memberno " + followed_no);
    JSONObject obj  = new JSONObject();
    int follower_no = (int)session.getAttribute("memberno");
    
    // 나 세션
    followsVO.setFollower_no(follower_no);
    
    // 상대방
    followsVO.setFollowed_no(followed_no);
    
    
    int count = this.followsProc.exist_cnt(followsVO);
    if (count == 0) {
   // 팔로우 관계가 있는지 확인
      int cnt = this.followsProc.follower_insert(followsVO);
      obj.put("cnt", cnt);
    } else {
      // 이미 팔로우 관계가 있으면 0을 반환
      obj.put("cnt", 0);
    }
    
    
    
    return obj.toString();
  }
  
  
  @PostMapping(value="/delete_friends")
  @ResponseBody
  public String delete_friends(Model model, HttpSession session, FollowsVO followsVO, @RequestBody Map<String, Integer> payload) {
    int followed_no = payload.get("memberno");
    
    System.out.println("memberno " + followed_no);
    JSONObject obj  = new JSONObject();
    HashMap<String, Object> map = new HashMap<String, Object>();
    
    int follower_no = (int)session.getAttribute("memberno");
    System.out.println("follower_no" + follower_no);
    // 나 세션
    map.put("follower_no", follower_no);
    
    // 상대방
    map.put("followed_no", followed_no);
    
    // 나 세션
    followsVO.setFollower_no(follower_no);
    
    // 상대방
    followsVO.setFollowed_no(followed_no);
    
    int count = this.followsProc.exist_cnt(followsVO);
    if (count == 1) {
   // 팔로우 관계가 있는지 확인
      int cnt = this.followsProc.delete_friends(map);
      obj.put("cnt", cnt);
    } else {
      // 이미 팔로우 관계가 있으면 0을 반환
      obj.put("cnt", 0);
    }
    
    return obj.toString();
  }
}
