package load;

import core.APIObject;
import core.Point;
import core.Vector;

public class Load extends APIObject {

    public Point a;
    public Vector val;

    public Load(Point a, Vector val) {
        this.a = a;
        this.val = val;
    }
    
    public Load copy() {
        return new Load(a.copy(), val.copy());
    }

    @Override
    public String toString() {
        return "Load: {Point: " + a.toString() + ", Value: " + val.toString() + "}";
    }
}
