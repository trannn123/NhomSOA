package QLDTAN;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hoadon")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HoaDonWS {

    IHoaDon service = new HoaDonImpl();

    @POST
    @Path("/TaoHoaDon")
    public Response taoHoaDon(HoaDon hd) {
        int id = service.taoHoaDon(hd);
        if (id > 0) {
            return Response.ok(id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Tạo hóa đơn thất bại")
                .build();
    }

    @POST
    @Path("/ThemChiTietHoaDon")
    public Response themChiTietHoaDon(ChiTietHoaDon ct) {

        boolean kq = service.themChiTietHoaDon(ct);

        if (kq) {
            return Response.ok("Thêm chi tiết hóa đơn thành công").build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Thêm chi tiết thất bại")
                .build();
    }

    @GET
    @Path("/LayDanhSachHoaDon")
    public Response layDanhSachHoaDon() {
System.out.println("Lay danh sach hoa don");
        List<HoaDon> ds = service.layDanhSachHoaDon();

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/LayChiTietHoaDon/{id}")
    public Response layChiTietHoaDon(@PathParam("id") int id) {

        List<ChiTietHoaDon> ds = service.layChiTietHoaDon(id);

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("/LayHoaDonTheoNguoiDung/{nguoiDungId}")
    public Response layHoaDonTheoNguoiDung(@PathParam("nguoiDungId") int nguoiDungId) {

        List<HoaDon> ds = service.layHoaDonTheoNguoiDung(nguoiDungId);

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST)
                        .entity("Không lấy được hóa đơn theo người dùng")
                        .build();
    }
    
    @PUT
    @Path("/CapNhatSoLuong")
    @Consumes(MediaType.TEXT_PLAIN) 
    @Produces(MediaType.TEXT_PLAIN)
    public Response capNhatSoLuong(
            @QueryParam("chiTietHoaDonId") int idChiTietHoaDon,
            @QueryParam("monAnId") int monAnId,
            @QueryParam("soLuong") int soLuong) {

        boolean result = service.capNhatSoLuongMonAnTrongChiTietHoaDon(idChiTietHoaDon, monAnId, soLuong);

        if (result) {
            return Response.ok("Cập nhật thành công").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cập nhật thất bại hoặc vượt quá tồn kho")
                    .build();
        }
    }
}