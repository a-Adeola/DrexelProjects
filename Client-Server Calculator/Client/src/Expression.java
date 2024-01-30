public abstract class Expression {

    protected String value;
    protected Expression root;

    public Expression(String value){
        this.value = value;
    }

    public abstract String getValue();

    public abstract void setValue(String newValue);

    public abstract String accept(Visitor visitor);

    public abstract void addLeftChild(Expression child);
    public abstract void addRightChild(Expression child);

    public abstract Expression getLeftChild();

    public abstract Expression getRightChild();

    public abstract void setRoot(Expression atomExpr);

    public abstract Expression getRoot();

    public abstract void reset();
}
