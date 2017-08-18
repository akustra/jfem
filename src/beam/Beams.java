package beam;

import core.APIObjects;
import core.Point;

public class Beams extends APIObjects<Beam> {

    public Beams copy() {
        Beams tmp = new Beams();
        for (int i = 0; i < list.size(); i++) {
            tmp.add(list.get(i).copy());
        }
        return tmp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i).toString()).append("\n");
        }
        return builder.toString();
    }
    
    public void replace(Point orig, Point newPoint) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).replace(orig, newPoint);            
        }
    }
}
