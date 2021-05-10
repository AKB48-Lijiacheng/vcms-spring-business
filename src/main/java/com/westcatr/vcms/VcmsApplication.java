package com.westcatr.vcms;

import com.westcatr.rd.base.aredis.annotation.EnableICacheKey;
import com.westcatr.rd.base.aredis.annotation.EnableIHutoolCache;
import com.westcatr.rd.base.authority.annotation.EnableIAuthority;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;


@EnableIHutoolCache
@EnableICacheKey
//@EnableCaching
// @NacosPropertySource(dataId = "app", autoRefreshed = true)
// @NacosPropertySource(dataId = "publicConfig", autoRefreshed = true)
@EnableIAuthority
@SpringBootApplication
@MapperScan("com.westcatr.vcms.mapper")
public class VcmsApplication extends WebMvcConfigurerAdapter {

    public static void main(final String[] args) {
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
        SpringApplication.run(VcmsApplication.class, args);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setUrlDecode(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
