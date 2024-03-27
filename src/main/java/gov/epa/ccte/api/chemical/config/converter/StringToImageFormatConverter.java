package gov.epa.ccte.api.chemical.config.converter;

import gov.epa.ccte.api.chemical.domain.ImageFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringToImageFormatConverter implements Converter<String, ImageFormat> {
    @Override
    public ImageFormat convert(String source) {
        log.info("image format={}", source);
        return ImageFormat.valueOf(source.toUpperCase());

//        try{
//            log.info("image format={}", source);
//
//            return ImageFormat.valueOf(source.toUpperCase());
//
//        } catch (IllegalArgumentException e){
//            log.error("image format {} not found", source);
//            throw new TypeValueNotFoundProblem("Image format", source);
//        }
    }
}
