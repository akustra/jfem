package struct;

import beam.Beam;
import bond.Bond;
import core.Points;
import load.Load;
import math.Matrix;

public class Linear {

    /**
     * Returns sorted list of unique points in structure
     */
    public static Points getPoints(final Struct s) {
        Points z = new Points();
        for (int i = 0; i < s.getBeams().size(); i++) {
            Beam beam = s.getBeams().get(i);
            z.add(beam.a);
            z.add(beam.b);
        }
        for (int i = 0; i < s.getBonds().size(); i++) {
            Bond bond = s.getBonds().get(i);
            z.add(bond.a);
        }
        for (int i = 0; i < s.getLoads().size(); i++) {
            Load load = s.getLoads().get(i);
            z.add(load.a);
        }

        z.sort();
        for (int i = z.size() - 1; i > 0; i--) {
            if (z.get(i).equals(z.get(i - 1))) {
                z.remove(i);
            }
        }
        return z;
    }

    /**
     * Returns sorted list of unique points in structure for enabled elements
     */
    public static Points getEnabledPoints(final Struct s) {
        Points z = new Points();
        for (int i = 0; i < s.getBeams().size(); i++) {
            Beam beam = s.getBeams().get(i);
            if (beam.isEnabled()) {
                z.add(beam.a);
                z.add(beam.b);
            }
        }
        for (int i = 0; i < s.getBonds().size(); i++) {
            Bond bond = s.getBonds().get(i);
            if (bond.isEnabled()) {
                z.add(bond.a);
            }
        }
        for (int i = 0; i < s.getLoads().size(); i++) {
            Load load = s.getLoads().get(i);
            if (load.isEnabled()) {
                z.add(load.a);
            }
        }

        z.sort();
        for (int i = z.size() - 1; i > 0; i--) {
            if (z.get(i).equals(z.get(i - 1))) {
                z.remove(i);
            }
        }
        return z;
    }

    /**
     * Returns global stiffness matrix K
     */
    public static Matrix getStiffnessMatrix(final Struct s, final Points u) {
        Matrix z = Matrix.createSq(u.size() * 2);

        for (Beam beam : s.getBeams()) {
            int idA = u.indexOf(beam.a);
            int idB = u.indexOf(beam.b);

            int[] connAB = new int[]{2 * idA, 2 * idA + 1, 2 * idB, 2 * idB + 1};

            Matrix k = getTrussElementStiffnessMatrix(beam);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    z.setAdd(connAB[i], connAB[j], k.get(i, j));
                }
            }
        }

        return z;
    }

    /**
     * Returns global stiffness matrix K for enabled elements
     */
    public static Matrix getEnabledStiffnessMatrix(final Struct s, final Points u) {
        Matrix z = Matrix.createSq(u.size() * 2);

        for (Beam beam : s.getEnabledBeams()) {
            int idA = u.indexOf(beam.a);
            int idB = u.indexOf(beam.b);

            int[] connAB = new int[]{2 * idA, 2 * idA + 1, 2 * idB, 2 * idB + 1};

            Matrix k = getTrussElementStiffnessMatrix(beam);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    z.setAdd(connAB[i], connAB[j], k.get(i, j));
                }
            }
        }

        return z;
    }

    /**
     * Returns global load matrix L
     */
    public static Matrix getLoadMatrix(final Struct s, final Points u) {
        Matrix z = Matrix.createCol(u.size() * 2);

        for (Load load : s.getLoads()) {
            int idA = u.indexOf(load.a);

            z.setAdd(2 * idA, 0, load.val.x);
            z.setAdd(2 * idA + 1, 0, load.val.y);
        }

        return z;
    }

    /**
     * Returns global load matrix L for enabled elements
     */
    public static Matrix getEnabledLoadMatrix(final Struct s, final Points u) {
        Matrix z = Matrix.createCol(u.size() * 2);

        for (Load load : s.getEnabledLoads()) {
            int idA = u.indexOf(load.a);

            z.setAdd(2 * idA, 0, load.val.x);
            z.setAdd(2 * idA + 1, 0, load.val.y);
        }

        return z;
    }

    /**
     * Returns reduced global stiffness matrix K according to boundary
     * conditions
     */
    public static Matrix getReducedStiffnessMatrix(final Struct s, final Points u) {
        Matrix z = getStiffnessMatrix(s, u);

        for (Bond bond : s.getBonds()) {
            int idA = u.indexOf(bond.a);

            if (bond.val.x != 0) {
                z.setRow(2 * idA, 0.0);
                z.setCol(2 * idA, 0.0);
                z.set(2 * idA, 2 * idA, 1.0);
            }
            if (bond.val.y != 0) {
                z.setRow(2 * idA + 1, 0.0);
                z.setCol(2 * idA + 1, 0.0);
                z.set(2 * idA + 1, 2 * idA + 1, 1.0);
            }
        }

        return z;
    }

    /**
     * Returns reduced global stiffness matrix K according to boundary
     * conditions for enabled elements
     */
    public static Matrix getReducedEnabledStiffnessMatrix(final Struct s, final Points u) {
        Matrix z = getEnabledStiffnessMatrix(s, u);

        for (Bond bond : s.getEnabledBonds()) {
            int idA = u.indexOf(bond.a);

            if (bond.val.x != 0) {
                z.setRow(2 * idA, 0.0);
                z.setCol(2 * idA, 0.0);
                z.set(2 * idA, 2 * idA, 1.0);
            }
            if (bond.val.y != 0) {
                z.setRow(2 * idA + 1, 0.0);
                z.setCol(2 * idA + 1, 0.0);
                z.set(2 * idA + 1, 2 * idA + 1, 1.0);
            }
        }

        return z;
    }

    /**
     * Returns reduced global load matrix L according to boundary conditions
     */
    public static Matrix getReducedLoadMatrix(final Struct s, final Points u) {
        Matrix z = getLoadMatrix(s, u);

        for (Bond bond : s.getBonds()) {
            int idA = u.indexOf(bond.a);

            if (bond.val.x != 0) {
                z.setRow(2 * idA, 0.0);
            }
            if (bond.val.y != 0) {
                z.setRow(2 * idA + 1, 0.0);
            }
        }

        return z;
    }

    /**
     * Returns reduced global load matrix L according to boundary conditions for
     * enabled elements
     */
    public static Matrix getReducedEnabledLoadMatrix(final Struct s, final Points u) {
        Matrix z = getEnabledLoadMatrix(s, u);

        for (Bond bond : s.getEnabledBonds()) {
            int idA = u.indexOf(bond.a);

            if (bond.val.x != 0) {
                z.setRow(2 * idA, 0.0);
            }
            if (bond.val.y != 0) {
                z.setRow(2 * idA + 1, 0.0);
            }
        }

        return z;
    }

    /**
     * Returns truss element transformed local stiffness matrix
     */
    private static Matrix getTrussElementStiffnessMatrix(Beam beam) {
        Matrix z = Matrix.createSq(4);
        
        double length = beam.getLength();

        double c = (beam.b.x - beam.a.x) / length;
        double s = (beam.b.y - beam.a.y) / length;

        z.setRow(0, new double[]{c * c, c * s, -c * c, -c * s});
        z.setRow(1, new double[]{c * s, s * s, -c * s, -s * s});
        z.setRow(2, new double[]{-c * c, -c * s, c * c, c * s});
        z.setRow(3, new double[]{-c * s, -s * s, c * s, s * s});

        z.imul((beam.mat.young * beam.sec.area) / length);

        return z;
    }
}
