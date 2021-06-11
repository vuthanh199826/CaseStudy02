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

    public boolean validateRegex(String regex, String REGEX) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(regex);
        return matcher.matches();
    }

    public String getCodeRegex() {
        return CODE_REGEX;
    }

    public String getNameRegex() {
        return NAME_REGEX;
    }

    public String getDobRegex() {
        return DOB_REGEX;
    }

    public String getAddressRegex() {
        return ADDRESS_REGEX;
    }

    public String getEmailRegex() {
        return EMAIL_REGEX;
    }

    public String getGenderRegex() {
        return GENDER_REGEX;
    }

    public String getGpaRegex() {
        return GPA_REGEX;
    }
}
