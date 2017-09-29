package com.whitewoodcity.core.node.canvas;

import com.whitewoodcity.core.node.Node;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;

public class Canvas implements Node {
    private javafx.scene.canvas.Canvas body;

    public Canvas(){
        body= new javafx.scene.canvas.Canvas();
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void setWidth(double width){
        body.setWidth(width);
    }

    public double getWidth(){
        if(body.getWidth() > 0.000001) return body.getWidth();
        else return body.getWidth();
    }

    public void setHeight(double height){
        body.setHeight(height);
    }

    public double getHeight(){
        if(body.getHeight() > 0.000001) return body.getHeight();
        else return body.getHeight();
    }

    public DoubleProperty widthProperty(){
        return body.widthProperty();
    }

    public DoubleProperty heightProperty(){
        return body.heightProperty();
    }

    public DoubleProperty width_property(){
        return widthProperty();
    }

    public DoubleProperty height_property(){
        return heightProperty();
    }

    public DoubleProperty widthproperty(){
        return widthProperty();
    }

    public DoubleProperty heightproperty(){
        return heightProperty();
    }

    public GraphicsContext graphicsContext(){
        return new GraphicsContext(body.getGraphicsContext2D());
    }

    public GraphicsContext graphicscontext(){
        return graphicsContext();
    }

    public GraphicsContext graphics_context(){
        return graphicsContext();
    }

    public void clear(){
        body.getGraphicsContext2D().clearRect(0,0,body.getWidth(),body.getHeight());
    }

    public void drawImage(Image img, double x, double y){
        body.getGraphicsContext2D().drawImage(img, x, y);
    }

    public void draw_image(Image img, double x, double y){
        this.drawImage(img, x, y);
    }

    public void drawimage(Image img, double x, double y){
        this.drawImage(img, x, y);
    }

    public void drawImage(Image img, double x, double y, double w, double h){
        body.getGraphicsContext2D().drawImage(img, x, y, w, h);
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
        body.getGraphicsContext2D().drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
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

    public void text(String text){
        this.text(text,0,0);
    }

    public void text(String text, double x, double y){
        body.getGraphicsContext2D().fillText(text, x, y);
    }

    public void text(String text, double x, double y, double maxWidth){
        body.getGraphicsContext2D().fillText(text, x, y, maxWidth);
    }
}
