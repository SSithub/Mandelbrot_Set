package mandelbrotset;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Mandelbrot extends Application {

    double iterations = 20;
    final double ITERATIONSINCREASERATE = Math.E/2;
    double scale = .003;
    final double ZOOMRATE = 2;
    final double WIDTH = Screen.getPrimary().getVisualBounds().getMaxX();
    final double HEIGHT = Screen.getPrimary().getVisualBounds().getMaxY();
    WritableImage image = new WritableImage((int) WIDTH, (int) HEIGHT);
    final ImageView IMAGEVIEW = new ImageView(image);
    final Group ROOT = new Group(IMAGEVIEW);
    double initialX;
    double initialY;
    double offsetX;
    double offsetY;
    int x = 0;
    int y = 0;
//    final Timeline timer = new Timeline(new KeyFrame(Duration.ONE, event -> {
//        setPixel(x, y);
//        if (x < WIDTH-1) {
//            x++;
//        } else {
//            y++;
//            x = 0;
//            if (y >= HEIGHT) {
//                y = 0;
//            }
//        }
//    }));
//    void setupTimer(){
//        timer.setCycleCount(Animation.INDEFINITE);
//        timer.play();
//        timer.setRate(400);
//    }
//
//    void setPixel(int x, int y) {
//        Complex c = new Complex(scale * (x - WIDTH / 2), scale * (HEIGHT / 2 - y));
//        Complex z = c.clone();
//        for (int i = 1;; i++) {//Skipped the first iteration
//            if (z.magnitude() >= 2) {
//                image.getPixelWriter().setColor(x, y, Color.WHITE);
//                break;
//            } else if (i > iterations) {
//                image.getPixelWriter().setColor(x, y, Color.BLACK);
//                break;
//            }
//            z = function(z, c);
//        }
//    }

    Complex function(Complex z, Complex c) {
        return Complex.add(Complex.square(z), c);
    }

    Image generateImage() {
        WritableImage image = new WritableImage((int) WIDTH, (int) HEIGHT);
        double centerX = WIDTH / 2 - offsetX;
        double centerY = HEIGHT / 2 - offsetY;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Complex c = new Complex(scale * (x - centerX), scale * (centerY - y));//Converting pixel indexes to coordinates for a complex number
                Complex z = c.clone();
                for (int i = 1;; i++) {//Skipped the first iteration
                    if (z.magnitude() >= 2) {
                        image.getPixelWriter().setColor(x, y, Color.WHITE);
                        break;
                    } else if (i > iterations) {
                        image.getPixelWriter().setColor(x, y, Color.BLACK);
                        break;
                    }
                    z = function(z, c);
                }
            }
        }
        return image;
    }

    @Override
    public void start(Stage stage) {
        IMAGEVIEW.setImage(generateImage());
        Scene scene = new Scene(ROOT, 0, 0);
        scene.setOnKeyPressed(event -> {
            KeyCode pressed = event.getCode();
            if (pressed == KeyCode.LEFT || pressed == KeyCode.A) {
                offsetX -= 100;
                IMAGEVIEW.setImage(generateImage());
            } else if (pressed == KeyCode.RIGHT || pressed == KeyCode.D) {
                offsetX += 100;
                IMAGEVIEW.setImage(generateImage());
            } else if (pressed == KeyCode.UP || pressed == KeyCode.W) {
                offsetY -= 100;
                IMAGEVIEW.setImage(generateImage());
            } else if (pressed == KeyCode.DOWN || pressed == KeyCode.S) {
                offsetY += 100;
                IMAGEVIEW.setImage(generateImage());
            } else if (pressed == KeyCode.EQUALS) {
                scale /= ZOOMRATE;
                offsetX *= ZOOMRATE;
                offsetY *= ZOOMRATE;
                iterations *= ITERATIONSINCREASERATE;
                IMAGEVIEW.setImage(generateImage());
            } else if (pressed == KeyCode.MINUS) {
                scale *= ZOOMRATE;
                offsetX /= ZOOMRATE;
                offsetY /= ZOOMRATE;
                iterations /= ITERATIONSINCREASERATE;
                IMAGEVIEW.setImage(generateImage());
            }
        });
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
