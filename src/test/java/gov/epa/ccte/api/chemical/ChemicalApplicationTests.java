package gov.epa.ccte.api.chemical;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.service.ChemicalDetailService;


@SpringBootTest
@ActiveProfiles("test")
class ChemicalApplicationTests {
	
	@MockitoBean
	ChemicalDetailService ChemicalDetailService;
	
	@MockitoBean
	ChemicalDetailRepository chemicalDetailRepository;

	@Test
	void contextLoads() {
	}


}
