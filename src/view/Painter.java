package view;

import beam.Beam;
import beam.Beams;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class Painter {

    private static Path2D path = new Path2D.Double();

    private static Color beamColor = Color.black;

    private static Color loadColor = Color.red;
    private static Color bondColor = Color.blue;

    private static void drawBeam(Graphics2D g, final Beam beam) {
        g.setColor(beamColor);

        path.reset();
        path.moveTo(beam.a.x, beam.a.y);
        path.lineTo(beam.b.x, beam.b.y);

        g.draw(path);
    }

    public static void drawBeams(Graphics2D g, final Beams beams) {
        for (Beam beam : beams) {
            drawBeam(g, beam);
        }
    }

}
