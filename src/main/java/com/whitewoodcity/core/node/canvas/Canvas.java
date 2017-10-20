package com.whitewoodcity.core.node.canvas;

import com.whitewoodcity.core.node.Node;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Canvas implements Node {
    private int counter = 0;
    private final int maxCounter = 10000;
    private final String hint = "Canvas API could only be used in the UI context(Application Thread) for unlimited times.";

    private javafx.scene.canvas.Canvas body;

    public Canvas(){
        if (Platform.isFxApplicationThread()) body= new javafx.scene.canvas.Canvas();
        else{
            counter++;
            Platform.runLater(() -> body= new javafx.scene.canvas.Canvas());
        }
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void setWidth(double width){
        if (Platform.isFxApplicationThread()) body.setWidth(width);
        else Platform.runLater(() -> body.setWidth(width));

    }

    public double getWidth() throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            return getWidthBase();
        }

        counter++;
        if(counter>maxCounter){
            throw new ExecutionException(new Exception(hint));
        }

        final FutureTask<Double> task = new FutureTask<>(() -> getWidthBase());
        Platform.runLater(task);
        return task.get();
    }

    private double getWidthBase(){
        if(body.getWidth() > 0.000001) return body.getWidth();
        else return body.getWidth();
    }

    public void setHeight(double height) throws ExecutionException{
        if (Platform.isFxApplicationThread()) body.setHeight(height);
        else{

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> body.setHeight(height));
        }
    }

    public double getHeight() throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            return getHeightBase();
        }

        counter++;
        if(counter>maxCounter){
            throw new ExecutionException(new Exception(hint));
        }

        final FutureTask<Double> task = new FutureTask<>(() -> getHeightBase());
        Platform.runLater(task);
        return task.get();
    }

    private double getHeightBase() throws InterruptedException,ExecutionException{
        if(body.getHeight() > 0.000001) return body.getHeight();
        else return body.getHeight();
    }

    public DoubleProperty widthProperty()throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            return body.widthProperty();
        }

        counter++;
        if(counter>maxCounter){
            throw new ExecutionException(new Exception(hint));
        }

        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.widthProperty());
        Platform.runLater(task);
        return task.get();

    }

    public DoubleProperty heightProperty()throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            return body.heightProperty();
        }

        counter++;
        if(counter>maxCounter){
            throw new ExecutionException(new Exception(hint));
        }

        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.heightProperty());
        Platform.runLater(task);
        return task.get();
    }

    public DoubleProperty width_property()throws InterruptedException,ExecutionException{
        return widthProperty();
    }

    public DoubleProperty height_property()throws InterruptedException,ExecutionException{
        return heightProperty();
    }

    public DoubleProperty widthproperty()throws InterruptedException,ExecutionException{
        return widthProperty();
    }

    public DoubleProperty heightproperty()throws InterruptedException,ExecutionException{
        return heightProperty();
    }

    public void clear() throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            clearBase();
        }else{

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(this::clearBase);
        }
    }
    private void clearBase(){
        body.getGraphicsContext2D().clearRect(0,0,body.getWidth(),body.getHeight());
    }

    public void image(Image img, double x, double y) throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            imageBase(img,x,y);
        }else{

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(()->imageBase(img,x,y));
        }
    }
    private void imageBase(Image img, double x, double y){
        body.getGraphicsContext2D().drawImage(img, x, y);
    }

    public void image(Image img, double x, double y, double w, double h) throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            imageBase(img,x,y,w,h);
        }else{

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(()->imageBase(img,x,y,w,h));
        }
    }
    private void imageBase(Image img, double x, double y, double w, double h){
        body.getGraphicsContext2D().drawImage(img, x, y, w, h);
    }

    public void image(Image img,
                      double sx, double sy, double sw, double sh,
                      double dx, double dy, double dw, double dh) throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            imageBase(img,sx,sy,sw,sh,dx,dy,dw,dh);
        }else {

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> imageBase(img, sx, sy, sw, sh, dx, dy, dw, dh));
        }
    }
    private void imageBase(Image img,
                          double sx, double sy, double sw, double sh,
                          double dx, double dy, double dw, double dh){
        body.getGraphicsContext2D().drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
    }

    public void text(String text) throws ExecutionException{
        this.text(text,0,0);
    }

    public void text(String text, double x, double y) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().fillText(text, x, y);
        }else {

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> {
                body.getGraphicsContext2D().fillText(text, x, y);
            });
        }
    }

    public void text(String text, double x, double y, double maxWidth) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().fillText(text, x, y, maxWidth);
        }else {

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> {
                body.getGraphicsContext2D().fillText(text, x, y, maxWidth);
            });
        }

    }

    public void line(double x, double y, double dx, double dy) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().strokeLine(x,y,dx,dy);
        }else {

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> {
                body.getGraphicsContext2D().strokeLine(x,y,dx,dy);
            });
        }
    }

    public void reset() throws ExecutionException{
        alpha(1);
        color("black");
        weight(1.0);
    }

    public void alpha(double alpha) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().setGlobalAlpha(alpha);
        }else {

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> {
                body.getGraphicsContext2D().setGlobalAlpha(alpha);
            });
        }
    }

    public void color(String color) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().setFill(Color.web(color));
            body.getGraphicsContext2D().setStroke(Color.web(color));
        }else {

            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> {
                body.getGraphicsContext2D().setFill(Color.web(color));
                body.getGraphicsContext2D().setStroke(Color.web(color));
            });
        }
    }

    public void rgb(int r, int g, int b) throws ExecutionException{
        color(Color.rgb(r,g,b).toString());
    }

    public void argb(double a, int r, int g, int b)throws ExecutionException{
        color(Color.rgb(r,g,b,a).toString());
    }

    public void hsb(double h, double s, double b) throws ExecutionException{
        color(Color.hsb(h,s,b).toString());
    }

    public void weight(double lw) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().setLineWidth(lw);
        }else {
            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> body.getGraphicsContext2D().setLineWidth(lw));
        }

    }

    public void rect(double x, double y, double w, double h) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().strokeRect(x,y,w,h);
        }else {
            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }

            Platform.runLater(() -> body.getGraphicsContext2D().strokeRect(x,y,w,h));
        }

    }

    public void rect(double x, double y, double w, double h, boolean filled)throws ExecutionException{
        if(filled){
            if (Platform.isFxApplicationThread()) {
                body.getGraphicsContext2D().fillRect(x,y,w,h);
            }else {
                counter++;
                if(counter>maxCounter){
                    throw new ExecutionException(new Exception(hint));
                }
                Platform.runLater(() -> body.getGraphicsContext2D().fillRect(x,y,w,h));
            }
        }else{
            rect(x,y,w,h);
        }
    }

    public void oval(double x, double y, double w, double h) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            body.getGraphicsContext2D().strokeOval(x,y,w,h);
        }else {
            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }
            Platform.runLater(() -> body.getGraphicsContext2D().strokeOval(x,y,w,h));
        }
    }

    public void oval(double x, double y, double w, double h, boolean filled) throws ExecutionException{
        if(filled){
            if (Platform.isFxApplicationThread()) {
                body.getGraphicsContext2D().fillOval(x,y,w,h);
            }else {
                counter++;
                if(counter>maxCounter){
                    throw new ExecutionException(new Exception(hint));
                }
                Platform.runLater(() -> body.getGraphicsContext2D().fillOval(x,y,w,h));
            }
        }else{
            oval(x,y,w,h);
        }
    }

    public void circle(double x, double y, double radius)throws ExecutionException{
        oval(x-radius,y-radius,radius*2,radius*2);
    }

    public void circle(double x, double y, double radius, boolean filled) throws ExecutionException{
        oval(x-radius,y-radius,radius*2,radius*2, filled);
    }

    public void square(double x, double y, double side)throws ExecutionException{
        rect(x,y,side,side);
    }

    public void square(double x, double y, double side, boolean filled)throws ExecutionException{
        rect(x,y,side,side, filled);
    }

    /**
     * Draws an image on a graphics context.
     *
     * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
     *   (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
     *
     * @param gc the graphics context the image is to be drawn on.
     * @param angle the angle of rotation.
     * @param tlx the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
     * @param tly the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
     * @param px the x pivot co-ordinate for the rotation (in canvas co-ordinates).
     * @param py the y pivot co-ordinate for the rotation (in canvas co-ordinates).
     */
    Rotate rotate = new Rotate();
    public void rotateImage(Image img, double tlx, double tly, double width, double height, double angle, double px, double py) throws ExecutionException{
        if (Platform.isFxApplicationThread()) {
            rotateImageBase(img,tlx,tly,width,height,angle,px,py);
        }else {
            counter++;
            if(counter>maxCounter){
                throw new ExecutionException(new Exception(hint));
            }
            Platform.runLater(() -> rotateImageBase(img, tlx, tly, width, height, angle, px, py));
        }
    }
    private void rotateImageBase(Image image, double tlx, double tly, double width, double height, double angle, double px, double py) {
        javafx.scene.canvas.GraphicsContext gc = body.getGraphicsContext2D();
        gc.save(); // saves the current state on stack, including the current transform
        rotate.setAngle(angle);
        rotate.setPivotX(px);
        rotate.setPivotY(py);
        gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
        gc.drawImage(image, tlx, tly, width, height);
        gc.restore(); // back to original state (before rotation)
    }
    public void rotate_image(Image image, double tlx, double tly, double width, double height, double angle, double px, double py)throws ExecutionException {
        rotateImage(image,tlx,tly,width,height,angle,px,py);
    }
    public void rotateimage(Image image, double tlx, double tly, double width, double height, double angle, double px, double py)throws ExecutionException {
        rotateImage(image,tlx,tly,width,height,angle,px,py);
    }
}
