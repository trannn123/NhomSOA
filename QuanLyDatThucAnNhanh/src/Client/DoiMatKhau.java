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
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("DangNhap");
			return;
		}

		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");

		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");

		out.println("</head>");
		out.println("<body>");

		out.println("<div class='container mt-5'>");

		out.println("<h3 class='mb-4'>Đổi mật khẩu</h3>");

		out.println("<form method='post'>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Mật khẩu cũ</label>");
		out.println("<input type='password' class='form-control' name='oldpass'>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Mật khẩu mới</label>");
		out.println("<input type='password' class='form-control' name='newpass'>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Nhập lại mật khẩu</label>");
		out.println("<input type='password' class='form-control' name='renewpass'>");
		out.println("</div>");

		out.println("<button class='btn btn-success'>Đổi mật khẩu</button>");
		out.println("<a href='XemThongTin' class='btn btn-secondary ms-2'>Hủy</a>");

		out.println("</form>");

		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

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