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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.chungthuc.NguoiDung;

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

		out.println("</head>");

		out.println("<body style='background:linear-gradient(135deg,#ff512f,#ffb347);min-height:100vh;'>");

		/* NAVBAR */

		out.println("<nav class='navbar navbar-dark bg-dark'>");
		out.println("<div class='container'>");

		out.println("<span class='navbar-brand'>QL Đặt Thức Ăn</span>");

		out.println("<div class='text-white'>");
		out.println(nd.getHoTen());
		out.println("<a href='DangXuat' class='btn btn-outline-light btn-sm ms-3'>Đăng xuất</a>");
		out.println("</div>");

		out.println("</div>");
		out.println("</nav>");

		/* CONTENT */

		out.println("<div class='container d-flex justify-content-center align-items-center' style='min-height:80vh;'>");

		out.println("<div class='bg-white rounded shadow p-5 text-center' style='max-width:700px;width:100%;'>");

		out.println("<h4 class='mb-4'>Trang hệ thống</h4>");

		out.println("<div class='row g-4'>");


		if("khachhang".equals(vaiTro)){
			
			out.println("<div class='col-12'>");
			out.println("<a href='DanhSachMon' class='text-decoration-none'>");
			out.println("<div class='border rounded p-4 hover'>");
			out.println("<i class='bi bi-basket fs-2'></i>");
			out.println("<p class='mt-2'>Đặt món</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
		}

		if("nhanvien".equals(vaiTro)){

			out.println("<div class='col-md-4'>");
			out.println("<a href='QuanLyNguoiDung' class='text-decoration-none text-dark'>");
			out.println("<div class='border rounded p-4'>");
			out.println("<i class='bi bi-people fs-2'></i>");
			out.println("<p class='mt-2'>Người dùng</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");

			out.println("<div class='col-md-4'>");
			out.println("<a href='QuanLyHoaDon' class='text-decoration-none text-dark'>");
			out.println("<div class='border rounded p-4'>");
			out.println("<i class='bi bi-receipt fs-2'></i>");
			out.println("<p class='mt-2'>Hóa đơn</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");

			out.println("<div class='col-md-4'>");
			out.println("<a href='QuanLyMon' class='text-decoration-none text-dark'>");
			out.println("<div class='border rounded p-4'>");
			out.println("<i class='bi bi-egg-fried fs-2'></i>");
			out.println("<p class='mt-2'>Món ăn</p>");
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
