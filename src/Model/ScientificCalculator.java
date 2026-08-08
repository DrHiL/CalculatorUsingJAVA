package Model;

/**
 * ScientificCalculator extends Calculator with trigonometric, power and angle conversion functions, as required by the scientific mode of the calculator application.
 */
public class ScientificCalculator extends Model.Calculator {

    /**
     * Converts an angle measured in degrees into radians.
     */
    public double degreesToRadians(double degrees) {

        return Math.toRadians(degrees);
    }

    /**
     * Calculates the sine of an angle given in radians.
     */
    public double sinRadians(double radians) {
        double result = Math.sin(radians);
        setLastResult(result);
        return result;
    }

    /**
     * Calculates the sine of an angle given in degrees.
     */
    public double sinDegrees(double degrees) {

        return sinRadians(degreesToRadians(degrees));
    }

    /**
     * Calculates the cosine of an angle given in radians.
     */
    public double cosRadians(double radians) {
        double result = Math.cos(radians);
        setLastResult(result);
        return result;
    }

    /**
     * Calculates the cosine of an angle given in degrees.
     */
    public double cosDegrees(double degrees) {

        return cosRadians(degreesToRadians(degrees));
    }

    /**
     * Calculates the tangent of an angle given in radians.
     */
    public double tanRadians(double radians) {
        double result = Math.tan(radians);
        setLastResult(result);
        return result;
    }

    /**
     * Calculates the tangent of an angle given in degrees.
     */
    public double tanDegrees(double degrees) {

        return tanRadians(degreesToRadians(degrees));
    }

    /**
     * Squares an integer.
     */
    public int square(int n) {
        int result = n * n;
        setLastResult(result);
        return result;
    }

    /**
     * Squares a floating-point number.
     */
    public double square(double n) {
        double result = n * n;
        setLastResult(result);
        return result;
    }

    /**
     * Cubes an integer.
     */
    public int cube(int n) {
        int result = n * n * n;
        setLastResult(result);
        return result;
    }

    /**
     * Cubes a floating-point number.
     */
    public double cube(double n) {
        double result = n * n * n;
        setLastResult(result);
        return result;
    }

    /**
     * Calculates the square root of a number.
     */
    public double squareRoot(double n) {
        if (n < 0) {
            throw new ArithmeticException("Cannot take the square root of a negative number.");
        }
        double result = Math.sqrt(n);
        setLastResult(result);
        return result;
    }

    /**
     * Calculates the reciprocal (1/x) of a number.
     */
    public double reciprocal(double n) {
        if (n == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        double result = 1.0 / n;
        setLastResult(result);
        return result;
    }

    /**
     * Calculates the cotangent of an angle given in radians.
     */
    public double cotRadians(double radians) {
        double tangent = Math.tan(radians);
        if (tangent == 0) {
            throw new ArithmeticException("Cotangent is undefined for this angle.");
        }
        double result = 1.0 / tangent;
        setLastResult(result);
        return result;
    }

    /**
     * Calculates the cotangent of an angle given in degrees.
     */
    public double cotDegrees(double degrees) {
        return cotRadians(degreesToRadians(degrees));
    }
}