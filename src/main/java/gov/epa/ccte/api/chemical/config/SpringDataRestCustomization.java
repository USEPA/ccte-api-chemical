package gov.epa.ccte.api.chemical.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.epa.ccte.api.chemical.config.converter.StringToImageFormatConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.auditing.AuditableBeanWrapperFactory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.mapping.LinkCollector;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.List;

@Component
public class SpringDataRestCustomization implements RepositoryRestConfigurer {

    @Autowired
    StringToImageFormatConverter stringToImageFormatConverter;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        //CorsRegistry registry = cors;
        //cors.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");

//        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    }

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {
        conversionService.addConverter(stringToImageFormatConverter);
        RepositoryRestConfigurer.super.configureConversionService(conversionService);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        RepositoryRestConfigurer.super.configureValidatingRepositoryEventListener(validatingListener);
    }

    @Override
    public void configureExceptionHandlerExceptionResolver(ExceptionHandlerExceptionResolver exceptionResolver) {
        RepositoryRestConfigurer.super.configureExceptionHandlerExceptionResolver(exceptionResolver);
    }

    @Override
    public void configureHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        RepositoryRestConfigurer.super.configureHttpMessageConverters(messageConverters);
    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        RepositoryRestConfigurer.super.configureJacksonObjectMapper(objectMapper);
    }

    @Override
    public AuditableBeanWrapperFactory customizeAuditableBeanWrapperFactory(AuditableBeanWrapperFactory factory) {
        return RepositoryRestConfigurer.super.customizeAuditableBeanWrapperFactory(factory);
    }

    @Override
    public LinkCollector customizeLinkCollector(LinkCollector collector) {
        return RepositoryRestConfigurer.super.customizeLinkCollector(collector);
    }
}
