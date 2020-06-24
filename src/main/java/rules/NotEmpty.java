package rules;

import exceptions.CheckFailed;

public class NotEmpty implements Rule {
    @Override
    public void check(String val) throws CheckFailed {
        if (val.equals("")) {
            throw new CheckFailed("Поле не может быть пустым");
        }
    }
}
