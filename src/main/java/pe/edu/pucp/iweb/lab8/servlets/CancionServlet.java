package pe.edu.pucp.iweb.lab8.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.pucp.iweb.lab8.beans.Cancion;
import pe.edu.pucp.iweb.lab8.dao.CancionDao;

@WebServlet(name = "CancionServlet", urlPatterns = {"/canciones"})
public class CancionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "nuevo":
                nuevo(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "crear":
                crear(request, response);
                break;
            case "actualizar":
                actualizar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/canciones");
                break;
        }
    }

    /** Ejercicio 2: listado. */
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: obtener las canciones (CancionDao), enviarlas a la vista,

        CancionDao cancionDao = new CancionDao();
        request.setAttribute("canciones", cancionDao.listar());

        request.getRequestDispatcher("listar.jsp").forward(request, response);


    }

    /** Ejercicio 2: formulario vacío. */
    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: enviar la lista de géneros (GeneroDao) a la vista
        CancionDao cancionDao = new CancionDao();

        request.getRequestDispatcher("form.jsp").forward(request, response);
    }

    /** Ejercicio 2: formulario cargado con los datos de la canción (parámetro id). */
    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: buscar la canción, enviar canción y géneros a la vista.

        int idCancion = Integer.parseInt(request.getParameter("id"));
        CancionDao cancionDao = new CancionDao();
        request.setAttribute("cancion", cancionDao.buscarPorId(idCancion));

        request.getRequestDispatcher("form.jsp").forward(request, response);
    }

    /** Ejercicio 2: POST crear. */
    private void crear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: leer parámetros, validar (título y artista no vacíos, duración > 0,
        //         año entre 1900 y 2026), insertar y redirigir al listado (Post/Redirect/Get).
        //         Si hay error: volver al formulario conservando lo escrito.

        CancionDao cancionDao = new CancionDao();
        Cancion cancion = new Cancion();
        cancion.setTitulo(request.getParameter("titulo"));
        cancion.setArtista(request.getParameter("artista"));
        cancion.setDuracionSeg(Integer.parseInt(request.getParameter("duracionSeg")));
        cancion.setAnio(Integer.parseInt(request.getParameter("anio")));
        cancion.setIdGenero(Integer.parseInt(request.getParameter("idGenero")));

        cancionDao.crear(cancion); // actualizamos la lista de canciones
        response.sendRedirect(request.getContextPath() + "/canciones");
    }

    /** Ejercicio 2: POST actualizar. */
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: igual que crear, pero con UPDATE
        CancionDao cancionDao = new CancionDao();
        Cancion cancion = new Cancion();
        cancion.setIdCancion(Integer.parseInt(request.getParameter("id")));
        cancion.setTitulo(request.getParameter("titulo"));
        cancion.setArtista(request.getParameter("artista"));
        cancion.setDuracionSeg(Integer.parseInt(request.getParameter("duracionSeg")));
        cancion.setAnio(Integer.parseInt(request.getParameter("anio")));
        cancion.setIdGenero(Integer.parseInt(request.getParameter("idGenero")));

        cancionDao.actualizar(cancion);
        response.sendRedirect(request.getContextPath() + "/canciones");
    }

    /** Ejercicio 3: POST eliminar. */
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: eliminar la canción y redirigir al listado
        CancionDao cancionDao = new CancionDao();
        cancionDao.eliminar(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect(request.getContextPath() + "/canciones");

    }
}
