package mike.bootstrap.utilities.helpers;

/**
 * String offset with position (starts at 1) and length.
 * 
 * @author Mike
 */
public class Offset {

    private final int position;
    private final int length;
    private final int startAt;
    private final int endAt;

    /**
     * @return offset with position and length set to 1
     */
    public static Offset of() {
        return new Offset(1, 1);
    }

    /**
     * @param position offset starts at position
     * @return offset starting at the given position and minimal length 1.
     */
    public static Offset of(int position) {
        return Offset.of(position, 1);
    }

    /**
     * @param position offset starts at position
     * @param length   offset length
     * @return
     */
    public static Offset of(int position, int length) {
        return new Offset(position, length);
    }

    /**
     * Constructor.
     */
    private Offset(int position, int length) {
        PreConditions.test(position > 0, "Offset: position must be a positive number");
        PreConditions.test(length > 0, "Offset: length must be a positive number");

        this.position = position;
        this.length = length;
        this.startAt = position - 1;
        this.endAt = this.startAt + this.length;
    }

    public int position() {
        return position;
    }

    public int length() {
        return length;
    }

    /**
     * Offset starting position (i.e.: position - 1)
     * 
     * @return starting position
     */
    public int startAt() {
        return startAt;
    }

    /**
     * Offset ending position (i.e.: position - 1 + length)
     * 
     * <pre>
     * Offset(2:2): "ABBCC" => "BB" (startAt=1, endAt=4 (excluded))
     * </pre>
     * 
     * @return ending position
     */
    public int endAt() {
        return endAt;
    }

    @Override
    public String toString() {
        return String.format("Offset [pos=%d, len=%d, startAt=%d, endAt=%d]", position, length,
                startAt, endAt);
    }
}
