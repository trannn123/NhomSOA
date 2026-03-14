package QLDTAN;

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
	        String matKhauHash = PasswordUtil.hashPassword(matKhau);
	        ps.setString(2, matKhauHash);
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

	public boolean dangKy(NguoiDung nd) {
	    boolean kq = false;

	    try {
	        Connection conn = DBConnection.getConnection();

	        String sql = "INSERT INTO nguoidung(ten_dang_nhap, mat_khau, ho_ten, email, so_dien_thoai, dia_chi, vai_tro, trang_thai) VALUES(?,?,?,?,?,?,?,?)";

	        PreparedStatement ps = conn.prepareStatement(sql);

	        ps.setString(1, nd.getTenDangNhap());
	        String matKhauHash = PasswordUtil.hashPassword(nd.getMatKhau());
	        ps.setString(2, matKhauHash);
	        ps.setString(3, nd.getHoTen());
	        ps.setString(4, nd.getEmail());
	        ps.setString(5, nd.getSoDienThoai());
	        ps.setString(6, nd.getDiaChi());
	        ps.setString(7, "khachhang");
	        ps.setString(8, "hoatdong");

	        int n = ps.executeUpdate();

	        if (n > 0) kq = true;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return kq;
	}

	@Override
	public boolean capNhatNguoiDung(NguoiDung nd) {
		try {
	        Connection conn = DBConnection.getConnection();
	        String sql = "UPDATE nguoidung SET ho_ten=?, email=?, so_dien_thoai=?, dia_chi=? WHERE id=?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, nd.getHoTen());
	        ps.setString(2, nd.getEmail());
	        ps.setString(3, nd.getSoDienThoai());
	        ps.setString(4, nd.getDiaChi());
	        ps.setInt(5, nd.getId());
	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	@Override
	public boolean kiemTraMatKhau(String tenDangNhap, String matKhau) {

	    try {

	        Connection conn = DBConnection.getConnection();

	        String sql = "SELECT * FROM nguoidung WHERE ten_dang_nhap=? AND mat_khau=?";

	        PreparedStatement ps = conn.prepareStatement(sql);

	        ps.setString(1, tenDangNhap);
	        ps.setString(2, PasswordUtil.hashPassword(matKhau));

	        ResultSet rs = ps.executeQuery();

	        boolean tonTai = rs.next();

	        conn.close();

	        return tonTai;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	@Override
	public boolean doiMatKhau(String tenDangNhap, String matKhauMoi) {

	    try {

	        Connection conn = DBConnection.getConnection();

	        String sql = "UPDATE nguoidung SET mat_khau=? WHERE ten_dang_nhap=?";

	        PreparedStatement ps = conn.prepareStatement(sql);

	        ps.setString(1, PasswordUtil.hashPassword(matKhauMoi));
	        ps.setString(2, tenDangNhap);

	        boolean kq = ps.executeUpdate() > 0;

	        conn.close();

	        return kq;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	@Override
	public NguoiDung xemThongTin(String tenDangNhap) {

	    NguoiDung nd = null;

	    try {

	        Connection conn = DBConnection.getConnection();

	        String sql = "SELECT * FROM nguoidung WHERE ten_dang_nhap=?";

	        PreparedStatement ps = conn.prepareStatement(sql);

	        ps.setString(1, tenDangNhap);

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

	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return nd;
	}

}
