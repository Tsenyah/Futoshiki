package Futopart3.futoshiki2;

/**
 * If filledSquares, run the test
 *
 * @author ianw
 * @version 1.0
 */
public class LessThanConstraint extends Constraint {

    public LessThanConstraint(FutoshikiSquare before, FutoshikiSquare after) {
        super(before, after);
    }

    public LessThanConstraint(Constraint c) {
        super(c);
    }

    public String getSymbol() {
        if (isHorizontal()) { // row constraint
            return "<";
        } else {              //column constraint
            return "^";
        }
    }

    @Override
    public boolean isSatisfied() {
        return !getBefore().isEmpty() && !getAfter().isEmpty()
                && getBefore().getValue() < getAfter().getValue();
    }
}
