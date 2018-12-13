package hieu.ddshop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/styles/**")
    	.addResourceLocations("classpath:/static/styles/");
    registry.addResourceHandler("/plugins/**")
		.addResourceLocations("classpath:/static/plugins/");
    registry.addResourceHandler("/js/**")
		.addResourceLocations("classpath:/static/js/");
    registry.addResourceHandler("/images/**")
		.addResourceLocations("classpath:/static/images/");
    
  }
  
}