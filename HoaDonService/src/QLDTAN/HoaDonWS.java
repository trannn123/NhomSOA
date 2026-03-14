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

    // Tạo hóa đơn
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

    // Thêm chi tiết hóa đơn
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

    // Lấy danh sách hóa đơn
    @GET
    @Path("/LayDanhSachHoaDon")
    public Response layDanhSachHoaDon() {

        List<HoaDon> ds = service.layDanhSachHoaDon();

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Lấy chi tiết hóa đơn
    @GET
    @Path("/LayChiTietHoaDon/{id}")
    public Response layChiTietHoaDon(@PathParam("id") int id) {

        List<ChiTietHoaDon> ds = service.layChiTietHoaDon(id);

        return ds != null
                ? Response.ok(ds).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }
}