package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ExtraData;
import gov.epa.ccte.api.chemical.repository.ExtraDataRepository;
import gov.epa.ccte.api.chemical.web.rest.errors.HigherNumberOfIdsException;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for getting the {@link ExtraData}s.
 */
@Slf4j
@RestController
public class ExtraDataResource implements ExtraDataApi {
    final private ExtraDataRepository repository;

    @Value("2000")
    private Integer batchSize;

    public ExtraDataResource(ExtraDataRepository repository) {
        this.repository = repository;
    }

    public List<ExtraData> extraDataByDtxsid(String dtxsid) {
        log.debug("dtxsid = {}", dtxsid);

        List<ExtraData> data = repository.findByDtxsid(dtxsid, ExtraData.class);
        if (data.isEmpty())
            throw new IdentifierNotFoundException("dtxsid", dtxsid);
        else
            return data;
    }

    public List<ExtraData> batchSearchExtraData(String[] dtxsids){
        if(dtxsids.length > batchSize)
            throw new HigherNumberOfIdsException(dtxsids.length, batchSize, "dtxsids");

        List<ExtraData> data = repository.findByDtxsidInOrderByDtxsidAsc(dtxsids, ExtraData.class);

        return data;
    }
}