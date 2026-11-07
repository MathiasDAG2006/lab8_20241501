package pe.edu.pucp.iweb.lab8.dao;

import java.sql.*;
import java.util.ArrayList;

import org.eclipse.tags.shaded.org.apache.xalan.processor.WhitespaceInfoPaths;
import pe.edu.pucp.iweb.lab8.beans.Cancion;

public class CancionDao extends BaseDao { // TODO: heredar de BaseDao

    /**
     * Ejercicio 2: todas las canciones (con el nombre de su género, JOIN),
     * ordenadas por título. Use PreparedStatement.
     */
    public ArrayList<Cancion> listar() {
        // TODO
        ArrayList<Cancion> canciones = new ArrayList<>();

        String sql = "SELECT * FROM cancion JOIN genero ON cancion.idGenero = genero.idGenero ORDER BY titulo";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cancion cancion = new Cancion(rs.getInt("idCancion"), rs.getString("titulo"), rs.getString("artista"), rs.getInt("duracionSeg"), rs.getInt("anio"), rs.getInt("idGenero"));
                canciones.add(cancion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Ejercicio 2: la canción con ese id, o null si no existe.
     */
    public Cancion buscarPorId(int idCancion) {

        // TODO

        String sql = "SELECT * FROM cancion WHERE idCancion = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                Cancion cancion = new Cancion(rs.getInt("idCancion"), rs.getString("titulo"), rs.getString("artista"), rs.getInt("duracionSeg"), rs.getInt("anio"), rs.getInt("idGenero"));
                return cancion;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ejercicio 2: INSERT de una canción nueva (PreparedStatement).
     */
    public void crear(Cancion cancion) {
        // TODO

        String sql = "INSERT INTO cancion (titulo, artista, duracionSeg, anio, idGenero) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cancion.getTitulo());
            pstmt.setString(2, cancion.getArtista());
            pstmt.setInt(3, cancion.getDuracionSeg());
            pstmt.setInt(4, cancion.getAnio());
            pstmt.setInt(5, cancion.getIdGenero());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejercicio 2: UPDATE de una canción existente (PreparedStatement).
     */
    public void actualizar(Cancion cancion) {
        // TODO
        String sql = "UPDATE cancion SET titulo = ?, artista = ?, duracionSeg = ?, anio = ?, idGenero = ? WHERE idCancion = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cancion.getTitulo());
            pstmt.setString(2, cancion.getArtista());
            pstmt.setInt(3, cancion.getDuracionSeg());
            pstmt.setInt(4, cancion.getAnio());
            pstmt.setInt(5, cancion.getIdGenero());
            pstmt.setInt(6, cancion.getIdCancion());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejercicio 3: DELETE por id (PreparedStatement).
     */
    public void eliminar(int idCancion) {
        // TODO

        String sql = "DELETE FROM cancion WHERE idCancion = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCancion);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
