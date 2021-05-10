package com.westcatr.vcms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @author : lijiacheng
 * @description :WebConfig
 * ---------------------------------
 * @since : Create in 2020/12/25 10:01
 */
@Configuration
public class WebConfig {

   /**
    * RequestContextListener监听器
    *
    * @return
    */
   @Bean
   public RequestContextListener requestContextListenerBean() {
      return new RequestContextListener();

   }
}
