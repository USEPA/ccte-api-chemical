package gov.epa.ccte.api.chemical.web.rest;

import gov.epa.ccte.api.chemical.domain.ImageFormat;
import gov.epa.ccte.api.chemical.repository.ChemicalDetailRepository;
import gov.epa.ccte.api.chemical.service.ChemicalImageUtils;
import gov.epa.ccte.api.chemical.web.rest.errors.IdentifierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_PLAIN;

/**
 * REST controller for retrieving chemical structure data and for getting the {@link gov.epa.ccte.api.chemical.domain.ChemicalDetail}s.
 */
@Slf4j
@RestController
public class ChemicalFileResource implements ChemicalFileApi {

    private final ChemicalDetailRepository detailRepository;

    public ChemicalFileResource(ChemicalDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    @Override
    public ResponseEntity<byte[]> imageByGsid(String gsid, ImageFormat format) {
        log.debug("gsid = {}, format = {}", gsid, format);

        if (format == ImageFormat.PNG || format == null) {
            byte[] image = detailRepository.getMolImageForGsid(gsid);
            return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
        } else if (format == ImageFormat.SVG) {
            String mol = detailRepository.getMolFileForGsid(gsid)
                    .orElseThrow(() -> new IdentifierNotFoundException("GSID", gsid));

            byte[] image = ChemicalImageUtils.smileToImage(mol, ImageFormat.SVG);
            return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<byte[]> imageByDtxsid(String dtxsid, ImageFormat format) {
        log.debug("dtxsid = {}, format = {}", dtxsid, format);

        if (format == ImageFormat.PNG || format == null) {
            byte[] image = detailRepository.getMolImageForDtxsid(dtxsid);
            return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
        } else if (format == ImageFormat.SVG) {
            String mol = detailRepository.getMolFileForDtxsid(dtxsid)
                    .orElseThrow(() -> new IdentifierNotFoundException("DTXSID", dtxsid));

            byte[] image = ChemicalImageUtils.smileToImage(mol, ImageFormat.SVG);
            return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<byte[]> imageByDtxcid(String dtxcid, ImageFormat format) {
        log.debug("dtxcid = {}, format = {}", dtxcid, format);

        if (format == ImageFormat.PNG || format == null) {
            byte[] image = detailRepository.getMolImageForDtxcid(dtxcid);
            return ResponseEntity.ok().contentType(IMAGE_PNG).body(image);
        } else if (format == ImageFormat.SVG) {
            String mol = detailRepository.getMolFileForDtxcid(dtxcid)
                    .orElseThrow(() -> new IdentifierNotFoundException("DTXCID", dtxcid));

            byte[] image = ChemicalImageUtils.smileToImage(mol, ImageFormat.SVG);
            return ResponseEntity.ok().contentType(MediaType.valueOf("image/svg+xml")).body(image);
        } else {
            return null;
        }
    }
    
    @Override
    public ResponseEntity<String> molByDtxsid( String dtxsid){
        log.debug("dtxsid = {}", dtxsid);

        String mol = detailRepository.getMolFileForDtxsid(dtxsid)
                .orElseThrow(()->new IdentifierNotFoundException("DTXSID", dtxsid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }
    
    @Override
    public ResponseEntity<String> molByDtxcid( String dtxcid){
        log.debug("dtxsid = {}", dtxcid);

        String mol = detailRepository.getMolFileForDtxcid(dtxcid)
                .orElseThrow(()->new IdentifierNotFoundException("DTXCID", dtxcid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }
    
    @Override
    public ResponseEntity<String> mrvByDtxsid( String dtxsid){
        log.debug("dtxsid = {}", dtxsid);

        String mol = detailRepository.getMrvFileForDtxsid(dtxsid)
                .orElseThrow(()->new IdentifierNotFoundException("DTXSID", dtxsid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }
    
    @Override
    public ResponseEntity<String> mrvByDtxcid( String dtxcid){
        log.debug("dtxsid = {}", dtxcid);

        String mol = detailRepository.getMrvFileForDtxcid(dtxcid)
                .orElseThrow(()->new IdentifierNotFoundException("DTXCID", dtxcid));

        return ResponseEntity.ok().contentType(TEXT_PLAIN).body(mol);
    }
    
    @Override
    public ResponseEntity<byte[]> generateImageBySmiles( String smiles, ImageFormat format){
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