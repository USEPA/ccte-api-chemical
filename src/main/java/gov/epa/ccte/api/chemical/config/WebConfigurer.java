package gov.epa.ccte.api.chemical.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final ApplicationProperties applicationProperties;

    public WebConfigurer(Environment env, ApplicationProperties applicationProperties) {
        this.env = env;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(Arrays.asList("Authorization,","x-api-key"));
        config.setAllowCredentials(false);
        //config.setMaxAge(3600);
        source.registerCorsConfiguration("/**", config);
        CorsFilter filter = new CorsFilter(source);
//        filter.setCorsProcessor(new DefaultCorsProcessor() {
//            @Override
//            public boolean processRequest(CorsConfiguration config, HttpServletRequest request, HttpServletResponse response)
//                    throws IOException {
//                if (request.getMethod().equals("OPTIONS") && request.getRequestURI().startsWith("/chemical/")) {
//                    // Exclude the API endpoint from preflight checks
//                    return true;
//                }
//                return super.processRequest(config, request, response);
//            }
//        });
        return filter;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = applicationProperties.getCors();
//        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
//            log.debug("Registering CORS filter");
//            source.registerCorsConfiguration("/**", config);
//            //source.registerCorsConfiguration("/management/**", config);
//            source.registerCorsConfiguration("/v3/api-docs", config);
//            source.registerCorsConfiguration("/swagger-ui/**", config);
//        }
//        return new CorsFilter(source);
//    }

    // following code is added for cache control using WebMvcConfigurer


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        WebContentInterceptor interceptor = new WebContentInterceptor();
        // we can use following for path based
//        interceptor.addCacheMapping(CacheControl.maxAge(0, TimeUnit.SECONDS)
//                .noTransform()
//                .mustRevalidate(), "/*");
        interceptor.setCacheControl( CacheControl.maxAge(0, TimeUnit.SECONDS)
                .noTransform()
                .mustRevalidate());
        //interceptor.setCacheControl(  "no-cache, no-store, max-age=0, must-revalidate");

        registry.addInterceptor(interceptor);
    }
}