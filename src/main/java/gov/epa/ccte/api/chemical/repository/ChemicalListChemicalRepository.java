package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ChemicalListChemical;
import gov.epa.ccte.api.chemical.web.rest.GhsLinkResponse;
import gov.epa.ccte.api.chemical.web.rest.WikipediaLinkResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(exported = false)
public interface ChemicalListChemicalRepository extends JpaRepository<ChemicalListChemical, Long> {



    @Query("select distinct c.listName from ChemicalListChemical c join ChemicalList l on c.listId = l.id and c.dtxsid = :dtxsid")
    List<String> getListNames(String dtxsid, String visibility);

    @Query(nativeQuery = true,
    value = " select distinct s.dtxsid from ch.v_chemical_search s join ch.v_chemical_list_chemicals_new l on l.dtxsid = s.dtxsid " +
            " where s.modified_value like :word% and upper(l.list_name) = upper(:list) ")
    List<String> startWith(String word, String list);

    @Query(nativeQuery = true,
            value = " select distinct s.dtxsid from ch.v_chemical_search s join ch.v_chemical_list_chemicals_new  l on l.dtxsid = s.dtxsid " +
                    " where s.modified_value like %:word% and upper(l.list_name) = upper(:list) ")
    List<String> contain(String word, String list);

    @Query(nativeQuery = true,
            value = " select distinct s.dtxsid from ch.v_chemical_search s join ch.v_chemical_list_chemicals_new  l on l.dtxsid = s.dtxsid " +
                    " where s.modified_value = :word and upper(l.list_name) = upper(:list) ")
    List<String> exact(String word, String list);

    @Query(nativeQuery = true,
    value = " select dtxsid || '-' || list_name from ch.v_chemical_list_chemicals_new  where dtxsid in (:dtxsids) and list_name in (:chemicalLists) ")
    List<String> chemicalListsAndDtxsids( List<String> chemicalLists, List<String> dtxsids);


    @Query("select new gov.epa.ccte.api.chemical.web.rest.GhsLinkResponse(d.dtxsid, " +
            "case when l.dtxsid is null then false else true end, " +
            "case when l.dtxsid is null then null else 'https://pubchem.ncbi.nlm.nih.gov/compound/' ||  d.dtxsid || '#section=GHS-Classification' end ) " +
            "from  ChemicalDetail d left join ChemicalListChemical l on l.dtxsid = d.dtxsid and l.listName = 'LCSSPUBCHEM' where d.dtxsid in (?1)")
    List<GhsLinkResponse> isGhsLinkExists(String[] dtxsid);

    @Query("select l.dtxsid from ChemicalListChemical l where upper(l.listName) = upper(:list)")
    List<String> getDtxsids(String list);

    @Query("select new gov.epa.ccte.api.chemical.web.rest.WikipediaLinkResponse(d.dtxsid, " +
            "case when l.dtxsid is null then null else 'https://en.wikipedia.org/wiki/' ||  d.inchikey || '#section=wiki-Classification' end ) " +
            "from  ChemicalDetail d left join ChemicalListChemical l on l.dtxsid = d.dtxsid and l.listName = 'WIKIPEDIA' where d.dtxsid in (?1)")
    List<WikipediaLinkResponse> isWikipediaLinkExists(String[] dtxsid);

}
