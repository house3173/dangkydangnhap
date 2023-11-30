package com.example.dangkydangnhap.controller.dangkydangnhap;

import com.example.dangkydangnhap.controller.guithongbao.LuuThongBao;
import com.example.dangkydangnhap.controller.guithongbao.functionHelpGuiThongBao;
import com.example.dangkydangnhap.dao.Helper;
import com.example.dangkydangnhap.dao.HoKhauDAO;
import com.example.dangkydangnhap.dao.NhanKhauDao;
import com.example.dangkydangnhap.database.SqlConnection;
import com.example.dangkydangnhap.model.HoKhau;
import com.example.dangkydangnhap.model.NhanKhau;
import com.example.dangkydangnhap.model.ThongTinNhanKhau;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.dangkydangnhap.dao.NhanKhauDao.TableType.NHANKHAU;

public class test{

    public static void main(String[] args) throws SQLException {

        Connection connection = SqlConnection.connect();

        // Tạo đối tượng Statement
        // Statement statement = connection.createStatement();

        String soCCCD = "001096000001";
        String ngaySinh = "1996-07-31";
        String hoTen = "Trịnh Văn Hậu";
        String idHoKhau = "1";

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM nhankhau where soCccd= ?");
        statement.setString(1, soCCCD);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet);
            ThongTinNhanKhau thongTinNhanKhau = HelperFromDao.get(resultSet);
            NhanKhau nhanKhau = new NhanKhau(thongTinNhanKhau);
            System.out.println(nhanKhau.getThongTinNhanKhau().getNgaySinh());
            System.out.println(functionHelp.LocalDateTimetoDateString(nhanKhau.getThongTinNhanKhau().getNgaySinh()));
        }


        //Connection connection = SqlConnection.connect();
        Optional<NhanKhau> selectNK = functionHelp.getNhanKhau(soCCCD);
        System.out.println(selectNK);
        selectNK.ifPresent(nhanKhau -> System.out.println(nhanKhau.getThongTinNhanKhau().getNgaySinh()));

        System.out.println(functionHelp.kiemTraThongTinDangKy(soCCCD, hoTen, ngaySinh,idHoKhau));

//        List<NhanKhau> dsnk = new ArrayList<>();
//        PreparedStatement statementdsnk = connection.prepareStatement("SELECT * FROM nhankhau");
//        ResultSet resultSetdsnk = statementdsnk.executeQuery();
//        while (resultSetdsnk.next()) {
//            ThongTinNhanKhau thongTinNhanKhau = HelperFromDao.get(resultSetdsnk);
//            NhanKhau nhanKhau = new NhanKhau(thongTinNhanKhau);
//            dsnk.add(nhanKhau);
//        }
//
//        for(NhanKhau nk : dsnk) {
//            System.out.println(nk.getThongTinNhanKhau().getHoTen() + " - " +
//                    nk.getThongTinNhanKhau().getCccd().getSoCccd());
//        }

        List<String> dshk = functionHelpGuiThongBao.getAllHKhoTenChuHo();
        for(String hk : dshk) {
            System.out.println(hk);
        }

        Optional<HoKhau> getHoKhau = functionHelpGuiThongBao.getHKByIdHK(1);
        if(getHoKhau.isEmpty()) {
            System.out.println("Khong tim thay ho khau");
        }else {
            HoKhau hk = getHoKhau.get();
            System.out.println(hk.getIdHoKhau()+"-"+hk.getTenChuHo());
        }

        List<NhanKhau> dsnkHaveIDHk = functionHelpGuiThongBao.getAllNhanKhauByIdHoKhau(1);
        for(NhanKhau nk : dsnkHaveIDHk) {
            System.out.println(nk.getThongTinNhanKhau().getHoTen() + " - " +
                    nk.getThongTinNhanKhau().getCccd().getSoCccd());
        }

        String intHK = LuuThongBao.laySoTuStringThongTin("12345 - Trijnh Vawn haau")[0];
        int id = Integer.parseInt(intHK);
        System.out.println(id);

        String soCccdDK = "001096000001";
        String hotenDK = "1996-07-31";
        String ngaysinhDK = "Trịnh Văn Hậu";
        String idhokhauDK = "2";

        System.out.println(soCccdDK + " " + hotenDK + " " + ngaysinhDK + " " + idhokhauDK);


    }
}
