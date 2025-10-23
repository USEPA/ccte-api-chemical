package gov.epa.ccte.api.chemical.web.rest;

//This will test REST end-points in the IndigoResource.java using WebMvcTest and MockitoBean

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

import gov.epa.ccte.api.chemical.service.IndigoService;

@ActiveProfiles("test")
@WebMvcTest(IndigoResource.class)
@RunWith(MockitoJUnitRunner.class)
public class IndigoResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	@MockitoBean
	private IndigoService indigoService;
	
	final private String mol = "  Mrv1805 07292016252D          \r\n"
				+ "\r\n"
				+ "  0  0  0     0  0            999 V3000\r\n"
				+ "M  V30 BEGIN CTAB\r\n"
				+ "M  V30 COUNTS 17 18 0 0 0\r\n"
				+ "M  V30 BEGIN ATOM\r\n"
				+ "M  V30 1 C 4.3641 -1.0888 0 0\r\n"
				+ "M  V30 2 C 5.453 -2.1778 0 0\r\n"
				+ "M  V30 3 C 6.9306 -1.8407 0 0\r\n"
				+ "M  V30 4 C 7.9936 -2.9555 0 0\r\n"
				+ "M  V30 5 C 7.5529 -4.4504 0 0\r\n"
				+ "M  V30 6 C 6.0665 -4.8048 0 0\r\n"
				+ "M  V30 7 C 4.995 -3.6814 0 0\r\n"
				+ "M  V30 8 O 8.6417 -5.5394 0 0\r\n"
				+ "M  V30 9 C 3.2752 -2.1778 0 0\r\n"
				+ "M  V30 10 C 3.6727 -3.6727 0 0\r\n"
				+ "M  V30 11 C 2.5839 -4.7616 0 0\r\n"
				+ "M  V30 12 C 1.0888 -4.3641 0 0\r\n"
				+ "M  V30 13 C 0.6914 -2.8778 0 0\r\n"
				+ "M  V30 14 C 1.7802 -1.7802 0 0\r\n"
				+ "M  V30 15 O 0 -5.453 0 0\r\n"
				+ "M  V30 16 C 5.936 0.4487 0 0\r\n"
				+ "M  V30 17 C 2.8753 0.4306 0 0\r\n"
				+ "M  V30 END ATOM\r\n"
				+ "M  V30 BEGIN BOND\r\n"
				+ "M  V30 1 1 1 2\r\n"
				+ "M  V30 2 1 1 9\r\n"
				+ "M  V30 3 2 2 3\r\n"
				+ "M  V30 4 1 2 7\r\n"
				+ "M  V30 5 1 3 4\r\n"
				+ "M  V30 6 2 4 5\r\n"
				+ "M  V30 7 1 5 6\r\n"
				+ "M  V30 8 1 5 8\r\n"
				+ "M  V30 9 2 6 7\r\n"
				+ "M  V30 10 2 9 10\r\n"
				+ "M  V30 11 1 9 14\r\n"
				+ "M  V30 12 1 10 11\r\n"
				+ "M  V30 13 2 11 12\r\n"
				+ "M  V30 14 1 12 13\r\n"
				+ "M  V30 15 1 12 15\r\n"
				+ "M  V30 16 2 13 14\r\n"
				+ "M  V30 17 1 1 16\r\n"
				+ "M  V30 18 1 1 17\r\n"
				+ "M  V30 END BOND\r\n"
				+ "M  V30 END CTAB\r\n"
				+ "M  END";
	
	final String mol2000 = "  -INDIGO-09292519062D\r\n"
			+ "\r\n"
			+ " 17 18  0  0  0  0  0  0  0  0999 V2000\r\n"
			+ "    4.3641   -1.0888    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    5.4530   -2.1778    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    6.9306   -1.8407    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    7.9936   -2.9555    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    7.5529   -4.4504    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    6.0665   -4.8048    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    4.9950   -3.6814    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    8.6417   -5.5394    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    3.2752   -2.1778    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    3.6727   -3.6727    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    2.5839   -4.7616    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    1.0888   -4.3641    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    0.6914   -2.8778    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    1.7802   -1.7802    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    0.0000   -5.4530    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    5.9360    0.4487    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "    2.8753    0.4306    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\r\n"
			+ "  1  2  1  0  0  0  0\r\n"
			+ "  1  9  1  0  0  0  0\r\n"
			+ "  2  3  2  0  0  0  0\r\n"
			+ "  2  7  1  0  0  0  0\r\n"
			+ "  3  4  1  0  0  0  0\r\n"
			+ "  4  5  2  0  0  0  0\r\n"
			+ "  5  6  1  0  0  0  0\r\n"
			+ "  5  8  1  0  0  0  0\r\n"
			+ "  6  7  2  0  0  0  0\r\n"
			+ "  9 10  2  0  0  0  0\r\n"
			+ "  9 14  1  0  0  0  0\r\n"
			+ " 10 11  1  0  0  0  0\r\n"
			+ " 11 12  2  0  0  0  0\r\n"
			+ " 12 13  1  0  0  0  0\r\n"
			+ " 12 15  1  0  0  0  0\r\n"
			+ " 13 14  2  0  0  0  0\r\n"
			+ "  1 16  1  0  0  0  0\r\n"
			+ "  1 17  1  0  0  0  0\r\n"
			+ "M  END";
	
	final String mol3000 = "  -INDIGO-09292519082D\r\n"
			+ "\r\n"
			+ "  0  0  0  0  0  0  0  0  0  0  0 V3000\r\n"
			+ "M  V30 BEGIN CTAB\r\n"
			+ "M  V30 COUNTS 17 18 0 0 0\r\n"
			+ "M  V30 BEGIN ATOM\r\n"
			+ "M  V30 1 C 4.3641 -1.0888 0.0 0\r\n"
			+ "M  V30 2 C 5.453 -2.1778 0.0 0\r\n"
			+ "M  V30 3 C 6.9306 -1.8407 0.0 0\r\n"
			+ "M  V30 4 C 7.9936 -2.9555 0.0 0\r\n"
			+ "M  V30 5 C 7.5529 -4.4504 0.0 0\r\n"
			+ "M  V30 6 C 6.0665 -4.8048 0.0 0\r\n"
			+ "M  V30 7 C 4.995 -3.6814 0.0 0\r\n"
			+ "M  V30 8 O 8.6417 -5.5394 0.0 0\r\n"
			+ "M  V30 9 C 3.2752 -2.1778 0.0 0\r\n"
			+ "M  V30 10 C 3.6727 -3.6727 0.0 0\r\n"
			+ "M  V30 11 C 2.5839 -4.7616 0.0 0\r\n"
			+ "M  V30 12 C 1.0888 -4.3641 0.0 0\r\n"
			+ "M  V30 13 C 0.6914 -2.8778 0.0 0\r\n"
			+ "M  V30 14 C 1.7802 -1.7802 0.0 0\r\n"
			+ "M  V30 15 O 0.0 -5.453 0.0 0\r\n"
			+ "M  V30 16 C 5.936 0.4487 0.0 0\r\n"
			+ "M  V30 17 C 2.8753 0.4306 0.0 0\r\n"
			+ "M  V30 END ATOM\r\n"
			+ "M  V30 BEGIN BOND\r\n"
			+ "M  V30 1 1 1 2\r\n"
			+ "M  V30 2 1 1 9\r\n"
			+ "M  V30 3 2 2 3\r\n"
			+ "M  V30 4 1 2 7\r\n"
			+ "M  V30 5 1 3 4\r\n"
			+ "M  V30 6 2 4 5\r\n"
			+ "M  V30 7 1 5 6\r\n"
			+ "M  V30 8 1 5 8\r\n"
			+ "M  V30 9 2 6 7\r\n"
			+ "M  V30 10 2 9 10\r\n"
			+ "M  V30 11 1 9 14\r\n"
			+ "M  V30 12 1 10 11\r\n"
			+ "M  V30 13 2 11 12\r\n"
			+ "M  V30 14 1 12 13\r\n"
			+ "M  V30 15 1 12 15\r\n"
			+ "M  V30 16 2 13 14\r\n"
			+ "M  V30 17 1 1 16\r\n"
			+ "M  V30 18 1 1 17\r\n"
			+ "M  V30 END BOND\r\n"
			+ "M  V30 END CTAB\r\n"
			+ "M  END";
	@Test
	void testToInchi() throws Exception {

	when(indigoService.mol2inchi(mol)).thenReturn("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3");
	
	mockMvc.perform(post("/chemical/indigo/to-inchi")
			.content(mol))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().string("InChI=1S/C15H16O2/c1-15(2,11-3-7-13(16)8-4-11)12-5-9-14(17)10-6-12/h3-10,16-17H,1-2H3"));
	}
	
	@Test
	void testToInchikey() throws Exception {

	when(indigoService.mol2inchikey(mol)).thenReturn("IISBACLAFKSPIT-UHFFFAOYSA-N");
	
	mockMvc.perform(post("/chemical/indigo/to-inchikey")
			.content(mol))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().string("IISBACLAFKSPIT-UHFFFAOYSA-N"));
	}
	
	@Test
	void testToSmiles() throws Exception {

	when(indigoService.mol2smiles(mol)).thenReturn("C(C)(C)(C1C=CC(O)=CC=1)C1C=CC(O)=CC=1");
	
	mockMvc.perform(post("/chemical/indigo/to-smiles")
			.content(mol))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().string("C(C)(C)(C1C=CC(O)=CC=1)C1C=CC(O)=CC=1"));
	}
	
	@Test
	void testToCanonicalSmiles() throws Exception {

	when(indigoService.mol2CanonicalSmiles(mol)).thenReturn(null);
	
	mockMvc.perform(post("/chemical/indigo/to-canonicalsmiles")
			.content(mol))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testToMolecularWeight() throws Exception {

	when(indigoService.mol2molWeight(mol)).thenReturn(null);
	
	mockMvc.perform(post("/chemical/indigo/to-molweight")
			.content(mol))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testToMol2000() throws Exception {

	when(indigoService.mol2molv2000(mol)).thenReturn(mol2000);
	
	mockMvc.perform(post("/chemical/indigo/to-mol2000")
			.content(mol))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testToMol3000() throws Exception {

	when(indigoService.mol2molv3000(mol)).thenReturn(mol3000);
	
	mockMvc.perform(post("/chemical/indigo/to-mol3000")
			.content(mol))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
	}
	
}
