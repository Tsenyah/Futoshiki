package Futopart3.futoshiki2;
/**
 * If there are filled squares, enable testing
 * 
 * @author ianw
 * @version 1.0
 */
public class GreaterThanConstraint extends Constraint
{

    public GreaterThanConstraint(FutoshikiSquare before, FutoshikiSquare after) {
        super(before, after);
    }

    public GreaterThanConstraint(Constraint c) {
        super(c);
    }

    
    
    
    @Override
    public String getSymbol()
    {
        if (isHorizontal()) { // row constraint
            return ">";
        }
        else {
            return "V";
        }
    }

    @Override
    public boolean isSatisfied() {
        return !getBefore().isEmpty() && !getAfter().isEmpty()
                && getBefore().getValue() > getAfter().getValue();
           }


}
