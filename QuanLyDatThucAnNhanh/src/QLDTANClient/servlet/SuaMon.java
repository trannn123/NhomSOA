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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.monan.MonAn;

/**
 * Servlet implementation class SuaMon
 */
@WebServlet("/SuaMon")
public class SuaMon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
			UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();

	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(uri);
    public SuaMon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String id = request.getParameter("id");
		String ten = request.getParameter("ten");
		String gia = request.getParameter("gia");
		String soluong = request.getParameter("soluong");
		String mota = request.getParameter("mota");
		
		if (ten != null) {

			target.path("rest")
				  .path("monan")
				  .path("SuaMon")
				  .path(id)
				  .path(ten)
				  .path(gia)
				  .path(soluong)
				  .path(mota)
				  .request()
				  .post(null);

			response.sendRedirect("QuanLyMon");
			return;
		}
		
		MonAn mon = target.path("rest")
				.path("monan")
				.path("TimMon")
				.path(id)
				.request(MediaType.APPLICATION_JSON)
				.get(MonAn.class);

		out.println("<html><head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Sửa món</title>");
		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
		out.println("</head><body class='container mt-4'>");

		out.println("<h2 class='text-warning'>Sửa món</h2>");

		out.println("<form>");

		out.println("<input type='hidden' name='id' value='" + mon.getId() + "'>");

		out.println("Tên món");
		out.println("<input class='form-control' name='ten' value='" + mon.getTenMon() + "'>");

		out.println("Giá");
		out.println("<input class='form-control' name='gia' value='" + mon.getGia() + "'>");

		out.println("Số lượng");
		out.println("<input class='form-control' name='soluong' value='" + mon.getSoLuong() + "'>");

		out.println("Mô tả");
		out.println("<input class='form-control' name='mota' value='" + mon.getMoTa() + "'>");

		out.println("<br>");

		out.println("<button class='btn btn-warning'>Cập nhật</button>");
		out.println("<a href='QuanLyMon' class='btn btn-secondary'>Quay lại</a>");

		out.println("</form>");
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
