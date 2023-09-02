package Mode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import Shape.MyGraphic;
import Line.MyLine;
/**
 1. 確認 line type
 2. 找出 mousePress 時的附近的點 以及 mouseRelease 時的點
 3. 如果找到第一個點才會繼續找第二個點，如果找到第二個點才會創建 Line 並加入 canvas 的 shape List中
 4. 移動時生出 tempLine
 **/
public class createLineMode extends Mode {
    private String type = null;                     // 線的 type
    private IGraphicMode shapeMode = new GraphicMode();
    private Point firstP = null;
    private List<MyGraphic> myGraphics;
    private int firstPortIndex = -1;
    private int secondPortIndex = -1;
    private MyGraphic firstMyGraphic = null;
    private MyGraphic secondMyGraphic = null;

    // 存 shape type
    public createLineMode(String type) {
        this.type = type;
    }

    // 找尋哪個 Obj 的點做連接
    private Point connectPort(Point p, String target) {
        for (int i = 0; i < myGraphics.size(); i++) {
            MyGraphic myGraphic = myGraphics.get(i);

            // 判斷 mouse press 是否在 obj 中
            int portIndex;
            String insideWitch = myGraphic.sideString(p);
            if (insideWitch != null && insideWitch != "insideLine") {
                // 如果 shape 在 group 中
                if(insideWitch == "insideGroup"){
                    myGraphic = myGraphic.getSelectedShape();
                    portIndex = Integer.parseInt(myGraphic.sideString(p));
                }
                else
                    portIndex = Integer.parseInt(insideWitch);

                switch (target) {
                    case "first":
                        firstMyGraphic = myGraphic;
                        firstPortIndex = portIndex;
                        break;
                    case "second":
                        secondMyGraphic = myGraphic;
                        secondPortIndex = portIndex;
                        break;
                }
                // 回傳中心點
                Point portLocation = new Point();
                portLocation.setLocation(myGraphic.getPort(portIndex).getCenterX(), myGraphic.getPort(portIndex).getCenterY());
                return portLocation;
            }
        }
        return null;
    }

    // 滑鼠按下後抓取第一個點
    public void mousePressed(MouseEvent e) {
        // 抓取 canvas上所有的shapes
        myGraphics = canvas.getShapeList();
        firstP = connectPort(e.getPoint(), "first");
    }

    // 當滑鼠移動時如果抓取到第一個點就創立一個 templine 連線用
    public void mouseDragged(MouseEvent e) {
        if (firstP != null) {
            MyLine myLine = shapeMode.createLine(type, firstP, e.getPoint());
            canvas.tempLine = myLine;
            canvas.repaint();
        }
    }

    // 滑鼠放開後的事件
    public void mouseReleased(MouseEvent e) {
        Point secondP;
        if (firstP != null) {
            secondP = connectPort(e.getPoint(), "second"); // 抓取結束時相鄰的點

            // 如果有抓到第二個點
            if (secondP != null) {
                MyLine myLine = shapeMode.createLine(type, firstP, secondP);
                canvas.addShape(myLine);

                // 設定兩個點的 port
                myLine.setPorts(firstMyGraphic.getPort(firstPortIndex), secondMyGraphic.getPort(secondPortIndex));

                // 在兩個物件的 port 之間新增連線
                firstMyGraphic.getPort(firstPortIndex).addLine(myLine);
                secondMyGraphic.getPort(secondPortIndex).addLine(myLine);
            }
            // reset
            canvas.tempLine = null;
            canvas.repaint();
            firstP = null;
        }
    }
}
