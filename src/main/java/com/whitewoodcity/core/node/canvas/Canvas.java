package com.whitewoodcity.core.node.canvas;

import com.whitewoodcity.core.node.Node;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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

    public void image(Image img, double x, double y){
        body.getGraphicsContext2D().drawImage(img, x, y);
    }

    public void image(Image img, double x, double y, double w, double h){
        body.getGraphicsContext2D().drawImage(img, x, y, w, h);
    }

    public void image(Image img,
                          double sx, double sy, double sw, double sh,
                          double dx, double dy, double dw, double dh){
        body.getGraphicsContext2D().drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
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

    public void line(double x, double y, double dx, double dy){
        body.getGraphicsContext2D().strokeLine(x,y,dx,dy);
    }

    public void reset(){
        alpha(1);
        color("black");
        weight(1.0);
    }

    public void alpha(double alpha){
        body.getGraphicsContext2D().setGlobalAlpha(alpha);
    }

    public void color(String color){
        body.getGraphicsContext2D().setFill(Color.web(color));
        body.getGraphicsContext2D().setStroke(Color.web(color));
    }

    public void rgb(int r, int g, int b){
        color(Color.rgb(r,g,b).toString());
    }

    public void argb(double a, int r, int g, int b){
        color(Color.rgb(r,g,b,a).toString());
    }

    public void hsb(double h, double s, double b){
        color(Color.hsb(h,s,b).toString());
    }

    public void weight(double lw){
        body.getGraphicsContext2D().setLineWidth(lw);
    }

    public void rect(double x, double y, double w, double h){
        body.getGraphicsContext2D().strokeRect(x,y,w,h);
    }

    public void rect(double x, double y, double w, double h, boolean filled){
        if(filled){
            body.getGraphicsContext2D().fillRect(x,y,w,h);
        }else{
            rect(x,y,w,h);
        }
    }

    public void oval(double x, double y, double w, double h){
        body.getGraphicsContext2D().strokeOval(x,y,w,h);
    }

    public void oval(double x, double y, double w, double h, boolean filled){
        if(filled){
            body.getGraphicsContext2D().fillOval(x,y,w,h);
        }else{
            oval(x,y,w,h);
        }
    }

    public void circle(double x, double y, double radius){
        oval(x-radius,y-radius,radius*2,radius*2);
    }

    public void circle(double x, double y, double radius, boolean filled){
        oval(x-radius,y-radius,radius*2,radius*2, filled);
    }

    public void square(double x, double y, double side){
        rect(x,y,side,side);
    }

    public void square(double x, double y, double side, boolean filled){
        rect(x,y,side,side, filled);
    }
}
