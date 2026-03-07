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
 * Servlet implementation class TimMon
 */
@WebServlet("/TimMon")
public class TimMon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public TimMon() {
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

        out.println("<html><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body class='container mt-4'>");

        out.println("<h2 class='text-primary'>Tìm món</h2>");

        out.println("<form>");
        out.println("<input class='form-control mb-3' name='id' placeholder='Nhập ID món'>");
        out.println("<button class='btn btn-primary'>Tìm</button>");
        out.println("<a href='QuanLyMon' class='btn btn-secondary ms-2'>Quay lại</a>");
        out.println("</form>");

        if(id != null){

            try{

                MonAn m = target
                        .path("rest")
                        .path("monan")
                        .path("TimMon")
                        .path(id)
                        .request(MediaType.APPLICATION_JSON)
                        .get(MonAn.class);

                out.println("<hr>");
                out.println("<h4>Kết quả</h4>");

                out.println("<p>ID: " + m.getId() + "</p>");
                out.println("<p>Tên: " + m.getTenMon() + "</p>");
                out.println("<p>Mô tả: " + m.getMoTa() + "</p>");
                out.println("<p>Số lượng: " + m.getSoLuong() + "</p>");
                out.println("<p>Giá: " + m.getGia() + "</p>");

            }catch(Exception e){
                out.println("<h5 class='text-danger'>Không tìm thấy món</h5>");
            }
        }

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
