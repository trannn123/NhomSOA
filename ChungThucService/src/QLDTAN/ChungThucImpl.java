package QLDTAN;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class ChungThucImpl implements IChungThuc{
	private static final URI URI_NGUOIDUNG = UriBuilder.fromUri("http://localhost:8080/NguoiDungService").build();
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(URI_NGUOIDUNG);
	
	@Override
	public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
	    try {
	        NguoiDung nd = target
	        		.path("rest")
	        		.path("nguoidung")
	        		.path(tenDangNhap)
	        		.request(MediaType.APPLICATION_JSON)
	        		.get(NguoiDung.class);
	        
	        if(nd!=null) {
	        	String matKhauHash = PasswordUtil.hashPassword(matKhau);
	        	if(matKhauHash.equals(nd.getMatKhau()) && "hoatdong".equals(nd.getTrangThai())) {
	        		NguoiDung kq = new NguoiDung();
	                kq.setId(nd.getId());
	                kq.setTenDangNhap(nd.getTenDangNhap());
	                kq.setVaiTro(nd.getVaiTro());
	                kq.setTrangThai(nd.getTrangThai());
	                return kq;
	        	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	@Override
	public boolean kiemTraMatKhau(String tenDangNhap, String matKhau) {
		NguoiDung nd = dangNhap(tenDangNhap, matKhau);
	    return nd != null;
	}
	
	@Override
	public boolean doiMatKhau(String tenDangNhap, String matKhauMoi) {
	    try {
	    	NguoiDung nd = new NguoiDung();
	    	nd.setTenDangNhap(tenDangNhap);
	    	nd.setMatKhau(matKhauMoi);
	    	Response res = target
	        		.path("rest")
	        		.path("nguoidung")
	        		.path("DoiMatKhau")
	        		.request(MediaType.APPLICATION_JSON)
	        		.put(Entity.entity(nd, MediaType.APPLICATION_JSON));
	    	return res.getStatus() == 200;
	    	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}
