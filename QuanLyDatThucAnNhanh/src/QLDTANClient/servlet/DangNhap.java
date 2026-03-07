package QLDTANClient.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.chungthuc.INguoiDung;
import QLDTAN.chungthuc.NguoiDung;
import QLDTAN.chungthuc.NguoiDungImpl;

/**
 * Servlet implementation class DangNhap
 */
@WebServlet("/DangNhap")
public class DangNhap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public DangNhap() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");
        // Nếu người dùng đã nhập tài khoản
        if (tenDangNhap != null && matKhau != null) {

            try {

                NguoiDung nd = target
                        .path("rest")
                        .path("nguoidung")
                        .path("DangNhap")
                        .path(tenDangNhap)
                        .path(matKhau)
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(""), NguoiDung.class);

                if (nd != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", nd);
                    response.sendRedirect("TrangChu");
                    return;
                }

            } catch (Exception e) {

                out.println("<h3>Sai tài khoản hoặc mật khẩu</h3>");

            }

        }

        // Hiển thị form đăng nhập
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");

        out.println("<title>Đăng nhập</title>");

        out.println("</head>");

        out.println("<body style='background:linear-gradient(135deg,#ff512f,#ffb347);height:100vh;display:flex;align-items:center;justify-content:center;'>");

        out.println("<div class='card p-4' style='width:400px;'>");

        out.println("<h3 class='text-center text-danger'>Đăng nhập hệ thống</h3>");

        out.println("<form method='get'>");

        out.println("<div class='mb-3'>");
        out.println("<label>Tên đăng nhập</label>");
        out.println("<input name='tenDangNhap' class='form-control'>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label>Mật khẩu</label>");
        out.println("<input type='password' name='matKhau' class='form-control'>");
        out.println("</div>");

        out.println("<button class='btn btn-danger w-100'>Đăng nhập</button>");

        out.println("</form>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
