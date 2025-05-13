/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.madufurini.loja.dao;

import br.madufurini.loja.DBConnection;
import br.madufurini.loja.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dudaf
 */
public class ProdutoDAO {
    public List<Produto> produtos;
    
    public ProdutoDAO() {
        if (produtos == null) {
            produtos = new ArrayList<>();
        }
    }
    
    public List<Produto> index() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(Double.parseDouble(rs.getString("preco")));
                produto.setEstoque(Integer.parseInt(rs.getString("estoque")));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }
    
    public boolean store(String nome, double preco, int estoque) {
        String sql = "INSERT INTO produtos(nome, preco, estoque) VALUES (?, ?, ?)";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setInt(3, estoque);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto. " + e.getMessage());
            return false;
        }
    }
    
    public boolean update(Produto produto, String nome, double preco, int estoque) {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, estoque = ? WHERE id = ?";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nome != null ? nome : produto.getNome());
            stmt.setDouble(2, preco != 0 ? preco : produto.getPreco());
            stmt.setInt(3, estoque != 0 ? estoque : produto.getEstoque());
            stmt.setInt(4, produto.getId()); // id na cláusula WHERE

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente. " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(Produto produto) {
        String sql = "DELETE FROM prodtos WHERE id = ?";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, produto.getId()); // id na cláusula WHERE

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente. " + e.getMessage());
            return false;
        }
    }
}
