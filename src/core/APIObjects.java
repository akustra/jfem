package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class APIObjects<T extends APIObject> implements Iterable<T> {

    protected List<T> list = new ArrayList<>();

    protected APIObjects() {
    }

    protected APIObjects(T... elements) {
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
    }

    public void add(T... elements) {
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Get / set">
    public int size() {
        return list.size();
    }

    public T get(int index) {
        return list.get(index);
    }

    public T set(int index, T element) {
        return list.set(index, element);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add / remove">
    public boolean add(T element) {
        return list.add(element);
    }

    public boolean remove(T element) {
        return list.remove(element);
    }
    //</editor-fold>

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }    
    
}
