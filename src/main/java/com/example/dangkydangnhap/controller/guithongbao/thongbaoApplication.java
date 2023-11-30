package com.example.dangkydangnhap.controller.guithongbao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class thongbaoApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(thongbaoApplication.class.getResource("/com/example/dangkydangnhap/view/guithongbao/guiThongBao.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 925, 670);
        stage.setTitle("Gửi thông báo");
        stage.setScene(scene);
        stage.show();
    }

}
