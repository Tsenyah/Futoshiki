package Futopart3.futoshiki2;
/**
 * The empty constraint class
 * 
 * @author ianw
 * @version 1.0
 */
public class EmptyConstraint extends Constraint
{

    public EmptyConstraint(FutoshikiSquare before, FutoshikiSquare after) {
        super(before, after);
    }


    
    public String getSymbol()
    {
        return " ";
    }

    @Override
    public boolean isSatisfied() {
        return true;
    }

    @Override
    public boolean isLegal() {
        return true;
    }
}
