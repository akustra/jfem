package struct;

import beam.*;
import bond.*;
import core.*;
import load.*;
import math.*;

public class Struct {

    private Beams beams = new Beams();
    private Bonds bonds = new Bonds();
    private Loads loads = new Loads();

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public Beams getBeams() {
        return beams;
    }
    
    public Beams getEnabledBeams() {
        Beams z = new Beams();
        for (Beam beam : beams) {
            if (beam.isEnabled()) {
                z.add(beam);
            }
        }
        return z;
    }
    
    public Bonds getBonds() {
        return bonds;
    }
    
    public Bonds getEnabledBonds() {
        Bonds z = new Bonds();
        for (Bond bond : bonds) {
            if (bond.isEnabled()) {
                z.add(bond);
            }
        }
        return z;
    }
    
    public Loads getLoads() {
        return loads;
    }
    
    public Loads getEnabledLoads() {
        Loads z = new Loads();
        for (Load load : loads) {
            if (load.isEnabled()) {
                z.add(load);
            }
        }
        return z;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    public void setBeams(Beams beams) {
        this.beams = beams;
    }

    public void setBonds(Bonds bonds) {
        this.bonds = bonds;
    }

    public void setLoads(Loads loads) {
        this.loads = loads;
    }
    //</editor-fold>
    
    public void add(Beam... beams) {
        this.beams.add(beams);
    }

    public void add(Bond... bonds) {
        this.bonds.add(bonds);
    }

    public void add(Load... loads) {
        this.loads.add(loads);
    }
    
    public Matrix getDispl() {
        Points u = Linear.getPoints(this);
        
        Matrix K = Linear.getReducedStiffnessMatrix(this, u);
        Matrix L = Linear.getReducedLoadMatrix(this, u);
        
        return K.solve(L);
    }
    
    public Matrix getEnabledDispl() {
        Points u = Linear.getEnabledPoints(this);
        
        Matrix K = Linear.getReducedEnabledStiffnessMatrix(this, u);
        Matrix L = Linear.getReducedEnabledLoadMatrix(this, u);
        
        return K.solve(L);
    }
}
