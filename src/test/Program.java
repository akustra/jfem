package test;

import beam.Beam;
import bond.Bond;
import core.Point;
import core.Vector;
import load.Load;
import struct.Struct;

public class Program {

    public static void main(String[] args) {
        Point a = new Point(0, 0);
        Point b = new Point(0, 1);
        Point c = new Point(1, 0);
        Point d = new Point(1, 1);
        Point e = new Point(2, 0);
        Point f = new Point(2, 1);

        Beam b1 = new Beam(a, b);
        Beam b2 = new Beam(b, d);
        Beam b3 = new Beam(b, c);
        Beam b4 = new Beam(a, d);
        Beam b5 = new Beam(a, c);
        Beam b6 = new Beam(c, d);
        Beam b7 = new Beam(d, f);
        Beam b8 = new Beam(d, e);
        Beam b9 = new Beam(c, f);
        Beam b10 = new Beam(c, e);
        Beam b11 = new Beam(e, f);

        Load l1 = new Load(d, new Vector(0, -1000));

        Bond c1 = new Bond(a, new Vector(1, 1));
        Bond c2 = new Bond(e, new Vector(0, 1));

        Struct s = new Struct();
        s.add(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11);
        s.add(l1);
        s.add(c1, c2);

        s.getDispl().print("Displacements");
    }
}
