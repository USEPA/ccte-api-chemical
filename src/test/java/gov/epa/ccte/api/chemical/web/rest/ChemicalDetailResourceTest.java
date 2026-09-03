package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the ChemicalDetailResource.java using WebMvcTest and MockitoBean
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.projection.chemicaldetail.*;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.service.ChemicalDetailService;
import gov.epa.ccte.api.chemical.service.SimilarSearchService;
import gov.epa.ccte.api.chemical.web.rest.errors.UnparseableSmilesException;
import gov.epa.ccte.api.chemical.web.rest.requests.Page;

import java.util.*;
import static org.hamcrest.Matchers.hasSize;

@ActiveProfiles("test")
@MockitoSettings(strictness = Strictness.WARN)
@WebMvcTest(ChemicalDetailResource.class)
@ExtendWith(MockitoExtension.class)
public class ChemicalDetailResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalDetailRepository detailRepository;
    @MockitoBean
    private ChemicalDetailService detailService;

    @MockitoBean
    private SimilarSearchService similarSearchService;

    private CcdAssayDetails ccdAssayDetails;
    private CcdChemicalDetails ccdChemicalDetails;
    private ChemicalDetailAll chemicalDetailAll;
    private ChemicalDetailAllIds chemicalDetailAllIds;
    private ChemicalDetailStandard standard1;
    private ChemicalDetailStandard2 standard2;
    private ChemicalIdentifier chemicalIdentifier;
    private ChemicalStructure chemicalStructure;
    private Compact compact;
    private NtaToolkit ntaToolkit;
    private Page detailsPage, idsPage;
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @BeforeEach
    void setUp() {

        ccdAssayDetails = factory.createProjection(CcdAssayDetails.class);
        ccdChemicalDetails = factory.createProjection(CcdChemicalDetails.class);
        ccdAssayDetails.setId(923260L);
        ccdAssayDetails.setCasrn("80-05-7");
        ccdAssayDetails.setCompoundId(182);
        ccdAssayDetails.setGenericSubstanceId(20182);
        ccdAssayDetails.setPreferredName("Bisphenol A");
        ccdAssayDetails.setActiveAssays(236);
        ccdAssayDetails.setCpdataCount(292L);
        ccdAssayDetails.setMolFormula("C15H16O2");
        ccdAssayDetails.setMonoisotopicMass(228.115029755);
        ccdAssayDetails.setAverageMass(228.291);
        ccdAssayDetails.setPercentAssays((double) 24);
        ccdAssayDetails.setPubChemCount(null);
        ccdAssayDetails.setPubMedCount((double) 3850);
        ccdAssayDetails.setStereo("0");
        ccdAssayDetails.setSourcesCount(176L);
        ccdAssayDetails.setQcLevel(1);
        ccdAssayDetails.setQcLevelDesc("Level 1: Expert curated, highest confidence in accuracy and consistency of unique chemical identifiers");
        ccdAssayDetails.setIsotope(0);
        ccdAssayDetails.setMulticomponent(0);
        ccdAssayDetails.setTotalAssays(981);
        ccdAssayDetails.setPubchemCid(null);
        ccdAssayDetails.setRelatedSubstanceCount(154L);
        ccdAssayDetails.setRelatedStructureCount(25L);
        ccdAssayDetails.setHasStructureImage(1);
        ccdAssayDetails.setIupacName(null);
        ccdAssayDetails.setSmiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        ccdAssayDetails.setInchiString("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3\n");
        ccdAssayDetails.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");
        ccdAssayDetails.setQcNotes("Repeating chemical structure unit of Polycarbonate made from bisphenol A");
        ccdAssayDetails.setQsarReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        ccdAssayDetails.setMsReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,BrCC(Br)COCC(Br)CBr.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CCCCOCC(COC1=CC=C(C=C1)C(C)(C)C1=CC=C(OCC(COCCCC)OCC2CO2)C=C1)OCC1CO1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CP(=O)(OC1=CC=CC=C1)OC1=CC=CC=C1,CC(=C)C(=O)OCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,ClC#N.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,NCCN1CCNCC1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NCCNCCN.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,N.ClCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NC(O)=O.NC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OCCO.OCCCO.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Pb++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1");
        ccdAssayDetails.setIrisLink("356");
        ccdAssayDetails.setPprtvLink(null);
        ccdAssayDetails.setWikipediaArticle("Bisphenol_A");
        ccdAssayDetails.setIsMarkush(false);
        ccdAssayDetails.setDtxsid("DTXSID7020182");
        ccdAssayDetails.setDtxcid("DTXCID30182");
        ccdAssayDetails.setToxcastSelect("236/981");
        ccdAssayDetails.setTop(14.303691779046352);
        ccdAssayDetails.setScaledTop(0.7151845889523176);
        ccdAssayDetails.setAc50((double) 100);
        ccdAssayDetails.setLogAc50((double) 2);
        ccdAssayDetails.setHitc(0);

        ccdChemicalDetails = factory.createProjection(CcdChemicalDetails.class);
        ccdChemicalDetails.setId(923260L);
        ccdChemicalDetails.setCasrn("80-05-7");
        ccdChemicalDetails.setCompoundId(182);
        ccdChemicalDetails.setGenericSubstanceId(20182);
        ccdChemicalDetails.setPreferredName("Bisphenol A");
        ccdChemicalDetails.setActiveAssays(236);
        ccdChemicalDetails.setCpdataCount(292L);
        ccdChemicalDetails.setMolFormula("C15H16O2");
        ccdChemicalDetails.setMonoisotopicMass(228.115029755);
        ccdChemicalDetails.setAverageMass(228.291);
        ccdChemicalDetails.setPercentAssays((double) 24);
        ccdChemicalDetails.setPubchemCount(null);
        ccdChemicalDetails.setPubmedCount((double) 3850);
        ccdChemicalDetails.setStereo("0");
        ccdChemicalDetails.setSourcesCount(176L);
        ccdChemicalDetails.setQcLevel(1);
        ccdChemicalDetails.setQcLevelDesc("Level 1: Expert curated, highest confidence in accuracy and consistency of unique chemical identifiers");
        ccdChemicalDetails.setIsotope(0);
        ccdChemicalDetails.setMulticomponent(0);
        ccdChemicalDetails.setTotalAssays(981);
        ccdChemicalDetails.setPubchemCid(null);
        ccdChemicalDetails.setRelatedSubstanceCount(154L);
        ccdChemicalDetails.setRelatedStructureCount(25L);
        ccdChemicalDetails.setHasStructureImage(1);
        ccdChemicalDetails.setIupacName(null);
        ccdChemicalDetails.setSmiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        ccdChemicalDetails.setInchiString("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3\n");
        ccdChemicalDetails.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");
        ccdChemicalDetails.setQcNotes("Repeating chemical structure unit of Polycarbonate made from bisphenol A");
        ccdChemicalDetails.setQsarReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        ccdChemicalDetails.setMsReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,BrCC(Br)COCC(Br)CBr.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CCCCOCC(COC1=CC=C(C=C1)C(C)(C)C1=CC=C(OCC(COCCCC)OCC2CO2)C=C1)OCC1CO1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CP(=O)(OC1=CC=CC=C1)OC1=CC=CC=C1,CC(=C)C(=O)OCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,ClC#N.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,NCCN1CCNCC1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NCCNCCN.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,N.ClCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NC(O)=O.NC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OCCO.OCCCO.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Pb++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1");
        ccdChemicalDetails.setIrisLink("356");
        ccdChemicalDetails.setPprtvLink(null);
        ccdChemicalDetails.setWikipediaArticle("Bisphenol_A");
        ccdChemicalDetails.setIsMarkush(false);
        ccdChemicalDetails.setDtxsid("DTXSID7020182");
        ccdChemicalDetails.setDtxcid("DTXCID30182");
        ccdChemicalDetails.setToxcastSelect("236/981");

        chemicalDetailAll = factory.createProjection(ChemicalDetailAll.class);
        chemicalDetailAll.setId(923260L);
        chemicalDetailAll.setCasrn("80-05-7");
        chemicalDetailAll.setCompoundId(182);
        chemicalDetailAll.setGenericSubstanceId(20182);
        chemicalDetailAll.setPreferredName("Bisphenol A");
        chemicalDetailAll.setActiveAssays(236);
        chemicalDetailAll.setCpdataCount(292L);
        chemicalDetailAll.setMolFormula("C15H16O2");
        chemicalDetailAll.setMonoisotopicMass(228.115029755);
        chemicalDetailAll.setAverageMass(228.291);
        chemicalDetailAll.setPercentAssays((double) 24);
        chemicalDetailAll.setPubchemCount(null);
        chemicalDetailAll.setPubmedCount((double) 3850);
        chemicalDetailAll.setStereo("0");
        chemicalDetailAll.setIsotope(0);
        chemicalDetailAll.setSourcesCount(176L);
        chemicalDetailAll.setQcLevel(1);
        chemicalDetailAll.setQcLevelDesc("Level 1: Expert curated, highest confidence in accuracy and consistency of unique chemical identifiers");
        chemicalDetailAll.setIsotope(0);
        chemicalDetailAll.setMulticomponent(0);
        chemicalDetailAll.setTotalAssays(981);
        chemicalDetailAll.setPubchemCid(null);
        chemicalDetailAll.setRelatedSubstanceCount(154L);
        chemicalDetailAll.setRelatedStructureCount(25L);
        chemicalDetailAll.setHasStructureImage(1);
        chemicalDetailAll.setIupacName(null);
        chemicalDetailAll.setSmiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        chemicalDetailAll.setInchiString("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3\n");
        chemicalDetailAll.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");
        chemicalDetailAll.setQcNotes("Repeating chemical structure unit of Polycarbonate made from bisphenol A");
        chemicalDetailAll.setQsarReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        chemicalDetailAll.setMsReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,BrCC(Br)COCC(Br)CBr.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CCCCOCC(COC1=CC=C(C=C1)C(C)(C)C1=CC=C(OCC(COCCCC)OCC2CO2)C=C1)OCC1CO1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CP(=O)(OC1=CC=CC=C1)OC1=CC=CC=C1,CC(=C)C(=O)OCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,ClC#N.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,NCCN1CCNCC1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NCCNCCN.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,N.ClCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NC(O)=O.NC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OCCO.OCCCO.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Pb++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1");
        chemicalDetailAll.setIrisLink("356");
        chemicalDetailAll.setPprtvLink(null);
        chemicalDetailAll.setWikipediaArticle("Bisphenol_A");
        chemicalDetailAll.setDescriptorStringTsv("0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t1\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t1\t1\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0");
        chemicalDetailAll.setIsMarkush(false);
        chemicalDetailAll.setDtxsid("DTXSID7020182");
        chemicalDetailAll.setDtxcid("DTXCID30182");
        chemicalDetailAll.setToxcastSelect("236/981");
        chemicalDetailAll.setExpocatMedianPrediction("5.50E-05");
        chemicalDetailAll.setExpocat("Y");
        chemicalDetailAll.setNhanes("Y");
        chemicalDetailAll.setToxvalData("Y");
        chemicalDetailAll.setWaterSolubilityTest(0.00124451);
        chemicalDetailAll.setWaterSolubilityOpera(0.000745153);
        chemicalDetailAll.setViscosityCpCptestPred(9.66051);
        chemicalDetailAll.setVaporPressuremmhgTestPred(0.00000259418);
        chemicalDetailAll.setVaporPresureMmhgOperaPred(6.77917e-8);
        chemicalDetailAll.setThermalConductivity(150.389);
        chemicalDetailAll.setTetrahymenaPyriformis(0.0000232274);
        chemicalDetailAll.setSurfaceTension(null);
        chemicalDetailAll.setSoilAbsorptionCoefficient(1436.23);
        chemicalDetailAll.setOralRatLd50Mol(0.0179887);
        chemicalDetailAll.setOperaKmDayOperaPred(1.85933);
        chemicalDetailAll.setOctanolWaterpartition(3.32044);
        chemicalDetailAll.setOctanolAirPartitionCoeff(8.38031);
        chemicalDetailAll.setMeltingPointDegctestPred(124.909);
        chemicalDetailAll.setMeltingPointDegcOperaPred(152.696);
        chemicalDetailAll.setHrFatheadMinnow(0.0000141906);
        chemicalDetailAll.setHrDiphniaLc50(0.00000691831);
        chemicalDetailAll.setHenrysLawAtm(1.25155e-7);
        chemicalDetailAll.setFlashPointDegcTestPred(188.141);
        chemicalDetailAll.setDevtoxTestpred(0.711);
        chemicalDetailAll.setDensity(1.195);
        chemicalDetailAll.setBoilingPointDegcTestPred(359.933);
        chemicalDetailAll.setBoilingPointDegcOperaPred(343.191);
        chemicalDetailAll.setBiodegredationHalfLifeDays(15.145);
        chemicalDetailAll.setBiodegredationFactorTestPred(117.22);
        chemicalDetailAll.setBioconcentrationFactorOperaPred(43.6523);
        chemicalDetailAll.setAtmosphericHydroxylationRate(1.63978e-11);
        chemicalDetailAll.setAmesMutagenicityTestPred(0.086);
        chemicalDetailAll.setPkaaOperaPred(9.46);
        chemicalDetailAll.setPkabOperaPred(10.293);

        chemicalDetailAllIds = factory.createProjection(ChemicalDetailAllIds.class);
        chemicalDetailAllIds.setId(923260L);
        chemicalDetailAllIds.setDtxsid("DTXSID7020182");
        chemicalDetailAllIds.setDtxcid("DTXCID30182");

        standard1 = factory.createProjection(ChemicalDetailStandard.class);
        standard1.setId(923260L);
        standard1.setCasrn("80-05-7");
        standard1.setCompoundId(182);
        standard1.setGenericSubstanceid(20182);
        standard1.setPreferredName("Bisphenol A");
        standard1.setActiveAssays(236);
        standard1.setCpdataCount(292L);
        standard1.setMolFormula("C15H16O2");
        standard1.setMonoisotopicMass(228.115029755);
        standard1.setPercentAssays((double) 24);
        standard1.setPubchemCount(null);
        standard1.setPubmedCount((double) 3850);
        standard1.setSourcesCount(176L);
        standard1.setQcLevel(1);
        standard1.setQcLevelDesc("Level 1: Expert curated, highest confidence in accuracy and consistency of unique chemical identifiers");
        standard1.setIsotope(0);
        standard1.setMulticomponent(0);
        standard1.setTotalAssays(981);
        standard1.setPubchemCid(null);
        standard1.setRelatedSubstanceCount(154L);
        standard1.setRelatedStructureCount(25L);
        standard1.setHasStructureImage(1);
        standard1.setIupacName(null);
        standard1.setSmiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        standard1.setInchiString("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3\n");
        standard1.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");
        standard1.setQcNotes("Repeating chemical structure unit of Polycarbonate made from bisphenol A");
        standard1.setQsarReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        standard1.setMsReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,BrCC(Br)COCC(Br)CBr.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CCCCOCC(COC1=CC=C(C=C1)C(C)(C)C1=CC=C(OCC(COCCCC)OCC2CO2)C=C1)OCC1CO1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CP(=O)(OC1=CC=CC=C1)OC1=CC=CC=C1,CC(=C)C(=O)OCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,ClC#N.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,NCCN1CCNCC1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NCCNCCN.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,N.ClCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NC(O)=O.NC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OCCO.OCCCO.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Pb++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1");
        standard1.setirisLink("356");
        standard1.setPprtvLink(null);
        standard1.setWikipediaArticle("Bisphenol_A");
        standard1.setDescriptorStringTsv("0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t1\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t1\t1\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t1\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0");
        standard1.setIsMarkush(false);
        standard1.setDtxsid("DTXSID7020182");
        standard1.setDtxcid("DTXCID30182");
        standard1.setToxcastSelect("236/981");

        standard2 = factory.createProjection(ChemicalDetailStandard2.class);
        standard2.setId(923260L);
        standard2.setDtxsid("DTXSID7020182");
        standard2.setCasrn("80-05-7");
        standard2.setPreferredName("Bisphenol A");
        standard2.setMolFormula("C15H16O2");
        standard2.setMonoisotopicMass(228.115029755);
        standard2.setQcLevel(1);
        standard2.setQcLevelDesc("Level 1: Expert curated, highest confidence in accuracy and consistency of unique chemical identifiers");
        standard2.setIupacName(null);
        standard2.setSmiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        standard2.setInchiString("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3\n");
        standard2.setAverageMass(228.291);
        standard2.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");

        chemicalIdentifier = factory.createProjection(ChemicalIdentifier.class);
        chemicalIdentifier.setCasrn("80-05-7");
        chemicalIdentifier.setPreferredName("Bisphenol A");
        chemicalIdentifier.setIupacName(null);
        chemicalIdentifier.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");
        chemicalIdentifier.setDtxsid("DTXSID7020182");
        chemicalIdentifier.setDtxcid("DTXCID30182");

        chemicalStructure = factory.createProjection(ChemicalStructure.class);
        chemicalStructure.setId(923260L);
        chemicalStructure.setCasrn("80-05-7");
        chemicalStructure.setPreferredName("Bisphenol A");
        chemicalStructure.setHasStructureImage(1);
        chemicalStructure.setSmiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        chemicalStructure.setInchiString("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3\n");
        chemicalStructure.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");
        chemicalStructure.setQsarReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        chemicalStructure.setMsreadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,BrCC(Br)COCC(Br)CBr.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CCCCOCC(COC1=CC=C(C=C1)C(C)(C)C1=CC=C(OCC(COCCCC)OCC2CO2)C=C1)OCC1CO1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CP(=O)(OC1=CC=CC=C1)OC1=CC=CC=C1,CC(=C)C(=O)OCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,ClC#N.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,NCCN1CCNCC1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NCCNCCN.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,N.ClCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NC(O)=O.NC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OCCO.OCCCO.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Pb++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1");
        chemicalStructure.setDtxsid("DTXSID7020182");
        chemicalStructure.setDtxcid("DTXCID30182");

        compact = factory.createProjection(Compact.class);
        compact.setDtxsid("DTXSID7020182");
        compact.setCasrn("80-05-7");
        compact.setPreferredName("Bisphenol A");

        ntaToolkit = factory.createProjection(NtaToolkit.class);
        ntaToolkit.setCasrn("80-05-7");
        ntaToolkit.setPreferredName("Bisphenol A");
        ntaToolkit.setActiveAssays(236);
        ntaToolkit.setCpDataCount(292L);
        ntaToolkit.setMolFormula("C15H16O2");
        ntaToolkit.setMonoisotopicMass(228.115029755);
        ntaToolkit.setPercentAssays((double) 24);
        ntaToolkit.setSourcesCount(176L);
        ntaToolkit.setTotalAssays(981);
        ntaToolkit.setSmiles("CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1");
        ntaToolkit.setInchikey("IISBACLAFKSPIT-UHFFFAOYSA-N");
        ntaToolkit.setMsReadySmiles("[13CH3]C([13CH3])(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[2H]C1=C([2H])C(=C([2H])C([2H])=C1O)C(C)(C)C1=C([2H])C([2H])=C(O)C([2H])=C1[2H],[2H]C([2H])([2H])C(C1=CC=C(O)C=C1)(C1=CC=C(O)C=C1)C([2H])([2H])[2H],[2H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[2H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Ba++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,BrCC(Br)COCC(Br)CBr.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)([13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1)[13C]1=[13CH][13CH]=[13C](O)[13CH]=[13CH]1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CCCCOCC(COC1=CC=C(C=C1)C(C)(C)C1=CC=C(OCC(COCCCC)OCC2CO2)C=C1)OCC1CO1,CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CP(=O)(OC1=CC=CC=C1)OC1=CC=CC=C1,CC(=C)C(=O)OCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,ClC#N.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,CS(O)(=O)=O.CS(O)(=O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[H]OC1=C([2H])C([2H])=C(C([2H])=C1[2H])C(C1=C([2H])C([2H])=C(O[H])C([2H])=C1[2H])(C([2H])([2H])[2H])C([2H])([2H])[2H],[Na+].[Na+].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1,NCCN1CCNCC1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NCCNCCN.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,N.ClCC1CO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,NC(O)=O.NC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,O=C1CCCCCO1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OCCO.OCCCO.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OC(O)=O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,OP(O)O.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1.CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1,[Pb++].CC(C)(C1=CC=C([O-])C=C1)C1=CC=C([O-])C=C1");
        ntaToolkit.setDtxsid("DTXSID7020182");
        ntaToolkit.setDtxcid("DTXCID30182");
        ntaToolkit.setExpocatMedianPrediction("5.50E-05");
        ntaToolkit.setExpocat("Y");
        ntaToolkit.setNhanes("Y");

        detailsPage = Page.builder()
                .size(1000)
                .total(1884812L)
                .next(591387L)
                .data(Collections.singletonList(standard2))
                .build();

        idsPage = Page.builder()
                .size(1000)
                .total(1884812L)
                .next(591387L)
                .data(Collections.singletonList(chemicalDetailAllIds))
                .build();

    }

    @Test
    void testGetAllChemicalDetails() throws Exception {

        when(detailService.getAllChemicals(any(), any(), any(), eq(null))).thenReturn(detailsPage);
        mockMvc.perform(get("/chemical/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].dtxsid").value(standard2.getDtxsid()));
    }

    @Test
    void testGetAllChemicalIds() throws Exception {

        when(detailService.getAllChemicals(any(), any(), any(), eq("all-ids"))).thenReturn(idsPage);

        mockMvc.perform(get("/chemical/all")
                .param("projection", "all-ids"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].dtxsid").value(chemicalDetailAllIds.getDtxsid()));
    }

    // when no projection is provided, an exception is thrown and the request is redirected to a private switch case on the resource page (after the fact)
    // the default projection is chemicaldetailall
    @Test
    void testGetChemicalDetailsByDtxsid() throws Exception {
        final List<Object> details = Collections.singletonList(chemicalDetailAll);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(eq(ids), any(), eq("dtxsid"))).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalDetailAll.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxsidCcdAssayDetails() throws Exception {
        final List<CcdAssayDetails> details = Collections.singletonList(ccdAssayDetails);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getCcdAssayDetails(ids)).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "ccdassaydetails"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ccdAssayDetails.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxsidCcdChemicalDetails() throws Exception {
        final List<CcdChemicalDetails> details = Collections.singletonList(ccdChemicalDetails);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(ids, CcdChemicalDetails.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "ccdchemicaldetails"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ccdChemicalDetails.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxsidAllDetails() throws Exception {
        final List<ChemicalDetailAll> details = Collections.singletonList(chemicalDetailAll);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailAll.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "chemicaldetailall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalDetailAll.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxsidDetailStandard() throws Exception {
        final List<ChemicalDetailStandard> details = Collections.singletonList(standard1);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailStandard.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "chemicaldetailstandard"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(standard1.getDtxsid())));
    }

    @Test
    void testGetChemicalDetailsByDtxsidChemicalIdentifier() throws Exception {
        final List<ChemicalIdentifier> details = Collections.singletonList(chemicalIdentifier);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalIdentifier.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "chemicalidentifier"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalIdentifier.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxsidChemicalStructure() throws Exception {
        final List<ChemicalStructure> details = Collections.singletonList(chemicalStructure);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalStructure.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "chemicalstructure"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalStructure.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxsidCompact() throws Exception {
        final List<Compact> details = Collections.singletonList(compact);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(ids, Compact.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "compact"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(compact.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxsidNtaToolkit() throws Exception {
        final List<NtaToolkit> details = Collections.singletonList(ntaToolkit);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};

        when(detailService.getChemicalDetailsForBatch(ids, NtaToolkit.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "ntatoolkit"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ntaToolkit.getDtxsid())));

    }

    // when no projection is provided, an exception is thrown and the request is redirected to a private switch case on the resource page (after the fact)
    // the default projection is chemicaldetailall
    @Test
    void testGetChemicalDetailsByDtxcid() throws Exception {
        final List<Object> details = Collections.singletonList(chemicalDetailAll);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(eq(ids), any(), eq("dtxcid"))).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalDetailAll.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidCcdAssayDetails() throws Exception {
        final List<CcdAssayDetails> details = Collections.singletonList(ccdAssayDetails);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getCcdAssayDetails(ids)).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "ccdassaydetails"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ccdAssayDetails.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidCcdChemicalDetails() throws Exception {
        final List<CcdChemicalDetails> details = Collections.singletonList(ccdChemicalDetails);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(ids, CcdChemicalDetails.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "ccdchemicaldetails"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ccdChemicalDetails.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidAllDetails() throws Exception {
        final List<ChemicalDetailAll> details = Collections.singletonList(chemicalDetailAll);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailAll.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "chemicaldetailall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalDetailAll.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidDetailStandard() throws Exception {
        final List<ChemicalDetailStandard> details = Collections.singletonList(standard1);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailStandard.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "chemicaldetailstandard"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(standard1.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidChemicalIdentifier() throws Exception {
        final List<ChemicalIdentifier> details = Collections.singletonList(chemicalIdentifier);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalIdentifier.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "chemicalidentifier"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalIdentifier.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidChemicalStructure() throws Exception {
        final List<ChemicalStructure> details = Collections.singletonList(chemicalStructure);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalStructure.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "chemicalstructure"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalStructure.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidCompact() throws Exception {
        final List<Compact> details = Collections.singletonList(compact);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(ids, Compact.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "compact"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(compact.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByDtxcidNtaToolkit() throws Exception {
        final List<NtaToolkit> details = Collections.singletonList(ntaToolkit);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};

        when(detailService.getChemicalDetailsForBatch(ids, NtaToolkit.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(get("/chemical/detail/search/by-dtxcid/{dtxcid}", "DTXCID30182")
                .param("projection", "ntatoolkit"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ntaToolkit.getDtxsid())));

    }

    // when no projection is provided, the batch searches defer to the default projection chemicaldetailall
    @Test
    void testGetChemicalDetailsByBatchDtxsid() throws Exception {
        final List<ChemicalDetailAll> details = Collections.singletonList(chemicalDetailAll);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailAll.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalDetailAll.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxsidCcdAssayDetails() throws Exception {
        final List<CcdAssayDetails> details = Collections.singletonList(ccdAssayDetails);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getCcdAssayDetails(ids)).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "ccdassaydetails")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ccdAssayDetails.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxsidCcdChemicalDetails() throws Exception {
        final List<CcdChemicalDetails> details = Collections.singletonList(ccdChemicalDetails);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, CcdChemicalDetails.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "ccdchemicaldetails")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ccdChemicalDetails.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxsidAllDetails() throws Exception {
        final List<ChemicalDetailAll> details = Collections.singletonList(chemicalDetailAll);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailAll.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "chemicaldetailall")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalDetailAll.getDtxsid())));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxsidDetailStandard() throws Exception {
        final List<ChemicalDetailStandard> details = Collections.singletonList(standard1);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailStandard.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "chemicaldetailstandard")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(standard1.getDtxsid())));
    }

    @Test
    void testGetChemicalDetailsByDtxsidBatchChemicalIdentifier() throws Exception {
        final List<ChemicalIdentifier> details = Collections.singletonList(chemicalIdentifier);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalIdentifier.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "chemicalidentifier")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(chemicalIdentifier.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxsidChemicalStructure() throws Exception {
        final List<ChemicalStructure> details = Collections.singletonList(chemicalStructure);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalStructure.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "chemicalstructure")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(chemicalStructure.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxsidCompact() throws Exception {
        final List<Compact> details = Collections.singletonList(compact);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, Compact.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "compact")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(compact.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxsidNtaToolkit() throws Exception {
        final List<NtaToolkit> details = Collections.singletonList(ntaToolkit);
        String dtxsid = "DTXSID7020182";
        String[] ids = new String[]{dtxsid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, NtaToolkit.class, "dtxsid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxsid/")
                .param("projection", "ntatoolkit")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(ntaToolkit.getDtxsid()));

    }

    // when no projection is provided, the batch searches defer to the default projection chemicaldetailall
    @Test
    void testGetChemicalDetailsByBatchDtxcid() throws Exception {
        final List<ChemicalDetailAll> details = Collections.singletonList(chemicalDetailAll);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailAll.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(chemicalDetailAll.getDtxsid()));
    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidCcdAssayDetails() throws Exception {
        final List<CcdAssayDetails> details = Collections.singletonList(ccdAssayDetails);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getCcdAssayDetails(ids)).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "ccdassaydetails")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(ccdAssayDetails.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidCcdChemicalDetails() throws Exception {
        final List<CcdChemicalDetails> details = Collections.singletonList(ccdChemicalDetails);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, CcdChemicalDetails.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "ccdchemicaldetails")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(ccdChemicalDetails.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidAllDetails() throws Exception {
        final List<ChemicalDetailAll> details = Collections.singletonList(chemicalDetailAll);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailAll.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "chemicaldetailall")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(chemicalDetailAll.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidDetailStandard() throws Exception {
        final List<ChemicalDetailStandard> details = Collections.singletonList(standard1);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalDetailStandard.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "chemicaldetailstandard")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(standard1.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidChemicalIdentifier() throws Exception {
        final List<ChemicalIdentifier> details = Collections.singletonList(chemicalIdentifier);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalIdentifier.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "chemicalidentifier")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(chemicalIdentifier.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidChemicalStructure() throws Exception {
        final List<ChemicalStructure> details = Collections.singletonList(chemicalStructure);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, ChemicalStructure.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "chemicalstructure")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(chemicalStructure.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidCompact() throws Exception {
        final List<Compact> details = Collections.singletonList(compact);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, Compact.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "compact")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(compact.getDtxsid()));

    }

    @Test
    void testGetChemicalDetailsByBatchDtxcidNtaToolkit() throws Exception {
        final List<NtaToolkit> details = Collections.singletonList(ntaToolkit);
        String dtxcid = "DTXCID30182";
        String[] ids = new String[]{dtxcid};
        String jsonBody = new JsonMapper().writeValueAsString(ids);

        when(detailService.getChemicalDetailsForBatch(ids, NtaToolkit.class, "dtxcid")).thenReturn(details);

        mockMvc.perform(post("/chemical/detail/search/by-dtxcid/")
                .param("projection", "ntatoolkit")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(ntaToolkit.getDtxsid()));

    }

    @Test
    public void findDetailsBySmilesWithBPA_shouldReturnProperResults() throws Exception {
        final var smiles = "CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1";
        final var serviceResult = Collections.singletonList(compact);

        when(similarSearchService.searchBySmiles(smiles)).thenReturn(serviceResult);

        var result = mockMvc.perform(get("/chemical/detail/search/by-smiles/")
                .param("smiles", smiles)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value("DTXSID7020182"));
    }

    @Test
    public void findDetailsWithMessySmiles_shouldReturnEmptyList() throws Exception {
        final var smiles = "[O+]#[C-][Fe]([C-]#[O+])[C-]#[O+].Cc1ccc(C(C)=O)cc1";
        final var serviceResult = Collections.<Compact>emptyList();

        when(similarSearchService.searchBySmiles(smiles)).thenReturn(serviceResult);

        var result = mockMvc.perform(get("/chemical/detail/search/by-smiles/")
                .param("smiles", smiles)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void findDetailsWithBadSmiles_shouldReturnBadRequest() throws Exception {
        final var smiles = "OU812";
        final var serviceResult = new UnparseableSmilesException(smiles, new RuntimeException("reason"));

        when(similarSearchService.searchBySmiles(smiles))
                .thenThrow(serviceResult);

        var result = mockMvc.perform(get("/chemical/detail/search/by-smiles/")
                .param("smiles", smiles)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

}
