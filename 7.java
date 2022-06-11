
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ConexaoUtil;
import ClienteDAO;

public class ClienteDAO implements GenericoDAO<ClienteDAO> {

	@Override
	public void inserir(ClienteDAO clienteDAO) throws PersistenciaExcpetion {
		try {
			Connection connection = ConexaoUtil.getInstance().getConnection();
			String sql = "INSERT INTO CLIENTE(ID_CLIENTE, NOME_CLIENTE, CPF_CLIENTE) " +
					"VALUES(?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, clienteDAO.getIdCliente());
			statement.setString(2, clienteDAO.getNome());
			statement.setLong(3, clienteDAO.getCpf());
			
			statement.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaExcpetion(e.getMessage(), e);
		}
	}
  
  @Override
	public List<ClienteDAO> listarTodos() throws PersistenciaExcpetion {
		List<ClienteDAO> listaClientes = new ArrayList<ClienteDAO>();
		try {
			Connection connection = ConexaoUtil.getInstance().getConnection();
			
			String sql = "SELECT * FROM CLIENTE";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				ClienteDAO clienteDAO = new ClienteDAO();
				ClienteDAO.setIdCliente(resultSet.getInt("ID_CLIENTE"));
				ClienteDAO.setNome(resultSet.getString("NOME_CLIENTE"));
				ClienteDAO.setCpf(resultSet.getLong("CPF_CLIENTE"));
				
				listaClientes.add(clienteDAO);
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaExcpetion(e.getMessage(), e);
		}
		return listaClientes;
	}

	@Override
	public void atualizar(ClienteDAO clienteDAO) throws PersistenciaExcpetion {
		try {
			Connection connection = ConexaoUtil.getInstance().getConnection();
			
			String sql = "UPDATE CLIENTE " +
					" SET NOME_CLIENTE = ?, " +
					" CPF_CLIENTE = ?" 
					" WHERE ID_CLIENTE = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, clienteDAO.getNome());
			statement.setLong(2, clienteDAO.getCpf());
			statement.setInt(3, clienteDAO.getIdCliente());
			
			statement.execute();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
			throw new PersistenciaExcpetion(e.getMessage(), e);
		}
	}

	@Override
	public void deletar(Integer IdCliente) throws PersistenciaExcpetion {
		try {
			Connection connection = ConexaoUtil.getInstance().getConnection();
			
			String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, IdCliente);
			
			statement.execute();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
			throw new PersistenciaExcpetion(e.getMessage(), e);
		}
	}

}
