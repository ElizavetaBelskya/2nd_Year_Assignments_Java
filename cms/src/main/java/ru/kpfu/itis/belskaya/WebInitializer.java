package ru.kpfu.itis.belskaya;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
import ru.kpfu.itis.belskaya.config.RootConfig;
import ru.kpfu.itis.belskaya.config.SecurityConfig;
import ru.kpfu.itis.belskaya.config.WebConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.servlet.ServletRegistration;


/**
 * @author Elizaveta Belskaya
 */

public class WebInitializer extends AbstractDispatcherServletInitializer {

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext cxt = new AnnotationConfigWebApplicationContext();
        cxt.register(WebConfig.class, SecurityConfig.class);
        return cxt;
    }


    @Override
    protected String[] getServletMappings() {
        return new String[] { "/", "/swagger-ui.html" };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("dispatchOptionsRequest", "true");
        registration.setInitParameter("swagger.ui.configuration", "{ \"validatorUrl\": \"\" }");
    }


    @Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext cxt = new AnnotationConfigWebApplicationContext();
        cxt.register(RootConfig.class, SecurityConfig.class);
        return cxt;
    }

}
