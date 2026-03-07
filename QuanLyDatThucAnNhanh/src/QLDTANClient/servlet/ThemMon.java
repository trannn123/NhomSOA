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
		request.getRequestDispatcher("jsp/ThemMon.jsp")
		   .forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String ten = request.getParameter("ten");
		String gia = request.getParameter("gia");
		String soluong = request.getParameter("soluong");
		String mota = request.getParameter("mota");
		try {
			WebTarget resource = target
					.path("rest")
					.path("monan")
					.path("ThemMon")
					.path(ten)
					.path(gia)
					.path(soluong)
					.path(mota);
			String result = resource
					.request()
					.post(null, String.class);
			request.setAttribute("msg", result);
		} catch (Exception e) {
			request.setAttribute("msg", "Lỗi kết nối REST API");
			e.printStackTrace();
		}
		request.getRequestDispatcher("jsp/ThemMon.jsp")
			   .forward(request, response);
	}
}
