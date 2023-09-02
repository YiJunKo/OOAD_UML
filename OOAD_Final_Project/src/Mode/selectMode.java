package Mode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import Line.MyLine;
import Shape.MyGraphic;

/**
 1. 抓取移動 shape 時的點
 2. 抓取選取框的點
 **/
public class selectMode extends Mode {
    private Point startP = null;
    private String judgeInside = null;
    private MyLine selectedMyLine = null;
    private List<MyGraphic> myGraphics;     // canvas 的所有 shape

    public void mousePressed(MouseEvent e) {
        startP = e.getPoint();
        myGraphics = canvas.getShapeList();

        // reset
        canvas.reset();

        // 找所有的 shape
        for (int i = myGraphics.size() - 1; i >= 0; i--) {
            MyGraphic myGraphic = myGraphics.get(i);
            judgeInside = myGraphic.sideString(e.getPoint());
            if (judgeInside != null) {
                // 如果點在 shape 裡，就把 selectedObj 改成這個 Shape
                canvas.selectedObj = myGraphic;
                break;
            }
        }
        canvas.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        // 計算移動量
        int moveX = e.getX() - startP.x;
        int moveY = e.getY() - startP.y;

        // 如果 Drag 的是物件的話
        if (canvas.selectedObj != null) {
            // 判斷是移動線還是物件
            if (judgeInside == "insideLine") {
                selectedMyLine = (MyLine) canvas.selectedObj;
                selectedMyLine.resetStartEnd(e.getPoint());
            }
            else {
                canvas.selectedObj.resetLocation(moveX, moveY);
            }
            startP.x = e.getX();
            startP.y = e.getY();
        }
        // 如果是拉取選取框的話
        else {
            // 判斷怎麼拉
            if (e.getX() > startP.x)
                canvas.SelectedArea.setBounds(startP.x, startP.y, Math.abs(moveX), Math.abs(moveY));
            else
                canvas.SelectedArea.setBounds(e.getX(), e.getY(), Math.abs(moveX), Math.abs(moveY));

        }
        canvas.repaint();
    }

    public void mouseReleased(MouseEvent e) {
        // 如果移動的是物件的話
        if (canvas.selectedObj != null) {
            // 如果是線的話
            if (judgeInside == "insideLine") {
                selectedMyLine = (MyLine) canvas.selectedObj;
                reconnectLine(e.getPoint());
            }
        }
        // 如果是選取框的話
        else {
            canvas.SelectedArea.setSize(Math.abs(e.getX() - startP.x), Math.abs(e.getY() - startP.y));
        }
        canvas.repaint();
    }

    // 抓取新的點重新連線
    private void reconnectLine(Point p) {
        for (int i = 0; i < myGraphics.size(); i++) {
            MyGraphic myGraphic = myGraphics.get(i);
            int portIndex;
            String judgeInside = myGraphic.sideString(p);
            // 如果是點在物件中不是點在線上
            if (judgeInside != null && judgeInside != "insideLine") {
                // 如果在 group 中
                if (judgeInside == "insideGroup") {
                    myGraphic = myGraphic.getSelectedShape();
                    portIndex = Integer.parseInt(myGraphic.sideString(p));
                }
                else
                    portIndex = Integer.parseInt(judgeInside);

                selectedMyLine.resetPort(myGraphic.getPort(portIndex), selectedMyLine);
                selectedMyLine.resetLocation();
            }
        }
    }
}
