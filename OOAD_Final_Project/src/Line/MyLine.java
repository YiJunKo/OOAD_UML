package Line;

import java.awt.*;
import java.awt.geom.Line2D;
import Shape.MyGraphic;
import Shape.Port;

/** MyLine 做的事情基本上就是 Drag 時 resetPort 然後還有一開始的 setPorts **/

public abstract class MyLine extends MyGraphic {
    protected Port[] ports = new Port[2];
    private String selectedFlag = null;

    // 預設要被實作掉
    public abstract void draw(Graphics g);

    // 線的兩點的 port
    public void setPorts(Port port1, Port port2) {
        this.ports[0] = port1;
        this.ports[1] = port2;
    }

    public void resetLocation(){
        this.x1 = (int) ports[0].getCenterX();
        this.y1 = (int) ports[0].getCenterY();
        this.x2 = (int) ports[1].getCenterX();
        this.y2 = (int) ports[1].getCenterY();
    }

    public void resetStartEnd(Point p) {
        if(selectedFlag == "firstP"){
            this.x1 = p.x;
            this.y1 = p.y;
        }
        else if(selectedFlag == "secondP") {
            this.x2 = p.x;
            this.y2 = p.y;
        }
    }

    public String sideString(Point p) {
        int tolerance = 5;
        if(distance(p) < tolerance) {
            double distToStart = Math.sqrt(((p.x - x1)*(p.x - x1)) + ((p.y - y1)*(p.y - y1)));
            double distToEnd = Math.sqrt(((p.x - x2)*(p.x - x2)) + ((p.y - y2)*(p.y - y2)));
            if(distToStart < distToEnd) {
                selectedFlag = "firstP";
            }
            else{
                selectedFlag = "secondP";
            }
            return "insideLine";
        }
        else
            return null;
    }

    public void resetPort(Port port, MyLine myLine) {
        port.addLine(myLine);
        if(selectedFlag == "firstP"){
            this.ports[0].removeLine(myLine);
            this.ports[0] = port;
        }
        else if(selectedFlag == "secondP"){
            this.ports[1].removeLine(myLine);
            this.ports[1] = port;
        }
    }

    // 計算按下點的座標與線的最小距離
    private double distance(Point p) {
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        return line.ptLineDist(p.getX(), p.getY());
    }
}
