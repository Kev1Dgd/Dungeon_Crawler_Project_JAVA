/**
 * The Direction enum represents the four cardinal directions
 * and provides a numeric value for each direction, which can be
 * used for animation or logic purposes.
 */
public enum Direction {
    NORTH(3), // Represents the North direction
    SOUTH(0), // Represents the South direction
    EAST(2),  // Represents the East direction
    WEST(1);  // Represents the West direction

    private final int value; // Numeric value associated with the direction

    /**
     * Constructor for the Direction enum.
     *
     * @param value the numeric value associated with this direction
     */
    Direction(int value) {
        this.value = value;
    }

    /**
     * Retrieves the numeric value associated with this direction.
     *
     * @return the numeric value of the direction
     */
    public int getValue() {
        return value;
    }
}
