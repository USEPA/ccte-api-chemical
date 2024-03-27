package gov.epa.ccte.api.chemical.web.rest;

import com.epam.indigo.Indigo;
import com.epam.indigo.IndigoObject;
import com.epam.indigo.IndigoRenderer;
import gov.epa.ccte.api.chemical.domain.ImageFormat;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.service.ChemicalImageUtils;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_PLAIN;

/**
 * REST controller for getting the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail}s.
 */
@Tag(name = "Chemical File Resource",
        description = "API endpoints for getting chemical structure data in mol, mrv and image (png or svg format) for given Chemical Identifier (DTXSID or DTXCID).")
//@SecurityRequirement(name = "api_key") // no need for api_key for this endpoint
@Slf4j
@RestController
public class ChemicalFileResource {

    //image size constants
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 300;

    private static final int MAX_WIDTH = 2560;
    private static final int MAX_HEIGHT = 2560;

    final private ChemicalDetailRepository detailRepository;

    public ChemicalFileResource(ChemicalDetailRepository detailRepository) {

        this.detailRepository = detailRepository;
    }

    /**
     * {@code GET  /chemical/file/image/search/by-dtxsid/:dtxsid} : get the "dtxsid" chemical image.
     *
     * @param dtxsid the matching dtxsid of the chemical image to retrieve.
     * @param format is enum with two values pgn and svg
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical image}.
     */
    @Operation(summary = "Get structure image by dtxsid")
    @RequestMapping(value = "chemical/file/image/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> imageByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid,
                                         @Parameter(name = "Image Format", description = "In case of absence, it will return png image") @RequestParam(value = "format", required = false) ImageFormat format){

        log.debug("dtxsid = {}, format = {}", dtxsid, format);


        if(format == ImageFormat.PNG || format == null){
            byte[] image = detailRepository.getMolImageForDtxsid(dtxsid);
            return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
        }else if(format == ImageFormat.SVG){
            String mol = detailRepository.getMolFileForDtxsid(dtxsid)
                    .orElseThrow(()->new IdentifierNotFoundException("DTXSID",dtxsid));

            byte[] image = getSvgImage(mol);

            return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
        }else{
            return null;
        }
    }

    /**
     * {@code GET  /chemical/file/image/search/by-dtxcid/:dtxcid} : get the "dtxcid" chemical image.
     *
     * @param dtxcid the matching dtxcid of the chemical image to retrieve.
     * @param format is enum with two values pgn and svg
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical image}.
     */
    @Operation(summary = "Get structure image by dtxcid",
            description = "This endpoint is deprecated. Please use /chemical-file/image/search/by-dtxcid/{dtxcid} instead.")
    @RequestMapping(value = "chemical/file/image/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> imageByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505") @PathVariable("dtxcid") String dtxcid,
                                         @Parameter(name = "Image Format", description = "In case of absence, it will return png image") @RequestParam(value = "format", required = false) ImageFormat format) {

        log.debug("dtxcid = {}, format = {}", dtxcid, format);


        if(format == ImageFormat.PNG || format == null){
            byte[] image = detailRepository.getMolImageForDtxcid(dtxcid);
            return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
        }else if(format == ImageFormat.SVG){
            String mol = detailRepository.getMolFileForDtxcid(dtxcid)
                    .orElseThrow(()->new IdentifierNotFoundException("DTXCID", dtxcid));

            // getSvgImage(mol)
            byte[] image = ChemicalImageUtils.smileToImage(mol, ImageFormat.SVG);

            return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
        }else{
            return null;
        }
    }

    private static byte[] getSvgImage(String mol) {
        Integer width=200;
        Integer height = 200;

        Indigo indigo = new Indigo();

        indigo.setOption("ignore-stereochemistry-errors", true);
        indigo.setOption("ignore-noncritical-query-features", true);
        indigo.setOption("aromaticity-model", "generic");

        IndigoRenderer renderer = new IndigoRenderer(indigo);
        indigo.setOption("render-margins", 5, 5);
        indigo.setOption("render-coloring", true);
//            if ( width != null )
//                indigo.setOption("render-image-width", width);
//            if ( height != null )
//                indigo.setOption("render-image-height", height);
        indigo.setOption("render-output-format", "svg");

        IndigoObject molecule = indigo.loadMolecule(mol);
        //ChemicalImageUtils.toSvg(molecule);
        molecule.dearomatize();

        return renderer.renderToBuffer(molecule);
    }

    /**
     * {@code GET  /chemical/file/mol/search/by-dtxsid/:dtxsid} : get the "dtxsid" chemical mol file.
     *
     * @param dtxsid the matching dtxsid of the chemical mol file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mol file}.
     */
    @Operation(summary = "Get mol file by dtxsid",
            description = "This endpoint is deprecated. Please use /chemical-file/mol/search/by-dtxsid/{dtxsid} instead.")
    @RequestMapping(value = "chemical/file/mol/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> molByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid){

        log.debug("dtxsid = {}", dtxsid);

        String mol = detailRepository.getMolFileForDtxsid(dtxsid)
                .orElseThrow(()->new IdentifierNotFoundException("DTXSID", dtxsid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }

    /**
     * {@code GET  /chemical/file/mol/search/by-dtxcid/:dtxcid} : get the "dtxcid" chemical mol file.
     *
     * @param dtxcid the matching dtxcid of the chemical mol file to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mol file}.
     */
    @Operation(summary = "Get mol file by dtxcid",
            description = "This endpoint is deprecated. Please use /chemical-file/mol/search/by-dtxcid/{dtxcid} instead.")

    @RequestMapping(value = "chemical/file/mol/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> molByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505") @PathVariable("dtxcid") String dtxcid){
        log.debug("dtxsid = {}", dtxcid);

        String mol = detailRepository.getMolFileForDtxcid(dtxcid)
                .orElseThrow(()->new IdentifierNotFoundException("DTXCID", dtxcid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }

    /**
     * {@code GET  chemical/file/mrv/search/by-dtxsid/{dtxsid}" : get the "dtxsid" chemical mrv file.
     * @param dtxsid the matching dtxcid of the chemical mrv file to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mrv file}.
     */
    @Operation(summary = "Get mrv file by dtxsid",
            deprecated = true,
            description = "This endpoint is deprecated. Please use /chemical-file/mrv/search/by-dtxsid/{dtxsid} instead.")
    @RequestMapping(value = "chemical/file/mrv/search/by-dtxsid/{dtxsid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> mrvByDtxsid(@Parameter(required = true, description = "DSSTox Substance Identifier", example = "DTXSID7020182") @PathVariable("dtxsid") String dtxsid){

        log.debug("dtxsid = {}", dtxsid);

        String mol = detailRepository.getMrvFileForDtxsid(dtxsid)
                .orElseThrow(()-> new IdentifierNotFoundException("DTXSID",dtxsid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }

    /**
     * {@code GET  chemical/file/mrv/search/by-dtxcid/{dtxcid} : get the "dtxcid" chemical mrv file.
     * @param dtxcid the matching dtxcid of the chemical mrv file to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical mrv file}.
     */
    @Operation(summary = "Get mrv file by dtxcid",
            description = "This endpoint is deprecated. Please use /chemical-file/mrv/search/by-dtxcid/{dtxcid} instead.")
    @RequestMapping(value = "chemical/file/mrv/search/by-dtxcid/{dtxcid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> mrvByDtxcid(@Parameter(required = true, description = "DSSTox Compound Identifier", example = "DTXCID505") @PathVariable("dtxcid") String dtxcid){
        log.debug("dtxsid = {}", dtxcid);

        String mol = detailRepository.getMrvFileForDtxcid(dtxcid)
                .orElseThrow(()-> new IdentifierNotFoundException("DTXCID",dtxcid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }

    /**
     * {@code GET  chemical/file/image/generate?smile=<smiles-string> : get generated structure image for smiles.
     * @param smiles generate structure image using smiles.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chemical structure image file}.
     *
     *
     */
    @Operation(summary = "Get generated structure image for smiles",
            description = "User can use generate structure image by providing smiles string")
    @RequestMapping(value = "chemical/file/image/generate", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> generateImageBySmiles(@Parameter(required = true, description = "SMILES String", example = "CC(C)(C1=CC=C(O)C=C1)C1=CC=C(O)C=C1")
                                                 @RequestParam(required = true, value = "smiles") String smiles,
                                                 @Parameter(name = "Image Format", description = "In case of absence, it will return png image")
                                                 @RequestParam(value = "format", required = false, defaultValue = "PNG") ImageFormat format){
        log.debug("smiles = {}, image type={}", smiles, format);

        byte[] image = ChemicalImageUtils.smileToImage(smiles, format);

        switch (format){
            case PNG -> {
                return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
            }
            case SVG -> {
                return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
            }
            default -> {
                return null;
            }
        }
    }

}

