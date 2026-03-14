package QLDTAN;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URLDecoder;
@Path("/quanly")
@Produces(MediaType.APPLICATION_JSON)
public class QuanLyWS {

    // Service chứng thực
    private static final URI uri1 =
            UriBuilder.fromUri("http://localhost:8080/ChungThucService").build();

    // Service món ăn
    private static final URI uri2 =
            UriBuilder.fromUri("http://localhost:8080/MonAnService").build();

    // Service hóa đơn
    private static final URI uri3 =
            UriBuilder.fromUri("http://localhost:8080/HoaDonService").build();

    
    Client client = ClientBuilder.newClient();

    WebTarget target1 = client.target(uri1);
    WebTarget target2 = client.target(uri2);
    WebTarget target3 = client.target(uri3);
    // =============================
    // ĐĂNG NHẬP
    // =============================
    @POST
    @Path("/dangnhap/{user}/{pass}")
    public NguoiDung dangNhap(
            @PathParam("user") String user,
            @PathParam("pass") String pass) {

        try {
            user = URLEncoder.encode(user, "UTF-8");
            pass = URLEncoder.encode(pass, "UTF-8");
        } catch (Exception e) {}

        return target1
                .path("rest")
                .path("nguoidung")
                .path("DangNhap")
                .path(user)
                .path(pass)
                .request(MediaType.APPLICATION_JSON)
                .post(null, NguoiDung.class);
    }

    // =============================
    // LẤY DANH SÁCH MÓN
    // =============================
    @GET
    @Path("/LayDanhSachMonAn")
    public List<MonAn> layDanhSachMon() {

        return target2
                .path("rest")
                .path("monan")
                .path("LayDanhSachMonAn")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<MonAn>>() {});
    }

    // =============================
    // THÊM MÓN
    // =============================
    @POST
    @Path("/ThemMon/{ten}/{gia}/{soluong}/{mota}")
    public String themMon(
            @PathParam("ten") String ten,
            @PathParam("gia") double gia,
            @PathParam("soluong") int soluong,
            @PathParam("mota") String mota) {

        return target2
                .path("rest")
                .path("monan")
                .path("ThemMon")
                .path(ten)
                .path(String.valueOf(gia))
                .path(String.valueOf(soluong))
                .path(mota)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""), String.class);
    }

    // =============================
    // SỬA MÓN
    // =============================
    @POST
    @Path("/SuaMon/{id}/{ten}/{gia}/{soluong}/{mota}/{trangthai}")
    public String suaMon(
            @PathParam("id") int id,
            @PathParam("ten") String ten,
            @PathParam("gia") double gia,
            @PathParam("soluong") int soluong,
            @PathParam("mota") String mota,
            @PathParam("trangthai") String trangthai) {

        return target2
                .path("rest")
                .path("monan")
                .path("SuaMon")
                .path(String.valueOf(id))
                .path(ten)
                .path(String.valueOf(gia))
                .path(String.valueOf(soluong))
                .path(mota)
                .path(trangthai)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""), String.class);
    }

    // =============================
    // XOÁ MÓN
    // =============================
    @POST
    @Path("/XoaMon/{id}")
    public String xoaMon(
            @PathParam("id") int id) {

        return target2
                .path("rest")
                .path("monan")
                .path("XoaMon")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""), String.class);
    }

    // =============================
    // TÌM MÓN
    // =============================
    @GET
    @Path("/TimMon/{id}")
    public MonAn timMon(@PathParam("id") int id) {

        return target2
                .path("rest")
                .path("monan")
                .path("TimMon")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(MonAn.class);
    }
    
    @GET
    @Path("/LayDanhSachMonCon")
    public List<MonAn> layDanhSachMonCon() {

        return target2
                .path("rest")
                .path("monan")
                .path("LayDanhSachMonCon")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<MonAn>>() {});
    }
    
    @POST
    @Path("/dangky")
    public String dangKy(NguoiDung nd) {

        return target1
                .path("rest")
                .path("nguoidung")
                .path("DangKy")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(nd, MediaType.APPLICATION_JSON), String.class);
    }
    
    @GET
    @Path("/ThongTinNguoiDung/{user}")
    public NguoiDung thongTinNguoiDung(@PathParam("user") String user) {

        return target1
                .path("rest")
                .path("nguoidung")
                .path("ThongTin")
                .path(user)
                .request(MediaType.APPLICATION_JSON)
                .get(NguoiDung.class);
    }
    
    @POST
    @Path("/DoiMatKhau/{user}/{passmoi}")
    public String doiMatKhau(
            @PathParam("user") String user,
            @PathParam("passmoi") String passmoi) {

        return target1
                .path("rest")
                .path("nguoidung")
                .path("DoiMatKhau")
                .path(user)
                .path(passmoi)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""), String.class);
    }
    
    @GET
    @Path("/KiemTraMatKhau/{user}/{pass}")
    public boolean kiemTraMatKhau(
            @PathParam("user") String user,
            @PathParam("pass") String pass) {

        String kq = target1
                .path("rest")
                .path("nguoidung")
                .path("KiemTraMatKhau")
                .path(user)
                .path(pass)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        return Boolean.parseBoolean(kq);
    }
    
    @POST
    @Path("/CapNhatThongTin/{id}/{hoten}/{email}/{sdt}/{diachi}")
    public String capNhatThongTin(
            @PathParam("id") int id,
            @PathParam("hoten") String hoten,
            @PathParam("email") String email,
            @PathParam("sdt") String sdt,
            @PathParam("diachi") String diachi) {

        try {

            hoten = URLEncoder.encode(hoten, "UTF-8");
            email = URLEncoder.encode(email, "UTF-8");
            sdt = URLEncoder.encode(sdt, "UTF-8");
            diachi = URLEncoder.encode(diachi, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return target1
                .path("rest")
                .path("nguoidung")
                .path("CapNhat")
                .path(String.valueOf(id))
                .path(hoten)
                .path(email)
                .path(sdt)
                .path(diachi)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""), String.class);
    }
    
    @POST
    @Path("/TaoHoaDon")
    public int taoHoaDon(HoaDon hd) {

        return target3
                .path("rest")
                .path("hoadon")
                .path("TaoHoaDon")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(hd, MediaType.APPLICATION_JSON), Integer.class);
    }
    
    @POST
    @Path("/ThemChiTietHoaDon")
    public String themChiTietHoaDon(ChiTietHoaDon ct) {

        return target3
                .path("rest")
                .path("hoadon")
                .path("ThemChiTietHoaDon")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(ct, MediaType.APPLICATION_JSON), String.class);
    }
}