package Client;

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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.NguoiDung;

/**
 * Servlet implementation class TrangChu
 */
@WebServlet("/TrangChu")
public class TrangChu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private static final URI uri = UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();

	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(uri);

	public TrangChu() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			response.sendRedirect("DangNhap");
			return;
		}
		
		NguoiDung nd = (NguoiDung) session.getAttribute("user");

		String vaiTro = nd.getVaiTro(); // nhanvien hoặc khach

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");

		out.println("<meta charset='UTF-8'>");
		out.println("<title>Trang chủ</title>");

		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

		out.println("<style>");

		out.println("body{");
		out.println("background:#f8f9fa;");
		out.println("min-height:100vh;");
		out.println("font-family:system-ui;");
		out.println("}");

		

		out.println(".dashboard-card{");
		out.println("background:white;");
		out.println("border:1px solid #eee;");
		out.println("transition:all 0.25s ease;");
		out.println("border-radius:14px;");
		out.println("cursor:pointer;");
		out.println("}");

		out.println(".dashboard-card:hover{");
		out.println("transform:translateY(-6px);");
		out.println("box-shadow:0 10px 25px rgba(0,0,0,0.08);");
		out.println("border-color:#ff6b2c;");
		out.println("}");

		out.println(".icon-box{");
		out.println("font-size:34px;");
		out.println("color:#ff6b2c;");
		out.println("transition:0.2s;");
		out.println("}");

		out.println(".dashboard-card:hover .icon-box{");
		out.println("transform:scale(1.15);");
		out.println("}");
		
		out.println(".navbar-brand{color:#ff6b2c !important;}");
		out.println(".me-3{color:#ff6b2c !important;}");

		out.println(".btn-outline-danger{");
		out.println("border-color:#ff6b2c;");
		out.println("color:#ff6b2c;");
		out.println("}");

		out.println(".btn-outline-danger:hover{");
		out.println("background:#ff6b2c;");
		out.println("color:white;");
		out.println("}");

		out.println("</style>");

		out.println("</head>");

		out.println("<body>");

		/* NAVBAR */

		out.println("<nav class='navbar bg-white border-bottom'>");
		out.println("<div class='container'>");

		out.println("<span class='navbar-brand fw-bold text-danger'>Quản Lý Đặt Thức Ăn</span>");

		out.println("<div class='d-flex align-items-center'>");

		out.println("<a href='TrangChu' class='btn btn-danger btn-sm me-2'>");
		out.println("<i class='bi bi-house'></i>");
		out.println("</a>");

		out.println("<span class='me-3 text-danger'>Xin chào <b>" + nd.getHoTen() + "</b></span>");

		out.println("<a href='DangXuat' class='btn btn-outline-danger btn-sm'>Đăng xuất</a>");

		out.println("</div>");

		out.println("</div>");
		out.println("</nav>");

		/* CONTENT */

		out.println("<div class='container d-flex justify-content-center align-items-center' style='min-height:80vh;'>");

		out.println("<div class='bg-white p-5 rounded shadow' style='max-width:700px;width:100%;'>");

		out.println("<h4 class='text-center mb-4 fw-bold'>Trang chủ</h4>");

		out.println("<div class='row g-4'>");


		if("khachhang".equals(vaiTro)){

			// MENU
			out.println("<div class='col-md-6'>");
			out.println("<a href='DanhSachMonAn' class='text-decoration-none text-dark'>");

			out.println("<div class='dashboard-card border p-4 text-center'>");

			out.println("<div class='icon-box'><i class='bi bi-basket'></i></div>");
			out.println("<div class='mt-2 fw-semibold'>Đặt món</div>");

			out.println("</div>");

			out.println("</a>");
			out.println("</div>");


			// THÔNG TIN NGƯỜI DÙNG
			out.println("<div class='col-md-6'>");
			out.println("<a href='QuanLyNguoiDung' class='text-decoration-none text-dark'>");

			out.println("<div class='dashboard-card border p-4 text-center'>");

			out.println("<div class='icon-box'><i class='bi bi-person-circle'></i></div>");
			out.println("<div class='mt-2 fw-semibold'>Thông tin cá nhân</div>");

			out.println("</div>");

			out.println("</a>");
			out.println("</div>");

		}


		if("nhanvien".equals(vaiTro)){

			out.println("<div class='col-md-4'>");
			out.println("<a href='QuanLyNguoiDung' class='text-decoration-none text-dark'>");

			out.println("<div class='dashboard-card border p-4 text-center'>");

			out.println("<div class='icon-box'><i class='bi bi-people'></i></div>");
			out.println("<div class='mt-2 fw-semibold'>Thông tin cá nhân</div>");

			out.println("</div>");

			out.println("</a>");
			out.println("</div>");


			out.println("<div class='col-md-4'>");
			out.println("<a href='QuanLyHoaDon' class='text-decoration-none text-dark'>");

			out.println("<div class='dashboard-card border p-4 text-center'>");

			out.println("<div class='icon-box'><i class='bi bi-receipt'></i></div>");
			out.println("<div class='mt-2 fw-semibold'>Hóa đơn</div>");

			out.println("</div>");

			out.println("</a>");
			out.println("</div>");


			out.println("<div class='col-md-4'>");
			out.println("<a href='QuanLyMon' class='text-decoration-none text-dark'>");

			out.println("<div class='dashboard-card border p-4 text-center'>");

			out.println("<div class='icon-box'><i class='bi bi-egg-fried'></i></div>");
			out.println("<div class='mt-2 fw-semibold'>Món ăn</div>");

			out.println("</div>");

			out.println("</a>");
			out.println("</div>");
		}

		out.println("</div>");

		out.println("</div>");
		out.println("</div>");

		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
