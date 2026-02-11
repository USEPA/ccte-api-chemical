package gov.epa.ccte.api.chemical.dto;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import gov.epa.ccte.api.chemical.projection.chemicallist.ChemicalListWithDtxsids;

public class ChemicalListWithDtxsidsDTO implements ChemicalListWithDtxsids {
    private String listName;
    private String label;
    private String type;
    private String shortDescription;
    private String longDescription;
    private Long chemicalCount;
    private Timestamp updatedAt;
    private Integer id;
    private List<String> dtxsids;

    public ChemicalListWithDtxsidsDTO(String listName, String label, String type, String shortDescription, String longDescription, Long chemicalCount, Timestamp updatedAt, Integer id, String dtxsids) {
        this.listName = listName;
        this.label = label;
        this.type = type;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.chemicalCount = chemicalCount;
        this.updatedAt = updatedAt;
        this.id = id;
        this.dtxsids = (dtxsids == null || dtxsids.isEmpty()) ? List.of() : Arrays.asList(dtxsids.split(","));
    }

    @Override
    public String getListName() { return listName; }
    @Override
    public String getLabel() { return label; }
    @Override
    public String getType() { return type; }
    @Override
    public String getShortDescription() { return shortDescription; }
    @Override
    public String getLongDescription() { return longDescription; }
    @Override
    public Long getChemicalCount() { return chemicalCount; }
    @Override
    public Timestamp getUpdatedAt() { return updatedAt; }
    @Override
    public Integer getId() { return id; }
    @Override
    public List<String> getDtxsids() { return dtxsids; }

    @Override
    public Void setListName(String listName) { this.listName = listName; return null; }
    @Override
    public Void setLabel(String label) { this.label = label; return null; }
    @Override
    public Void setType(String type) { this.type = type; return null; }
    @Override
    public Void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; return null; }
    @Override
    public Void setLongDescription(String longDescription) { this.longDescription = longDescription; return null; }
    @Override
    public Void setChemicalCount(Long chemicalCount) { this.chemicalCount = chemicalCount; return null; }
    @Override
    public Void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; return null; }
    @Override
    public Void setid(Integer id) { this.id = id; return null; }
    @Override
    public Void setDtxsids(List<String> dtxsids) { this.dtxsids = dtxsids; return null; }
}