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
import QLDTAN.MonAn;
import QLDTAN.NguoiDung;

@WebServlet("/ChiTietHoaDon")
public class ChiTietHoaDon extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh/").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);

    public ChiTietHoaDon() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        if (nd == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("HoaDonDaDat");
            return;
        }

        int hoaDonId = Integer.parseInt(idStr);
        List<QLDTAN.ChiTietHoaDon> dsChiTiet = null;

        try {
        	dsChiTiet = target
        	        .path("rest")
        	        .path("quanly")
        	        .path("LayChiTietHoaDon")
        	        .path(String.valueOf(hoaDonId))
        	        .request(MediaType.APPLICATION_JSON)
        	        .get(new GenericType<List<QLDTAN.ChiTietHoaDon>>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Chi tiết hóa đơn</title>");
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

        // Navbar
        out.println("<nav class='navbar bg-white border-bottom mb-4'>");
        out.println("<div class='container'>");
        out.println("<span class='navbar-brand'>Quản Lý Đặt Thức Ăn</span>");
        out.println("<div>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-house'></i> Trang chủ</a>");
        out.println("<a href='HoaDonDaDat' class='btn btn-outline-primary btn-sm me-2'><i class='bi bi-arrow-left'></i> Quay lại</a>");
        out.println("<a href='DangXuat' class='btn btn-danger btn-sm'><i class='bi bi-box-arrow-right'></i> Đăng xuất</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='card p-4'>");
        out.println("<h3 class='mb-4 text-center page-title'><i class='bi bi-receipt'></i> Chi tiết hóa đơn #" + hoaDonId + "</h3>");

        if (dsChiTiet == null || dsChiTiet.isEmpty()) {
            out.println("<div class='alert alert-warning text-center'>Hóa đơn này chưa có chi tiết.</div>");
        } else {
            out.println("<div class='table-responsive'>");
            out.println("<table class='table table-bordered table-hover align-middle text-center'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Tên món ăn</th>");
            out.println("<th>Số lượng</th>");
            out.println("<th>Giá</th>");
            out.println("<th>Thành tiền</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            double tong = 0;

            for (QLDTAN.ChiTietHoaDon ct : dsChiTiet) {
                double thanhTien = ct.getSoLuong() * ct.getGia();
                tong += thanhTien;

                String tenMon = "Không xác định";

                try {
                    MonAn mon = target
                            .path("rest")
                            .path("quanly")
                            .path("TimMon")
                            .path(String.valueOf(ct.getMonAnId()))
                            .request(MediaType.APPLICATION_JSON)
                            .get(MonAn.class);

                    if (mon != null) {
                        tenMon = mon.getTenMon();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                out.println("<tr>");
                out.println("<td>" + tenMon + "</td>");
                out.println("<td>" + ct.getSoLuong() + "</td>");
                out.println("<td class='text-primary fw-bold'>" + ct.getGia() + "</td>");
                out.println("<td class='text-danger fw-bold'>" + thanhTien + "</td>");
                out.println("</tr>");
            }

            out.println("<tr>");
            out.println("<td colspan='3' class='text-end fw-bold'>Tổng cộng</td>");
            out.println("<td class='text-danger fw-bold'>" + tong + "</td>");
            out.println("</tr>");

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
        }

        out.println("<div class='text-center mt-3'>");
        out.println("<a href='HoaDonDaDat' class='btn btn-orange'><i class='bi bi-arrow-left'></i> Quay lại danh sách hóa đơn</a>");
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
