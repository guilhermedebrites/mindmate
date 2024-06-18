package dao;

import model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO extends DAO {
	public UsuarioDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Usuario user) {
		boolean status = false;
		try {
			String sql = "INSERT INTO usuarios (nome_completo, email, senha) "
					+ "VALUES ('" + user.getNomeCompleto() + "', '"
					+ user.getEmail() + "', '" + user.getSenha() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Usuario get(int id) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuarios WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
				usuario = new Usuario(rs.getInt("id"), rs.getString("nome_completo"), rs.getString("email"),rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}

	public ArrayList<Usuario> getAll(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuarios";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("nome_completo"), rs.getString("email"),rs.getString("senha"));
				lista.add(usuario);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return lista;
	}

	public Usuario getByEmail(String email) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuarios WHERE email="+ "'" + email + "'";
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
				usuario = new Usuario(rs.getInt("id"), rs.getString("nome_completo"), rs.getString("email"),rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	
	public boolean update(Usuario usuario) {
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        	String sql = "UPDATE usuarios SET nome_completo = " + "'"
			+ usuario.getNomeCompleto() + "'" + ", email = " + "'"
			+ usuario.getEmail() + "'" + ", senha = " + "'" 
			+ usuario.getSenha() + "'" + " WHERE id = " + "'"
			+ usuario.getId() + "'";
			st.executeUpdate(sql);
			return true;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	
    }

}