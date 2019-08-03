package lk.sliit.yummyeats.Controller;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidater {

    private static final String  passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean isEmpty(EditText editText){
        if(editText.getText().toString().isEmpty())
            return true;
        else
            return false;
    }

    public boolean isValidPassword(EditText editText){
        String textValue = editText.getText().toString();
        if(textValue.matches(passwordPattern))
            return true;
        else
            return false;
    }

    public boolean isValidEmail(EditText editText){

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(editText.getText().toString());
        return matcher.find();
    }
}
