public class AddSubExpr extends Expression{

    protected Expression rightChild;
    protected Expression leftChild;

    public AddSubExpr(String value){
        super(value);
    }

    public void setValue(String newValue){
        this.value = newValue;
    }

    public void setRoot(Expression atomExpr) {
        this.root = atomExpr;
    }

    public Expression getRoot(){
        return this.root;
    }

    public void addLeftChild(Expression child){
        this.leftChild = child;
    }

    public void addRightChild(Expression child){
        this.rightChild = child;
    }

    public Expression getLeftChild(){
        return this.leftChild;
    }

    public Expression getRightChild(){
        return this.rightChild;
    }

    public String accept(Visitor visitor){
        return visitor.visit(this);
    }

    public String getValue(){
        return this.value;
    }

    public void reset(){
        setValue(" ");
        setRoot(null);
        addLeftChild(null);
        addRightChild(null);
    }

}
