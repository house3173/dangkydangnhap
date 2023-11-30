package com.example.dangkydangnhap.controller.dangkydangnhap;

import com.example.dangkydangnhap.model.CCCD;
import com.example.dangkydangnhap.model.ThongTinNhanKhau;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class Helper cung cấp các phương thức hỗ trợ cho các class khác trong package dao
 */
public class HelperFromDao {
    /**
     * @param resultSet ResultSet chứa dữ liệu cần trích xuất.
     * @return Thông tin của nhân khẩu
     * @throws SQLException Nếu có lỗi khi truy cập dữ liệu từ ResultSet.
     */
    public static ThongTinNhanKhau get(ResultSet resultSet) throws SQLException {
        String soCccd = resultSet.getString("soCccd");
        LocalDateTime ngayCap = resultSet.getTimestamp("ngayCap").toLocalDateTime();
        String noiCap = resultSet.getString("noiCap");

        int idHoKhau = resultSet.getInt("idHoKhau");
        String hoTen = resultSet.getString("hoTen");
        String biDanh = resultSet.getString("biDanh");
        LocalDateTime ngaySinh = resultSet.getTimestamp("ngaySinh").toLocalDateTime();
        String noiSinh = resultSet.getString("noiSinh");
        String nguyenQuan = resultSet.getString("nguyenQuan");
        String danToc = resultSet.getString("danToc");
        String tonGiao = resultSet.getString("tonGiao");
        String ngheNghiep = resultSet.getString("ngheNghiep");
        String noiLamViec = resultSet.getString("noiLamViec");
        LocalDateTime ngayDKTT = resultSet.getTimestamp("ngayDKTT").toLocalDateTime();
        String diaChiCu = resultSet.getString("diaChiCu");
        String quanHe = resultSet.getString("quanHe");

        CCCD cccd = new CCCD(soCccd, ngayCap, noiCap);

        return new ThongTinNhanKhau(cccd, idHoKhau, hoTen, biDanh, ngaySinh,
                noiSinh, nguyenQuan, danToc, tonGiao, ngheNghiep, noiLamViec, ngayDKTT,
                diaChiCu, quanHe);
    }

    /**
     * @param thongTinNhanKhau Thông tin nhân khẩu để đưa vào truy vấn.
     * @param statement        PreparedStatement đang được chuẩn bị.
     * @param index            Index bắt đầu để thiết lập giá trị trong PreparedStatement.
     * @return Index tiếp theo sẽ được sử dụng cho các giá trị khác nếu cần.
     * @throws SQLException Nếu có lỗi khi thiết lập giá trị trong PreparedStatement.
     */
    static int setValuesForStatement(ThongTinNhanKhau thongTinNhanKhau, PreparedStatement statement, int index) throws SQLException {
        statement.setTimestamp(index++, Timestamp.valueOf(thongTinNhanKhau.getCccd().getNgayCap()));
        statement.setString(index++, thongTinNhanKhau.getCccd().getNoiCap());

        statement.setInt(index++, thongTinNhanKhau.getIdHoKhau());
        statement.setString(index++, thongTinNhanKhau.getHoTen());
        statement.setString(index++, thongTinNhanKhau.getBiDanh());
        statement.setTimestamp(index++, Timestamp.valueOf(thongTinNhanKhau.getNgaySinh()));
        statement.setString(index++, thongTinNhanKhau.getNoiSinh());
        statement.setString(index++, thongTinNhanKhau.getNguyenQuan());
        statement.setString(index++, thongTinNhanKhau.getDanToc());
        statement.setString(index++, thongTinNhanKhau.getTonGiao());
        statement.setString(index++, thongTinNhanKhau.getNgheNghiep());
        statement.setString(index++, thongTinNhanKhau.getNoiLamViec());
        statement.setTimestamp(index++, Timestamp.valueOf(thongTinNhanKhau.getNgayDKTT()));
        statement.setString(index++, thongTinNhanKhau.getDiaChiCu());
        statement.setString(index++, thongTinNhanKhau.getQuanHe());

        return index;
    }
}
