package virophage.util;

/**
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class HexagonConstants {

    public static final double RADIUS = 100;
    public static final double TRI_WIDTH = RADIUS;
    public static final double TRI_HEIGHT = (int) (TRI_WIDTH / 2 * Math.sqrt(3));

    public static final double BOUNDS_WIDTH = RADIUS * 2;
    public static final double BOUNDS_HEIGHT = TRI_WIDTH * 2;

}