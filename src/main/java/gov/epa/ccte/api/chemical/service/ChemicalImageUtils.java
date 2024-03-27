package gov.epa.ccte.api.chemical.service;

import com.epam.indigo.Indigo;
import com.epam.indigo.IndigoObject;
import com.epam.indigo.IndigoRenderer;
import gov.epa.ccte.api.chemical.domain.ImageFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ChemicalImageUtils {

    private ChemicalImageUtils() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }



    public static String toSvg(IndigoObject molecule){
        Indigo indigo = new Indigo();
        indigo.setOption("ignore-stereochemistry-errors", true);
        indigo.setOption("ignore-noncritical-query-features", true);
        indigo.setOption("aromaticity-model", "generic");

        IndigoRenderer renderer = new IndigoRenderer(indigo);
        indigo.setOption("render-margins", 5, 5);
        indigo.setOption("render-coloring", true);
//        if ( width != null )
//            indigo.setOption("render-image-width", width);
//        if ( height != null )
//            indigo.setOption("render-image-height", height);
        indigo.setOption("render-output-format", "svg");

        molecule.dearomatize();

        return String.valueOf(renderer.renderToBuffer(molecule));
    }

    public static byte[] toPng(IndigoObject molecule){
        Indigo indigo = new Indigo();
        indigo.setOption("ignore-stereochemistry-errors", true);
        indigo.setOption("ignore-noncritical-query-features", true);
        indigo.setOption("aromaticity-model", "generic");

        IndigoRenderer renderer = new IndigoRenderer(indigo);
        indigo.setOption("render-margins", 5, 5);
//        indigo.setOption("render-coloring", true);
//        if ( width != null )
//            indigo.setOption("render-image-width", width);
//        if ( height != null )
//            indigo.setOption("render-image-height", height);
        indigo.setOption("render-output-format", "png");

        molecule.dearomatize();

        return renderer.renderToBuffer(molecule);
    }

    public static byte[] smileToImage(String smiles, ImageFormat imageFormat){

        log.debug("smiles={} , image format = {}", smiles, imageFormat.toString());

        Integer width=300;
        Integer height = 300;

        Indigo indigo = new Indigo();

        indigo.setOption("ignore-stereochemistry-errors", true);
        indigo.setOption("ignore-noncritical-query-features", true);
        indigo.setOption("aromaticity-model", "generic");

        IndigoRenderer renderer = new IndigoRenderer(indigo);
        indigo.setOption("render-margins", 5, 5);
        indigo.setOption("render-coloring", true);
//        if ( width != null )
//            indigo.setOption("render-image-width", width);
//        if ( height != null )
//            indigo.setOption("render-image-height", height);
        switch (imageFormat){
            case SVG -> indigo.setOption("render-output-format", "svg");
            case PNG -> indigo.setOption("render-output-format", "png");
        }

//        log.debug("format={}", indigo.getOption("render-output-format"));

        IndigoObject molecule = indigo.loadMolecule(smiles);
        //ChemicalImageUtils.toSvg(molecule);
        molecule.dearomatize();

        return renderer.renderToBuffer(molecule);
    }
}
