package bond;

import core.APIObjects;

public class Bonds extends APIObjects<Bond> {
    
    public Bonds copy() {
        Bonds tmp = new Bonds();
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
}
