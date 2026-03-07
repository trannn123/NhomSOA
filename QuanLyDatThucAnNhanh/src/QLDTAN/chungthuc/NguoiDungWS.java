package QLDTAN.chungthuc;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Path("/DangKy/{tenDangNhap}/{matKhau}/{hoTen}/{email}/{sdt}/{diaChi}")
    public Response dangKy(
            @PathParam("tenDangNhap") String tenDangNhap,
            @PathParam("matKhau") String matKhau,
            @PathParam("hoTen") String hoTen,
            @PathParam("email") String email,
            @PathParam("sdt") String sdt,
            @PathParam("diaChi") String diaChi) {

        NguoiDung nd = new NguoiDung();

        nd.setTenDangNhap(tenDangNhap);
        nd.setMatKhau(matKhau);
        nd.setHoTen(hoTen);
        nd.setEmail(email);
        nd.setSoDienThoai(sdt);
        nd.setDiaChi(diaChi);

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
    @Path("/CapNhat/{id}/{hoTen}/{email}/{sdt}/{diaChi}/{vaiTro}/{trangThai}")
    public Response capNhat(
            @PathParam("id") int id,
            @PathParam("hoTen") String hoTen,
            @PathParam("email") String email,
            @PathParam("sdt") String sdt,
            @PathParam("diaChi") String diaChi,
            @PathParam("vaiTro") String vaiTro,
            @PathParam("trangThai") String trangThai) {

        NguoiDung nd = new NguoiDung();

        nd.setId(id);
        nd.setHoTen(hoTen);
        nd.setEmail(email);
        nd.setSoDienThoai(sdt);
        nd.setDiaChi(diaChi);
        nd.setVaiTro(vaiTro);
        nd.setTrangThai(trangThai);

        boolean kq = service.capNhatNguoiDung(nd);

        if (kq) {
            return Response.ok("Cập nhật thành công").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cập nhật thất bại")
                    .build();
        }
    }
}
