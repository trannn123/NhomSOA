package QLDTAN.chungthuc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import QLDTAN.DBConnection;

public class NguoiDungImpl implements INguoiDung {

	@Override
	public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
		NguoiDung nd = null;
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "SELECT * FROM nguoidung WHERE ten_dang_nhap=? AND mat_khau=? AND trang_thai='hoatdong'";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, tenDangNhap);
	        ps.setString(2, matKhau);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            nd = new NguoiDung();
	            nd.setId(rs.getInt("id"));
	            nd.setTenDangNhap(rs.getString("ten_dang_nhap"));
	            nd.setHoTen(rs.getString("ho_ten"));
	            nd.setEmail(rs.getString("email"));
	            nd.setSoDienThoai(rs.getString("so_dien_thoai"));
	            nd.setDiaChi(rs.getString("dia_chi"));
	            nd.setVaiTro(rs.getString("vai_tro"));
	            nd.setTrangThai(rs.getString("trang_thai"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return nd;
	}

	@Override
	public boolean dangKy(NguoiDung nd) {
		try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "INSERT INTO nguoidung(ten_dang_nhap,mat_khau,ho_ten,email,so_dien_thoai,dia_chi,vai_tro,trang_thai,ngay_tao) VALUES(?,?,?,?,?,?,?,?,NOW())";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, nd.getTenDangNhap());
	        ps.setString(2, nd.getMatKhau());
	        ps.setString(3, nd.getHoTen());
	        ps.setString(4, nd.getEmail());
	        ps.setString(5, nd.getSoDienThoai());
	        ps.setString(6, nd.getDiaChi());
	        ps.setString(7, "khachhang");
	        ps.setString(8, "hoatdong");
	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	@Override
	public boolean capNhatNguoiDung(NguoiDung nd) {
		try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "UPDATE nguoidung SET ho_ten=?, email=?, so_dien_thoai=?, dia_chi=?, vai_tro=?, trang_thai=? WHERE id=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, nd.getHoTen());
	        ps.setString(2, nd.getEmail());
	        ps.setString(3, nd.getSoDienThoai());
	        ps.setString(4, nd.getDiaChi());
	        ps.setString(5, nd.getVaiTro());
	        ps.setString(6, nd.getTrangThai());
	        ps.setInt(7, nd.getId());
	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
