package test;

import beam.Beam;
import beam.Beams;
import bond.Bond;
import bond.Bonds;
import core.Point;
import core.Vector;
import javax.swing.JFrame;
import load.Load;
import load.Loads;
import math.Matrix;
import struct.Struct;
import view.Canvas;

public class Random1 {
    
    public static void main(String[] args) {
        java.util.Random generator = new java.util.Random(5);
        
        Point a = new Point(0, 0);
        Point b = new Point(1, 0);
        Point c = new Point(2, 0);
        Point d = new Point(3, 0);
        Point e = new Point(4, 0);
        Point f = new Point(1, 1);
        Point g = new Point(2, 1);
        Point h = new Point(3, 1);
        
        Beams bs = new Beams();
        bs.add(
                new Beam(a, b),
                new Beam(b, c),
                new Beam(c, d),
                new Beam(d, e),
                new Beam(e, h),
                new Beam(h, g),
                new Beam(g, f),
                new Beam(f, a),
                new Beam(b, f),
                new Beam(c, g),
                new Beam(d, h),
                new Beam(c, f),
                new Beam(c, h),
                new Beam(b, g),
                new Beam(d, g)
        );
        
        Loads ls = new Loads();
        ls.add(
                new Load(f, new Vector(0, -1000)),
                new Load(g, new Vector(0, -1000)),
                new Load(h, new Vector(0, -1000))
        );
        
        Bonds cs = new Bonds();
        cs.add(
                new Bond(a, new Vector(1, 1)),
                new Bond(e, new Vector(0, 1))
        );
        
        Struct s = new Struct();
        s.setLoads(ls);
        s.setBonds(cs);
        
        double best = 1e6;
        Beams winner = null;
        for (int i = 0; i < 1000000; i++) {            
            try {
                s.setBeams(bs);
                double n11 = norm1(s.getDispl());
                
                Beams bsMutated = bs.copy();
                randomizeBeams(bsMutated, ls, cs, generator);
                
                s.setBeams(bsMutated);
                double n12 = norm1(s.getDispl());
                
                if (n12 < n11) {
                    System.out.println("Iteration: " + i);
                    System.out.println("\tFound better: " + n12 + " < " + n11 + " (" + best + ")");
                    
                    best = n12;
                    winner = bsMutated;
                    
                    bs = bsMutated;
                } else {
                    best = n11;
                    winner = bs;
                }
            } catch (Exception ex) {
                System.out.println("\tIteration failed");
            }
        }
        
        JFrame fr = new JFrame();
        fr.setSize(800, 600);
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setVisible(true);
        Canvas p = new Canvas();
        p.beams = winner;
        fr.getContentPane().add(p);
        
    }

    // Maximal displacement norm
    private static final double norm1(Matrix displ) {
        return Math.abs(displ.max());
    }
    
    // Total displacement norm
    private static final double norm2(Matrix displ) {
        return Math.abs(displ.absSum());
    }
    
    private static final void randomizeBeams(Beams bs, Loads ls, Bonds cs, java.util.Random generator) {
        double step = 0.01;
        
        int randomBeam = generator.nextInt(bs.size() - 1);
        Beam b = bs.get(randomBeam);

        // Pick random point
        Point mutated = generator.nextDouble() > 0.5 ? b.a : b.b;
        
        boolean canMutate = true;

        // Point where load is added cannot be modified
        for (int j = 0; j < ls.size(); j++) {
            Load l = ls.get(j);
            if (mutated.equals(l.a)) {
                canMutate = false;
                break;
            }
        }

        // Point where bond is added cannot be modified
        for (int j = 0; j < cs.size(); j++) {
            Bond c = cs.get(j);
            if (mutated.equals(c.a)) {
                canMutate = false;
                break;
            }
        }
        
        final double dx = step * (generator.nextDouble() - 0.5);
        final double dy = step * (generator.nextDouble() - 0.5);

        if (mutated.x + dx < 0 || mutated.x + dx > 4) {
            canMutate = false;
        }
        if (mutated.y + dy < 0 || mutated.y + dy > 1) {
            canMutate = false;
        }
        
        // Mutate point
        if (canMutate) {
            Point newPoint = mutated.copy();
            newPoint.x += dx;
            newPoint.y += dy;
            
            bs.replace(mutated, newPoint);
        }
    }
    
}
