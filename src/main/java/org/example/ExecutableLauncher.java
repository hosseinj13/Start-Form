package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ExecutableLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ایجاد یک StackPane برای قرار دادن آیکون‌ها و پس‌زمینه
        StackPane root = new StackPane();

        // ایجاد تصویر پس‌زمینه
        Image backgroundImage = new Image("file:C:\\Program Files (x86)\\NasrMahan\\PCClient\\Background.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(400);  // عرض فرم
        backgroundView.setFitHeight(300); // ارتفاع فرم
        backgroundView.setPreserveRatio(false); // پر کردن کل فضا

        // ایجاد HBox برای نگهداری آیکون‌ها در وسط
        HBox hbox = new HBox(20); // فاصله بین آیکون‌ها
        hbox.setStyle("-fx-alignment: center;");

        // اضافه کردن آیکون‌ها به HBox
        hbox.getChildren().addAll(
                createIconButton("C:\\Program Files (x86)\\NasrMahan\\PCClient\\OIP.png", "C:\\Program Files (x86)\\NasrMahan\\PCClient\\NasrMahan.exe")
        );

        // اضافه کردن پس‌زمینه و HBox به StackPane
        root.getChildren().addAll(backgroundView, hbox);

        // ایجاد صحنه و نمایش آن
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Executable & JAR Launcher");
        primaryStage.show();
    }

    // متد برای ایجاد آیکون‌های قابل کلیک
    private Button createIconButton(String iconPath, String executablePath) {
        Image icon = new Image("file:" + iconPath); // بارگذاری آیکون از مسیر مشخص شده
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(400); // تنظیم عرض آیکون
        imageView.setFitHeight(300); // تنظیم ارتفاع آیکون

        // ساخت دکمه از آیکون
        Button button = new Button();
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;");

        // افزودن اکشن به دکمه
        button.setOnAction(event -> openExecutable(executablePath));

        return button;
    }

    // متد برای اجرای فایل اجرایی (EXE یا JAR)
    private void openExecutable(String executablePath) {
        try {
            // دایرکتوری که فایل اجرایی در آن قرار دارد
            File executableDir = new File("C:\\Program Files (x86)\\NasrMahan\\PCClient");

            // تنظیم محیط برای افزودن مسیر فولدر به PATH
            ProcessBuilder processBuilder = new ProcessBuilder(executablePath);
            processBuilder.directory(executableDir); // تنظیم دایرکتوری برای اجرا
            processBuilder.environment().put("PATH", executableDir.getAbsolutePath() + ";" + System.getenv("PATH"));

            // اجرا کردن برنامه
            if (executablePath.endsWith(".exe")) {
                processBuilder.start();
            } else if (executablePath.endsWith(".jar")) {
                new ProcessBuilder("java", "-jar", executablePath).start();
            }
        } catch (IOException e) {
            System.err.println("Error running executable: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
