package RE.util;

/**
 * Provides an easy way to determine Directions, also based on own coordinates
 * 
 * @author Larethian
 * 
 */
public enum Direction {
	NORTH(0, 0, -1), EAST(1, 0, 0), WEST(-1, 0, 0), SOUTH(0, 0, 1), UP(0, 1, 0), DOWN(
			0, -1, 0), SELF(0, 0, 0);

	/**
	 * The xOffset. This is always either -1, 0 or 1.
	 */
	public final int xOffset;
	/**
	 * The yOffset. This is always either -1, 0 or 1.
	 */
	public final int yOffset;
	/**
	 * The zOffset. This is always either -1, 0 or 1.
	 */
	public final int zOffset;

	Direction(int xOffset, int yOffset, int zOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}

	/**
	 * Returns the opposite of the Direction it is called on.
	 * <p>
	 * <p>
	 * For example: NORTH.getOpposite() -> SOUTH
	 * <p>
	 * Attention: SELF.getOpposite() -> SELF
	 * 
	 * @return The opposite Direction to the Direction this method was called
	 *         on.
	 */
	public Direction getOpposite() {
		switch (this) {
		case DOWN:
			return UP;
		case UP:
			return DOWN;
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case SELF:
			return SELF;
		default:
			throw new RuntimeException("I have no idea what went wrong.");
		}
	}

	/**
	 * See at {@link Direction#getOpposite()}
	 * 
	 * @param dir
	 *            The Direction to get the opposite of.
	 * @return See at {@link Direction#getOpposite()}
	 */
	public static Direction getOpposite(Direction dir) {
		return dir.getOpposite();
	}

	/**
	 * This provides a {@link Vector3i} with the Offset this Method is called
	 * on.
	 * 
	 * @return A Vector3i containing the offset.
	 */
	public Vector3i getOffset() {
		return new Vector3i(this.xOffset, this.yOffset, this.zOffset);
	}
}
