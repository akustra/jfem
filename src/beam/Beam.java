package beam;

import core.APIObject;
import core.Point;

public class Beam extends APIObject {

    public Point a;
    public Point b;

    public Material mat;
    public Section sec;

    public Beam(Point a, Point b) {
        this.a = a;
        this.b = b;

        this.mat = new Material();
        this.sec = new Section();
    }

    public Beam(Point a, Point b, Material mat, Section sec) {
        this.a = a;
        this.b = b;

        this.mat = mat;
        this.sec = sec;
    }

    public double getLength() {
        return a.distanceTo(b);
    }

    public Beam copy() {
        return new Beam(a.copy(), b.copy(), mat.copy(), sec.copy());
    }

    @Override
    public String toString() {
        return "Beam: {Point: " + a.toString() + ", Point: " + b.toString() + "}";
    }

    public void replace(Point orig, Point newPoint) {
        if (orig.equals(a)) {
            a = newPoint.copy();
        }
        if (orig.equals(b)) {
            b = newPoint.copy();
        }
    }
}
