package QLDTAN;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
@Path("/nguoidung")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class NguoiDungWS {
	INguoiDung service = new NguoiDungImpl();
	
	@POST
	public Response dangKy(NguoiDung nd) {
	    boolean kq = service.dangKy(nd);
	    if (kq) {
	        return Response.ok("Đăng ký thành công").build();
	    } else {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("Đăng ký thất bại")
	                .build();
	    }
	}
	
	@PUT
    @Path("/CapNhat")
    public Response capNhat(NguoiDung nd) {
        boolean kq = service.capNhatNguoiDung(nd);
        if (kq) {
            return Response.ok("Cập nhật thành công").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cập nhật thất bại")
                    .build();
        }
    }
	
	@GET
	@Path("/{tenDangNhap}")
	public Response timThongTinTheoTenDangNhap(@PathParam("tenDangNhap") String tenDangNhap) {
	    NguoiDung nd = service.xemThongTin(tenDangNhap);
	    if (nd != null) {
	    	nd.setMatKhau(null);
	        return Response.ok(nd).build();
	    } else {
	        return Response.status(Response.Status.NOT_FOUND)
	                .entity("Không tìm thấy người dùng")
	                .build();
	    }
	}
	
	@PUT
	@Path("/DoiMatKhau")
	public Response doiMatKhau(NguoiDung nd) {
	    boolean kq = service.doiMatKhau(
	        nd.getTenDangNhap(),
	        nd.getMatKhau(),
	        nd.getMatKhauMoi()
	    );
	    if (kq) {
	        return Response.ok().build();
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED)
	        		.entity("Mật khẩu cũ không đúng")
	        		.build();
	    }
	}
	
	@DELETE
	@Path("/{tenDangNhap}")
	public Response xoa(@PathParam("tenDangNhap") String tenDangNhap) {
	    boolean kq = service.xoaNguoiDung(tenDangNhap);
	    if (kq) {
	        return Response.ok("Xóa thành công").build();
	    } else {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("Xóa thất bại")
	                .build();
	    }
	}
	
	@GET
	@Path("/id/{id}")
	public Response timThongTinTheoId(@PathParam("id") int id) {
	    NguoiDung nd = service.timTheoId(id);
	    if (nd != null) {
	    	nd.setMatKhau(null);
	        return Response.ok(nd).build();
	    }
	    return Response.status(Response.Status.NOT_FOUND)
	            .entity("Không tìm thấy người dùng")
	            .build();
	}
	
	@GET
	@Path("/auth/{tenDangNhap}")
	public Response chungThucNguoiDung(@PathParam("tenDangNhap") String tenDangNhap) {
	    NguoiDung nd = service.xemThongTin(tenDangNhap);
	    if (nd != null) {
	        return Response.ok(nd).build();
	    } else {
	        return Response.status(Response.Status.NOT_FOUND)
	                .entity("Không tìm thấy người dùng")
	                .build();
	    }
	}
}


