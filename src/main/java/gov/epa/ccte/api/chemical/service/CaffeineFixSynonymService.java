package gov.epa.ccte.api.chemical.service;

import com.nextmovesoftware.CaffeineFix.CaffeineFix;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CaffeineFixSynonymService {

    @Value(value = "classpath:synonym.cfx")
    private Resource dictionaryResource;

    CaffeineFix caffeineFix;

    @PostConstruct
    private void init() throws IOException {
        caffeineFix = new CaffeineFix(dictionaryResource.getFile().getPath());
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
