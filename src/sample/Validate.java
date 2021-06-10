package sample;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    private static final String CODE_REGEX = "^[CAP]{1}[0-9]{4}[GHIKLM]{1}[0-9]{1}$";
    private static final String NAME_REGEX = "\\D{1,}";
    private static final String DOB_REGEX = "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$";
    private static final String ADDRESS_REGEX = "\\D{1,}";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String GENDER_REGEX = "^(?i)(true|false)$";
    private static final String GPA_REGEX = "^[0-9]{1}$";

    public Validate() {
    }
    public boolean validateAdress(String regex) {
        Pattern pattern = Pattern.compile(ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }
    public boolean validateGpa(String regex) {
        Pattern pattern = Pattern.compile(GPA_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }
    public boolean validateGender(String regex) {
        Pattern pattern = Pattern.compile(GENDER_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    public boolean validateCode(String regex) {
        Pattern pattern = Pattern.compile(CODE_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }
    public boolean validateName(String regex){
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }
    public boolean validateDob(String regex){
        Pattern pattern = Pattern.compile(DOB_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }
    public boolean validateEmail(String regex){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }
}
