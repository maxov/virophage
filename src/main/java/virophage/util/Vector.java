package virophage.util;

import java.awt.*;

/**
 * A <code>Vector</code> represents a mathematical vector, with its many properties.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
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

    /**
     * Construct a vector from an x and why coordinate.
     *
     * @param x an x coordinate
     * @param y a y coordinate
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Convert a Point to a vector.
     *
     * @param point a Point
     */
    public Vector(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    /**
     * Check if two vectors are equal.
     *
     * @param that a vector
     * @return a boolean, if they are equal.
     */
    public boolean equals(Vector that) {
        return x == that.x && y == that.y;
    }

    /**
     * Negate this vector(turn it 180 degrees).
     *
     * @return a negated vector
     */
    public Vector negate() {
        return new Vector(-x, -y);
    }

    /**
     * Get the magnitude of this vector.
     *
     * @return a double, magnitude
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Normalize this vector so it has a magnitude of 1, but in the same direction.
     *
     * @return a normalized vector
     */
    public Vector normalize() {
        return new Vector(x / magnitude(), y / magnitude());
    }

    /**
     * Add this vector to another one.
     *
     * @param that another vecter
     * @return the resultant vector
     */
    public Vector add(Vector that) {
        return new Vector(x + that.x, y + that.y);
    }

    /**
     * Subtract another vector from this one (Equivalent to adding the negated vector).
     *
     * @param that another vector
     * @return the resultant vector
     */
    public Vector subtract(Vector that) {
        return this.add(that.negate());
    }

    /**
     * Do the mathematical dot product between vectors.
     *
     * @param that another vector
     * @return the resultant vector
     */
    public double dot(Vector that) {
        return x * that.x + y * that.y;
    }

    /**
     * Return the direction of this vector, in radians. Careful care is taken
     * to ensure that this is done correctly.
     *
     * @return the direction in radians
     */
    public double direction() {
        if (x > 0) {
            return Math.atan(y / x);
        } else if (x < 0) {
            return Math.atan(y / x) + Math.PI;
        } else {
            if (y > 0) {
                return Math.PI / 2;
            } else if (y < 0) {
                return 3 * Math.PI / 2;
            } else {
                throw new ArithmeticException("A zero vector has no direction");
            }
        }
    }

    /**
     * Rotate this vector by a certain angle.
     *
     * @param theta a direction in radians
     * @return a rotated vector
     */
    public Vector rotate(double theta) {
        double newTheta = theta + direction();
        return new Vector(Math.cos(newTheta), Math.sin(newTheta)).scale(magnitude());
    }

    /**
     * Perform scalar multiplication on this vector.
     *
     * @param scalar an umber
     * @return a scaled vector
     */
    public Vector scale(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    /**
     * Convert this vector to a Point.
     *
     * @return a Point
     */
    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    /**
     * Convert this vector to a Dimension.
     *
     * @return a Dimension
     */
    public Dimension toDimension() {
        return new Dimension((int) x, (int) y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * A string representation of this vector(in row format).
     *
     * @return a String
     */
    public String toString() {
        return "[" + x + " " + y + "]";
    }

}
