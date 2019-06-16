package Futopart3.futoshiki2;

/**
 * Abstract class Constraint - Hold the two squares, and ensure there is a test,
 * and a way of getting a symbol
 *
 * @author ianw
 * @version 1.1
 */
public abstract class Constraint implements java.io.Serializable{
private static final long SerialVersionUID = 1L;
    //coordinates of squares involved in this constraint
    private FutoshikiSquare before, after;

    public FutoshikiSquare getBefore() {
        return before;
    }

    public void setBefore(FutoshikiSquare before) {
        this.before = before;
    }

    public FutoshikiSquare getAfter() {
        return after;
    }

    public void setAfter(FutoshikiSquare after) {
        this.after = after;
    }

    private boolean editable;

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Constraint(FutoshikiSquare before, FutoshikiSquare after) {
        this.before = before;
        this.after = after;
    }

    public Constraint(Constraint c) {
        this.before = c.before;
        this.after = c.after;
    }
    
    protected boolean isHorizontal() {
        return before.getRow() == after.getRow();
    }

    public boolean isLegal() {
        return before.isEmpty() || after .isEmpty() ||
                (!before.isEmpty() && !after.isEmpty() && isSatisfied());
    }

    public abstract boolean isSatisfied();

    public abstract String getSymbol();
}
