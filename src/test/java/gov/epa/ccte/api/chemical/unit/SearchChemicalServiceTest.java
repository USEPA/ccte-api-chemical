package gov.epa.ccte.api.chemical.unit;

import gov.epa.ccte.api.chemical.repository.ChemicalSearchRepository;
import gov.epa.ccte.api.chemical.service.CaffeineFixSynonymService;
import gov.epa.ccte.api.chemical.service.OpsinService;
import gov.epa.ccte.api.chemical.service.SearchChemicalService;
import gov.epa.ccte.api.chemical.web.rest.BatchMsReadyMassForm;
import gov.epa.ccte.api.chemical.web.rest.errors.InvalidBatchMsReadyRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class SearchChemicalServiceTest {

	private SearchChemicalService searchChemicalService;

	@BeforeEach
	void setUp() {
		CaffeineFixSynonymService caffeineFixService = mock(CaffeineFixSynonymService.class);
		ChemicalSearchRepository searchRepository = mock(ChemicalSearchRepository.class);
		OpsinService opsinService = mock(OpsinService.class);

		searchChemicalService = new SearchChemicalService(caffeineFixService, searchRepository, opsinService);
	}
	
	@Test
	@DisplayName("Given valid form when getMsReadyBatchResult is called then results are returned")
	void testGetMsReadyBatchResultValidForm() {
	    CaffeineFixSynonymService caffeineFixService = mock(CaffeineFixSynonymService.class);
	    ChemicalSearchRepository searchRepository = mock(ChemicalSearchRepository.class);
	    OpsinService opsinService = mock(OpsinService.class);

	    SearchChemicalService service = new SearchChemicalService(caffeineFixService, searchRepository, opsinService);

	    BatchMsReadyMassForm form = new BatchMsReadyMassForm();
	    form.setMasses(List.of(12.0));
	    form.setError(2);

	    org.mockito.Mockito.when(searchRepository.searchMsReadyMass(org.mockito.ArgumentMatchers.anyDouble(), org.mockito.ArgumentMatchers.anyDouble()))
	            .thenReturn(List.of("DTXSID123"));

	    var result = service.getMsReadyBatchResult(form);

	    assertEquals(List.of("DTXSID123"), result.get(12.0));
	}


	@Test
	@DisplayName("Given null form when getMsReadyBatchResult is called then InvalidBatchMsReadyRequestException is thrown")
	void testGetMsReadyBatchResultWithNullForm() {
		InvalidBatchMsReadyRequestException ex = assertThrows(InvalidBatchMsReadyRequestException.class,
				() -> searchChemicalService.getMsReadyBatchResult(null));

		assertEquals("request body must not be empty.", ex.getMessage());
	}

	@Test
	@DisplayName("Given null masses when getMsReadyBatchResult is called then InvalidBatchMsReadyRequestException is thrown")
	void testGetMsReadyBatchResultWithNullMasses() {
		BatchMsReadyMassForm form = new BatchMsReadyMassForm();
		form.setMasses(null);
		form.setError(2);

		InvalidBatchMsReadyRequestException ex = assertThrows(InvalidBatchMsReadyRequestException.class,
				() -> searchChemicalService.getMsReadyBatchResult(form));

		assertEquals("masses must not be empty.", ex.getMessage());
	}

	@Test
	@DisplayName("Given empty masses when getMsReadyBatchResult is called then InvalidBatchMsReadyRequestException is thrown")
	void testGetMsReadyBatchResultWithEmptyMasses() {
		BatchMsReadyMassForm form = new BatchMsReadyMassForm();
		form.setMasses(Collections.emptyList());
		form.setError(2);

		InvalidBatchMsReadyRequestException ex = assertThrows(InvalidBatchMsReadyRequestException.class,
				() -> searchChemicalService.getMsReadyBatchResult(form));

		assertEquals("masses must not be empty.", ex.getMessage());
	}

	@Test
	@DisplayName("Given null error when getMsReadyBatchResult is called then InvalidBatchMsReadyRequestException is thrown")
	void testGetMsReadyBatchResultWithNullError() {
		BatchMsReadyMassForm form = new BatchMsReadyMassForm();
		form.setMasses(List.of(12.0, 16.1));
		form.setError(null);

		InvalidBatchMsReadyRequestException ex = assertThrows(InvalidBatchMsReadyRequestException.class,
				() -> searchChemicalService.getMsReadyBatchResult(form));

		assertEquals("error must not be null.", ex.getMessage());
	}

	@Test
	@DisplayName("Given masses containing null when getMsReadyBatchResult is called then InvalidBatchMsReadyRequestException is thrown")
	void testGetMsReadyBatchResultWithNullMassElement() {
		BatchMsReadyMassForm form = new BatchMsReadyMassForm();
		form.setMasses(Arrays.asList(12.0, null, 16.1));
		form.setError(2);

		InvalidBatchMsReadyRequestException ex = assertThrows(InvalidBatchMsReadyRequestException.class,
				() -> searchChemicalService.getMsReadyBatchResult(form));

		assertEquals("masses must not contain null values.", ex.getMessage());
	}

}
