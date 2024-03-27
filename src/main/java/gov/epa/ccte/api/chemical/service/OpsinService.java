package gov.epa.ccte.api.chemical.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.ac.cam.ch.wwmm.opsin.NameToInchi;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

@Slf4j
@Service
public class OpsinService {

    private NameToInchi nameToInchi = new NameToInchi();
    private NameToStructure nameToStructure = NameToStructure.getInstance();

    public String toInChI(String name){

        return nameToInchi.parseToInchi(name);
    }

    public String toInChIKey(String name){

        return nameToInchi.parseToStdInchiKey(name);
    }

    public String toSmiles(String name){
        OpsinResult result = nameToStructure.parseChemicalName(name);
        return result.getSmiles();
    }

}
