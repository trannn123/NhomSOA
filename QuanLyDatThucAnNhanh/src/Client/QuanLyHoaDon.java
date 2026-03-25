package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import QLDTAN.HoaDon;
import QLDTAN.NguoiDung;
import QLDTAN.Util;

@WebServlet("/QuanLyHoaDon")
public class QuanLyHoaDon extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final URI URI_HOADON =
            UriBuilder.fromUri("http://localhost:8080/HoaDonService").build();

    Client client = ClientBuilder.newClient();
    WebTarget target_HoaDon = client.target(URI_HOADON);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        if (nd == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        List<HoaDon> dsHoaDon = target_HoaDon
                .path("rest")
                .path("hoadon")
                .path("LayDanhSachHoaDon")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<HoaDon>>() {});

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Quản lý hóa đơn</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-brand{color:#ff6b2c !important;font-weight:bold;}");
        out.println(".card{border:none;border-radius:16px;box-shadow:0 4px 12px rgba(0,0,0,0.08);}");
        out.println(".table thead{background:#ff6b2c;color:white;}");
        out.println(".page-title{font-weight:700;color:#333;}");
        out.println(".btn-orange{background:#ff6b2c;color:white;border:none;}");
        out.println(".btn-orange:hover{background:#e85d22;color:white;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='navbar bg-white border-bottom mb-4'>");
        out.println("<div class='container'>");
        out.println("<span class='navbar-brand'>Quản Lý Đặt Thức Ăn</span>");
        out.println("<div>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-house'></i> Trang chủ</a>");
        out.println("<a href='DangXuat' class='btn btn-danger btn-sm'><i class='bi bi-box-arrow-right'></i> Đăng xuất</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='card p-4'>");

        out.println("<h3 class='mb-4 text-center page-title'>");
        out.println("<i class='bi bi-receipt'></i> Quản lý hóa đơn");
        out.println("</h3>");

        out.println("<div class='table-responsive'>");
        out.println("<table class='table table-bordered table-hover align-middle text-center'>");

        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Mã</th>");
        out.println("<th>Ngày</th>");
        out.println("<th>Tổng SL</th>");
        out.println("<th>Tổng tiền</th>");
        out.println("<th>Trạng thái</th>");
        out.println("<th>Thao tác</th>");
        out.println("</tr>");
        out.println("</thead>");

        out.println("<tbody>");

        for (HoaDon hd : dsHoaDon) {
            out.println("<tr>");

            out.println("<td>#" + hd.getId() + "</td>");
            out.println("<td>" + hd.getNgayDat() + "</td>");
            out.println("<td>" + Util.getTongSoLuongCuaHoaDon(hd) + "</td>");
            out.println("<td class='text-danger fw-bold'>" + Util.getTongTienCuaHoaDon(hd) + "</td>");

            String trangThai = hd.getTrangThai().equals("thanh_cong")
                    ? "<span class='badge bg-success'>Thành công</span>"
                    : "<span class='badge bg-warning text-dark'>Đang xử lý</span>";

            out.println("<td>" + trangThai + "</td>");

            out.println("<td>");
            out.println("<a href='ChiTietHoaDonQuanLy?id=" + hd.getId() + "' class='btn btn-primary btn-sm me-1'><i class='bi bi-eye'></i></a>");
            out.println("<a href='SuaHoaDon?id=" + hd.getId() + "' class='btn btn-warning btn-sm me-1'><i class='bi bi-pencil'></i></a>");
            out.println("</td>");

            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}