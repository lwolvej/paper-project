package org.duohuo.paper.config.xss;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

/**
 * @author lwolvej
 */
@Configuration
public class XssConfig {

    @Bean("xssFilterRegistrationBean")
    public FilterRegistrationBean<Filter> xssFilterRegistrationBean() {
        FilterRegistrationBean<Filter> initXssFilterBean = new FilterRegistrationBean<>();
        initXssFilterBean.setFilter(new XssFilter());
        initXssFilterBean.setOrder(1);
        initXssFilterBean.setEnabled(true);
        initXssFilterBean.addUrlPatterns("/*");
        initXssFilterBean.setDispatcherTypes(DispatcherType.REQUEST);
        return initXssFilterBean;
    }
}
