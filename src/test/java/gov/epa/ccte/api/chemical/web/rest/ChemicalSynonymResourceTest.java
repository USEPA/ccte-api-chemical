package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the ChemicalSynonymResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.CoreMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.projection.CcdSynonymFlatProjection;
import gov.epa.ccte.api.chemical.projection.ChemicalSynonymAll;
import gov.epa.ccte.api.chemical.repository.ChemicalSynonymRepository;

import java.util.*;

@ActiveProfiles("test")
@WebMvcTest(ChemicalSynonymResource.class)
@RunWith(MockitoJUnitRunner.class)
public class ChemicalSynonymResourceTest {

	@Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalSynonymRepository synonymRepository;

    private CcdSynonymFlatProjection ccdSynonymFlatProjection;
    private ChemicalSynonymAll chemicalSynonymAll;
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    // Mock implementation of ChemicalSynonymAll interface for testing
    // This is needed because Jackson (the JSON serializer) cannot serialize Mockito mocks, which contain internal fields that are not compatible with standard Java bean serialization
    private static class ChemicalSynonymAllMock implements ChemicalSynonymAll {
        // Fields
    	private String dtxsid;
        private String[] pcCode;
        private String[] valid;
        private String[] good;
        private String[] deletedCasrn;
        private String[] other;
        private String[] beilstein;
        private String[] alternateCasrn;
        // Constructor
        public ChemicalSynonymAllMock(String dtxsid, String pcCode[], String[] valid, String[] good, String[] deletedCasrn, String[] other, String[] beilstein, String[] alternateCasrn) {
            this.dtxsid = dtxsid;
            this.pcCode = pcCode;
            this.valid = valid;
            this.good = good;
            this.deletedCasrn = deletedCasrn;
            this.other = other;
            this.beilstein = beilstein;
            this.alternateCasrn = alternateCasrn;
        }
        // Getters
        @Override public String getDtxsid() { return dtxsid; }
        @Override public String[] getPcCode() { return pcCode; }
        @Override public String[] getValid() { return valid; }
        @Override public String[] getGood() { return good; }
        @Override public String[] getDeleted() { return deletedCasrn; }
        @Override public String[] getOther() { return other; }
        @Override public String[] getBeilstein() { return beilstein; }
        @Override public String[] getAlternate() { return alternateCasrn; }

    }

    @BeforeEach
    void setUp(){
    	
    	ccdSynonymFlatProjection = factory.createProjection(CcdSynonymFlatProjection.class);
    	ccdSynonymFlatProjection.setSynonym("4,4’-Propane-2,2-diyldiphenol");
    	ccdSynonymFlatProjection.setQuality("valid_synonym");
    	
    	String[] valid = {"4,4’-Propane-2,2-diyldiphenol","80-05-7","Bisphenol A","Phenol, 4,4'-(1-methylethylidene)bis-"};
    	String[] good = {"2,2-Bis(4-hydroxyphenyl)propane","2,2-Bis(4'-hydroxyphenyl) propane","2,2'-Bis(4-hydroxyphenyl)propane","2,2-BIS-(4-HYDROXY-PHENYL)-PROPANE","2,2-Bis(p-hydroxyphenyl)propane","2,2-Di(4-Hydroxyphenyl) Propane","2,2-DI(4-HYDROXYPHENYL)PROPANE","2,2-Di(4-phenylol)propane","4,4'-(1-Methylethylidene)bisphenol","4,4'-Bisphenol A","(4,4'-Dihydroxydiphenyl)dimethylmethane","4,4'-DIHYDROXYPHENYL-2,2-PROPANE","4,4'-isopropilidendifenol","4,4'-Isopropylidendiphenol","4,4'-Isopropylidene bisphenol","4,4'-Isopropylidenebis[phenol]","4,4'-isopropylidenediphenol","4,4-ISOPROPYLIDENE DIPHENYL","4,4'-Methylethylidenebisphenol","Bis(4-hydroxyphenyl)dimethylmethane","BIS[PHENOL], 4,4'-(1-METHYLETHYLIDENE)-","BISPHENOL, 4,4'-(1-METHYLETHYLIDENE)-","Bisphenol-A","Bis(p-hydroxyphenyl)propane","Diphenol methylethylidene","Diphenylolpropane","Hidorin F 285","Isopropylidenebis(4-hydroxybenzene)","NSC 1767","NSC 17959","Parabis","Parabis A","Phenol, 4,4'-isopropylidenedi-","Pluracol 245","p,p'-Bisphenol A","p,p'-Dihydroxydiphenylpropane","p,p'-Isopropylidenebisphenol","p,p'-Isopropylidenediphenol","P,P'-ISOPROPYLIDENE DIPHENOL","Rikabanol", "β,β'-Bis(p-hydroxyphenyl)propane"};
    	String[] deleted = {"137885-53-1","1429425-26-2","146479-75-6","27360-89-0","28106-82-3","37808-08-5"};
    	String[] other = {"1,1'-(1-Methylethylidene)bisphenylol, 9CI","(1-methylethylidene)bis-Phenol","201-245-8","201-245-8","2,2-(4,4-Dihydroxydiphenyl)propane","2,2-(4,4'-Dihydroxydiphenyl)propane","2,2-Bis(4,4'-hydroxyphenyl)propane","2,2-Bis-4'-hydroxyfenylpropan","2,2-Bis (4-hydroxyphenol) propane","2,2-bis(4-hydroxyphenyl)propane","2, 2-Bis(4-hydroxyphenyl)propane","2,2-Bis(4'-hydroxyphenyl)propane","2,2-Bis(4'-hydroxyphenyl) propane","2,2'-Bis(4-hydroxyphenyl)propane","2,2-Bis[4-Hydroxyphenyl]propane","2, 2-Bis(hydroxyphenyl)propane","2,2-Bis(hydroxyphenyl)propane","2,2-Bis(hydroxyphenyl)propane","2,2-Bis(hydroxyphenyl)propane","2,2-Bis(p-hydroxyphenyl)propane","2,2-Bis(p-hydroxyphenyl)-Propane","2,2-Di-(4'-Hydroxyphenyl)-propane","2, 2-Di(4-phenylol)propane","4-06-00-06717","4-[1-(4-Hydroxyphenyl)-1-methylethyl]phenol","4-[2-(4-hydroxyphenyl)propan-2-yl]phenol","4-[2-(4-hydroxyphenyl)propan-2-yl]phenol","4,4'-(1-Methylethane-1,1-diyl)diphenol","4,4'-(1-Methylethylidene)bisphenol","4,4'-(1-Methylethylidene)bis-Phenol","4,4'-(1-Methylethylidene)bisphenol, 9CI","4, 4'-Bisphenol A","4,4'-Dihydroxdiphenylpropane","4,4'-dihydroxy-2,2-diphenylpropane","4,4'-Dihydroxy-2,2-diphenylpropane","4, 4'-Dihydroxydiphenyl-2,2-propane","4,4'-Dihydroxydiphenyl-2,2-propane","4, 4'-Dihydroxydiphenyldimethylmethane","4,4'-Dihydroxydiphenyldimethylmethane","4, 4'-Dihydroxydiphenylpropane","4,4'-Dihydroxydiphenylpropane","4,4'-Dimethylmethylenediphenol","4,4'-Dimethylmethylenedi-Phenol","4,4'-Isopropylidenebisphenol","4,4'-Isopropylidenebis[phenol]","4,4-Isopropylidenediphenol","4,4-Isopropylidenediphenol","4,4'-Isopropylidenediphenol","4,4'-Isopropylidenediphenol","4,4'-Isopropylidene diphenol","4,4[-Isopropylidenediphenol","4,4'-Isopropylidenedi-Phenol","4,4'-ISOPROPYLIDENE-DIPHENOL","4,4'-Isopropylidenediphenol B","4_4'-isopropylidenedi-phenol (bisphenol a)","4,4'-ISOPROPYLIDENEDIPHENOL (BISPHENOL A)","4_4'-isopropylidenediphenol_ (bisphenol a) (sara 313)","4_4'-isopropylidenediphenol_ (bisphenol a)         (sara iii)","4_4'-isopropylidenediphenol (sara 313)","4_4'-isopropylidenediphenol   (sara iii)","4_4'-isopropylidenediphenol  (sara iii)","4_4'-isopropylidenediphenol (sara iii)","4_4'-isopropylidenediphenol  (sara iii)/bisphenol a","4_4'-isopropylidenediphenol  (sara iii)/bisphenol-a","4,4' Isopropylidinediphenol","4,4'-(Propane-2,2-diyl)diphenol","4,4'-Propane-2,2-diyldiphenol","4,4'-PROPANE-2,2-DIYLDIPHENOL","4,4’-Propane-2,2-diyldiphenol (bisphenol A)","beta, beta'-Bis(p-hydroxyphenyl)propane","beta,beta'-Bis(p-hydroxyphenyl)propane","beta,beta-Di-(p-hydroxyphenyl)propane","beta-Di-(p-hydroxyphenyl)propane","beta-Di-p-hydroxyphenylpropane","beta-Di-p-hydroxyphenylpropane","Biphenol a","Biphenol A","Bis(4-hyd roxyphenyl) dimethylmethane","Bis(4-hydroxyphenyl) dimethylmethane","Bis(4-hydroxyphenyl)propane","Bis(4-hydroxyphenyl) propane","Bisfenol A","Bisferol a","Bisphenol","bisphenol-A","Bis-phenol A","Bisphenol A","Bisphenol A.","Bisphenol-A (.alpha.)","bisphenol a_ bisphenol","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan *96-1*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan  *96-2*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan  *96-3*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan   *96-4*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan  *96-4*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan *96-4*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan  *97-1*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan  *97-2*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan  *97-3*","bisphenol a_ bisphenol_ 2_2-bis-4'-hydroxylpropan  *98-1*","Bisphenol A (BPA)","Bisphenol A (BPA) (creatinine adjusted)","bisphenol a resin","bisphenol a (sara 313)","bisphenol a (sara iii)","bisphenol-a (sara iii)","Bisphenol A,  water, dissolved (ug/l)","BPA","BPA","BRN 1107700","Di-2,2-(4-Hydroxyphenyl)propane","Dian","Dian","Diano","Diano","Dimethyl bis(p-hydroxyphenyl)methane","Dimethylbis(p-hydroxyphenyl)methane","Dimethylmethylene-p,p'-diphenol","DIPHENYLOLPROPANE","EC No.: 201-245-8","EINECS 201-245-8","Hydrogenated bisphenol a","IISBACLAFKSPIT-UHFFFAOYSA-N","Ipognox 88","Isopropylidenediphenol","Millad hbpa","mixture of triisobutylene, diisobutylene (2,4,4-trimethylpentene) and  4,4'-(methylethylidene) bisphenol (bisphenol a)","NCGC00090952-07","NCGC00260537-01","NCI-C50635","Parabis A","Phenol, (1-methylethylidene)bis-","Phenol, (1-methylethylidene)bis-","phenol_ 4_4'-(1-methylethylidene) bis-","Phenol, 4,4'-(1-methylethylidene)bis-","Phenol, 4,4'-dimethylmethylenedi-","phenol_ 4_4'-isopropylenedi-_ (4_4'-isopropylidenediphenol) (bisphenol a) (sara iii)","phenol_ 4_4'-isopropylenedi-_ (bisphenol a resin) (sara 313)","phenol_ 4_4'-isopropylenedi-_ (bisphenol a)  (sara 313)","Phenol, 4,4'-isopropylidenedi-","phenol_4_4'-isopropylidenedi-_ (4_4'-isopropylidenediphenol)","phenol_ 4_4'-isopropylidenedi-_4_4'-isopropylidenediphenol_ (bisphenol a) (sara iii)","phenol_ 4_4'-isopropylidenedi-_ (4_4'- isopropylidenediphenol (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (4_4'- isopropylidenediphenol) (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (4_4'-isopropylidene diphenol) (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (4_4'-isopropylidenediphenol) (sara 313)","phenol_ 4_4'-isopropylidenedi)_ (4_4'-isopropylidenediphenol) (sara 313)","phenol_ 4_4'-isopropyl idenedi-_ (4_4'-isopropylidenediphenol) (sara 313). ld50 (oral_ rat): 3000 mg/kg.","phenol_ 4_4'-isopropylidenedi-_ (4_4'-isopropylidenediphenol) (sara 313). vp:0.","phenol_ 4_4'-isopropylidenedi-_ (4_4'-isopropylidenediphenol) (sara 313) vp: 0.00","phenol_ 4_4'-isopropylidenedi-_ (4_4'-isopropylidenediphenol) (sara iii)","phenol_4_4'-isopropylidenedi-_(4_4'-isopropylidenediphenol) (sara iii)","phenol_ 4_4'-isopropylidene di-_ (bisphenol a)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a)","phenol_4_4'-isopropylidenedi-_ (bisphenol a)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a (bpa)) (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a). ld50 (oral):2000 mg/kg.","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a).ld50:(oral_rat) 2000 mg/kg","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a).ld50:(oral_rat) 2000 mg/kg.","phenol_4_4'-isopropylidenedi-_ (bisphenol a)_(mfr's invalid cas#: 80-5-7).","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a polycarbonate)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a resin)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a resin (bpa))","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a resin (bpa) (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a resin (bpa)) (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a resin) (bpa) (sara 313). ld50:(oral_rat) >5 g/kg.","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a resin)  (saraiii)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a resin) (saraiii)","phenol_ 4_4'-isopropylidenedi-_ ( bisphenol a) (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a) (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol-a) (sara 313)","phenol_4_4'-isopropylidenedi-_ (bisphenol a) (sara 313)","phenol_4_4'-isopropylidenedi-_ (bisphenol a) (sara 313))","phenol_4.4'-isopropylidenedi-_ (bisphenol a)  (sara 313)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a) (sara 313) ld50: (oral) 3250 mg/kg","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a) (sara 313) ld50 (oral_ rat):4.2 g/kg","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a) (sara 313) % wt: <100 ppm","phenol_ 4_4'-isopropylidenedi-_  (bisphenol a)  (sara iii)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a) (sara iii)","phenol_4_4'-isopropylidenedi_(bisphenol-a) (sara iii)","phenol_4_4'-isopropylidenedi-_(bisphenol-a) (sara iii)","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a)  (sara iii).ld50:(oral) 3250 mg/kg.","phenol_ 4_4'-isopropylidenedi-_ (bisphenol a). vp: 0.01 @ 20c. ld50 (oral_rat): 1999.9 mg/kg.","phenol_4_4'-isopropylidenedi-_ (bisphenol a). vp: 0.01 @20c. ld50: oral(rat)2000 mg/kg.","phenol_ 4_4'-isopropylidenedi-_(p_p'-isopropylidenediphenol) (sara 313)","phenol_ 4_4'-isopropylidenedi-_       (sara iii)(4_4'-isopropylidenediphenol) (bisphenol-a)","phenol_ 4_4'-isopropylideneol-_ (bisphenol a) (sara 313)","phenol_ 4_4_-isopropylidenidi-_ (4_4'-isopropylidenediphenol) (bisphenol a) (sara iii)","phenol_ 4_4'-isopropylidinedi-_ (bisphenol a resin) (sara iii)","Pluracol 245","P, p'-Dihydroxydiphenyldimethylmethane","P,p'-Dihydroxydiphenyldimethylmethane","P, p'-Dihydroxydiphenylpropane","p,p'-Isopropylidenebisphenol","p,p'-Isopropylidenediphenol","P, p'-Isopropylidenediphenol","Propane, 2,2-bis(p-hydroxyphenyl)-","Rikabanol","total bisphenol a","Tox21_202992","Tox21_400088","Ucar bisphenol a","Ucar bisphenol HP","UNII-MLT3645I99"};
    	chemicalSynonymAll = new ChemicalSynonymAllMock("DTXSID7020182", null, valid, good, deleted, other, null, null);
    }
    
    @Test
    void testGetSynonymsByDtxsid() throws Exception {
        final List<ChemicalSynonymAll> synonyms = Collections.singletonList(chemicalSynonymAll);

    	when(synonymRepository.findByDtxsidAndIsPublic("DTXSID7020182", true, ChemicalSynonymAll.class)).thenReturn(Optional.of(synonyms.get(0)));
        
        mockMvc.perform(get("/chemical/synonym/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
  		.andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
  		.andExpect(content().string(containsString(chemicalSynonymAll.getDtxsid()))); 
       
        

    }
    
    @Test
    void testGetSynonymsByDtxsidCcd() throws Exception {
        final List<CcdSynonymFlatProjection> synonyms = Collections.singletonList(ccdSynonymFlatProjection);

        when(synonymRepository.getFlatSynonymsByDtxsid("DTXSID7020182")).thenReturn(synonyms);

        mockMvc.perform(get("/chemical/synonym/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
				.param("projection", "ccd-synonyms"))
				.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        		.andExpect(jsonPath("$[0].synonym").value(ccdSynonymFlatProjection.getSynonym()));

    }
    
    @Test
    void testGetSynonymsByBatchDtxsid() throws Exception {
    	final List<ChemicalSynonymAll> synonyms = Collections.singletonList(chemicalSynonymAll);
        String[] jsonArray = {"DTXSID7020182"};
        String jsonBody = new ObjectMapper().writeValueAsString(jsonArray);
        		
        when(synonymRepository.findByDtxsidInAndIsPublicOrderByDtxsidAsc(Arrays.asList(jsonArray), true, ChemicalSynonymAll.class)).thenReturn(synonyms);
        
        mockMvc.perform(post("/chemical/synonym/search/by-dtxsid/")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(jsonBody))
        		.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dtxsid").value(chemicalSynonymAll.getDtxsid()))
                .andReturn();
    }
    
}