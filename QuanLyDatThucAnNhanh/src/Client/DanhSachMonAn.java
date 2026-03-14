package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.MonAn;
import QLDTAN.NguoiDung;

@WebServlet("/DanhSachMonAn")
public class DanhSachMonAn extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Response res = target
                .path("rest")
                .path("quanly")
                .path("LayDanhSachMonCon")
                .request(MediaType.APPLICATION_JSON)
                .get();

        List<MonAn> ds = null;

        if (res.getStatus() == 200) {
            ds = res.readEntity(new GenericType<List<MonAn>>() {});
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Danh sách món ăn</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".card:hover{transform:translateY(-5px);box-shadow:0 10px 25px rgba(0,0,0,0.1);transition:0.2s}");
        out.println(".navbar-brand{color:#ff6b2c !important;}");
        out.println(".btn-warning{background:#ff6b2c;border:none;color:white;}");
        out.println(".btn-warning:hover{background:#ff5a14;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        /* NAVBAR */

        out.println("<nav class='navbar bg-white border-bottom mb-4'>");
        out.println("<div class='container'>");

        out.println("<span class='navbar-brand fw-bold'>Quản Lý Đặt Thức Ăn</span>");

        out.println("<div>");

        out.println("<a href='TrangChu' class='btn btn-danger btn-sm me-2'>");
        out.println("<i class='bi bi-house'></i>");
        out.println("</a>");

        if (nd != null) {
            out.println("<span class='me-3 text-danger'>Xin chào <b>" + nd.getHoTen() + "</b></span>");
        }

        out.println("<a href='DangXuat' class='btn btn-outline-danger btn-sm'>Đăng xuất</a>");
        out.println("<a href='XemGioHang' class='btn btn-outline-danger btn-sm'>Xem giỏ hàng</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        /* DANH SÁCH MÓN */

        out.println("<div class='container'>");

        out.println("<h3 class='text-center fw-bold mb-4'>Danh sách món ăn</h3>");

        if (ds != null && !ds.isEmpty()) {

            out.println("<div class='row g-4'>");

            for (MonAn m : ds) {

                out.println("<div class='col-md-4'>");

                out.println("<div class='card h-100 shadow-sm'>");

                out.println("<div class='card-body'>");

                out.println("<h5 class='card-title fw-bold'>" + m.getTenMon() + "</h5>");

                out.println("<p class='text-muted'>" + m.getMoTa() + "</p>");

                out.println("<p class='fw-bold text-danger'>Giá: " + m.getGia() + "</p>");

                out.println("<a href='ThemGioHang?id=" + m.getId() + "' class='btn btn-warning w-100'>");
                out.println("<i class='bi bi-cart-plus'></i> Thêm vào giỏ");
                out.println("</a>");

                out.println("</div>");
                out.println("</div>");

                out.println("</div>");
            }

            out.println("</div>");

        } else {

            out.println("<div class='alert alert-warning text-center'>Không có món ăn</div>");

        }

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}