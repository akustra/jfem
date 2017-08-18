package bond;

import core.APIObject;
import core.Point;
import core.Vector;

public class Bond extends APIObject {

    public Point a;
    public Vector val;

    public Bond(Point a, Vector val) {
        this.a = a;
        this.val = val;
    }

    public Bond copy() {
        return new Bond(a.copy(), val.copy());
    }

    @Override
    public String toString() {
        return "Bond: {Point: " + a.toString() + ", Value: " + val.toString() + "}";
    }
}
