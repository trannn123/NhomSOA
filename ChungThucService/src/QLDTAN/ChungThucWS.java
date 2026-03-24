package QLDTAN;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/chungthuc")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChungThucWS {
	IChungThuc service = new ChungThucImpl();
	
	@POST
	@Path("/DangNhap")
    public Response dangNhap(NguoiDung yc) {
        NguoiDung nd = service.dangNhap(yc.getTenDangNhap(), yc.getMatKhau());
        if (nd != null) {
            return Response.ok(nd).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Sai tài khoản hoặc mật khẩu")
                    .build();
        }
    }
	
	@POST
	@Path("/KiemTraMatKhau")
	public Response kiemTraMatKhau(NguoiDung yc) {
	    boolean kq = service.kiemTraMatKhau(yc.getTenDangNhap(), yc.getMatKhau());
	    return Response.ok(kq).build();
	}
	
	@PUT
	@Path("/DoiMatKhau")
	public Response doiMatKhau(NguoiDung yc) {
	    boolean kq = service.doiMatKhau(yc.getTenDangNhap(), yc.getMatKhau());
	    if (kq) {
	        return Response.ok("Đổi mật khẩu thành công").build();
	    } else {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("Đổi mật khẩu thất bại")
	                .build();
	    }
	}
}
