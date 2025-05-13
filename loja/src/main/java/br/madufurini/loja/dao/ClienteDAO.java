/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.madufurini.loja.dao;

import br.madufurini.loja.DBConnection;
import br.madufurini.loja.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author dudaf
 */
public class ClienteDAO {
    public List<Cliente> clientes;
    
    public ClienteDAO() {
        if (clientes == null) {
            clientes = new ArrayList<>();
        }
    }
    
    public List<Cliente> index() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public boolean store(String nome, String telefone) {
        String sql = "INSERT INTO clientes(nome, telefone) VALUES (?, ?)";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nome);
            stmt.setString(2, telefone);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente. " + e.getMessage());
            return false;
        }
    }
    
    public boolean update(Cliente cliente, String nome, String telefone) {
        String sql = "UPDATE clientes SET nome = ?, telefone = ? WHERE id = ?";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nome != null ? nome : cliente.getNome());
            stmt.setString(2, telefone != null ? telefone : cliente.getTelefone());
            stmt.setInt(3, cliente.getId()); // id na cláusula WHERE

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente. " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(Cliente cliente) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, cliente.getId()); // id na cláusula WHERE

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente. " + e.getMessage());
            return false;
        }
    }
}