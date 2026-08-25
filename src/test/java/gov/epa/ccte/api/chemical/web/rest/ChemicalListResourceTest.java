package gov.epa.ccte.api.chemical.web.rest;

import org.junit.jupiter.api.BeforeEach;

//This will test REST end-points in the ChemicalListResource.java using WebMvcTest and MockitoBean

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

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.dto.ChemicalListWithDtxsidsDTO;
import gov.epa.ccte.api.chemical.projection.chemicallist.*;
import gov.epa.ccte.api.chemical.repository.ChemicalListRepository;
import gov.epa.ccte.api.chemical.repository.ChemicalListChemicalRepository;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.web.rest.requests.ChemicalListsAndDtxsids;

import java.sql.Timestamp;
import java.util.*;

@ActiveProfiles("test")
@MockitoSettings(strictness = Strictness.WARN)
@WebMvcTest(ChemicalListResource.class)
@ExtendWith(MockitoExtension.class)
public class ChemicalListResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ChemicalListRepository listRepository;
    @MockitoBean
    private ChemicalListChemicalRepository chemicalListChemicalRepository;
    @MockitoBean
    private SearchChemicalService listService;

    private ChemicalList chemicalList;
    private ChemicalListName listName;
    private ChemicalListWithDtxsidsDTO listWithDtxsids;
    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @BeforeEach
    public void setup() {

        chemicalList = ChemicalList.builder()
                .id(1348)
                .listName("BIOSOLIDS")
                .type("federal")
                .shortDescription("Biosolids lists change over time and are versioned iteratively. This panel navigates between the various versions.")
                .longDescription("Biosolids lists change over time and are versioned iteratively. This panel navigates between the various versions which will be released over time.\r\nThe list of substances displayed below represents the latest iteration of biosolids (BIOSOLOIDS2021 - November 2021). For the versioned lists please use the hyperlinked lists below.<br/><br/>\r\n\r\n<a href='https://comptox.epa.gov/dashboard/chemical_lists/BIOSOLIDS2021' target='_blank'>BIOSOLIDS2021 - November 2021</a><br/><br/>\r\n\r\n<a href='https://comptox.epa.gov/dashboard/chemical_lists/BIOSOLIDS2022' target='_blank'>BIOSOLIDS2022 - December 2022</a><br/><br/>")
                .chemicalCount(726L)
                .updatedAt(new Date().toInstant())
                .build();

        listName = factory.createProjection(ChemicalListName.class);
        listName.setListName("BIOSOLIDS");

        listWithDtxsids = new ChemicalListWithDtxsidsDTO("ACSREAG", null, "other", "The ACS Committee on Analytical Reagents sets purity specifications for almost 500 reagent chemicals and over 500 standard-grade reference materials.", "The ACS Committee on Analytical Reagents sets purity specifications for almost 500 reagent chemicals and over 500 standard-grade reference materials. These specifications have become the de facto standards for chemicals used in many high-purity applications. In addition to detailing these specifications, ACS Reagent Chemicals provides general physical properties and analytical uses for all reagent chemicals as well as guidelines for standard analytical methods. The online book is available at <a href='https://pubs.acs.org/isbn/9780841230460' target='_blank'>https://pubs.acs.org/isbn/9780841230460</a>", 414L, Timestamp.valueOf("2022-05-19 09:48:41"), 363, null);
        listWithDtxsids.setDtxsids(Arrays.asList(
                "DTXSID00143067", "DTXSID0020078", "DTXSID0020103", "DTXSID0020230", "DTXSID0020650", "DTXSID0020789", "DTXSID0020868", "DTXSID0020941", "DTXSID0021125", "DTXSID0021177", "DTXSID0021206", "DTXSID0021381", "DTXSID0021383", "DTXSID0021759", "DTXSID0021965", "DTXSID0024268", "DTXSID0024395", "DTXSID0025234", "DTXSID0027014", "DTXSID0029634", "DTXSID0029638", "DTXSID0029684", "DTXSID0029713", "DTXSID0032079", "DTXSID0035667", "DTXSID0040125", "DTXSID0040175", "DTXSID0042167", "DTXSID0049810", "DTXSID0051445", "DTXSID0058770", "DTXSID0061660", "DTXSID00858866", "DTXSID00892225", "DTXSID00893439", "DTXSID00894906", "DTXSID00937140", "DTXSID101026650", "DTXSID10143230", "DTXSID10143674", "DTXSID1020190", "DTXSID1020194", "DTXSID1020229", "DTXSID1020306", "DTXSID1020647", "DTXSID1020774", "DTXSID1021247", "DTXSID1021291", "DTXSID1021328", "DTXSID1021378", "DTXSID1021740", "DTXSID1022001", "DTXSID10228450", "DTXSID1023235", "DTXSID1023784", "DTXSID1024172", "DTXSID1026031", "DTXSID1026039", "DTXSID1029621", "DTXSID1029623", "DTXSID1029677", "DTXSID1029704", "DTXSID1034185", "DTXSID1035238", "DTXSID1037018", "DTXSID1042154", "DTXSID1049431", "DTXSID1049437", "DTXSID1049564", "DTXSID1049641", "DTXSID1049774", "DTXSID1049801", "DTXSID1052533", "DTXSID1064792", "DTXSID1064879", "DTXSID1065477", "DTXSID10872533", "DTXSID10883144", "DTXSID10889580", "DTXSID10890712", "DTXSID10894851", "DTXSID201016307", "DTXSID201104392", "DTXSID20153567", "DTXSID2020131", "DTXSID2020630", "DTXSID2020711", "DTXSID2020715", "DTXSID2020977", "DTXSID2021159", "DTXSID2021238", "DTXSID2021288", "DTXSID2021319", "DTXSID2021446", "DTXSID2021731", "DTXSID2021735", "DTXSID2021739", "DTXSID2023723", "DTXSID2023852", "DTXSID2023985", "DTXSID2024115", "DTXSID2025424", "DTXSID2025509", "DTXSID2025688", "DTXSID2026076", "DTXSID2027044", "DTXSID2027090", "DTXSID2029616", "DTXSID2029668", "DTXSID2034384", "DTXSID2034839", "DTXSID2035013", "DTXSID2035069", "DTXSID2036245", "DTXSID2041125", "DTXSID2044260", "DTXSID2044349", "DTXSID2044393", "DTXSID2047484", "DTXSID2051421", "DTXSID2051502", "DTXSID2051633", "DTXSID2059728", "DTXSID2064812", "DTXSID2064864", "DTXSID2073986", "DTXSID2075003", "DTXSID20889354", "DTXSID20893158", "DTXSID20894105", "DTXSID20933003", "DTXSID20976556", "DTXSID20980375", "DTXSID20999109", "DTXSID301015564", "DTXSID301043480", "DTXSID3020332", "DTXSID3020833", "DTXSID3020964", "DTXSID3021271", "DTXSID3021275", "DTXSID3021516", "DTXSID3021720", "DTXSID3021932", "DTXSID3021982", "DTXSID30228452", "DTXSID3025469", "DTXSID3029108", "DTXSID3029653", "DTXSID3031521", "DTXSID3032042", "DTXSID3032048", "DTXSID3033983", "DTXSID3034903", "DTXSID3036238", "DTXSID3038694", "DTXSID3039242", "DTXSID3040273", "DTXSID3043994", "DTXSID3044704", "DTXSID3047003", "DTXSID3047718", "DTXSID3058799", "DTXSID3059428", "DTXSID3064851", "DTXSID3066920", "DTXSID3073135", "DTXSID30858819", "DTXSID30889360", "DTXSID30894075", "DTXSID30894792", "DTXSID30897728", "DTXSID401015755", "DTXSID401018678", "DTXSID4020080", "DTXSID4020298", "DTXSID4020533", "DTXSID4020795", "DTXSID40208366", "DTXSID4020901", "DTXSID4021343", "DTXSID4021426", "DTXSID4021890", "DTXSID4021923", "DTXSID4021975", "DTXSID4022816", "DTXSID4024309", "DTXSID4025824", "DTXSID4025909", "DTXSID4029690", "DTXSID4029692", "DTXSID4040183", "DTXSID4041602", "DTXSID4042123", "DTXSID4042125", "DTXSID4042381", "DTXSID4044161", "DTXSID4044400", "DTXSID4044406", "DTXSID4049402", "DTXSID4061969", "DTXSID4063080", "DTXSID4063585", "DTXSID4064135", "DTXSID4065656", "DTXSID40858820", "DTXSID40858865", "DTXSID40872829", "DTXSID40889371", "DTXSID40897395", "DTXSID50158904", "DTXSID5020079", "DTXSID5020106", "DTXSID5020152", "DTXSID5020235", "DTXSID5020653", "DTXSID5020730", "DTXSID5020811", "DTXSID5021124", "DTXSID5021178", "DTXSID5021461", "DTXSID5021835", "DTXSID5021889", "DTXSID5023873", "DTXSID5023875", "DTXSID5024182", "DTXSID5024263", "DTXSID5024394", "DTXSID5025497", "DTXSID5025946", "DTXSID5025948", "DTXSID5026918", "DTXSID5027699", "DTXSID5029633", "DTXSID5029683", "DTXSID5029685", "DTXSID5029689", "DTXSID5034488", "DTXSID5035242", "DTXSID5039224", "DTXSID5040544", "DTXSID5042245", "DTXSID5042320", "DTXSID5043710", "DTXSID5044316", "DTXSID5044520", "DTXSID5047457", "DTXSID5049576", "DTXSID5049788", "DTXSID5058480", "DTXSID5058800", "DTXSID5058933", "DTXSID5060065", "DTXSID5062392", "DTXSID5064126", "DTXSID5064889", "DTXSID5069596", "DTXSID50886497", "DTXSID50892432", "DTXSID50894850", "DTXSID50932342", "DTXSID60168170", "DTXSID60177308", "DTXSID6020143", "DTXSID6020195", "DTXSID6020226", "DTXSID6020359", "DTXSID6020438", "DTXSID6020511", "DTXSID6020515", "DTXSID6020692", "DTXSID6020856", "DTXSID6020937", "DTXSID6020939", "DTXSID6021741", "DTXSID6021909", "DTXSID6022000", "DTXSID6022977", "DTXSID6023602", "DTXSID6023947", "DTXSID6024464", "DTXSID6025565", "DTXSID6025983", "DTXSID6026296", "DTXSID6029701", "DTXSID6029705", "DTXSID6032061", "DTXSID6034005", "DTXSID6040795", "DTXSID6041682", "DTXSID6042313", "DTXSID6044195", "DTXSID6044197", "DTXSID6044351", "DTXSID6047448", "DTXSID6050463", "DTXSID6073994", "DTXSID6074257", "DTXSID6075302", "DTXSID60858781", "DTXSID60883437", "DTXSID60893218", "DTXSID60893253", "DTXSID60894063", "DTXSID60973463", "DTXSID701015754", "DTXSID70158941", "DTXSID70172884", "DTXSID7020009", "DTXSID7020130", "DTXSID7020637", "DTXSID7020762", "DTXSID7020899", "DTXSID7020928", "DTXSID7021360", "DTXSID7021940", "DTXSID7022910", "DTXSID7023192", "DTXSID7024370", "DTXSID7025425", "DTXSID7025716", "DTXSID7026025", "DTXSID7026368", "DTXSID7027043", "DTXSID7029619", "DTXSID7034410", "DTXSID7034672", "DTXSID7034836", "DTXSID7035012", "DTXSID7035016", "DTXSID7041126", "DTXSID7041170", "DTXSID7042435", "DTXSID7047514", "DTXSID7051505", "DTXSID7059434", "DTXSID7059690", "DTXSID7061560", "DTXSID7064819", "DTXSID7073983", "DTXSID7074668", "DTXSID70872953", "DTXSID70890617", "DTXSID70897287", "DTXSID70937652", "DTXSID70973656", "DTXSID801009841", "DTXSID801026649", "DTXSID8020090", "DTXSID8020121", "DTXSID8020204", "DTXSID8020703", "DTXSID8020917", "DTXSID80210423", "DTXSID8021276", "DTXSID8021482", "DTXSID8021484", "DTXSID8022408", "DTXSID80228003", "DTXSID8023632", "DTXSID8025337", "DTXSID8025961", "DTXSID8026145", "DTXSID8027030", "DTXSID8031940", "DTXSID8034902", "DTXSID8035506", "DTXSID8035681", "DTXSID8042476", "DTXSID8047004", "DTXSID8048860", "DTXSID8049200", "DTXSID8049751", "DTXSID8051382", "DTXSID8058798", "DTXSID8062155", "DTXSID8062866", "DTXSID8064858", "DTXSID8067866", "DTXSID80883212", "DTXSID80889451", "DTXSID80889471", "DTXSID80893977", "DTXSID901015512", "DTXSID90172467", "DTXSID9020584", "DTXSID9020663", "DTXSID9020667", "DTXSID90209202", "DTXSID9021269", "DTXSID9021340", "DTXSID9021348", "DTXSID9021924", "DTXSID90228493", "DTXSID9026423", "DTXSID9026631", "DTXSID9029641", "DTXSID9029691", "DTXSID9031066", "DTXSID9031939", "DTXSID9040344", "DTXSID9040635", "DTXSID9042124", "DTXSID9044459", "DTXSID9047754", "DTXSID9049665", "DTXSID9064924", "DTXSID9065285", "DTXSID90657542", "DTXSID90883440", "DTXSID90883460", "DTXSID90894076", "DTXSID90975516", "DTXSID90983454"));


    }

    @Test
    void testGetAllChemicalListDetails() throws Exception {
        final List<ChemicalList> lists = Collections.singletonList(chemicalList);

        when(listRepository.findAllOrderByTypeAscAndListNameAsc(ChemicalList.class)).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listName").value(chemicalList.getListName()));
    }

    @Test
    void testGetAllChemicalListDetailsProjection() throws Exception {
        final List<ChemicalList> lists = Collections.singletonList(chemicalList);

        when(listRepository.findAllOrderByTypeAscAndListNameAsc(ChemicalList.class)).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/all")
                .param("projection", "chemicallistall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listName").value(chemicalList.getListName()));
    }

    @Test
    void testGetAllChemicalListNames() throws Exception {
        final List<ChemicalListName> lists = Collections.singletonList(listName);

        when(listRepository.findAllOrderByTypeAscAndListNameAsc(ChemicalListName.class)).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/all")
                .param("projection", "chemicallistname"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllChemicalListsWithDtxsids() throws Exception {
        final List<ChemicalListWithDtxsidsDTO> lists = Collections.singletonList(listWithDtxsids);

        when(listRepository.getListsWithDtxsids()).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/all")
                .param("projection", "chemicallistwithdtxsids"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listName").value(listWithDtxsids.getListName()));
    }

    @Test
    void testGetAllListTypes() throws Exception {
        final List<String> types = Arrays.asList("federal", "state", "other");

        when(listRepository.getAllTypes()).thenReturn(types);

        mockMvc.perform(get("/chemical/list/type"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("federal")))
                .andExpect(content().string(containsString("state")))
                .andExpect(content().string(containsString("other")));
    }

    @Test
    void testGetChemicalListsByType() throws Exception {
        final List<Object> lists = Collections.singletonList(chemicalList);

        when(listRepository.findByTypeIgnoreCaseOrderByListNameAsc(eq("federal"), any())).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-type/{type}", "federal"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value(chemicalList.getType()));

    }

    @Test
    void testGetChemicalListsByTypeProjection() throws Exception {
        final List<ChemicalList> lists = Collections.singletonList(chemicalList);

        when(listRepository.findByTypeIgnoreCaseOrderByListNameAsc("federal", ChemicalList.class)).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-type/{type}", "federal")
                .param("projection", "chemicallistall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value(chemicalList.getType()));

    }

    @Test
    void testGetChemicalListNamesByType() throws Exception {
        final List<ChemicalListName> lists = Collections.singletonList(listName);

        when(listRepository.findByTypeIgnoreCaseOrderByListNameAsc("federal", ChemicalListName.class)).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-type/{type}", "federal")
                .param("projection", "chemicallistname"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listName").value(listName.getListName()));

    }

    @Test
    void testGetChemicalListsWithDtxsidsByType() throws Exception {
        final List<ChemicalListWithDtxsidsDTO> lists = Collections.singletonList(listWithDtxsids);

        when(listRepository.getListsWithDtxsidsByType("other")).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-type/{type}", "other")
                .param("projection", "chemicallistwithdtxsids"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value(listWithDtxsids.getType()));

    }

    @Test
    void testGetChemicalListsByName() throws Exception {
        final Optional<ChemicalList> lists = Optional.of(chemicalList);

        when(listRepository.findByListNameIgnoreCase(eq("BIOSOLIDS"))).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-name/{name}", "BIOSOLIDS"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalList.getListName())));

    }

    @Test
    void testGetChemicalListsByNameProjection() throws Exception {
        final Optional<ChemicalList> lists = Optional.of(chemicalList);

        when(listRepository.findByListNameIgnoreCase("BIOSOLIDS")).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-name/{name}", "BIOSOLIDS")
                .param("projection", "chemicallistall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(chemicalList.getListName())));

    }

    @Test
    void testGetChemicalListsWithDtxsidsByName() throws Exception {
        final Optional<ChemicalListWithDtxsidsDTO> lists = Optional.of(listWithDtxsids);

        when(listRepository.getListWithDtxsidsByListName("ACSREAG")).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-name/{name}", "ACSREAG")
                .param("projection", "chemicallistwithdtxsids"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(listWithDtxsids.getListName())));

    }

    @Test
    void testGetChemicalListsByDtxsid() throws Exception {
        final List<ChemicalList> lists = Collections.singletonList(chemicalList);

        when(listRepository.getListsByDtxsid("DTXSID7020182")).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-dtxsid/{dtxsid}", "DTXSID7020182"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listName").value(chemicalList.getListName()));

    }

    @Test
    void testGetChemicalListsByDtxsidProjection() throws Exception {
        final List<ChemicalList> lists = Collections.singletonList(chemicalList);

        when(listRepository.getListsByDtxsid("DTXSID7020182")).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "chemicallistall"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listName").value(chemicalList.getListName()));

    }

    @Test
    void testGetChemicalListNamesByDtxsid() throws Exception {
        final List<ChemicalListName> lists = Collections.singletonList(listName);
        final List<String> chemicalLists = chemicalListChemicalRepository.getListNames("DTXSID7020182", "PUBLIC");

        when(listRepository.findByListNameInIgnoreCaseOrderByListNameAsc(chemicalLists, ChemicalListName.class)).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "chemicallistname"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @Test
    void testGetChemicalListsCcd() throws Exception {
        final List lists = Collections.singletonList(chemicalList);

        when(listRepository.getListsByDtxsidCcd("DTXSID7020182")).thenReturn(lists);

        mockMvc.perform(get("/chemical/list/search/by-dtxsid/{dtxsid}", "DTXSID7020182")
                .param("projection", "ccdchemicaldetaillists"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listName").value(chemicalList.getListName()));

    }

    @Test
    void testGetDtxsidsByListNameAndChemicalNameStartingWith() throws Exception {
        String searchWord = listService.preprocessingSearchWord("ammo");


        when(chemicalListChemicalRepository.startWith(searchWord, "40CFR1164")).thenReturn(Arrays.asList("DTXSID0020078", "DTXSID0023872", "DTXSID1050462"));

        mockMvc.perform(get("/chemical/list/chemicals/search/start-with/{list}/{word}", "40CFR1164", "ammo"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DTXSID0020078")));
    }

    @Test
    void testGetDtxsidsByListNameAndChemicalNameExact() throws Exception {
        String searchWord = listService.preprocessingSearchWord("ammonia");

        when(chemicalListChemicalRepository.exact(searchWord, "40CFR1164")).thenReturn(Arrays.asList("DTXSID0023872"));

        mockMvc.perform(get("/chemical/list/chemicals/search/equal/{list}/{word}", "40CFR1164", "ammonia"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DTXSID0023872")));
    }

    @Test
    void testGetDtxsidsByListNameAndChemicalNameSubstring() throws Exception {
        String searchWord = listService.preprocessingSearchWord("monia");

        when(chemicalListChemicalRepository.contain(searchWord, "40CFR1164")).thenReturn(Arrays.asList("DTXSID0020078", "DTXSID0023872", "DTXSID2064072"));

        mockMvc.perform(get("/chemical/list/chemicals/search/contain/{list}/{word}", "40CFR1164", "monia"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DTXSID0020078")));
    }

    @Test
    void testGetDtxsidsByBatchDtxsidsAndListNames() throws Exception {
        ChemicalListsAndDtxsids request = new ChemicalListsAndDtxsids();
        request.setDtxsids(Arrays.asList("DTXSID7020182"));
        request.setChemicalLists(Arrays.asList("WIKIPEDIA", "COA_Summaries", "tan_db"));

        when(chemicalListChemicalRepository.chemicalListsAndDtxsids(request.getChemicalLists(), request.getDtxsids())).thenReturn(Arrays.asList("DTXSID0020078", "DTXSID0023872", "DTXSID2064072"));

        mockMvc.perform(post("/chemical/list/chemicals/search/by-dtxsid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DTXSID0020078")));
    }

}
