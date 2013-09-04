package RE.util;

public enum Direction {
	NORTH(0,0,-1),
	EAST(1,0,0),
	WEST(-1,0,0),
	SOUTH(0,0,1),
	UP(0,1,0),
	DOWN(0,-1,0),;

	public final int xOffset;
	public final int yOffset;
	public final int zOffset;

	Direction(int xOffset, int yOffset, int zOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}
	
	public Direction getOpposite()
	{
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
		default:
			throw new RuntimeException("I have no idea what went wrong.");
		}
	}
}
