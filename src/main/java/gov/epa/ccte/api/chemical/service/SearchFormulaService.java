package gov.epa.ccte.api.chemical.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@Service
public class SearchFormulaService {

    public List<String> getValidFormulas(String formula) {

        // This compounds map for  parsed compounds with their count and order preserved
        Map<String, ArrayList<String>>  compoundMap;

        formula = formula.replaceAll("\\s",""); // remove all spaces
        // Parse formula in compounds
        compoundMap = parseFormula(formula);

        List<String> formulas = buildFormulasArray(compoundMap);

        formulas = removeAtomsWithZero(formulas); // case when we have C0 which should be removed from formula

        return formulas;
    }

    public Map<String, ArrayList<String>> parseFormula(String formula) {

        ArrayList<String> elements = new ArrayList<>();

        //using linked hash map to preserve order and not sort
        Map<String, ArrayList<String>>  compoundMap = new LinkedHashMap<>();

        //
        Pattern pattern = Pattern.compile("[A-Z][a-z]*[(]?[\\d]*[-]?[\\d]*[)]?");

        Matcher matcher = pattern.matcher(formula);

        while(matcher.find()){
            String element = matcher.group();
            String atom = parseAtom(element);
            ArrayList<String> atoms = parseAtoms(element, atom);
            compoundMap.put(atom, atoms);
        }


        return compoundMap;
    }

    public ArrayList<String> parseAtoms(String element, String atom) {
        ArrayList<String> atoms = new ArrayList<>();

        Pattern pattern = Pattern.compile("[\\d]+");

        Matcher matcher = pattern.matcher(element);

        int start = 0;
        int end = 0;

        if(matcher.find()){
            String temp = matcher.group();
            start = Integer.parseInt(temp);
        }

        if(matcher.find())
            end = Integer.parseInt(matcher.group());

        if(matcher.find())
            start = 0; // this is error

        if(end != 0) {
            // we have range
            for(int i = start; i <= end; i++)
               // if(i!=0) // we shouldn't have O0 atom
                if(i==1){
                    atoms.add(atom);
                } else {
                    atoms.add(atom + String.valueOf(i));
                }
        }else{
            //single atom
            //atoms.add(atom + String.valueOf(start));
            if(start==0){
                atoms.add(atom);
            } else {
                atoms.add(atom + String.valueOf(start));
            }
        }

        return atoms;
    };

    private String parseAtom(String element) {
        String atom="";

        Pattern pattern = Pattern.compile("[A-Z][a-z]*");

        Matcher matcher = pattern.matcher(element);

        // I need to implement check for CoC condition which is a wrong chemical name
        while(matcher.find()){
            atom = matcher.group();
        }

        return atom;
    }

    private <T, C extends Collection<T>> Stream<C> ofCombinations(
            Collection<? extends Collection<T>> values, Supplier<C> supplier) {
        if (values.isEmpty())
            return Stream.empty();
        return comb(new ArrayList<>(values), 0, null, supplier);
    }

    private static <T, C extends Collection<T>> Stream<C> comb(
            List<? extends Collection<T>> values, int offset, Prefix<T> prefix,
            Supplier<C> supplier) {
        if (offset == values.size() - 1)
            return values.get(offset).stream()
                    .map(e -> new Prefix<>(prefix, e).addTo(supplier.get()));
        return values.get(offset).stream()
                .flatMap(e -> comb(values, offset + 1, new Prefix<>(prefix, e), supplier));
    }

    private static class Prefix<T> {
        final T value;
        final Prefix<T> parent;

        Prefix(Prefix<T> parent, T value) {
            this.parent = parent;
            this.value = value;
        }

        // put the whole prefix into given collection
        <C extends Collection<T>> C addTo(C collection) {
            if (parent != null)
                parent.addTo(collection);
            collection.add(value);
            return collection;
        }
    }


    private List<String> buildFormulasArray(Map<String, ArrayList<String>> compoundMap){
        // C6H(8-10))O(0-2) --> C6H8O0, C6H8O, C6H8O2, C6H9O0, C6H9O, C6H9O2, C6H10O0, C6H10O, C6H10O2.
        Set<String> elements = compoundMap.keySet();
        ArrayList<String> formulaArray = new ArrayList<>();

        Stream<Collection<String>> stream = ofCombinations(compoundMap.values(), LinkedHashSet::new);

        stream.forEach(e-> formulaArray.add(String.join("", e)));

        return formulaArray;
    }

    public List<String> removeAtomsWithZero(List<String> formulas){
        List<String> processedFormula = new ArrayList<>();

        for(String formula : formulas){
            processedFormula.add(formula.replaceAll("([A-Z][a-z]*0)+", ""));

        }

        return processedFormula;
    }
}
