package Mode;

import java.awt.*;

import Line.MyLine;
import Shape.*;

public interface IGraphicMode {
    public MyShape createShape(String shapeType, Point p);
    public MyLine createLine(String lineType, Point startP, Point endP);
}
