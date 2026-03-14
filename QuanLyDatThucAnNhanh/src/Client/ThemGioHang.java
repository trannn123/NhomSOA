package Client;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import QLDTAN.GioHang;
import QLDTAN.MonAn;

/**
 * Servlet implementation class ThemGioHang
 */
@WebServlet("/ThemGioHang")
public class ThemGioHang extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/QuanLyDatThucAnNhanh/").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public ThemGioHang() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");

		if(idStr == null){
		    response.sendRedirect("DanhSachMonAn");
		    return;
		}
		int id = Integer.parseInt(idStr);
        MonAn mon = target
                .path("rest")
                .path("quanly")
                .path("TimMon")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<MonAn>() {});

        HttpSession session = request.getSession();

        List<GioHang> cart = (List<GioHang>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean tonTai = false;

        for (GioHang g : cart) {
            if (g.getMon().getId() == id) {
                g.setSoLuong(g.getSoLuong() + 1);
                tonTai = true;
                break;
            }
        }

        if (!tonTai) {
            GioHang gh = new GioHang(mon, 1);
            cart.add(gh);
        }

        session.setAttribute("cart", cart);

        response.sendRedirect("DanhSachMonAn");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
