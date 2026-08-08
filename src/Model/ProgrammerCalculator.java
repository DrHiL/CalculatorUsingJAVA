package Model;

/**
 * ProgrammerCalculator extends Calculator with bitwise operations used by the programmer mode of the calculator application. All operations work on 32-bit integers.
 */
public class ProgrammerCalculator extends Model.Calculator {

    /**
     * Performs a bitwise logical AND between two integers.
     */
    public int logicalAND(int a, int b) {
        int result = a & b;
        setLastResult(result);
        return result;
    }

    /**
     * Performs a bitwise logical OR between two integers.
     */
    public int logicalOR(int a, int b) {
        int result = a | b;
        setLastResult(result);
        return result;
    }

    /**
     * Performs a bitwise logical XOR (exclusive or) between two integers.
     */
    public int XOR(int a, int b) {
        int result = a ^ b;
        setLastResult(result);
        return result;
    }

    /**
     * Performs a bitwise inversion (NOT) on an integer, flipping every bit.

     */
    public int bitwiseInversion(int a) {
        int result = ~a;
        setLastResult(result);
        return result;
    }

    /**
     * Shifts the bits of an integer to the left by a given number of positions.
     */
    public int leftShift(int a, int positions) {
        int result = a << positions;
        setLastResult(result);
        return result;
    }

    /**
     * Shifts the bits of an integer to the right by a given number of positions.
     */
    public int rightShift(int a, int positions) {
        int result = a >> positions;
        setLastResult(result);
        return result;
    }

    /**
     * Converts an integer to its binary (base 2) string representation.
     */
    public String toBinaryString(int a) {

        return Integer.toBinaryString(a);
    }

    /**
     * Converts an integer to its octal (base 8) string representation.
     */
    public String toOctalString(int a) {

        return Integer.toOctalString(a);
    }

    /**
     * Converts an integer to its hexadecimal (base 16) string representation.
     */
    public String toHexString(int a) {

        return Integer.toHexString(a).toUpperCase();
    }
}
