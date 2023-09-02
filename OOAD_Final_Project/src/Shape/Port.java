package Shape;

import Line.MyLine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 1. 把線加入這個 port 的 List
 2. 把線移除這個 port 的 List
 3. 建置 Shape 的 port
 4. resetLine
 **/
public class Port extends Rectangle {
    private List<MyLine> myLines = new ArrayList<MyLine>();   // 存這個 port 有哪些連接的線

    public void addLine(MyLine myLine) {
        myLines.add(myLine);
    }

    public void removeLine(MyLine myLine) {
        myLines.remove(myLine);
    }

    public void setPort(int centerX, int centerY, int offset) {
        int x = centerX - offset;
        int y = centerY - offset;
        int w = offset * 2;
        int h = offset * 2;
        setBounds(x, y, w, h);
    }

    // reset 這個 port 上的所有 line
    public void resetLines() {
        for(int i = 0; i < myLines.size(); i++){
            MyLine myLine = myLines.get(i);
            myLine.resetLocation();
        }
    }
}
