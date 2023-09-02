package Shape;

import java.awt.*;

/** 實作畫 usecase shape **/
public class usecaseObj extends MyShape {
    // x1, y1 圖形中心點
    public usecaseObj(int x1, int y1) {
        this.width = 120;
        this.height = 90;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + width;
        this.y2 = y1 + height;
        setPorts();
    }

    public void draw(Graphics g) {
        // 畫 usecase 橢圓
        g.drawOval(x1, y1, width, height);

        // 找到位置並畫上 usecase 名稱
        int stringWidth = g.getFontMetrics(font).stringWidth(objectName);
        double empty = (Math.abs(x1-x2) - stringWidth)/2;
        g.setFont(font);
        g.drawString(objectName, x1 + (int)empty, y1 + 50);
    }
}
