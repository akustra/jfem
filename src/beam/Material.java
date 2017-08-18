package beam;

public class Material {

    public double poiss;
    public double young;

    public Material() {
        this.poiss = 0.3;
        this.young = 205.0e9;
    }

    public Material(double poiss, double young) {
        this.poiss = poiss;
        this.young = young;
    }

    public Material copy() {
        return new Material(poiss, young);
    }

    @Override
    public String toString() {
        return "Material: {Poiss: " + poiss + ", Young: " + young + "}";
    }
}
