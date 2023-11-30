module com.example.dangkydangnhap {
    requires javafx.controls;
    requires javafx.fxml;
    requires annotations;
    requires java.sql;


    opens com.example.dangkydangnhap to javafx.fxml;

    exports com.example.dangkydangnhap;
    exports com.example.dangkydangnhap.controller.dangkydangnhap;
    exports com.example.dangkydangnhap.controller.guithongbao;
    exports com.example.dangkydangnhap.controller.taoKhoanPhi;

    opens com.example.dangkydangnhap.controller.dangkydangnhap to javafx.fxml;
    opens com.example.dangkydangnhap.controller.guithongbao to javafx.fxml;
    opens com.example.dangkydangnhap.controller.taoKhoanPhi to javafx.fxml;
}