package lk.sliit.yummyeats.Controller;

import android.widget.EditText;

public class InputValidater {

    String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

    public boolean isEmpty(EditText editText){
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
        return true;
    }
}
