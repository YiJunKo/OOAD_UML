package Mode;

import java.awt.*;

import Line.MyLine;
import Line.associationLine;
import Line.compositionLine;
import Line.generalizationLine;
import Shape.*;

/** 之後新增新的 line 或 shape 較方便 **/
public class GraphicMode implements IGraphicMode {
    public MyShape createShape(String objType, Point p) {
        if(objType.equals("class")){
            return new classObj(p.x, p.y);
        }
        else if(objType.equals("usecase")){
            return new usecaseObj(p.x, p.y);
        }
        return null;
    }

    public MyLine createLine(String lineType, Point firstP, Point secondP) {
        if(lineType.equals("associate")){
            return new associationLine(firstP.x, firstP.y, secondP.x, secondP.y);
        }
        else if(lineType.equals("general")){
            return new generalizationLine(firstP.x, firstP.y, secondP.x, secondP.y);
        }
        else if(lineType.equals("composite")) {
            return new compositionLine(firstP.x, firstP.y, secondP.x, secondP.y);
        }
        return null;
    }
}