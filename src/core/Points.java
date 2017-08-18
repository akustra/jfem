package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Points implements Iterable<Point> {

    private List<Point> list = new ArrayList<>();

    public Points() {
    }

    public Points(Point... points) {
        for (int i = 0; i < points.length; i++) {
            list.add(points[i]);
        }
    }

    public void sort() {
        Collections.sort(list);
    }

    public int indexOf(Point point) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(point)) {
                return i;
            }
        }
        return -1;
    }

    //<editor-fold defaultstate="collapsed" desc="Get / set">
    public int size() {
        return list.size();
    }

    public Point get(int index) {
        return list.get(index);
    }

    public Point set(int index, Point point) {
        return list.set(index, point);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add / remove">
    public boolean add(Point point) {
        return list.add(point);
    }

    public boolean add(Point... points) {
        boolean z = false;
        for (int i = 0; i < points.length; i++) {
            z = z || add(points[i]);
        }
        return z;
    }
    
    public void remove(int index){
        list.remove(index);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Iterable impl">
    @Override
    public Iterator<Point> iterator() {
        return list.iterator();
    }
    //</editor-fold>
    
    public Points copy() {
        Points tmp = new Points();
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
