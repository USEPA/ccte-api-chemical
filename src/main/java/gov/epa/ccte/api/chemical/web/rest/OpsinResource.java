package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.service.OpsinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for getting the response from OPSIN library
 */
@Slf4j
@RestController
public class OpsinResource implements OpsinApi {

    private final OpsinService opsinService;

    public OpsinResource(OpsinService opsinService) {
        this.opsinService = opsinService;
    }

    @Override
    public String toInChI(String name) throws IOException {
        return opsinService.toInChI(name);
    }

    @Override
    public String toInChIKey(String name) throws IOException {
        return opsinService.toInChIKey(name);
    }

    @Override
    public String toSmiles(String name) throws IOException {
        return opsinService.toSmiles(name);
    }
}