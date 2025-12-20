import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import com.google.gson.Gson;

public class GameServlet extends HttpServlet {

    private WorldState world = new WorldState();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(world));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        PlayerData player = gson.fromJson(req.getReader(), PlayerData.class);
        world.updatePlayer(player);
        resp.getWriter().write("{\"status\":\"ok\"}");
    }
}
