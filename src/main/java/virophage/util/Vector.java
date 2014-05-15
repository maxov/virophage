package virophage.util;

import java.awt.*;

/**
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 * Class for representing vector operations.
 */
public class Vector {

    /**
     * The x unit vector.
     */
    public static final Vector i = new Vector(1, 0);

    /**
     * The y unit vector.
     */
    public static final Vector j = new Vector(0, 1);

    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public boolean equals(Vector that) {
        return x == that.x && y == that.y;
    }

    public Vector negate() {
        return new Vector(-x, -y);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        return new Vector(x / magnitude(), y / magnitude());
    }

    public Vector add(Vector that) {
        return new Vector(x + that.x, y + that.y);
    }

    public Vector subtract(Vector that) {
        return this.add(that.negate());
    }

    public double direction() {
        if(x > 0) {
            return Math.atan(y / x);
        } else if (x < 0) {
            return Math.atan(y / x) + Math.PI;
        } else {
            if(y > 0) {
                return Math.PI / 2;
            } else if(y < 0) {
                return 3 * Math.PI / 2;
            } else {
                throw new ArithmeticException("A zero vector has no direction");
            }
        }
    }

    public Vector rotate(double theta) {
        double newTheta = theta + direction();
        double magnitude = magnitude();
        return new Vector(Math.cos(newTheta) * magnitude, Math.sin(newTheta) * magnitude);
    }

    public Vector scale(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    public Dimension toDimension() {
        return new Dimension((int) x, (int) y);
    }

}
