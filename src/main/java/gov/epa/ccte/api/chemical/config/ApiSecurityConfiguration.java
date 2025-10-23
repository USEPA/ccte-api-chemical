package gov.epa.ccte.api.chemical.config;


import gov.epa.ccte.api.chemical.domain.ApiKey;
import gov.epa.ccte.api.chemical.domain.ApprovedHost;
import gov.epa.ccte.api.chemical.repository.ApiKeyRepository;
import gov.epa.ccte.api.chemical.repository.ApprovedHostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Profile("apikey")
@Configuration
public class ApiSecurityConfiguration {

    private final ApiKeyRepository repository;
    private final ApprovedHostRepository approvedHostRepository;

    public ApiSecurityConfiguration(ApiKeyRepository repository, ApprovedHostRepository approvedHostRepository) {
        this.repository = repository;
        this.approvedHostRepository = approvedHostRepository;
    }

    @Bean
    public ConcurrentHashMap<UUID, String> apiKeyStore(){
        log.debug("*** start filling ApiKeyStore ***");

        ConcurrentHashMap<UUID, String> keyStore = new ConcurrentHashMap<>();

        log.debug("*** start loading api keys ***");


        List<ApiKey> keys = repository.findAll();

        for(ApiKey key : keys)
            keyStore.put(key.getId(), key.getDataScope());

        log.info("*** {} keys are loaded. *** ", keys.size());

        return keyStore;
    }

    @Bean
    public ConcurrentHashMap<String, String> approvedOriginStoreFromDB(){
        log.debug("*** start loading approved hosts ***");
        ConcurrentHashMap<String, String> approvedOriginStore = new ConcurrentHashMap<>();

        List<ApprovedHost> hosts = approvedHostRepository.findAll();

        for(ApprovedHost host : hosts){
            approvedOriginStore.put(host.getHostName(), host.getHostName());
        }

        log.info("*** {} hosts are loaded. *** ", approvedOriginStore.size());

        return approvedOriginStore;
    }

//    @Bean
//    public ConcurrentHashMap<String, String> approvedOriginStore(){
//        log.debug("*** start filling approvedOriginStore ***");
//
//        ConcurrentHashMap<String, String> approvedOriginStore = new ConcurrentHashMap<>();
//        // make sure it is https even it is localhost
//        approvedOriginStore.put("https://localhost:3003", "https://localhost:3003");
//        approvedOriginStore.put("https://localhost:8888", "https://localhost:8888");
//        approvedOriginStore.put("http://localhost:3003", "http://localhost:3003");
//        approvedOriginStore.put("http://localhost:8888", "http://localhost:8888");
//        approvedOriginStore.put("https://ccte-ccd-dev.epa.gov", "https://ccte-ccd-dev.epa.gov");
//        approvedOriginStore.put("https://ccte-ccd-stg.epa.gov", "https://ccte-ccd-stg.epa.gov");
//        approvedOriginStore.put("https://ccte-ccd-prod.epa.gov", "https://ccte-ccd-prod.epa.gov");
//        approvedOriginStore.put("https://ccte-ccd.epa.gov", "https://ccte-ccd.epa.gov");
//        approvedOriginStore.put("https://comptox.epa.gov", "https://comptox.epa.gov");
//        approvedOriginStore.put("https://ccte-api-s.app.cloud.gov", "https://ccte-api-s.app.cloud.gov");
//        approvedOriginStore.put("https://v2626umcth886.rtord.epa.gov:8888", "https://v2626umcth886.rtord.epa.gov:8888");
//        approvedOriginStore.put("https://v2626umcth877.rtord.epa.gov:8001", "https://v2626umcth877.rtord.epa.gov:8001");
//        approvedOriginStore.put("https://v2626umcth878.rtord.epa.gov:8001", "https://v2626umcth878.rtord.epa.gov:8001");
//        approvedOriginStore.put("https://comptoxstaging.rtpnc.epa.gov", "https://comptoxstaging.rtpnc.epa.gov");
//        approvedOriginStore.put("https://v2626umcth875.rtord.epa.gov:8001", "https://v2626umcth875.rtord.epa.gov:8001");
//
//
//        log.info("*** {} urls are loaded. *** ", approvedOriginStore.size());
//
//        return approvedOriginStore;
//    }
}
