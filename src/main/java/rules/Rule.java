package rules;

import exceptions.CheckFailed;

public interface Rule {
    public void check(String val) throws CheckFailed;
}
