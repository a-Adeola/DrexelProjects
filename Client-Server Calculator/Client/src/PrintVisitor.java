public class PrintVisitor implements Visitor{


    @Override
    public String visit(Expression expression) {
        return printer(expression);
    }

    public String printer(Expression expression){
        String toPrint = "";
        Expression leftChild;
        Expression rightChild;

        if(expression.getRoot() != null){
            leftChild = expression.getRoot().getLeftChild();
            rightChild = expression.getRoot().getRightChild();
        } else {
            leftChild = expression.getLeftChild();
            rightChild = expression.getRightChild();
        }

        if (leftChild != null){
            toPrint += printer(leftChild);
        }
        toPrint += expression.getValue();
        if (rightChild != null){
            toPrint += printer(rightChild);
        }

        return toPrint;
    }
}
