package virophage.util;

import java.util.ArrayList;

import virophage.gui.GameClient;

/**
 * A <code>Location</code> represents the x & y coordinates of a cell.
 *
 * @author      Max Ovsiankin, Leon Ren
 * @since       2014-05-6
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
    
    public static final double[][] MULT_MATRIX = {
    	{0.666667/HexagonConstants.RADIUS, -0.333333/HexagonConstants.RADIUS},
    	{0, 0.57735/HexagonConstants.RADIUS}
    };
    
    public static Location mfind(Vector pos) {
    	return new Location(
    			(int) (pos.x *MULT_MATRIX[0][0] + pos.y * MULT_MATRIX[0][1]), 
    			(int) (pos.x *MULT_MATRIX[1][0] + pos.y * MULT_MATRIX[1][1]));
    }

    public final int x;
    public final int y;
    public final int z;

    /**
     * Constructs a new Location at (x,y)
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
     * Constructs a new Location at (x,y)
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

    public boolean inHexagon(int n) {
        return Math.abs(x) <= n && Math.abs(y) <= n;
    }

    public boolean equals(Location other) {
        return (this.x == other.x && this.y == other.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Gets the neighbors of this Location.
     * @return the list of neighbors for this locaiton
     */
    public ArrayList<Location> getNeighbors() {
        ArrayList<Location> list = new ArrayList<Location>();

        for (int[] neighbor : NEIGHBORS) {
            Location l = new Location(this.x + neighbor[0], this.y + neighbor[1]);
            if (l.inHexagon(GameClient.N) && !l.equals(this)) {
                list.add(l);
            }
        }

        return list;
    }
    
    public boolean isNeighbor(Location loc) {
    	for(Location neighbor: getNeighbors()) {
    		if(neighbor.equals(loc)) {
    			return true;
    		}
    	}
    	return false;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
