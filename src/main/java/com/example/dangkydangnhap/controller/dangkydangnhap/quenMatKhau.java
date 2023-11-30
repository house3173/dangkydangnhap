package com.example.dangkydangnhap.controller.dangkydangnhap;

import com.example.dangkydangnhap.dao.TaiKhoanNhanKhauDAO;
import com.example.dangkydangnhap.database.SqlConnection;
import com.example.dangkydangnhap.model.TaiKhoanNhanKhau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.dangkydangnhap.controller.dangkydangnhap.functionHelp.connection;

public class quenMatKhau implements Initializable {

    @FXML
    private TextField MKmoi;

    @FXML
    private PasswordField MKmoiHidden;

    @FXML
    private TextField MKmoiNhapLai;

    @FXML
    private PasswordField MKmoiNhapLaiHidden;

    @FXML
    private CheckBox boxMKmoi;

    @FXML
    private CheckBox boxMKmoiNhapLai;

    @FXML
    private Text errorInfo;

    @FXML
    private TextField hoTenMK;

    @FXML
    private TextField idHoKhauMK;

    @FXML
    private DatePicker ngaySinhMK;

    @FXML
    private TextField soCccdMK;

    @FXML
    private AnchorPane vungMKmoi;

    @FXML
    private Text errorPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vungMKmoi.setVisible(false);
        soCccdMK.textProperty().addListener((observable, oldValue, newValue) -> {
            vungMKmoi.setVisible(false);
        });
        hoTenMK.textProperty().addListener((observable, oldValue, newValue) -> {
            vungMKmoi.setVisible(false);
        });
        idHoKhauMK.textProperty().addListener((observable, oldValue, newValue) -> {
            vungMKmoi.setVisible(false);
        });
        ngaySinhMK.valueProperty().addListener((observable, oldValue, newValue) -> {
            vungMKmoi.setVisible(false);
        });

    }

    /**
     * Phương thức kiểm tra thông tin tài khoản thay đổi mật khẩu
     * @return true/fale là kết quả kiểm tra
     * throws SQLException lỗi truy vấn dữ liệu
     */
    public boolean kiemTraThongTin() throws SQLException {
        // Lấy thông tin đăng ký
        String hotenDK = hoTenMK.getText();
        String ngaysinhDK = ngaySinhMK.getValue().toString();
        String socccdDK = soCccdMK.getText();
        String idhokhauDK = idHoKhauMK.getText();

        System.out.println(soCccdMK + " " + hotenDK + " " + ngaysinhDK + " " + idhokhauDK);

        return functionHelp.kiemTraThongTinDangKy(socccdDK, hotenDK, ngaysinhDK, idhokhauDK);
    }

    /**
     * Phương thức xử lý sự kiện kiểm tra thông tin hợp lệ cấp quyền thay đổi mật khẩu
     * @param e sự kiện click button "Tiếp tục"
     * throws SQLException lỗi truy vấn dữ liệu
     */
    public void tiepTucThayDoi(ActionEvent e) throws SQLException {
        if(soCccdMK.getText().isEmpty() || hoTenMK.getText().isEmpty()
                || ngaySinhMK.getValue() == null || idHoKhauMK.getText().isEmpty()) {
            errorInfo.setText("Yêu cầu nhập đầy đủ thông tin");
            return;
        }

        TaiKhoanNhanKhauDAO tknkDao = new TaiKhoanNhanKhauDAO(connection);
        Optional<TaiKhoanNhanKhau> tknk = tknkDao.get(soCccdMK.getText());
        if(tknk.isEmpty()) {
            errorInfo.setText("Tài khoản nhân khẩu chưa tồn tại!");
            return;
        }

        if(kiemTraThongTin()) {
            errorInfo.setText("");
            vungMKmoi.setVisible(true);
        } else {
            errorInfo.setText("Thông tin chưa chính xác");
        }
    }

    /**
     * Phương thức thực hiện ẩn/hiện mật khẩu mới
     * @param event sự kiện click checkbox
     */
    public void checkboxHiddenMKmoi(ActionEvent event) {
        functionHelp.changeVisibilityPassword(event, boxMKmoi, MKmoi, MKmoiHidden);
    }

    /**
     * Phương thức thục hiện ẩn/hiện mật khẩu mới nhập lại
     * @param event sự kiện click checkbox
     */
    public void checkboxHiddenMKmoiNhapLai(ActionEvent event) {
        functionHelp.changeVisibilityPassword(event, boxMKmoiNhapLai, MKmoiNhapLai, MKmoiNhapLaiHidden);
    }

    /**
     * Phương thức thực hiện đổi mật khẩu thành công
     * @param e sự kiện click button "Thay đổi nâjt khẩu"
     * @param soCccd soCccd của người thay đổi mật khẩu
     * @param matkhau mật khẩu đựoc xác nhận
     * @throws IOException xảy ra lỗi
     */
    public void doiMKThanhCong(ActionEvent e, String soCccd, String matkhau) throws IOException {
        TaiKhoanNhanKhauDAO tknkDao = new TaiKhoanNhanKhauDAO(connection);
        Optional<TaiKhoanNhanKhau> selectTK = tknkDao.get(soCccd);

        if(selectTK.isEmpty()) {
            return;
        }

        String tenTaiKhoan = selectTK.get().getTentaikhoan();
        TaiKhoanNhanKhau tknk = new TaiKhoanNhanKhau(soCccd,tenTaiKhoan, matkhau);
        tknkDao.delete(selectTK.get());
        tknkDao.save(tknk);


        // Thông báo đăng ký thành công
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Thay đổi mật khẩu thành công\nBạn đã được chuyển về trang đăng nhập" +
                "\nĐăng nhập với số CCCD của bạn: " +  soCccd +
                "\nMật khẩu đăng nhập của bạn là: " +  matkhau);
        alert.setTitle("Thay đổi mật khẩu thành công");
        alert.show();

        // Quay trở lại trang đăng nhập
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/dangkydangnhap/view/dangkydangnhap/dangNhap.fxml"));
        Parent dangnhap = loader.load();
        Scene scene = new Scene(dangnhap);
        stage.setScene(scene);
    }

    /**
     * Phương thức đồng ý thay đổi và kiểm tra mật khẩu
     * @param event sự kiện click button "Thay đổi mật khẩu"
     * @throws IOException xảy ra lỗi
     */
    public void xacNhanMatKhau(ActionEvent event) throws IOException {
        // Lay thong tin tu hai truong mat khau
        String mkMoiNhap = functionHelp.layMatKhau(boxMKmoi, MKmoi, MKmoiHidden);
        String mkMoiNhapLai = functionHelp.layMatKhau(boxMKmoiNhapLai, MKmoiNhapLai, MKmoiNhapLaiHidden);
        System.out.println(mkMoiNhap + " === " + mkMoiNhapLai);

        // Xac nhan mat khau khong thanh cong
        if(mkMoiNhap.isEmpty()) {
            errorPassword.setText("Trường mật khẩu mới chưa được nhập");
            return;
        }
        if(mkMoiNhapLai.isEmpty()) {
            errorPassword.setText("Trường mật khẩu nhập lại chưa được nhập");
            return;
        }
        if(!mkMoiNhap.equals(mkMoiNhapLai)) {
            errorPassword.setText("Mật khẩu nhập lại chưa chính xác");
            return;
        }

        // Xac nhan mat khau thanh cong
        doiMKThanhCong(event, soCccdMK.getText(), mkMoiNhap);
    }

    /**
     * Phương thức quy lại trang đăng nhập khi không đổi mật khẩu nữa
     * @param e sự kiện click vào icon Return
     * @throws IOException xảy ra lỗi
     */
    public void returnLogin (ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/dangkydangnhap/view/dangkydangnhap/dangNhap.fxml"));
        Parent dangnhap = loader.load();
        Scene scene = new Scene(dangnhap);
        stage.setScene(scene);
    }

}
