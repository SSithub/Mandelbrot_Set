package mandelbrotset;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Mandelbrot extends Application {

    final int ITERATIONS = 100;
    final double SCALE = 1;
    final double WIDTH = Screen.getPrimary().getVisualBounds().getMaxX();
    final double HEIGHT = Screen.getPrimary().getVisualBounds().getMaxY();
    final Group ROOT = new Group();
    final ImageView IMAGE = new ImageView();

    Complex function(Complex z, Complex c) {
        return Complex.add(Complex.square(z), c);
    }

    Image generateImage() {
        WritableImage image = new WritableImage((int) WIDTH, (int) HEIGHT);
        PixelWriter writer = image.getPixelWriter();
        double centerX = WIDTH/2;
        double centerY = HEIGHT/2;
        //(0,0) corresponds to (centerX, centerY), each pixel will be the size of the SCALE field.
        //The top right pixel would be (centerX + centerX, centerY - centerY) and its corresponding complex number is (SCALE * centerX) + (SCALE * centerY)i
    }

    @Override
    public void start(Stage stage) {
        ROOT.getChildren().add(IMAGE);
        Scene scene = new Scene(ROOT, 0, 0);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
//        Complex c = new Complex(0, 1);
//        Complex z = c.clone();
//        int i = 0;
//        while (true) {
//            i++;
//            System.out.println(z);
//            z = function(z, c);
//            if (z.equals(c)) {
//                break;
//            }
//        }
//        System.out.println("Iterations:");
//        System.out.println(i);
    }

}
