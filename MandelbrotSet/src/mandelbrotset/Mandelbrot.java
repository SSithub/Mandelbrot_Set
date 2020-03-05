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
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Mandelbrot extends Application {

    final int ITERATIONS = 100;
    final double SCALE = .00012;//Default: .002
    final double WIDTH = Screen.getPrimary().getVisualBounds().getMaxX();
    final double HEIGHT = Screen.getPrimary().getVisualBounds().getMaxY();
    WritableImage image = new WritableImage((int) WIDTH, (int) HEIGHT);
    final ImageView IMAGEVIEW = new ImageView(image);
    final Group ROOT = new Group(IMAGEVIEW);
    double initialX;
    double initialY;
    AtomicInteger atomic = new AtomicInteger();
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

    Complex function(Complex z, Complex c) {
        return Complex.add(Complex.square(z), c);
    }

    Image generateImage() {
        WritableImage image = new WritableImage((int) WIDTH, (int) HEIGHT);
        double centerX = WIDTH / 2 + 6000;
        double centerY = HEIGHT / 2 - 2500;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Complex c = new Complex(SCALE * (x - centerX), SCALE * (centerY - y));//Converting pixel indexes to coordinates for a complex number
                Complex z = c.clone();
                for (int i = 1;; i++) {//Skipped the first iteration
                    if (z.magnitude() >= 2) {
                        image.getPixelWriter().setColor(x, y, Color.WHITE);
                        break;
                    } else if (i > ITERATIONS) {
                        image.getPixelWriter().setColor(x, y, Color.BLACK);
                        break;
                    }
                    z = function(z, c);
                }
            }
        }
        return image;
    }

    void setPixel(int x, int y) {
        Complex c = new Complex(SCALE * (x - WIDTH / 2), SCALE * (HEIGHT / 2 - y));
        Complex z = c.clone();
        for (int i = 1;; i++) {//Skipped the first iteration
            if (z.magnitude() >= 2) {
                image.getPixelWriter().setColor(x, y, Color.WHITE);
                break;
            } else if (i > ITERATIONS) {
                image.getPixelWriter().setColor(x, y, Color.BLACK);
                break;
            }
            z = function(z, c);
        }
    }
    double xOffset;
    double yOffset;
    @Override
    public void start(Stage stage) {
        IMAGEVIEW.setImage(generateImage());
        Scene scene = new Scene(ROOT, 0, 0);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
