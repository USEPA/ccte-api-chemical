package gov.epa.ccte.api.chemical.domain;

import gov.epa.ccte.api.chemical.projection.search.CcdChemicalSearchResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigInteger;
import java.time.Instant;

@Entity
@Table(name = "v_chemical_search", schema = "ch")
// start - projection mappings for search results
@SqlResultSetMapping( name = "ccd",
        classes = @ConstructorResult(
                targetClass = CcdChemicalSearchResult.class,
                columns = {
                        @ColumnResult(name="dtxsid"),
                        @ColumnResult(name="dtxcid"),
                        @ColumnResult(name="generic_substance_id", type = Integer.class),
                        @ColumnResult(name="casrn"),
                        @ColumnResult(name="preferred_name"),
                        @ColumnResult(name="compound_id", type = Integer.class),
                        @ColumnResult(name="stereo", type = Integer.class),
                        @ColumnResult(name="isotope", type = Integer.class),
                        @ColumnResult(name="multicomponent", type = Integer.class),
                        @ColumnResult(name="pubchem_count", type = Integer.class),
                        @ColumnResult(name="pubmed_count",type = Integer.class),
                        @ColumnResult(name="sources_count", type = Integer.class),
                        @ColumnResult(name="cpdata_count", type = Long.class),
                        @ColumnResult(name="active_assays", type = Integer.class),
                        @ColumnResult(name="total_assays", type = Integer.class),
                        @ColumnResult(name="percent_assays", type = BigInteger.class),
                        @ColumnResult(name="toxcast_select"),
                        @ColumnResult(name="monoisotopic_mass", type = Double.class),
                        @ColumnResult(name="mol_formula"),
                        @ColumnResult(name="qc_level", type = Integer.class),
                        @ColumnResult(name="qc_level_desc"),
                        @ColumnResult(name="pubchem_cid", type = Integer.class),
                        @ColumnResult(name="has_structure_image", type = Boolean.class),
                        @ColumnResult(name="related_substance_count", type = Integer.class),
                        @ColumnResult(name="related_structure_count", type = Integer.class),
                        @ColumnResult(name="iupac_name"),
                        @ColumnResult(name="smiles"),
                        @ColumnResult(name="inchi_string"),
                        @ColumnResult(name="inchikey"),
                        @ColumnResult(name="average_mass", type = Double.class),
                        @ColumnResult(name="rank", type = Integer.class),
                        @ColumnResult(name="search_match"),
                        @ColumnResult(name="search_word")
                }
        ))
// end - projection mappings for search results
// start - named queries for search results ChemicalSearch
@NamedNativeQuery(
        name = "ChemicalSearch.equalCcd",
        resultSetMapping = "ccd",
        query = " select cd.dtxsid,  cd.dtxcid, cd.generic_substance_id, cd.casrn, cd.preferred_name, cd.compound_id, cd.stereo, cd.isotope, " +
                " cd.multicomponent, cd.pubchem_count, cd.pubmed_count, cd.sources_count, cd.cpdata_count, cd.active_assays, cd.total_assays, " +
                " cd.percent_assays, cd.toxcast_select, cd.monoisotopic_mass, cd.mol_formula, cd.qc_level, cd.qc_level_desc, cd.pubchem_cid, " +
                " cd.has_structure_image, cd.related_substance_count, cd.related_structure_count, cd.iupac_name, cd.smiles, cd.inchi_string, " +
                " cd.inchikey, cd.average_mass, sc.rank, sc.search_name as search_match, sc.search_value as search_word\n" +
                " from ch.v_chemical_details  cd join ( \n" +
                " select  dtxsid, dtxcid, search_name, search_value, modified_value,rank \n" +
                " from ch.v_chemical_search where modified_value = :searchWord ) sc on cd.dtxsid=sc.dtxsid or cd.dtxcid=sc.dtxcid " +
                " order by rank, search_value asc "
)
@NamedNativeQuery(
        name = "ChemicalSearch.startWithCcd",
        resultSetMapping = "ccd",
        query = " select cd.dtxsid,  cd.dtxcid, cd.generic_substance_id, cd.casrn, cd.preferred_name, cd.compound_id, cd.stereo, cd.isotope, cd.multicomponent, cd.pubchem_count, cd.pubmed_count, cd.sources_count, cd.cpdata_count, cd.active_assays, cd.total_assays, cd.percent_assays, cd.toxcast_select, cd.monoisotopic_mass, cd.mol_formula, cd.qc_level, cd.qc_level_desc, cd.pubchem_cid, cd.has_structure_image, cd.related_substance_count, cd.related_structure_count, cd.iupac_name, cd.smiles, cd.inchi_string, cd.inchikey, cd.average_mass, sc.rank, sc.search_name as search_match, sc.search_value as search_word\n" +
                " from ch.v_chemical_details  cd join (\n" +
                " select  dtxsid, dtxcid, search_name, search_value, modified_value,rank\n" +
                " from ch.v_chemical_search where modified_value like :searchWord )  sc\n" +
                "    on cd.dtxsid=sc.dtxsid and cd.dtxsid is not null\n" +
                " union all\n" +
                " select cd.dtxsid,  cd.dtxcid, cd.generic_substance_id, cd.casrn, cd.preferred_name, cd.compound_id, cd.stereo, cd.isotope, cd.multicomponent, cd.pubchem_count, cd.pubmed_count, cd.sources_count, cd.cpdata_count, cd.active_assays, cd.total_assays, cd.percent_assays, cd.toxcast_select, cd.monoisotopic_mass, cd.mol_formula, cd.qc_level, cd.qc_level_desc, cd.pubchem_cid, cd.has_structure_image, cd.related_substance_count, cd.related_structure_count, cd.iupac_name, cd.smiles, cd.inchi_string, cd.inchikey, cd.average_mass, sc.rank, sc.search_name as search_match, sc.search_value as search_word\n" +
                " from ch.v_chemical_details  cd join (\n" +
                " select  dtxsid, dtxcid, search_name, search_value , modified_value,rank\n" +
                " from ch.v_chemical_search where modified_value like :searchWord% )  sc\n" +
                "    on cd.dtxsid is null and cd.dtxcid=sc.dtxcid"
)
@NamedNativeQuery(
        name = "ChemicalSearch.containCcd",
        resultSetMapping = "ccd",
        query = " select cd.dtxsid,  cd.dtxcid, cd.generic_substance_id, cd.casrn, cd.preferred_name, cd.compound_id, cd.stereo, cd.isotope, cd.multicomponent, cd.pubchem_count, cd.pubmed_count, cd.sources_count, cd.cpdata_count, cd.active_assays, cd.total_assays, cd.percent_assays, cd.toxcast_select, cd.monoisotopic_mass, cd.mol_formula, cd.qc_level, cd.qc_level_desc, cd.pubchem_cid, cd.has_structure_image, cd.related_substance_count, cd.related_structure_count, cd.iupac_name, cd.smiles, cd.inchi_string, cd.inchikey, cd.average_mass, sc.rank, sc.search_name as search_match, sc.search_value as search_word\n" +
                " from ch.v_chemical_details  cd join (\n" +
                " select  dtxsid, dtxcid, search_name, search_value, modified_value,rank\n" +
                " from ch.v_chemical_search where modified_value like CONCAT('%', :searchWord,'%') )  sc\n" +
                "    on cd.dtxsid=sc.dtxsid and cd.dtxsid is not null\n" +
                " union all\n" +
                " select cd.dtxsid,  cd.dtxcid, cd.generic_substance_id, cd.casrn, cd.preferred_name, cd.compound_id, cd.stereo, cd.isotope, cd.multicomponent, cd.pubchem_count, cd.pubmed_count, cd.sources_count, cd.cpdata_count, cd.active_assays, cd.total_assays, cd.percent_assays, cd.toxcast_select, cd.monoisotopic_mass, cd.mol_formula, cd.qc_level, cd.qc_level_desc, cd.pubchem_cid, cd.has_structure_image, cd.related_substance_count, cd.related_structure_count, cd.iupac_name, cd.smiles, cd.inchi_string, cd.inchikey, cd.average_mass, sc.rank, sc.search_name as search_match, sc.search_value as search_word\n" +
                " from ch.v_chemical_details  cd join (\n" +
                " select  dtxsid, dtxcid, search_name, search_value , modified_value,rank\n" +
                " from ch.v_chemical_search where modified_value like CONCAT('%', :searchWord,'%') )  sc\n" +
                "    on cd.dtxsid is null and cd.dtxcid=sc.dtxcid"
)
// end - named queries for search results
public class ChemicalSearch {
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "dtxsid", length = 20)
    private String dtxsid;

    @Size(max = 20)
    @Column(name = "dtxcid", length = 20)
    private String dtxcid;

    @Size(max = 255)
    @Column(name = "casrn")
    private String casrn;

    @Column(name = "smiles")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String smiles;

    @Size(max = 255)
    @Column(name = "preferred_name")
    private String preferredName;

    @Size(max = 50)
    @Column(name = "search_group", length = 50)
    private String searchGroup;

    @Size(max = 50)
    @Column(name = "search_name", length = 50)
    private String searchName;

    @Column(name = "search_value")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String searchValue;

    @Column(name = "modified_value")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String modifiedValue;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "has_structure_image")
    private Integer hasStructureImage;

    @Column(name = "is_markush")
    private Boolean isMarkush;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_at")
    private Instant createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDtxsid() {
        return dtxsid;
    }

    public void setDtxsid(String dtxsid) {
        this.dtxsid = dtxsid;
    }

    public String getDtxcid() {
        return dtxcid;
    }

    public void setDtxcid(String dtxcid) {
        this.dtxcid = dtxcid;
    }

    public String getCasrn() {
        return casrn;
    }

    public void setCasrn(String casrn) {
        this.casrn = casrn;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSearchGroup() {
        return searchGroup;
    }

    public void setSearchGroup(String searchGroup) {
        this.searchGroup = searchGroup;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getModifiedValue() {
        return modifiedValue;
    }

    public void setModifiedValue(String modifiedValue) {
        this.modifiedValue = modifiedValue;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getHasStructureImage() {
        return hasStructureImage;
    }

    public void setHasStructureImage(Integer hasStructureImage) {
        this.hasStructureImage = hasStructureImage;
    }

    public Boolean getIsMarkush() {
        return isMarkush;
    }

    public void setIsMarkush(Boolean isMarkush) {
        this.isMarkush = isMarkush;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}