package rules;

import exceptions.CheckFailed;

public class Min implements Rule {
    private final int bound;

    public Min(int val) {
        this.bound = val;
    }

    @Override
    public void check(String val) throws CheckFailed {

        int v = Integer.parseInt(val);
        if (v < bound) {
            throw new CheckFailed(String.format("Значение должно быть больше %s", bound));
        }
    }
}
