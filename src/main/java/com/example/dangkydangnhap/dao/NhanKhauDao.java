package com.example.dangkydangnhap.dao;

import com.example.dangkydangnhap.model.NhanKhau;
import com.example.dangkydangnhap.model.ThongTinNhanKhau;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class NhanKhauDao triển khai interface DataAccessObject để thao tác với đối tượng NhanKhau trong cơ sở dữ liệu.
 */
public class NhanKhauDao implements DataAccessObject<NhanKhau, String> {
    private final Connection connection;
    private final String table_name;
    
    public enum TableType {
        NHANKHAU,
        NHANKHAUCHUATHEM
    }
    
    /**
     * Khởi tạo một đối tượng NhanKhauDao với kết nối cơ sở dữ liệu được cung cấp.
     *
     * @param connection Kết nối đến cơ sở dữ liệu.
     */
    public NhanKhauDao(Connection connection, TableType tableType) {
        this.connection = connection;
        if (tableType == TableType.NHANKHAU) {
            table_name = "nhankhau";
        } else {
            table_name = "themnhankhau";
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<NhanKhau> getAll() {
        List<NhanKhau> danhSachNhanKhau = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ?");
            statement.setString(1, table_name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                NhanKhau nhanKhau = _get(resultSet);
                danhSachNhanKhau.add(nhanKhau);
            }
        } catch (SQLException e) {
        
        }
        return danhSachNhanKhau;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<NhanKhau> get(String soCccd) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ? WHERE soCccd = ?");
            statement.setString(1, table_name);
            statement.setString(2, soCccd);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                NhanKhau nhanKhau = _get(resultSet);
                return Optional.of(nhanKhau);
            }
        } catch (SQLException e) {
        
        }
        return Optional.empty();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(@NotNull NhanKhau nhanKhau) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ?" +
                    "(soCccd, ngayCap, noiCap, idHoKhau, hoTen, biDanh, ngaySinh, noiSinh, nguyenQuan, " +
                    "danToc, tonGiao, ngheNghiep, noiLamViec, ngayDKTT, diaChiCu, quanHe) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, table_name);
            statement.setString(2, nhanKhau.getThongTinNhanKhau().getCccd().getSoCccd());
            _setValuesForStatement(nhanKhau, statement, 3);
            statement.executeUpdate();
        } catch (SQLException e) {
        
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(@NotNull NhanKhau nhanKhau) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE ? SET" +
                    "ngayCap = ?, " +
                    "noiCap = ?, " +
                    "idHoKhau = ?, " +
                    "hoTen = ?, " +
                    "biDanh = ?, " +
                    "ngaySinh = ?, " +
                    "noiSinh = ?, " +
                    "nguyenQuan = ?, " +
                    "danToc = ?, " +
                    "tonGiao = ?, " +
                    "ngheNghiep = ?, " +
                    "noiLamViec = ?, " +
                    "ngayDKTT = ?, " +
                    "diaChiCu = ?, " +
                    "quanHe = ?" +
                    "WHERE soCccd = ?");
            statement.setString(1, table_name);
            int parameterIndex = _setValuesForStatement(nhanKhau, statement, 2);
            statement.setString(parameterIndex, nhanKhau.getThongTinNhanKhau().getCccd().getSoCccd());
            statement.executeUpdate();
        } catch (SQLException e) {
        
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NotNull NhanKhau nhanKhau) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ? WHERE soCccd = ?");
            statement.setString(1, table_name);
            statement.setString(2, nhanKhau.getThongTinNhanKhau().getCccd().getSoCccd());
            statement.executeUpdate();
        } catch (SQLException e) {
        
        }
    }
    
    /**
     * Phương thức private để chuyển đổi dữ liệu từ ResultSet thành đối tượng NhanKhau.
     *
     * @param resultSet ResultSet chứa dữ liệu từ cơ sở dữ liệu.
     * @return Đối tượng NhanKhau được tạo từ dữ liệu ResultSet.
     * @throws SQLException Nếu có lỗi khi truy cập dữ liệu từ ResultSet.
     */
    private NhanKhau _get(ResultSet resultSet) throws SQLException {
        ThongTinNhanKhau thongTinNhanKhau = Helper.get(resultSet);
        
        return new NhanKhau(thongTinNhanKhau);
    }
    
    /**
     * Phương thức private để thiết lập giá trị cho PreparedStatement khi thêm hoặc cập nhật NhanKhau.
     *
     * @param nhanKhau  Đối tượng NhanKhau cần được thêm hoặc cập nhật.
     * @param statement PreparedStatement đang được chuẩn bị.
     * @param index     Index bắt đầu để thiết lập giá trị trong PreparedStatement.
     * @return Index tiếp theo sẽ được sử dụng cho các giá trị khác nếu cần.
     * @throws SQLException Nếu có lỗi khi thiết lập giá trị trong PreparedStatement.
     */
    private int _setValuesForStatement(NhanKhau nhanKhau, PreparedStatement statement, int index) throws SQLException {
        return Helper.setValuesForStatement(nhanKhau.getThongTinNhanKhau(), statement, index);
    }
}
