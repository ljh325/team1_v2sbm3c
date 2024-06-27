package dev.mvc.team1_v2sbm3c;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.adcontents.Adcontents;
import dev.mvc.contents.Contents;
import dev.mvc.health.Health;
import dev.mvc.member.Member;
import dev.mvc.recordImage.RecordImage;
import dev.mvc.reviewImage.ReviewImage;
import dev.mvc.tool.Tool;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows: path = "C:/kd/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:///C:/kd/deploy/resort_v2sbm3c_blog/contents/storage
      
        // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:////home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage
      
        // JSP 인식되는 경로: http://localhost:9091/contents/storage";
        registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir());
        registry.addResourceHandler("/health/storage/**").addResourceLocations("file:///" +  Health.getUploadDir());
        registry.addResourceHandler("/adcontents/storage/**").addResourceLocations("file:///" +  Adcontents.getUploadDir());
        registry.addResourceHandler("/member/storage/**").addResourceLocations("file:///" +  Member.getUploadDir());
        registry.addResourceHandler("/reviewImage/storage/**").addResourceLocations("file:///" +  ReviewImage.getUploadDir());
        registry.addResourceHandler("/recordImage/storage/**").addResourceLocations("file:///" +  RecordImage.getUploadDir());
        // JSP 인식되는 경로: http://localhost:9091/attachfile/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
        
        // JSP 인식되는 경로: http://localhost:9091/member/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
    }
 
}