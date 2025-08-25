package gov.epa.ccte.api.chemical.projection.chemicallist;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Projection for {@link gov.epa.ccte.api.chemical.domain.ChemicalList}
 */
public interface ChemicalListAll extends ChemicalListBase{
    Integer getId();

    String getListName();

    String getLabel();

    String getType();

    String getVisibility();

    String getShortDescription();

    String getLongDescription();

    Long getChemicalCount();

    Instant getCreatedAt();

    Instant getUpdatedAt();

//    @Value("#{DateTimeFormatter.ofPattern('dd.MM.yyyy').withZone(ZoneId.systemDefault()).format(target.createdAt)}")
//    Instant getCreateDateAt();

//    default String getCreatedAtDate(){
//        try{
//            return DateTimeFormatter.ofPattern("yyyy.MM.dd").withZone(ZoneId.systemDefault()).format(getCreatedAt());
//        }catch (Exception e){
//            System.out.println(e.toString());
//            return "";
//        }
//    }
//
//    default String getUpdatedAtDate(){
//        try{
//            return DateTimeFormatter.ofPattern("yyyy.MM.dd").withZone(ZoneId.systemDefault()).format(getUpdatedAt());
//        }catch (Exception e){
//            System.out.println(e.toString());
//            return "";
//        }
//    }
}