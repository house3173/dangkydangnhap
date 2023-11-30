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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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


public class DangKyOfficalController implements Initializable {
    @FXML
    private ComboBox<String> cauhoixacnhan;

    @FXML
    private TextField cautraloi;

    @FXML
    private TextField hoTen;

    @FXML
    private TextField idHoKhau;

    @FXML
    private TextField matKhau;

    @FXML
    private DatePicker ngaySinh;

    @FXML
    private TextField soCccd;

    @FXML
    private TextField tenTaiKhoan;

    @FXML
    private Text textError;
    @FXML
    private AnchorPane thongTinTaiKhoan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Phương thức kiểm tra thông tin đăng ký tài khoản có khớp với dữ liệu nhân khẩu
     * @return true/fale là kết quả kiểm tra
     * throws SQLException lỗi truy vấn dữ liệu
     */
    public boolean kiemTraDangKy() throws SQLException {

        // Lấy thông tin đăng ký
        String hotenDK = hoTen.getText();
        String ngaysinhDK = ngaySinh.getValue().toString();
        String socccdDK = soCccd.getText();
        String idhokhauDK = idHoKhau.getText();

        return functionHelp.kiemTraThongTinDangKy(socccdDK, hotenDK, ngaysinhDK, idhokhauDK);
    }

    /**
     * Phương thức đưa ra thông báo khi đăng ký tài khoản mới thành công
     * @param e sự kiện click chuột
     * @throws Exception xảy ra lỗi
     */
    public void signUpSuccess (ActionEvent e) throws Exception {
        // Lưu thông tin tài khoản vào bảng taikhoan

        TaiKhoanNhanKhau tknk = new TaiKhoanNhanKhau(soCccd.getText(), tenTaiKhoan.getText(), matKhau.getText());
        Connection connection = SqlConnection.connect();
        TaiKhoanNhanKhauDAO tknkDao = new TaiKhoanNhanKhauDAO(connection);
        tknkDao.save(tknk);


        // Thông báo đăng ký thành công
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Đăng ký thành công\nBạn đã được chuyển về trang đăng nhập" +
                "\nĐăng nhập với số CCCD của bạn: " +  soCccd.getText() +
                "\nMật khẩu đăng nhập của bạn là: " +  matKhau.getText() );
        alert.setTitle("Đăng ký thành công");
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
     * Phương thức xử lý sự kiện click chuột vào button đăng ký
     * @param e sự kiện click chuột button "Đăng ký"
     */
    public void signUpClicked (ActionEvent e) throws Exception {
        if(hoTen.getText().isEmpty() || ngaySinh.getValue() == null
                || soCccd.getText().isEmpty() || idHoKhau.getText().isEmpty()) {
            textError.setText("Nhập đầy đủ thông tin cá nhân để được xác nhận!");
            return;
        }

        if(tenTaiKhoan.getText().isEmpty() || matKhau.getText().isEmpty()) {
            textError.setText("Yêu cầu nhập đầy đủ tên tài khoản và mật khẩu!");
            return;
        }

        // Kiem tra tai khoan da ton tai
        TaiKhoanNhanKhauDAO tknkdao = new TaiKhoanNhanKhauDAO(connection);
        Optional<TaiKhoanNhanKhau> tknkSelect = tknkdao.get(soCccd.getText());
        if(tknkSelect.isPresent()) {
            textError.setText("Tài khoản đã tồn tại!");
            return;
        }

        // Kiem tra sai thong tin ca nhan
        if(kiemTraDangKy()) {
            signUpSuccess(e);
        } else {
            textError.setText("Thông tin cá nhân chưa chính xác! Vui lòng nhập lại!");
        }
    }

    /**
     * Phương thức quy lại trang đăng nhập khi không đăng ký nữa
     * @param e sự kiện click vào icon Return
     * @throws IOException
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
