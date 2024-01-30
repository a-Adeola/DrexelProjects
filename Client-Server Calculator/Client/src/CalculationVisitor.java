import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculationVisitor implements Visitor{

    @Override
    public String visit(Expression expression) {
        return calculate(expression);
    }

    public String calculate(Expression expression){
        String equation = getEquation(expression);

        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (char c : equation.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            } else {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb = new StringBuilder();
                }
                tokens.add(Character.toString(c));
            }
        }

        if (sb.length() > 0) {
            tokens.add(sb.toString());
        }

        evalMultDivision(tokens);
        return evalAddSub(tokens);
    }

    private static void evalMultDivision(List<String> tokens) {
        double result = 0.0;

        for (int i = 0; i < tokens.size(); i++) {
            String digit = tokens.get(i);
            if (digit.equals("*") || digit.equals("/")) {
                double leftOperand = Double.parseDouble(tokens.get(i - 1));
                double rightOperand = Double.parseDouble(tokens.get(i + 1));

                if (digit.equals("*")){
                    result = leftOperand * rightOperand;
                } else {
                    result = leftOperand / rightOperand; //what ifr right is 0
                }

                tokens.remove(i + 1);
                tokens.set(i, Double.toString(result));
                tokens.remove(i - 1);
                i--;
            }
        }
    }

    private static String evalAddSub(List<String> tokens) {
        double result = Double.parseDouble(tokens.get(0));

        String operator = "";

        for (int i = 1; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals("+") || token.equals("-")) {
                operator = token;
            } else {
                double operand = Double.parseDouble(token);
                if (operator.equals("+")) {
                    result += operand;
                } else {
                    result -= operand;
                }
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(result);
    }

    public String getEquation(Expression expression){
        String equation = "";
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
            equation += getEquation(leftChild);
        }
        equation += expression.getValue();
        if (rightChild != null){
            equation += getEquation(rightChild);
        }

        return equation;
    }
}
