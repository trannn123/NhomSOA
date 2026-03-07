package QLDTANClient.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.monan.MonAn;

/**
 * Servlet implementation class QuanLyMon
 */
@WebServlet("/QuanLyMon")
public class QuanLyMon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public QuanLyMon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý món ăn</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body class='container mt-4'>");
        
        out.println("<h2 class='text-danger'>Quản lý món ăn</h2>");
        out.println("<a href='ThemMon' class='btn btn-success mb-3'>Thêm món</a>");
        out.println("<a href='TimMon' class='btn btn-primary mb-3'>Tìm món</a>");
        try {
        	List<MonAn> list = target
        			.path("rest")
        			.path("monan")
        			.path("LayDanhSachMonAn")
        			.request(MediaType.APPLICATION_JSON)
        			.get(new GenericType<List<MonAn>>() {});
        	
        	out.println("<table class='table table-bordered'>");
            out.println("<tr class='table-dark'>");
            out.println("<th>Mã</th>");
            out.println("<th>Tên món</th>");
            out.println("<th>Mô tả</th>");
            out.println("<th>Số lượng</th>");
            out.println("<th>Giá</th>");
            out.println("<th>Trạng thái</th>");
            out.println("<th>Thao tác</th>");
            out.println("</tr>");
            
            for (MonAn m : list) {

                out.println("<tr>");

                out.println("<td>" + m.getId() + "</td>");
                out.println("<td>" + m.getTenMon() + "</td>");
                out.println("<td>" + m.getMoTa() + "</td>");
                out.println("<td>" + m.getSoLuong() + "</td>");
                out.println("<td>" + m.getGia() + "</td>");
                out.println("<td>" + m.getTrangThai() + "</td>");
                out.println("<td>");

                out.println("<a class='btn btn-warning btn-sm' href='SuaMon?id="
                        + m.getId() + "'>Sửa</a> ");

                out.println("<a class='btn btn-danger btn-sm' onclick=\"return confirm('Bạn có chắc muốn xóa món này?')\" href='XoaMon?id="
                        + m.getId() + "'>Xóa</a>");

                out.println("</td>");

                out.println("</tr>");
            }
            out.println("</table>");
        }
        catch (Exception e) {
            out.println("<h4 class='text-danger'>Không lấy được danh sách món</h4>");

        }
        out.println("<a href='TrangChu' class='btn btn-secondary'>Quay lại</a>");
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
