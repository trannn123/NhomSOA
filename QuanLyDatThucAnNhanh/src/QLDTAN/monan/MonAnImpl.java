package QLDTAN.monan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import QLDTAN.DBConnection;

public class MonAnImpl implements IMonAn {

    @Override
    public List<MonAn> layDanhSachMon() {

        List<MonAn> list = new ArrayList<>();

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM monan";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MonAn m = new MonAn();

                m.setId(rs.getInt("id"));
                m.setTenMon(rs.getString("ten_mon"));
                m.setGia(rs.getDouble("gia"));
                m.setSoLuong(rs.getInt("so_luong"));
                m.setTrangThai(rs.getString("trang_thai"));
                m.setMoTa(rs.getString("mo_ta"));
                m.setNgayTao(rs.getTimestamp("ngay_tao"));

                list.add(m);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean themMon(MonAn m) {

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO monan(ten_mon,gia,so_luong,trang_thai,mo_ta,ngay_tao) VALUES(?,?,?,?,?,NOW())";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, m.getTenMon());
            ps.setDouble(2, m.getGia());
            ps.setInt(3, m.getSoLuong());
            ps.setString(4, m.getTrangThai());
            ps.setString(5, m.getMoTa());

            ps.executeUpdate();

            conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean suaMon(MonAn m) {

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE monan SET ten_mon=?,gia=?,so_luong=?,mo_ta=?,trang_thai=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, m.getTenMon());
            ps.setDouble(2, m.getGia());
            ps.setInt(3, m.getSoLuong());
            ps.setString(4, m.getMoTa());
            ps.setString(5, m.getTrangThai());
            ps.setInt(6, m.getId());

            ps.executeUpdate();

            conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean xoaMon(int id, String trangThai) {

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE monan SET trang_thai=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, trangThai);
            ps.setInt(2, id);

            ps.executeUpdate();

            conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    @Override
    public MonAn timMonTheoId(int id) {

        MonAn mon = null;

        try {

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM MonAn WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                mon = new MonAn();

                mon.setId(rs.getInt("id"));
                mon.setTenMon(rs.getString("ten_mon"));
                mon.setGia(rs.getDouble("gia"));
                mon.setSoLuong(rs.getInt("so_luong"));
                mon.setMoTa(rs.getString("mo_ta"));
                mon.setTrangThai(rs.getString("trang_thai"));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return mon;
    }
}