package gov.epa.ccte.api.chemical.service;


import com.epam.indigo.Indigo;
import com.epam.indigo.IndigoException;
import com.epam.indigo.IndigoInchi;
import com.epam.indigo.IndigoObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndigoService {

    public String mol2name(String mol){
        try {
            Indigo indigo = getIndigo();
            IndigoObject m = indigo.loadQueryMolecule(mol);
            return m.name();
        } catch ( IndigoException ex ) {
            log.error("mol2smiles {}: {}", mol, ex.getMessage());
            return null;
        }
    }

    public String mol2smiles(String mol){
        try {
            Indigo indigo = getIndigo();
            IndigoObject m = indigo.loadQueryMolecule(mol);
            return m.smiles();
        } catch ( IndigoException ex ) {
            log.error("mol2smiles {}: {}", mol, ex.getMessage());
            return null;
        }
    }

    public String mol2CanonicalSmiles(String mol){
        try {
            Indigo indigo = getIndigo();
            IndigoObject m = indigo.loadQueryMolecule(mol);
            return m.canonicalSmiles();
        } catch ( IndigoException ex ) {
            log.error("mol2smiles {}: {}", mol, ex.getMessage());
            return null;
        }
    }

    public String mol2molv3000(String mol){
        try {
            Indigo indigo = getIndigo();
            IndigoObject m = indigo.loadQueryMolecule(mol);
            return m.molfile();
        } catch ( IndigoException ex ) {
            log.error("mol2smiles {}: {}", mol, ex.getMessage());
            return null;
        }
    }

    public String mol2molv2000(String mol){
        try {
            Indigo indigo = getIndigo();
            indigo.setOption("molfile-saving-mode", "2000");
            IndigoObject m = indigo.loadQueryMolecule(mol);
            return m.molfile();
        } catch ( IndigoException ex ) {
            log.error("mol2smiles {}: {}", mol, ex.getMessage());
            return null;
        }
    }

    public String mol2inchikey(String mol){
        try {
            Indigo indigo = getIndigo();
            IndigoObject m = indigo.loadMolecule(mol);
            IndigoInchi indigoInchi = new IndigoInchi(indigo);
            String inchi = indigoInchi.getInchi(m);
            return inchi;
        } catch ( IndigoException ex ) {
            log.error("mol2smiles {}: {}", mol, ex.getMessage());
            return null;
        }
    }

    public Double mol2molWeight(String mol){
        try {
            Indigo indigo = getIndigo();
            IndigoObject m = indigo.loadQueryMolecule(mol);
            return m.molecularWeight();
        } catch ( IndigoException ex ) {
            log.error("mol2smiles {}: {}", mol, ex.getMessage());
            return null;
        }
    }


    public static Indigo getIndigo() {
        Indigo indigo = new Indigo();
        indigo.setOption("ignore-stereochemistry-errors", true);
        indigo.setOption("molfile-saving-mode", "3000");
        indigo.setOption("aromaticity-model", "generic");
        return indigo;
    }
}
