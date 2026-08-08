package Model;

/**
 * This StorageConverter is a Model class that converts digital storage amounts between bytes, kilobytes, megabytes, gigabytes, terabytes and petabytes.
 * It follows the convention that 1 kilobyte = 1024 bytes.
 */
public class StorageConverter {

    private static final double UNIT = 1024;
    private static final double KILOBYTE = UNIT;
    private static final double MEGABYTE = KILOBYTE * UNIT;
    private static final double GIGABYTE = MEGABYTE * UNIT;
    private static final double TERABYTE = GIGABYTE * UNIT;
    private static final double PETABYTE = TERABYTE * UNIT;


    private double lastConvertedValue;

    /**
     * Creates a new StorageConverter with the last converted value set to 0.
     */
    public StorageConverter() {
        this.lastConvertedValue = 0.0;
    }

    /**
     * Returns the most recently converted value.
     */
    public double getLastConvertedValue() {
        return lastConvertedValue;
    }

    /**
     * Sets the last converted value directly. Useful for resetting state.
     */
    public void setLastConvertedValue(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Storage values cannot be negative.");
        }
        this.lastConvertedValue = value;
    }


    /** Converts bytes to kilobytes.
     */
    public double bytesToKilobytes(double bytes) {
        return remember(bytes / KILOBYTE);
    }

    /** Converts bytes to megabytes.
   */
    public double bytesToMegabytes(double bytes) {
        return remember(bytes / MEGABYTE);
    }

    /** Converts bytes to gigabytes.
    */
    public double bytesToGigabytes(double bytes) {
        return remember(bytes / GIGABYTE);
    }

    /** Converts bytes to terabytes.
    */
    public double bytesToTerabytes(double bytes) {
        return remember(bytes / TERABYTE);
    }

    /** Converts bytes to petabytes.
    */
    public double bytesToPetabytes(double bytes) {
        return remember(bytes / PETABYTE);
    }



    /** Converts kilobytes to bytes.
     */
    public double kilobytesToBytes(double kb) {
        return remember(kb * KILOBYTE);
    }

    /** Converts kilobytes to megabytes.
    */
    public double kilobytesToMegabytes(double kb) {
        return remember(kb / UNIT);
    }

    /** Converts kilobytes to gigabytes.
    */
    public double kilobytesToGigabytes(double kb) {
        return remember(kb / (UNIT * UNIT));
    }

    /** Converts kilobytes to terabytes.
     */
    public double kilobytesToTerabytes(double kb) {
        return remember(kb / (UNIT * UNIT * UNIT));
    }

    /** Converts kilobytes to petabytes.
     */
    public double kilobytesToPetabytes(double kb) {
        return remember(kb / (UNIT * UNIT * UNIT * UNIT));
    }



    /** Converts megabytes to bytes.
     */
    public double megabytesToBytes(double mb) {
        return remember(mb * MEGABYTE);
    }

    /** Converts megabytes to kilobytes.
     */
    public double megabytesToKilobytes(double mb) {
        return remember(mb * UNIT);
    }

    /** Converts megabytes to gigabytes.
     */
    public double megabytesToGigabytes(double mb) {
        return remember(mb / UNIT);
    }

    /** Converts megabytes to terabytes.
     */
    public double megabytesToTerabytes(double mb) {
        return remember(mb / (UNIT * UNIT));
    }

    /** Converts megabytes to petabytes.
     */
    public double megabytesToPetabytes(double mb) {
        return remember(mb / (UNIT * UNIT * UNIT));
    }



    /** Converts gigabytes to bytes.
     */
    public double gigabytesToBytes(double gb) {
        return remember(gb * GIGABYTE);
    }

    /** Converts gigabytes to kilobytes.
     */
    public double gigabytesToKilobytes(double gb) {
        return remember(gb * UNIT * UNIT);
    }

    /** Converts gigabytes to megabytes.
     */
    public double gigabytesToMegabytes(double gb) {
        return remember(gb * UNIT);
    }

    /** Converts gigabytes to terabytes.
     */
    public double gigabytesToTerabytes(double gb) {
        return remember(gb / UNIT);
    }

    /** Converts gigabytes to petabytes.
     */
    public double gigabytesToPetabytes(double gb) {
        return remember(gb / (UNIT * UNIT));
    }



    /** Converts terabytes to bytes.
     */
    public double terabytesToBytes(double tb) {
        return remember(tb * TERABYTE);
    }

    /** Converts terabytes to kilobytes.
    */
    public double terabytesToKilobytes(double tb) {
        return remember(tb * UNIT * UNIT * UNIT);
    }

    /** Converts terabytes to megabytes.
    */
    public double terabytesToMegabytes(double tb) {
        return remember(tb * UNIT * UNIT);
    }

    /** Converts terabytes to gigabytes.
    */
    public double terabytesToGigabytes(double tb) {
        return remember(tb * UNIT);
    }

    /** Converts terabytes to petabytes.
    */
    public double terabytesToPetabytes(double tb) {
        return remember(tb / UNIT);
    }



    /** Converts petabytes to bytes.
    */
    public double petabytesToBytes(double pb) {
        return remember(pb * PETABYTE);
    }

    /** Converts petabytes to kilobytes.
    */
    public double petabytesToKilobytes(double pb) {
        return remember(pb * UNIT * UNIT * UNIT * UNIT);
    }

    /** Converts petabytes to megabytes.
    */
    public double petabytesToMegabytes(double pb) {
        return remember(pb * UNIT * UNIT * UNIT);
    }

    /** Converts petabytes to gigabytes.
    */
    public double petabytesToGigabytes(double pb) {
        return remember(pb * UNIT * UNIT);
    }

    /** Converts petabytes to terabytes.
    */
    public double petabytesToTerabytes(double pb) {
        return remember(pb * UNIT);
    }

    /**
     * This method stores a freshly converted value and returns it,
     * so that every conversion method above can remain a short one liner.
     */
    private double remember(double value) {
        this.lastConvertedValue = value;
        return value;
    }
}
