package virophage.util;

import java.util.ArrayList;

/**
 * A <code>Location</code> represents the x & y coordinates of a cell.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class Location {

    public static final Vector X_VECTOR = new Vector(
            0.5 * HexagonConstants.TRI_WIDTH + HexagonConstants.RADIUS,
            HexagonConstants.TRI_HEIGHT
    );

    public static final Vector Y_VECTOR = new Vector(
            0,
            2 * HexagonConstants.TRI_HEIGHT
    );

    public static final Vector Z_VECTOR = new Vector(
            0.5 * HexagonConstants.TRI_WIDTH + HexagonConstants.RADIUS,
            -HexagonConstants.TRI_HEIGHT
    );

    public static final int[][] NEIGHBORS = {
            {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {+1, -1}, {+1, 0}
    };

    public final int x;
    public final int y;
    public final int z;

    /**
     * Constructs a new Location at (x,y)
     *
     * @param x the xCoordinate
     * @param y the yCoordinate
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        // z is a computed property
        this.z = -(x + y);
    }

    /**
     * Constructs a new Location at (x,y,z)
     *
     * @param x the xCoordinate
     * @param y the yCoordinate
     * @param z an internal parameter
     */
    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector asCoordinates() {
        return X_VECTOR.scale(x).add(Y_VECTOR.scale(y));
    }

    public double getDirectionTowards(Location that) {
        return that.asCoordinates().subtract(asCoordinates()).direction();
    }

    /**
     * Check if this Location is within a hexagon of a size n.
     *
     * @param n the size of the hexagon(radius)
     * @return a boolean
     */
    public boolean inHexagon(int n) {
        return Math.abs(x) <= n && Math.abs(y) <= n;
    }

    public boolean equals(Location other) {
        return (this.x == other.x && this.y == other.y && this.z == other.z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Gets the neighbors of this Location.
     *
     * @return the list of neighbors for this locaiton
     */
    public ArrayList<Location> getNeighbors() {
        ArrayList<Location> list = new ArrayList<Location>();

        for (int[] neighbor : NEIGHBORS) {
            Location l = new Location(this.x + neighbor[0], this.y + neighbor[1]);
            if (l.inHexagon(GameConstants.N) && !l.equals(this)) {
                list.add(l);
            }
        }

        return list;
    }

    /**
     * Check if the given location is a neighbor.
     *
     * @param loc a Location
     * @return a boolean
     */
    public boolean isNeighbor(Location loc) {
        for (Location neighbor : getNeighbors()) {
            if (neighbor.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A string representation of this location.
     *
     * @return a String
     */
    public String toString() {
        return "(" + x + ", " + y + ", (" + z + "))";
    }

}
