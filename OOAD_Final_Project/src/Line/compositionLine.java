package Line;

import java.awt.Graphics;

/** 實作畫 compositionLine **/
public class compositionLine extends MyLine {
    private int diamondW = 10, diamondH = 10;
    public compositionLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void draw(Graphics g) {
        g.drawLine(x1, y1, x2, y2);
        drawArrow(g);
    }

    // compositionLine 菱形箭頭
    private void drawArrow(Graphics g) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - diamondW, xn = xm, ym = diamondH, yn = -diamondH, x;

        x = xm*(dx/D) - ym*(dy/D) + x1;
        ym = xm*(dy/D) + ym*(dx/D) + y1;
        xm = x;

        x = xn*(dx/D) - yn*(dy/D) + x1;
        yn = xn*(dy/D) + yn*(dx/D) + y1;
        xn = x;

        double xq = (diamondH*2/D)*x1 + ((D-diamondH*2)/D)*x2;
        double yq = (diamondH*2/D)*y1 + ((D-diamondH*2)/D)*y2;

        int[] xpoints = {x2, (int) xm, (int) xq, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yq, (int) yn};

        g.fillPolygon(xpoints, ypoints, 4);
    }
}
