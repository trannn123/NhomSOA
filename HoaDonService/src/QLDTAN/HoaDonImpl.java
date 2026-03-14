package QLDTAN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HoaDonImpl implements IHoaDon {

	public int taoHoaDon(HoaDon hd) {

	    int id = -1;

	    try {
	    	Connection conn = DBConnection.getConnection();

	        String sql = "INSERT INTO hoadon(nguoi_dung_id, ngay_dat, trang_thai, tong_tien, tong_so_luong) VALUES(?,?,?,?,?)";

	        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setInt(1, hd.getNguoiDungId());
	        ps.setString(2, hd.getNgayDat());
	        ps.setString(3, hd.getTrangThai());
	        ps.setDouble(4, hd.getTongTien());
	        ps.setInt(5, hd.getTongSoLuong());

	        ps.executeUpdate();

	        ResultSet rs = ps.getGeneratedKeys();

	        if (rs.next()) {
	            id = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return id;
	}

    @Override
    public boolean themChiTietHoaDon(ChiTietHoaDon ct) {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO chitiethoadon(hoa_don_id, mon_an_id, so_luong, gia) VALUES(?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getHoaDonId());
            ps.setInt(2, ct.getMonAnId());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getGia());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<HoaDon> layDanhSachHoaDon() {

        List<HoaDon> ds = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM hoadon";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                HoaDon hd = new HoaDon();

                hd.setId(rs.getInt("id"));
                hd.setNguoiDungId(rs.getInt("nguoi_dung_id"));
                hd.setNgayDat(rs.getString("ngay_dat"));
                hd.setTrangThai(rs.getString("trang_thai"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTongSoLuong(rs.getInt("tong_so_luong"));

                ds.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    @Override
    public List<ChiTietHoaDon> layChiTietHoaDon(int hoaDonId) {

        List<ChiTietHoaDon> ds = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM chitiethoadon WHERE hoa_don_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, hoaDonId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ChiTietHoaDon ct = new ChiTietHoaDon();

                ct.setId(rs.getInt("id"));
                ct.setHoaDonId(rs.getInt("hoa_don_id"));
                ct.setMonAnId(rs.getInt("mon_an_id"));
                ct.setSoLuong(rs.getInt("so_luong"));
                ct.setGia(rs.getDouble("gia"));

                ds.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
}