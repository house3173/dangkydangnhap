package com.example.dangkydangnhap.controller.dangkydangnhap;

import com.example.dangkydangnhap.dao.TaiKhoanNhanKhauDAO;
import com.example.dangkydangnhap.database.SqlConnection;
import com.example.dangkydangnhap.model.NhanKhau;
import com.example.dangkydangnhap.model.TaiKhoanNhanKhau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.dangkydangnhap.controller.dangkydangnhap.functionHelp.connection;

public class dangNhapController implements Initializable {
    @FXML
    private TextField soCccdDangNhap;
    @FXML
    private  TextField password;
    @FXML
    private Text errorLogin;
    @FXML
    private PasswordField passwordHidden;
    @FXML
    private CheckBox checkBoxPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(connection);
    }

    /**
     * phương thức xử lý sự kiện ẩn hiện password
     * @param event sự kiện click checkbox
     */
    public void changeVisibilityPasswordDN (ActionEvent event) {
        functionHelp.changeVisibilityPassword(event, checkBoxPassword, password, passwordHidden);
    }

    /**
     * Phương thức xử lý sự kiện chuyển đến trang đăng ký khi nhấn vào button "Đăng ký"
     * @param e sự kiện nhấn chuột
     * @throws IOException xảy ra lỗi
     */
    public void signUp (ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/dangkydangnhap/view/dangkydangnhap/dangKy.fxml"));
        Parent dangky = loader.load();
        Scene scene = new Scene(dangky);
        stage.setScene(scene);
    }

    /**
     * Phương thức xử lý sự kiện chuyển đến trang đổi mật khẩu khi nhấn vào button "Quên mật khẩu"
     * @param e sự kiện nhấn chuột
     * @throws IOException xảy ra lỗi
     */
    public void quenMatKhau (ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/dangkydangnhap/view/dangkydangnhap/thayDoiMatKhau.fxml"));
        Parent dangky = loader.load();
        Scene scene = new Scene(dangky);
        stage.setScene(scene);
    }

    /**
     * Phương thức sự kiện đăng nhập thành công
     * @param e sự kiện click chuột button Đăng nhập
     * @throws Exception xảy ra lỗi
     */
    public void loginSuccess(ActionEvent e) throws Exception {
        Optional<NhanKhau> selectNK = functionHelp.getNhanKhau(soCccdDangNhap.getText());

        if(selectNK.isEmpty()) {
            return;
        }

        String tenTaiKhoan = selectNK.get().getThongTinNhanKhau().getHoTen();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dangkydangnhap/view/dangkydangnhap/trangChu.fxml"));
        Parent home = loader.load();
        trangChuController trangchucontroller = loader.getController();
        trangchucontroller.display("Xin chào, " + tenTaiKhoan + "!");

        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(home);
        stage.setScene(scene);
    }

    /**
     * Sự kiện kiểm tra thông tin đăng nhập và chuyển đến trang chủ khi nhấn vào button "Đăng nhập"
     * @param e sự kiện nhấn chuột
     * @throws Exception xảy ra lỗi
     */
    public  void login (ActionEvent e) throws Exception {
        if (soCccdDangNhap.getText().isEmpty() || (password.getText().isEmpty() && passwordHidden.getText().isEmpty())) {
            errorLogin.setText("Hãy nhập đầy đủ thông tin");
            return;
        }
        // Lấy thông tin đăng nhập
        String cccd = soCccdDangNhap.getText();

        // Lấy thông tin mật khẩu
        String matkhau = functionHelp.layMatKhau(checkBoxPassword, password,passwordHidden);

        // Lấy thông tin từ CSDL để đối chiếu
        TaiKhoanNhanKhauDAO tknkDao = new TaiKhoanNhanKhauDAO(connection);
        Optional<TaiKhoanNhanKhau> selectTK = tknkDao.get(cccd);

        System.out.println(cccd + " " + matkhau);
        System.out.println(selectTK);

        if(selectTK.isPresent()) {
            TaiKhoanNhanKhau tknk = selectTK.get();
            System.out.println(tknk.getSoCCCD()+ " " + tknk.getPass());
            if(tknk.getPass().equals(matkhau)) {
                loginSuccess(e);
            } else {
                errorLogin.setText("Mã số đăng nhập hoặc \nmật khẩu chưa đúng");
            }
        } else {
            errorLogin.setText("Mã số đăng nhập hoặc \nmật khẩu chưa đúng");
        }

    }

}