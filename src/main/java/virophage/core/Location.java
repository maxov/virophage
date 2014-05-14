package virophage.core;

import java.util.ArrayList;

import virophage.gui.GameClient;
import virophage.util.HexagonConstants;
import virophage.util.Vector;

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

    public final int x;
    public final int y;
    public final int z;
    
    public final int[][] neighbors = {
    		{-1,  0}, {-1, +1}, { 0, -1}, {0,  +1}, {+1, -1}, {+1, 0}
    };

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        // z is a computed property
        this.z = -(x + y);
    }

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector asCoordinates() {
        return X_VECTOR.scale(x).add(Y_VECTOR.scale(y));
    }

    public boolean isValidLoc() {
    	return (Math.abs(x+y) <= GameClient.N);
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
    
    public ArrayList<Location> getNeighbors() {
    	ArrayList<Location> list = new ArrayList<Location>();
    
    	for (int i = 0; i < neighbors.length; i++) {
    			Location l = new Location(this.x + neighbors[i][0], this.y + neighbors[i][1]);
    			if (l.isValidLoc() && !l.equals(this)) {
    				list.add(l);
    			}
    	}
    	
    	return list;
    }
}
