package view;

import java.awt.geom.Point2D;

import core.Point;
import core.Points;
import struct.Linear;
import struct.Struct;

public class Geometry {

    public static Point2D getCenter(final Struct struct) {
        double minX = 0, minY = 0, maxX = 0, maxY = 0;

        Points points = Linear.getPoints(struct);
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            minX = (minX < point.x) ? minX : point.x;
            minY = (minY < point.y) ? minY : point.y;
            maxX = (maxX > point.x) ? maxX : point.x;
            maxY = (maxY > point.y) ? maxY : point.y;
        }

        return new Point2D.Double(0.5 * (maxX + minX), 0.5 * (maxY + minY));
    }

    public static Point2D getCenter(final Point2D a, final Point2D b) {
        return new Point2D.Double(0.5 * (a.getX() + b.getX()), 0.5 * (a.getY() + b.getY()));
    }
}
