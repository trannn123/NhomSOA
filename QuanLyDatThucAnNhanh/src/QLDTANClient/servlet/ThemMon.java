package QLDTANClient.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

/**
 * Servlet implementation class ThemMon
 */
@WebServlet("/ThemMon")
public class ThemMon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri = UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(uri);
    public ThemMon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ten = request.getParameter("ten");
		String gia = request.getParameter("gia");
		String soluong = request.getParameter("soluong");
		String mota = request.getParameter("mota");
		if (ten != null && gia != null && soluong != null && mota != null) {

			try {

				target
				.path("rest")
				.path("monan")
				.path("ThemMon")
				.path(ten)
				.path(gia)
				.path(soluong)
				.path(mota)
				.request()
				.post(null);

				response.sendRedirect("QuanLyMon");
				return;

			} catch (Exception e) {

				out.println("<h3 class='text-danger'>Thêm món thất bại</h3>");

			}
		}
		out.println("<html><head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Thêm món</title>");
		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
		out.println("</head>");

		out.println("<body class='container mt-4'>");

		out.println("<h2 class='text-success'>Thêm món ăn</h2>");

		out.println("<form>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Tên món</label>");
		out.println("<input type='text' class='form-control' name='ten' "
		        + "placeholder='Nhập tên món ăn (VD: Gà rán KFC)' required>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Giá</label>");
		out.println("<input type='number' step='0.01' min='0' class='form-control' name='gia' "
		        + "placeholder='Nhập giá món (VD: 45000.50)' required>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Số lượng</label>");
		out.println("<input type='number' min='1' class='form-control' name='soluong' "
		        + "placeholder='Nhập số lượng (VD: 10)' required>");
		out.println("</div>");

		out.println("<div class='mb-3'>");
		out.println("<label class='form-label'>Mô tả</label>");
		out.println("<textarea class='form-control' name='mota' rows='3' "
		        + "placeholder='Nhập mô tả món ăn (VD: Gà rán giòn, cay nhẹ)'></textarea>");
		out.println("</div>");

		out.println("<button class='btn btn-success'>Thêm</button>");
		out.println("<a href='QuanLyMon' class='btn btn-secondary'>Quay lại</a>");

		out.println("</form>");

		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
