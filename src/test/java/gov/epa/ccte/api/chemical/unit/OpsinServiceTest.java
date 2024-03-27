package gov.epa.ccte.api.chemical.unit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpsinServiceTest {
    private static gov.epa.ccte.api.chemical.service.OpsinService opsinService;

    @BeforeAll
    public static void setUp() {
        opsinService = new gov.epa.ccte.api.chemical.service.OpsinService();
    }

    @AfterAll
    public static void cleanUp(){
        opsinService = null;
    }

    @Test
    public void testToInchi(){
        assertEquals("InChI=1/C2H5NO/c1-2(3)4/h1H3,(H2,3,4)/f/h3H2", opsinService.toInChI("acetamide"));
    }

    @Test
    public void testParseToStdInChIKey(){
        assertEquals("DLFVBJFMPXGRIB-UHFFFAOYSA-N", opsinService.toInChIKey("acetamide"));
    }
}
