package se2203.assignment1;

import javafx.scene.image.ImageView;

public class HydraHead extends ImageView {

    private int head, xPos, yPos;

    public HydraHead(javafx.scene.image.Image image, int head, int x, int y)
    {
        super(image);
        this.head = head;
        this.xPos = x;
        this.yPos = y;
    }

    public int getHead(){return head;}
    public int getXPos(){return xPos;}
    public int getyPos(){return yPos;}

}
