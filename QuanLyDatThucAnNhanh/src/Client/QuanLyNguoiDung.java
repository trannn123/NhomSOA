package Client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import QLDTAN.NguoiDung;

@WebServlet("/QuanLyNguoiDung")
public class QuanLyNguoiDung extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("user") == null){
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý tài khoản</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");

        out.println("body{background:#f8f9fa;font-family:system-ui;}");

        out.println(".navbar-brand{color:#ff6b2c !important;}");
        out.println(".me-3{color:#ff6b2c !important;}");

        out.println(".btn-outline-danger{border-color:#ff6b2c;color:#ff6b2c;}");
        out.println(".btn-outline-danger:hover{background:#ff6b2c;color:white;}");

        out.println(".card-box{");
        out.println("background:white;");
        out.println("border-radius:14px;");
        out.println("border:1px solid #eee;");
        out.println("transition:all .25s;");
        out.println("cursor:pointer;");
        out.println("width:100%;");
        out.println("height:130px;");
        out.println("display:flex;");
        out.println("flex-direction:column;");
        out.println("justify-content:center;");
        out.println("align-items:center;");
        out.println("}");

        out.println(".card-box:hover{");
        out.println("transform:translateY(-5px);");
        out.println("box-shadow:0 10px 25px rgba(0,0,0,0.08);");
        out.println("border-color:#ff6b2c;");
        out.println("}");

        out.println(".icon-box{font-size:34px;color:#ff6b2c;}");

        out.println(".row .col-md-4{display:flex;}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        /* NAVBAR */

        out.println("<nav class='navbar bg-white border-bottom'>");
        out.println("<div class='container'>");

        out.println("<span class='navbar-brand fw-bold'>Quản Lý Đặt Thức Ăn</span>");

        out.println("<div class='d-flex align-items-center'>");

        out.println("<a href='TrangChu' class='btn btn-danger btn-sm me-2'>");
        out.println("<i class='bi bi-house'></i>");
        out.println("</a>");

        out.println("<span class='me-3'>Xin chào <b>"+ nd.getHoTen() +"</b></span>");

        out.println("<a href='DangXuat' class='btn btn-outline-danger btn-sm'>Đăng xuất</a>");

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        /* CONTENT */

        out.println("<div class='container d-flex justify-content-center align-items-center' style='min-height:80vh;'>");

        out.println("<div class='bg-white p-5 rounded shadow' style='max-width:650px;width:100%;'>");

        out.println("<h4 class='text-center mb-4 fw-bold'>Quản lý tài khoản</h4>");

        out.println("<div class='row g-4'>");

        /* XEM THÔNG TIN */

        out.println("<div class='col-md-4'>");
        out.println("<a href='XemThongTin' class='text-decoration-none text-dark d-block w-100'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-person'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Xem thông tin</div>");
        out.println("</div>");
        out.println("</a>");
        out.println("</div>");

        /* CHỈNH SỬA */

        out.println("<div class='col-md-4'>");
        out.println("<a href='ChinhSuaThongTin' class='text-decoration-none text-dark d-block w-100'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-pencil'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Chỉnh sửa</div>");
        out.println("</div>");
        out.println("</a>");
        out.println("</div>");

        /* ĐỔI MẬT KHẨU */

        out.println("<div class='col-md-4'>");
        out.println("<a href='DoiMatKhau' class='text-decoration-none text-dark d-block w-100'>");
        out.println("<div class='card-box'>");
        out.println("<div class='icon-box'><i class='bi bi-key'></i></div>");
        out.println("<div class='mt-2 fw-semibold'>Đổi mật khẩu</div>");
        out.println("</div>");
        out.println("</a>");
        out.println("</div>");

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