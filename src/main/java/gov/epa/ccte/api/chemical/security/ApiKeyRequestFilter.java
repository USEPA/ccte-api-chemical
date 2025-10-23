package gov.epa.ccte.api.chemical.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.epa.ccte.api.chemical.web.rest.errors.AuthorizationProblem;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Profile("apikey")
@Component
public class ApiKeyRequestFilter extends GenericFilterBean {

    private final ConcurrentHashMap<UUID, String> keyStore;// = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, String> approvedOriginStore;// = new ConcurrentHashMap();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String keyName;

    public ApiKeyRequestFilter(ConcurrentHashMap<UUID, String> keyStore,
                               ConcurrentHashMap<String, String> approvedOriginStore,
                               @Value("${application.api-key-name}") String keyName) {
        this.keyStore = keyStore;
        this.approvedOriginStore = approvedOriginStore;
        this.keyName = keyName;

        //initializeKeyStore(repository);
        //initializeApprovedOriginStore();
    }

//    private void initializeApprovedOriginStore() {
//        approvedOriginStore = new ConcurrentHashMap<>();
//        approvedOriginStore.put("http://localhost:3003", "http://localhost:3003");
//        approvedOriginStore.put("http://localhost:8888", "http://localhost:8888");
//        approvedOriginStore.put("https://ccte-ccd-dev.epa.gov", "https://ccte-ccd-dev.epa.gov");
//        approvedOriginStore.put("https://ccte-ccd-stg.epa.gov", "https://ccte-ccd-stg.epa.gov");
//        approvedOriginStore.put("https://ccte-ccd-prod.epa.gov", "https://ccte-ccd-prod.epa.gov");
//        approvedOriginStore.put("https://comptox.epa.gov", "https://comptox.epa.gov");
//        approvedOriginStore.put("https://ccte-api-s.app.cloud.gov", "https://ccte-api-s.app.cloud.gov");
//    }

//    private void initializeKeyStore(ApiKeyRepository repository) {
//        keyStore = new ConcurrentHashMap<>();
//
//        List<ApiKey> keys = repository.findAll();
//
//        for(ApiKey key : keys)
//            keyStore.put(key.getId(), key.getEmail());
//
//        log.info("*** {} keys are loaded. *** ", keys.size());
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("*** checking the API key ***");

        // dump the request headers
        log.info("*** request headers ***");
//        Enumeration<String> headerNames = ((HttpServletRequest) servletRequest).getHeaderNames();

//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String header = headerNames.nextElement();
//                log.info("Header: {} = {}", header, ((HttpServletRequest) servletRequest).getHeader(header));
//            }
//        }

        HttpServletRequest req = (HttpServletRequest) servletRequest;

//        if(path.startsWith("/api") == false){
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }

        //
        if(shouldCheckApiKey(req)) {
            log.info("*** API key check is checking ***");
            //String headers = String.valueOf(req.getHeaderNames());
            String key = getApiKeyfromHttpHeader(req.getHeader(keyName));

            // In case user is provided api key through parameter x-api-key
            if (key == null || key.isEmpty()) {
                // get key from the URL parameter
                key = getApiKeyFromQueryParam(req.getQueryString());
            }

            if (key == null || key.isEmpty()) {
                // api key is missing
                returnErrorMsg(servletResponse, key);
            } else if (isKeyExist(key)) {
                // api key found
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                // api doesn't match
                returnErrorMsg(servletResponse, key);
                log.info("*** API key {} not recognized *** ", key);
            }
        }else{
            log.info("*** API key check is skipped ***");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean shouldCheckApiKey(HttpServletRequest req) {
        // check if request has method OPTIONS or remote is localhost
        String method = req.getMethod();    // OPTIONS
        String origin = Optional.ofNullable(req.getHeader("origin")).orElse("");  // example - origin = http://localhost:8888
        String referer = Optional.ofNullable(req.getHeader("Referer")).orElse("");  // example - referer = http://localhost:8888/dashboard
        String path = Optional.ofNullable(req.getServletPath()).orElse(""); // example - path = /chemical/file/image/search/by-dtxsid/DTXSID7020182
        String server = Optional.ofNullable(req.getHeader("x-forwarded-server")).orElse("");  // example - x-forwarded-server = comptoxstaging.rtpnc.epa.gov
        String host = Optional.ofNullable(req.getHeader("host")).orElse("");  // example - Header: host = api-ccte-stg.epa.gov


        String refererdHost;
        if(!referer.isEmpty()){
            refererdHost = "https://" + referer.split("/")[2];  // example - referredHost = localhost:8888
        }else{
            refererdHost = ""; //"https://" + req.getHeader("Referer").split("/")[2];  // example - referredHost = localhost:8888
        }

        log.debug("method = {}, origin = {}, x-forwarded-server = {}, host = {}, referer ={}, refererdHost = {}, path={} ",method, origin, server, host, referer, refererdHost, path);

        // if chemical/file path - allow access to images without any api key
        if(path.contains("/chemical/file/") || path.contains("/chemical/health")){
            log.debug("skipping api-key check");
            return false;
        }
//        log.debug("host check = {}", approvedOrigin("https://"+ host));
//        log.debug("server check = {}", approvedOrigin("https://"+ server));
//        log.debug("refererdHost check = {}", approvedOrigin(refererdHost));
//        log.debug("origin check = {}", approvedOrigin(origin));

//        if(method.equalsIgnoreCase("OPTIONS") || approvedOrigin(origin) || approvedOrigin(refererdHost))
        if(approvedOrigin(origin) || approvedOrigin(refererdHost) || approvedOrigin("https://"+ server))
            return false;
        else
            return true;
    }

    private boolean approvedOrigin(String origin) {

        return origin != null && approvedOriginStore.containsKey(origin);
    }


    private String getApiKeyFromQueryParam(String query){

        // an example -  format=svg&x-api-key=f1d96bdd-223a-434e-b1c0-af373a59a19e

        if(query != null){
            String[] params = query.split("&");

            for(String param: params){
                int idx = param.indexOf("=");
                if(param.substring(0,idx).equalsIgnoreCase(keyName)){
                    return URLDecoder.decode(param.substring(idx + 1), StandardCharsets.UTF_8);
                }
            }
        }
        return "";
    }

    private String getApiKeyfromHttpHeader(String key) {
        // Get apikey from the http header
        //String key = req.getHeader(keyName) == null ? "" : req.getHeader(keyName);

        if(key == null || key.isEmpty()){
            log.debug("Custom http  header {} not found", keyName);
        }

        return key;
    }

    private void returnErrorMsg(ServletResponse servletResponse, String key) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        AuthorizationProblem problem;

        if(key == null || key.isEmpty()){
            // header is missing
            problem = AuthorizationProblem.builder()
                    .title("API Header Not Found")
                    .detail("Every API call should pass assigned API key through custom http header or query parameter. Request is missing " + keyName+ "." )
                    .build();

        }else{
            // incorrect apikey
            problem = AuthorizationProblem.builder()
                    .title("API Key Not Found")
                    .detail("Please contact API admin for requesting a valid API key. Key " + key + " not found.")
                    .build();
        }

        String obj = mapper.writeValueAsString(problem);

        resp.reset();
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json");
        servletResponse.setContentLength(obj.length());
        servletResponse.getWriter().write(obj);
    }

    private boolean isKeyExist(String key) {
        if(key == null)
            return false;
        else if(keyStore.isEmpty())
            return false;
        else{
            try{
                return keyStore.containsKey(UUID.fromString(key));
            } catch (Exception e){
                return false;
            }
        }

    }
}
