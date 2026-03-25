package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.HoaDon;
import QLDTAN.NguoiDung;
import QLDTAN.Util;

@WebServlet("/HoaDonDaDat")
public class HoaDonDaDat extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/HoaDonService").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);

    public HoaDonDaDat() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        if (nd == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        List<HoaDon> dsHoaDon = null;

        try {
            dsHoaDon = target
                    .path("rest")
                    .path("hoadon")
                    .path("LayHoaDonTheoNguoiDung")
                    .path(String.valueOf(nd.getId()))
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<HoaDon>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Hóa đơn đã đặt</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".card{border:none;border-radius:16px;box-shadow:0 4px 12px rgba(0,0,0,0.08);}");
        out.println(".table thead{background:#dc3545;color:white;}");
        out.println(".badge-trangthai{font-size:14px;padding:8px 12px;border-radius:20px;}");
        out.println(".page-title{font-weight:700;color:#333;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        // Navbar đồng bộ màu đỏ, bo góc giống các trang khác
        out.println("<nav class='navbar bg-white border-bottom shadow-sm mb-4'>");
        out.println("<div class='container'>");

        out.println("<span class='navbar-brand fw-bold text-danger'>Quản Lý Đặt Thức Ăn</span>");

        out.println("<div class='d-flex align-items-center'>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-house'></i> Trang chủ</a>");
        out.println("<a href='DanhSachMonAn' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-list'></i> Món ăn</a>");
        out.println("<a href='XemGioHang' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-cart'></i> Giỏ hàng</a>");
        
        out.println("<span class='me-3 text-danger'>Xin chào <b>" + nd.getHoTen() + "</b></span>");
        out.println("<a href='DangXuat' class='btn btn-danger btn-sm'><i class='bi bi-box-arrow-right'></i> Đăng xuất</a>");
        out.println("</div>");

        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='card p-4'>");
        out.println("<h3 class='mb-4 text-center page-title'><i class='bi bi-receipt-cutoff'></i> Hóa đơn đã đặt</h3>");

        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
            out.println("<div class='alert alert-warning text-center'>Bạn chưa có hóa đơn nào.</div>");
        } else {
            out.println("<div class='table-responsive'>");
            out.println("<table class='table table-bordered table-hover align-middle text-center'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Mã hóa đơn</th>");
            out.println("<th>Ngày đặt</th>");
            out.println("<th>Tổng số lượng</th>");
            out.println("<th>Tổng tiền</th>");
            out.println("<th>Trạng thái</th>");
            out.println("<th>Thao tác</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            System.out.println("Hoa don do dai" +  dsHoaDon.size());

            for (HoaDon hd : dsHoaDon) {
                out.println("<tr>");
                out.println("<td><b>#" + hd.getId() + "</b></td>");
                out.println("<td>" + hd.getNgayDat() + "</td>");
                out.println("<td>" + Util.getTongSoLuongCuaHoaDon(hd) + "</td>");
                out.println("<td class='text-danger fw-bold'>" + Util.getTongTienCuaHoaDon(hd) + "</td>");

                String trangThai = hd.getTrangThai();
                String trangThaiHienThi = trangThai;
                String mau = "bg-secondary";

                if ("dang_xu_ly".equalsIgnoreCase(trangThai)) {
                    mau = "bg-warning text-dark";
                    trangThaiHienThi = "Đang xử lý";
                } else if ("thanh_cong".equalsIgnoreCase(trangThai)) {
                    mau = "bg-success";
                    trangThaiHienThi = "Thành công";
                } else if ("huy".equalsIgnoreCase(trangThai)) {
                    mau = "bg-danger";
                    trangThaiHienThi = "Hủy";
                }

                out.println("<td><span class='badge " + mau + " badge-trangthai'>" + trangThaiHienThi + "</span></td>");
                out.println("<td>");
                out.println("<a href='ChiTietHoaDon?id=" + hd.getId() + "' class='btn btn-sm btn-primary'>");
                out.println("<i class='bi bi-eye'></i> Xem chi tiết");
                out.println("</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
        }

        out.println("<div class='text-center mt-3'>");
        out.println("<a href='DanhSachMonAn' class='btn btn-danger'>Tiếp tục đặt món</a>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
