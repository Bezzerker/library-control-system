package com.barikhashvili.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.barikhashvili.library")
@PropertySource("classpath:database.properties")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);

        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("assets/webfonts/**").addResourceLocations("classpath:/static/assets/webfonts/");
        registry.addResourceHandler("assets/css/**").addResourceLocations("classpath:/static/assets/css/");
        registry.addResourceHandler("assets/js/**").addResourceLocations("classpath:/static/assets/js/");
        registry.addResourceHandler("assets/saas/**").addResourceLocations("classpath:/static/assets/saas/");
        registry.addResourceHandler("assets/saas/base/**").addResourceLocations("classpath:/static/assets/saas/base");
        registry.addResourceHandler("assets/saas/components/**").addResourceLocations("classpath:/static/assets/saas/components");
        registry.addResourceHandler("assets/saas/layout/**").addResourceLocations("classpath:/static/assets/saas/layout");
        registry.addResourceHandler("assets/saas/libs/**").addResourceLocations("classpath:/static/assets/saas/libs");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String driver = environment.getProperty("driver");
        if (driver != null) dataSource.setDriverClassName(driver);
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
