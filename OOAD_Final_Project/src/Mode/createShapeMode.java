package Mode;

import java.awt.event.MouseEvent;
import Shape.*;

/**
 1. 創建 shape 時的 Mode
 2. 當按下滑鼠就會導到 shape run createShape(type, point)
 **/
public class createShapeMode extends Mode{
    private String type = null;
    private IGraphicMode shapeMode = new GraphicMode();
    public createShapeMode(String type) {
        this.type = type;
    }

    // mouse press 創建 shape
    public void mousePressed(MouseEvent e) {
        MyShape shape = shapeMode.createShape(type, e.getPoint());
        canvas.addShape(shape);
        canvas.repaint();
    }
}