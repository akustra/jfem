package core;

public final class Point implements Comparable<Point> {

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point p) {
        return Math.hypot(p.x - x, p.y - y);
    }

    @Override
    public boolean equals(Object obj) {
        final Point point = (Point) obj;
        if (Math.abs(point.x - x) > Options.geo_tol) {
            return false;
        }
        if (Math.abs(point.y - y) > Options.geo_tol) {
            return false;
        }

        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Comparable impl">
    @Override
    public int compareTo(Point point) {
        if (x > point.x) {
            return 1;
        }
        if (x < point.x) {
            return -1;
        }
        if (y > point.y) {
            return 1;
        }
        if (y < point.y) {
            return -1;
        }

        return 0;
    }
    //</editor-fold>

    public Point copy() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}
