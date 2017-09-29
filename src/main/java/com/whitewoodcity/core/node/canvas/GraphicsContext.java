package com.whitewoodcity.core.node.canvas;

import javafx.scene.image.Image;

public class GraphicsContext{
    private javafx.scene.canvas.GraphicsContext body;

    public GraphicsContext(javafx.scene.canvas.GraphicsContext body){
        this.body = body;
    }

    public void drawImage(Image img, double x, double y){
        body.drawImage(img, x, y);
    }

    public void draw_image(Image img, double x, double y){
        this.drawImage(img, x, y);
    }

    public void drawimage(Image img, double x, double y){
        this.drawImage(img, x, y);
    }

    public void drawImage(Image img, double x, double y, double w, double h){
        body.drawImage(img, x, y, w, h);
    }

    public void draw_image(Image img, double x, double y, double w, double h){
        drawImage(img, x, y, w, h);
    }

    public void drawimage(Image img, double x, double y, double w, double h){
        drawImage(img, x, y, w, h);
    }

    public void drawImage(Image img,
                          double sx, double sy, double sw, double sh,
                          double dx, double dy, double dw, double dh){
        body.drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
    }

    public void draw_image(Image img,
                          double sx, double sy, double sw, double sh,
                          double dx, double dy, double dw, double dh){
        drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
    }

    public void drawimage(Image img,
                           double sx, double sy, double sw, double sh,
                           double dx, double dy, double dw, double dh){
        drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
    }

    public void clear(int x, int y, int w, int h){
        body.clearRect(x,y,w,h);
    }

}
