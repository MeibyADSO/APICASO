package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.*;

public class ClienteDaoJDBC {

    private static final String SQL_SELECT = "SELECT IdCliente, Nombre, Email,Telefono, Estado"
            + " FROM mibd.cliente";

    private static final String SQL_SELECT_BY_ID = "SELECT IdCliente, Nombre, Email,Telefono,Estado"
            + " FROM mibd.cliente WHERE IdCliente = ?";

    private static final String SQL_INSERT = "INSERT INTO mibd.cliente(Nombre, Email,Telefono,Estado) "
            + " VALUES(?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE mibd.cliente "
            + " SET Nombre=?, Email=? ,Telefono=?, Estado=? WHERE IdCliente=?";

    private static final String SQL_DELETE = "DELETE FROM mibd.cliente WHERE IdCliente = ?";

    public List<Cliente> listar() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("Nombre");
                String email = rs.getString("Email");
                String telefono = rs.getString("Telefono");
                 String estado = rs.getString("Estado");


                cliente = new Cliente(idCliente, nombre, email, telefono,estado);
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return clientes;
    }

    public Cliente encontrar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, cliente.getIdCliente());
            rs = stmt.executeQuery();
            rs.absolute(1);//nos posicionamos en el primer registro devuelto

            String nombre = rs.getString("Nombre");
            String email = rs.getString("Email");
            String telefono = rs.getString("Telefono");
            String estado = rs.getString("Estado");

            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setEstado(estado);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return cliente;
    }

    public int insertar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEstado());


            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int actualizar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEstado());

            stmt.setInt(5, cliente.getIdCliente());

            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int eliminar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, cliente.getIdCliente());

            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

}
