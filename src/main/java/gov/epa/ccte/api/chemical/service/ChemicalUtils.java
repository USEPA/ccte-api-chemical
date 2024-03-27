package gov.epa.ccte.api.chemical.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChemicalUtils {
    public ChemicalUtils() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isDtxcid(String dtxcid) {
        dtxcid = dtxcid.toUpperCase();
        return dtxcid.matches("DTXCID(.*)");
    }

    public static boolean isDtxsid(String dtxsid) {
        dtxsid = dtxsid.toUpperCase();
        return dtxsid.matches("DTXSID(.*)");
    }

    public static boolean isECNumber(String casrn) {
        return casrn.matches("^\\d{3}-\\d{3}-\\d$");
    }

    public static boolean isChemicalSynonym(String word){

        return !isCasrn(word) && !isDtxcid(word) && !isDtxsid(word) && !isECNumber(word) && !isInchiKey(word) && !isInchiKeySkeleton(word);
    }

    public static boolean isInchiKey(String inchikey) {
        inchikey = inchikey.toUpperCase();
        return inchikey.matches("[A-Z]{14}-[A-Z]{10}-[A-Z]");
    }

    public static boolean isInchiKeySkeleton(String inchikeyskeleton) {
        inchikeyskeleton = inchikeyskeleton.toUpperCase();
        return inchikeyskeleton.matches("[A-Z]{14}");
    }

    public static String inchikeyWithoutCharge(String inchikey){
        return inchikey.substring(0,25);
    }

    public static String inchikeyWithoutSecondlayer(String inchikey){
        return inchikey.substring(0,14);
    }

    public static boolean isCasrn(String casrn) {
        return casrn.matches("^\\d{1,7}-\\d{2}-\\d$");
    }
    public static boolean checkCasrnFormat(String casrn, boolean checkForDash) {
// Check the string against the mask
        if (checkForDash && !casrn.matches("^\\d{1,7}-\\d{2}-\\d$")) {
            return false;
        } else {
// Remove the dashes
            casrn = casrn.replaceAll("-", "");
            int sum = 0;
            for (int indx = 0; indx < casrn.length() - 1; indx++) {
                sum += (casrn.length() - indx - 1) * Integer.parseInt(casrn.substring(indx, indx + 1));
            }
// Check digit is the last char, compare to sum mod 10.
            log.debug("v1= {} and v2= {}",Integer.parseInt(casrn.substring(casrn.length() - 1)), (sum % 10));
            return Integer.parseInt(casrn.substring(casrn.length() - 1)) == (sum % 10);
        }
    }

}
