package com.example.dangkydangnhap.controller.guithongbao;

import com.example.dangkydangnhap.controller.dangkydangnhap.HelperFromDao;
import com.example.dangkydangnhap.dao.HoKhauDAO;
import com.example.dangkydangnhap.dao.TaiKhoanNhanKhauDAO;
import com.example.dangkydangnhap.database.SqlConnection;
import com.example.dangkydangnhap.model.HoKhau;
import com.example.dangkydangnhap.model.NhanKhau;
import com.example.dangkydangnhap.model.TaiKhoanNhanKhau;
import com.example.dangkydangnhap.model.ThongTinNhanKhau;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class functionHelpGuiThongBao {
    public static Connection connection = SqlConnection.connect();

    /**
     * Phương thức lấy ra toàn bộ thông tin các nhân khẩu từ hộ khẩu
     * @return List<NhanKhau> danh sách các nhân khẩu
     * @throws SQLException có lỗi truy vấn từ cơ sở dữ liệu
     */
    public static List<NhanKhau> getAllNhanKhau() throws SQLException {
        List<NhanKhau> danhsachnhankhau = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM nhankhau");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ThongTinNhanKhau thongTinNhanKhau = HelperFromDao.get(resultSet);
            NhanKhau nhanKhau = new NhanKhau(thongTinNhanKhau);
            danhsachnhankhau.add(nhanKhau);
        }
        return  danhsachnhankhau;
    }

    /**
     * Phương thức lấy một danh sách thông tin nhân khẩu dưới dạng: hoten - soCccd
     * @return danh sách các thông tin về hoten và soCccd của nhân khẩu
     * @throws SQLException xảy ra lỗi truy vấn dữ liệu
     */
    public static List<String> getAllNKsoCccd() throws SQLException {
        List<NhanKhau> danhsachnhankhau = functionHelpGuiThongBao.getAllNhanKhau();
        List<String>  hoTenCccd = new ArrayList<>();

        for(NhanKhau nhankhau : danhsachnhankhau) {
            String infoNK = nhankhau.getThongTinNhanKhau().getHoTen() + " - " +
                    nhankhau.getThongTinNhanKhau().getCccd().getSoCccd();
            hoTenCccd.add(infoNK);
        }
        return hoTenCccd;
    }

    /**
     * Phương thức lấy một danh sách thông tin nhân khẩu dưới dạng: idHoKhau - tenChuHo
     * @return danh sách thông  các hộ khẩu
     */
    public static List<String> getAllHKhoTenChuHo() {
        HoKhauDAO hkd = new HoKhauDAO(connection);
        List<HoKhau> dshk = hkd.getAll();
        List<String> infoHk = new ArrayList<>();
        for(HoKhau hk: dshk) {
            infoHk.add(hk.getIdHoKhau() + " - " + hk.getTenChuHo());
        }
        return infoHk;
    }

    /**
     * Phương thức thêm thông báo mới
     * @param soCccd: soCccd người nhận thông báo
     * @param tieude: tiêu đề thông báp
     * @param noidung: nội dung thông báo
     * @param ngayTao: ngày tạo thông báo
     * @throws SQLException lỗi truy vấn dữ liệu
     */
    public static void insertThongBao(String soCccd, String tieude, String noidung, LocalDateTime ngayTao) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO thongbao" +
                "(soCCCD, tieuDe, noiDung, ngayTao) " +
                "VALUES (?, ?, ?, ?)");
        statement.setString(1, soCccd);
        statement.setString(2, tieude);
        statement.setString(3, noidung);
        statement.setTimestamp(4, Timestamp.valueOf(ngayTao));

        statement.executeUpdate();
    }

    /**
     * Phương thứuc kiểm tra tồn tại tài khoản nhân khẩu hay không
     * @param soCccd: số Cccd của nhân khẩu
     * @return true/false tồn tại tài khoản
     */
    public static boolean kiemTraTonTaiTK(String soCccd) {
        TaiKhoanNhanKhauDAO tknkDao = new TaiKhoanNhanKhauDAO(connection);
        Optional<TaiKhoanNhanKhau> selectTK = tknkDao.get(soCccd);
        if(selectTK.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Phương thức lấy một hộ khẩu khi có idHoKhau
     * @param idHoKhau của hộ khẩu
     * @return Optional<HoKhau> chứa thông tin hộ khẩu hoặc rỗng
     * @throws SQLException xảy ra lỗi truy vấn dữ liệu
     */
    public static Optional<HoKhau> getHKByIdHK(int idHoKhau) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM hokhau WHERE idHoKhau = ?");
        statement.setInt(1, idHoKhau);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            HoKhau hoKhau = new HoKhau(
                    resultSet.getInt("idHoKhau"),
                    resultSet.getString("tenChuHo"),
                    resultSet.getString("soCccdChuHo"),
                    resultSet.getString("diaChiNha"),
                    resultSet.getTimestamp("ngayTaoHK").toLocalDateTime());
            return Optional.of(hoKhau);
        }
        return  Optional.empty();
    }

    /**
     * Phương thức lấy danh sách nhân khẩu của một hộ
     * @param idHoKhau của hộ khẩu
     * @return List<NhanKhau> chứa danh sách các nhân khẩu của hộ
     * @throws SQLException xảy ra lỗi truy vấn dữ liệu
     */
    public static List<NhanKhau> getAllNhanKhauByIdHoKhau(int idHoKhau) throws SQLException {
        List<NhanKhau> danhsachnhankhau = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM nhankhau WHERE idHoKhau = ?");
        statement.setInt(1,idHoKhau);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ThongTinNhanKhau thongTinNhanKhau = HelperFromDao.get(resultSet);
            NhanKhau nhanKhau = new NhanKhau(thongTinNhanKhau);
            danhsachnhankhau.add(nhanKhau);
        }
        return  danhsachnhankhau;
    }

}
