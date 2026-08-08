package Model;

/**
 * This class is the standard calculator, it provides the four basic arithmetic operations plus modulo, and keeps track of the result of the last calculation performed.
 */
public class Calculator {


    private double lastResult;
    private String description;
    private String pendingOperator;
    private double accumulatedValue;
    private boolean pendingOperandEmbedded;
    //Constructor
    public Calculator() {
        this.lastResult = 0.0;
        this.description = "";
        this.pendingOperator = null;
        this.accumulatedValue = 0.0;
        this.pendingOperandEmbedded = false;
    }

    public double getLastResult() {

        return lastResult;
    }


    protected void setLastResult(double lastResult) {

        this.lastResult = lastResult;
    }

    public String getDescription() {

        return description;
    }

    public boolean isPartial() {

        return pendingOperator != null;
    }

    public double getResult() {

        return accumulatedValue;
    }

    public void clearAll() {
        this.lastResult = 0.0;
        this.accumulatedValue = 0.0;
        this.description = "";
        this.pendingOperator = null;
        this.pendingOperandEmbedded = false;
    }


    public void startFreshEntry() {
        if (!isPartial()) {
            description = "";
            pendingOperandEmbedded = false;
        }
    }

    public double applyBinaryOperator(String operatorSymbol, String operandText, double operandValue) {
        if (pendingOperator != null) {
            accumulatedValue = compute(pendingOperator, accumulatedValue, operandValue);
            if (pendingOperandEmbedded) {
                description = description + " " + operatorSymbol;
                pendingOperandEmbedded = false;
            } else {
                description = description + " " + operandText + " " + operatorSymbol;
            }
        } else if (!description.isEmpty()) {
            description = description + " " + operatorSymbol;
        } else {
            accumulatedValue = operandValue;
            description = operandText + " " + operatorSymbol;
        }
        pendingOperator = operatorSymbol;
        return accumulatedValue;
    }

    /**
     * Finalises the pending calculation (if any) using the value currently on display as the
     * second operand, exactly as though the user had pressed "=".
     */
    public double applyEquals(String operandText, double operandValue) {
        if (pendingOperator != null) {
            accumulatedValue = compute(pendingOperator, accumulatedValue, operandValue);
            if (!pendingOperandEmbedded) {
                description = description + " " + operandText;
            }
            pendingOperandEmbedded = false;
            pendingOperator = null;
        } else if (description.isEmpty()) {
            accumulatedValue = operandValue;
            description = operandText;
        }
        return accumulatedValue;
    }

    /**
     * Records that a unary function (such as square root or sine) has just been applied to
     * the value on display.
     */
    public void applyUnary(String funcLabel, String operandWrappedText, double resultValue) {
        if (pendingOperator != null) {
            description = description.isEmpty() ? operandWrappedText : description + " " + operandWrappedText;
            pendingOperandEmbedded = true;
        } else if (!description.isEmpty()) {
            description = funcLabel + "(" + description + ")";
            accumulatedValue = resultValue;
        } else {
            description = operandWrappedText;
            accumulatedValue = resultValue;
        }
    }

    /**
     * Computes the result of combining two operands with the given binary operator symbol,
     * reusing this calculator's own arithmetic methods so behaviour (and error handling)
     * stays consistent with direct calls to add/subtract/multiply/divide.
     */
    private double compute(String operatorSymbol, double x, double y) {
        switch (operatorSymbol) {
            case "+": return add(x, y);
            case "-": return subtract(x, y);
            case "×": return multiply(x, y);
            case "÷": return divide(x, y);
            default: return y;
        }
    }

    /**
     * sum two numbers together. a + b
     */
    public double add(double a, double b) {
        setLastResult(a + b);
        return lastResult;
    }

    /**
     * Subtracts 2 numbers between a and b .
     */
    public double subtract(double a, double b) {
        setLastResult(a - b);
        return lastResult;
    }

    /**
     * Multiplies two numbers together.
     */
    public double multiply(double a, double b) {
        setLastResult(a * b);
        return lastResult;
    }

    /**
     * Divides 2 num together.
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        setLastResult(a / b);
        return lastResult;
    }

    /**
     * Calculates the remainder of dividing the first num by the second.
     */
    public double modulo(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot perform modulo by zero.");
        }
        setLastResult(a % b);
        return lastResult;
    }
}