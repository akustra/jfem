package beam;

public class Section {

    public double area;
    public double mass;
    public double ix, iy;
    public double wx, wy;

    public Section() {
        this.area = 4.38e-4; // RO 51/2.9 A = 4.38 cm^2
        this.mass = 3.44;    // RO 51/2.9 m = 3.44 kg/m
        this.ix = 12.7e-8;   // RO 51/2.9 Ix = 12.7 cm^4
        this.iy = 12.7e-8;   // RO 51/2.9 Iy = 12.7 cm^4
        this.wx = 4.99e-6;   // RO 51/2.9 Wx = 4.99 cm^3
        this.wy = 4.99e-6;   // RO 51/2.9 Wy = 4.99 cm^3
    }

    public Section(double area, double mass, double ix, double iy, double wx, double wy) {
        this.area = area;
        this.mass = mass;
        this.ix = ix;
        this.iy = iy;
        this.wx = wx;
        this.wy = wy;
    }

    public Section copy() {
        return new Section(area, mass, ix, iy, wx, wy);
    }

    @Override
    public String toString() {
        return "Section: {Area: " + area + ", Mass: " + mass + "}";
    }
}
