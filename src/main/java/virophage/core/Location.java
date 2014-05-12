package virophage.core;

import virophage.render.HexagonNode;
import virophage.util.Vector;

public class Location {

    public static final Vector X_VECTOR = new Vector(
            0.5 * HexagonNode.TRI_WIDTH + HexagonNode.RADIUS,
            HexagonNode.TRI_HEIGHT
    );

    public static final Vector Y_VECTOR = new Vector(
            0,
            2 * HexagonNode.TRI_HEIGHT
    );

    public static final Vector Z_VECTOR = new Vector(
            0.5 * HexagonNode.TRI_WIDTH + HexagonNode.RADIUS,
            -HexagonNode.TRI_HEIGHT
    );

    public final int x;
    public final int y;
    public final int z;

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


}
