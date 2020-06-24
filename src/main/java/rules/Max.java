package rules;

import exceptions.CheckFailed;

public class Max implements Rule {
    private final int bound;

    public Max(int val) {
        this.bound = val;
    }

    @Override
    public void check(String val) throws CheckFailed {
        int v = Integer.parseInt(val);
        if (v > bound) {
            throw new CheckFailed(String.format("Значение должно быть меньше %s", bound));
        }
    }
}
