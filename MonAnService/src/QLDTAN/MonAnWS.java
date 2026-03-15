package QLDTAN;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/monan")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MonAnWS {

    IMonAn service = new MonAnImpl();
    @GET
    @Path("/LayDanhSachMonCon")
    public Response layDanhSachMonCon() {
        List<MonAn> kq = service.layDanhSachMonCon();
        return kq != null
                ? Response.ok(kq).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/LayDanhSachMonAn")
    public Response layDanhSachMonAn() {
        List<MonAn> kq = service.layDanhSachMon();
        return kq != null
                ? Response.ok(kq).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/ThemMon/{ten}/{gia}/{soluong}/{mota}")
    public Response themMon(@PathParam("ten") String ten, @PathParam("gia") double gia, @PathParam("soluong") int soluong, @PathParam("mota") String mota) {
    	MonAn mon = new MonAn();
        mon.setTenMon(ten);
        mon.setGia(gia);
        mon.setSoLuong(soluong);
        mon.setMoTa(mota);
        mon.setTrangThai("con");
        boolean kq = service.themMon(mon);
        return kq
                ? Response.ok("Thêm món thành công").build()
                : Response.status(Response.Status.BAD_REQUEST).entity("Thêm thất bại").build();
    }

    @POST
    @Path("/SuaMon/{id}/{ten}/{gia}/{soluong}/{mota}/{trangthai}")
    public Response suaMon(
            @PathParam("id") int id,
            @PathParam("ten") String ten,
            @PathParam("gia") double gia,
            @PathParam("soluong") int soluong,
            @PathParam("mota") String mota,
            @PathParam("trangthai") String trangthai) {

        MonAn mon = new MonAn();
        mon.setId(id);
        mon.setTenMon(ten);
        mon.setGia(gia);
        mon.setSoLuong(soluong);
        mon.setMoTa(mota);
        mon.setTrangThai(trangthai);
        boolean kq = service.suaMon(mon);
        return kq
                ? Response.ok("Sửa thành công").build()
                : Response.status(Response.Status.BAD_REQUEST).entity("Sửa thất bại").build();
    }
    
    @POST
    @Path("/XoaMon/{id}")
    public Response xoaMon(@PathParam("id") int id) {

        boolean kq = service.xoaMon(id);

        if (kq) {
            return Response.ok("Xóa thành công").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Xóa thất bại")
                    .build();
        }
    }
    
    @GET
    @Path("/TimMon/{id}")
    public Response timMon(@PathParam("id") int id) {

        MonAn mon = service.timMonTheoId(id);

        if(mon != null){
            return Response.ok(mon).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Không tìm thấy món")
                .build();
    }
    
   
    
    
}