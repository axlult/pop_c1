import org.demoClasePooc125.dao.CursoDAO;
import org.demoClasePooc125.misc.Conexion;
import org.demoClasePooc125.model.Curso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;


import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CursoDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private CursoDAO cursoDAO;

    @Test
    public void testObtenCurso() throws SQLException {
        try (MockedStatic<Conexion> mockedConexion = mockStatic(Conexion.class)) {
            mockedConexion.when(Conexion::getConnection).thenReturn(mockConnection);

            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true, true, false);
            when(mockResultSet.getInt("id")).thenReturn(1, 2);
            when(mockResultSet.getString("nombre")).thenReturn("Matemáticas", "Historia");
            when(mockResultSet.getString("descripcion")).thenReturn("Álgebra", "Prehistoria");
            when(mockResultSet.getString("estado")).thenReturn("Activo", "Inactivo");

            List<Curso> cursos = cursoDAO.obtenCurso();

            assertEquals(2, cursos.size());

            Curso curso1 = cursos.get(0);
            assertEquals(1, curso1.getId());
            assertEquals("Matemáticas", curso1.getNombre());
            assertEquals("Álgebra", curso1.getDescripcion());
            assertEquals("Activo", curso1.getEstado());

            verify(mockResultSet, times(2)).next();
            verify(mockStatement).close();
        }
    }

    @Test
    public void testInsertarCurso() throws SQLException {
        try (MockedStatic<Conexion> mockedConexion = mockStatic(Conexion.class)) {
            mockedConexion.when(Conexion::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            Curso curso = new Curso(0, "Física", "Mecánica", "Activo");
            cursoDAO.insertarCurso(curso);

            verify(mockPreparedStatement).setString(1, "Física");
            verify(mockPreparedStatement).setString(2, "Mecánica");
            verify(mockPreparedStatement).setString(3, "Activo");
            verify(mockPreparedStatement).executeUpdate();
            verify(mockPreparedStatement).close();
        }
    }

    @Test
    public void testActualizarCurso() throws SQLException {
        try (MockedStatic<Conexion> mockedConexion = mockStatic(Conexion.class)) {
            mockedConexion.when(Conexion::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            Curso curso = new Curso(1, "Química", "Orgánica", "Inactivo");
            cursoDAO.actualizarCurso(curso);

            verify(mockPreparedStatement).setString(1, "Química");
            verify(mockPreparedStatement).setString(2, "Orgánica");
            verify(mockPreparedStatement).setString(3, "Inactivo");
            verify(mockPreparedStatement).setInt(4, 1);
            verify(mockPreparedStatement).executeUpdate();
            verify(mockPreparedStatement).close();
        }
    }

    @Test
    public void testEliminarCurso() throws SQLException {
        try (MockedStatic<Conexion> mockedConexion = mockStatic(Conexion.class)) {
            mockedConexion.when(Conexion::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            cursoDAO.eliminarCurso(1);

            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).executeUpdate();
            verify(mockPreparedStatement).close();
        }
    }

    @Test
    public void testSQLExceptionHandling() throws SQLException {
        try (MockedStatic<Conexion> mockedConexion = mockStatic(Conexion.class)) {
            mockedConexion.when(Conexion::getConnection).thenReturn(mockConnection);

            when(mockConnection.createStatement()).thenThrow(new SQLException("Error de prueba"));

            List<Curso> cursos = cursoDAO.obtenCurso();

            assertTrue(cursos.isEmpty());
        }
    }
}
