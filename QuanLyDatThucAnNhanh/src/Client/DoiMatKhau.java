package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.NguoiDung;

@WebServlet("/DoiMatKhau")
public class DoiMatKhau extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh/").build();

    Client client = ClientBuilder.newClient(new ClientConfig());
    WebTarget target = client.target(uri);

    public DoiMatKhau() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Đổi mật khẩu</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-custom{background:#ffffff;box-shadow:0 2px 10px rgba(0,0,0,0.06);}");
        out.println(".navbar-brand{color:#ff6b2c !important;font-weight:700;font-size:1.3rem;}");
        out.println(".card-custom{border:none;border-radius:18px;box-shadow:0 4px 14px rgba(0,0,0,0.08);}");
        out.println(".page-title{font-weight:700;color:#333;}");
        out.println(".btn-orange{background:#ff6b2c;color:white;border:none;}");
        out.println(".btn-orange:hover{background:#e85d22;color:white;}");
        out.println(".form-label{font-weight:600;color:#444;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        // Navbar
        out.println("<nav class='navbar navbar-expand-lg navbar-custom mb-4'>");
        out.println("<div class='container'>");
        out.println("<a class='navbar-brand' href='TrangChu'><i class='bi bi-shop'></i> Quản Lý Đặt Thức Ăn</a>");
        out.println("<div class='d-flex'>");
        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm me-2'><i class='bi bi-house'></i> Trang chủ</a>");
        out.println("<a href='XemThongTin' class='btn btn-outline-primary btn-sm me-2'><i class='bi bi-person'></i> Thông tin cá nhân</a>");
        out.println("<a href='DangXuat' class='btn btn-danger btn-sm'><i class='bi bi-box-arrow-right'></i> Đăng xuất</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        // Content
        out.println("<div class='container'>");
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-8 col-lg-6'>");
        out.println("<div class='card card-custom p-4'>");

        out.println("<h3 class='mb-4 text-center page-title'><i class='bi bi-key'></i> Đổi mật khẩu</h3>");

        out.println("<form method='post'>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Mật khẩu cũ</label>");
        out.println("<input type='password' class='form-control' name='oldpass' required>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Mật khẩu mới</label>");
        out.println("<input type='password' class='form-control' name='newpass' required>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Nhập lại mật khẩu</label>");
        out.println("<input type='password' class='form-control' name='renewpass' required>");
        out.println("</div>");

        out.println("<div class='d-flex justify-content-center gap-2 mt-4'>");
        out.println("<button type='submit' class='btn btn-orange'><i class='bi bi-check-circle'></i> Đổi mật khẩu</button>");
        out.println("<a href='XemThongTin' class='btn btn-secondary'><i class='bi bi-x-circle'></i> Hủy</a>");
        out.println("</div>");

        out.println("</form>");

        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung user = (NguoiDung) session.getAttribute("user");

        String matKhauCu = request.getParameter("oldpass");
        String matKhauMoi = request.getParameter("newpass");
        String nhapLai = request.getParameter("renewpass");

        if (!matKhauMoi.equals(nhapLai)) {
            response.sendRedirect("DoiMatKhau");
            return;
        }

        String tenDangNhap = user.getTenDangNhap();

        try {
            String ketQua = target.path("rest")
                    .path("quanly")
                    .path("KiemTraMatKhau")
                    .path(tenDangNhap)
                    .path(matKhauCu)
                    .request(MediaType.APPLICATION_JSON)
                    .get(String.class);

            boolean hopLe = Boolean.parseBoolean(ketQua);

            if (!hopLe) {
                response.sendRedirect("DoiMatKhau");
                return;
            }

            target.path("rest")
                    .path("quanly")
                    .path("DoiMatKhau")
                    .path(tenDangNhap)
                    .path(matKhauMoi)
                    .request(MediaType.APPLICATION_JSON)
                    .post(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("XemThongTin");
    }
}
