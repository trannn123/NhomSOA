package QLDTAN;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class HoaDonImpl implements IHoaDon {
	private static final URI URI_NGUOIDUNG = UriBuilder.fromUri("http://localhost:8080/NguoiDungService").build();
	private static final URI URI_MONAN = UriBuilder.fromUri("http://localhost:8080/MonAnService").build();
	
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target_NguoiDung = client.target(URI_NGUOIDUNG);
	WebTarget target_MonAn = client.target(URI_MONAN);
	@Override
	public int taoHoaDon(HoaDon hd) {
	    int id = -1;
	    try {
	    	Connection conn = DBConnection.getConnection();
	    	Response res = target_NguoiDung.path("rest")
                    .path("nguoidung")
                    .path("id")
                    .path(String.valueOf(hd.getNguoiDungId())) 
                    .request()
                    .get();
	    	if (res.getStatus() != 200) {
	    	    System.out.println("Người dùng không tồn tại");
	    	    return -1;
	    	}
	    	NguoiDung nd = res.readEntity(NguoiDung.class);
	    	if (nd == null) {
	            System.out.println("Người dùng không tồn tại, không tạo hóa đơn");
	            return -1;
	        }
	        String sql = "INSERT INTO hoadon(nguoi_dung_id, trang_thai, tong_tien, tong_so_luong) VALUES(?,?,?,?)";
	        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setInt(1, hd.getNguoiDungId());
	        ps.setString(2, hd.getTrangThai());
	        ps.setDouble(3, hd.getTongTien());
	        ps.setInt(4, hd.getTongSoLuong());
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
	    boolean result = false;
	    try {
	        Connection conn = DBConnection.getConnection();
	        String sqlHoaDon = "SELECT id FROM hoadon WHERE id=?";
	        try (PreparedStatement psHoaDon = conn.prepareStatement(sqlHoaDon)) {
	            psHoaDon.setInt(1, ct.getHoaDonId());
	            try (ResultSet rs = psHoaDon.executeQuery()) {
	                if (!rs.next()) {
	                    System.out.println("Hóa đơn không tồn tại, không thể thêm chi tiết");
	                    return false;
	                }
	            }
	        }
	        Response res = target_MonAn.path("rest")
                    .path("monan")
                    .path("TimMon")
                    .path(String.valueOf(ct.getMonAnId()))
                    .request()
                    .get();
	        if (res.getStatus() != 200) {
	            System.out.println("Món ăn không tồn tại");
	            return false;
	        }

	        MonAn mon = res.readEntity(MonAn.class);
	        if (mon == null) {
	            System.out.println("Món ăn không tồn tại, không thể thêm chi tiết");
	            return false;
	        }
	        Response resDecrease = target_MonAn.path("rest")
                    .path("monan")
                    .path("GiamSoLuong")
                    .path(String.valueOf(ct.getMonAnId()))
                    .path(String.valueOf(ct.getSoLuong()))
                    .request(MediaType.APPLICATION_JSON)
                    .get();
	        
	        if(resDecrease.getStatus() != 200) {
	        	System.out.println("Không thể giảm số lượng món ăn");
	        	return false;
	        }
	        
	        String sql = "INSERT INTO chitiethoadon(hoa_don_id, mon_an_id, so_luong, gia) VALUES(?,?,?,?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, ct.getHoaDonId());
	        ps.setInt(2, ct.getMonAnId());
	        ps.setInt(3, ct.getSoLuong());
	        ps.setDouble(4, mon.getGia());
	        result = ps.executeUpdate() > 0;
	        ps.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
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
                Timestamp ts = rs.getTimestamp("ngay_dat");

                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);

                MyDate md = new MyDate(
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR)
                );
                
                hd.setNgayDat(md);
                System.out.println("Ngay dat: " + hd.getNgayDat());
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
    
    @Override
    public List<HoaDon> layHoaDonTheoNguoiDung(int nguoiDungId) {
        List<HoaDon> ds = new ArrayList<>();
        try {
        	Response res = target_NguoiDung.path("rest")
        	        .path("nguoidung")
        	        .path("id")
        	        .path(String.valueOf(nguoiDungId))
        	        .request()
        	        .get();
        	if (res.getStatus() != 200) {
        	    System.out.println("Người dùng không tồn tại");
        	    return ds;
        	}
        	NguoiDung nd = res.readEntity(NguoiDung.class);
        	if (nd == null) {
                System.out.println("Người dùng không tồn tại, không lấy hóa đơn");
                return ds; 
            }
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM hoadon WHERE nguoi_dung_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, nguoiDungId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("id"));
                hd.setNguoiDungId(rs.getInt("nguoi_dung_id"));
                Timestamp ts = rs.getTimestamp("ngay_dat");

                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);

                MyDate md = new MyDate(
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR)
                );
                
                hd.setNgayDat(md);
                System.out.println("Ngay dat: " + hd.getNgayDat());
                hd.setTrangThai(rs.getString("trang_thai"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTongSoLuong(rs.getInt("tong_so_luong"));
                ds.add(hd);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}