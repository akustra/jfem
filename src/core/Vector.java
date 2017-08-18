package core;

public final class Vector {

    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector copy() {
        return new Vector(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}
