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
			response.sendRedirect(request.getContextPath() + "/DangNhap");
			return;
		}
		
		NguoiDung nd = (NguoiDung) session.getAttribute("user");

		String vaiTro = nd.getVaiTro(); // nhanvien hoặc khach

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");

		out.println("<meta charset='UTF-8'>");

		out.println(
				"<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");

		out.println("<title>Trang chủ</title>");

		out.println("</head>");

		out.println("<body style='background:#f2f2f2;'>");

		out.println("<div class='container mt-5'>");

		out.println("<div class='card p-4 text-center'>");

		out.println("<h3 class='text-danger'>Trang hệ thống</h3>");

		out.println("<p>Xin chào <b>" + nd.getHoTen() + "</b></p>");

		out.println("<br>");

		// Khách + nhân viên đều xem món
		out.println("<a class='btn btn-primary m-2' href='DanhSachMon'>Xem món</a>");

		if ("nhanvien".equals(vaiTro)) {

			out.println("<a class='btn btn-success m-2' href='QuanLyNguoiDung'>Quản lý người dùng</a>");

			out.println("<a class='btn btn-warning m-2' href='QuanLyHoaDon'>Quản lý hóa đơn</a>");

			out.println("<a class='btn btn-info m-2' href='QuanLyMon'>Quản lý món</a>");

		}

		out.println("<br><br>");

		out.println("<a class='btn btn-danger' href='DangXuat'>Đăng xuất</a>");

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
