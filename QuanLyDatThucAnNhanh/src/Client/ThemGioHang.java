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

@WebServlet("/ThemGioHang")
public class ThemGioHang extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final URI uri =
            UriBuilder.fromUri("http://localhost:8080/MonAnService").build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);

    public ThemGioHang() {
        super();
    }

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String from = request.getParameter("from");

        if (idStr == null || idStr.trim().isEmpty()) {
            if ("cart".equals(from)) {
                response.sendRedirect("XemGioHang");
            } else {
                response.sendRedirect("DanhSachMonAn");
            }
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            if ("cart".equals(from)) {
                response.sendRedirect("XemGioHang");
            } else {
                response.sendRedirect("DanhSachMonAn");
            }
            return;
        }

        MonAn mon = target
                .path("rest")
                .path("monan")
                .path("TimMon")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<MonAn>() {});

        if (mon == null) {
            if ("cart".equals(from)) {
                response.sendRedirect("XemGioHang");
            } else {
                response.sendRedirect("DanhSachMonAn");
            }
            return;
        }

        HttpSession session = request.getSession();
        List<GioHang> cart = (List<GioHang>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean tonTai = false;

        for (GioHang g : cart) {
            if (g.getMon() != null && g.getMon().getId() == id) {
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

        if ("cart".equals(from)) {
            response.sendRedirect("XemGioHang");
        } else {
            response.sendRedirect("DanhSachMonAn");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
