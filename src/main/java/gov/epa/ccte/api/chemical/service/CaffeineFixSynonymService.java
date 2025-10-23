package gov.epa.ccte.api.chemical.service;

import com.nextmovesoftware.CaffeineFix.CaffeineFix;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class CaffeineFixSynonymService {

    @Value(value = "classpath:synonym.cfx")
    private Resource dictionaryResource;

    CaffeineFix caffeineFix;

    @PostConstruct
    private void init() throws IOException {
        File tempFile = File.createTempFile("synonym", ".cfx");
        tempFile.deleteOnExit(); // Ensure it gets deleted on exit
        try (InputStream inputStream = dictionaryResource.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        caffeineFix = new CaffeineFix(tempFile.getAbsolutePath());
    }

    public List<String> caffeineFix(String nameString){

        List<String> suggestions = caffeineFix.suggestions(nameString, 1);

        if(suggestions.size()>0) return suggestions;
            suggestions = caffeineFix.suggestions(nameString, 2);
        if(suggestions.size()>0) return suggestions;
            suggestions = caffeineFix.suggestions(nameString, 3);
        if(suggestions.size()>0) return suggestions;
            return null;
    }
}
