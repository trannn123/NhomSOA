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
    @Path("/DangNhap/{tenDangNhap}/{matKhau}")
    public Response dangNhap(
            @PathParam("tenDangNhap") String tenDangNhap,
            @PathParam("matKhau") String matKhau) {

        NguoiDung nd = service.dangNhap(tenDangNhap, matKhau);

        if (nd != null) {
            return Response.ok(nd).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sai tài khoản hoặc mật khẩu")
                    .build();
        }
    }
	
	@POST
	@Path("/DangKy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	
	@POST
    @Path("/CapNhat/{id}/{hoTen}/{email}/{sdt}/{diaChi}")
    public Response capNhat(
            @PathParam("id") int id,
            @PathParam("hoTen") String hoTen,
            @PathParam("email") String email,
            @PathParam("sdt") String sdt,
            @PathParam("diaChi") String diaChi) {

		
        NguoiDung nd = new NguoiDung();

        nd.setId(id);
        nd.setHoTen(hoTen);
        nd.setEmail(email);
        nd.setSoDienThoai(sdt);
        nd.setDiaChi(diaChi);

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
	@Path("/ThongTin/{tenDangNhap}")
	public Response thongTin(@PathParam("tenDangNhap") String tenDangNhap) {

	    NguoiDung nd = service.xemThongTin(tenDangNhap);

	    if (nd != null) {
	        return Response.ok(nd).build();
	    } else {
	        return Response.status(Response.Status.NOT_FOUND)
	                .entity("Không tìm thấy người dùng")
	                .build();
	    }
	}
	
	@GET
	@Path("/KiemTraMatKhau/{user}/{pass}")
	public Response kiemTraMatKhau(
	        @PathParam("user") String user,
	        @PathParam("pass") String pass) {

	    boolean kq = service.kiemTraMatKhau(user, pass);

	    return Response.ok(kq).build();
	}
	
	@POST
	@Path("/DoiMatKhau/{user}/{passmoi}")
	public Response doiMatKhau(
	        @PathParam("user") String user,
	        @PathParam("passmoi") String passmoi) {

	    boolean kq = service.doiMatKhau(user, passmoi);

	    if (kq) {
	        return Response.ok("Đổi mật khẩu thành công").build();
	    } else {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("Đổi mật khẩu thất bại")
	                .build();
	    }
	}
}


