/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.madufurini.loja.dao;

import br.madufurini.loja.DBConnection;
import br.madufurini.loja.model.Cliente;
import br.madufurini.loja.model.ItensPedido;
import br.madufurini.loja.model.Pedido;
import br.madufurini.loja.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dudaf
 */
public class PedidoDAO {
    public List<Pedido> pedidos;
    
    public PedidoDAO() {
        if (pedidos == null) {
            pedidos = new ArrayList<>();
        }
    }
    
    public List<Pedido> index() {
        List<Pedido> pedidos = new ArrayList<>();
        Map<Integer, Pedido> pedidoMap = new HashMap<>(); // Para evitar pedidos duplicados

        String sql = "SELECT p.id AS pedido_id, p.data_pedido, p.status, " +
                     "c.id AS cliente_id, c.nome AS cliente_nome, " +
                     "i.id_produto, i.quantidade, i.valor_unitario AS valor_total_item, " +
                     "pr.nome AS produto_nome, pr.preco AS preco_unitario " +
                     "FROM pedidos p " +
                     "INNER JOIN clientes c ON c.id = p.id_cliente " +
                     "INNER JOIN itens_pedido i ON i.id_pedido = p.id " +
                     "INNER JOIN produtos pr ON pr.id = i.id_produto " +
                     "ORDER BY p.id";

        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int pedidoId = rs.getInt("pedido_id");

                Pedido pedido = pedidoMap.get(pedidoId);
                if (pedido == null) {
                    pedido = new Pedido();
                    pedido.setId(pedidoId);
                    pedido.setData_pedido(rs.getDate("data_pedido"));
                    pedido.setStatus(rs.getString("status"));

                    // Cliente
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cliente_id"));
                    cliente.setNome(rs.getString("cliente_nome"));
                    pedido.setId_cliente(cliente.getId());
                    pedido.setCliente(cliente);
                    
                    pedido.setItens(new ArrayList<>());
                    pedidoMap.put(pedidoId, pedido);
                }

                // ItemPedido
                ItensPedido item = new ItensPedido();
                item.setId_produto(rs.getInt("id_produto"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setValor_unitario(rs.getDouble("valor_total_item"));

                Produto produto = new Produto();
                produto.setId(rs.getInt("id_produto"));
                produto.setNome(rs.getString("produto_nome"));
                produto.setPreco(rs.getDouble("preco_unitario"));

                pedido.getItens().add(item);
            }

            pedidos.addAll(pedidoMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
    
    public boolean store(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (id_cliente, data_pedido, valor_total) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO itens_pedido (id_pedido, id_produto, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";

        PreparedStatement stmtPedido = null;
        PreparedStatement stmtItem = null;
        ResultSet generatedKeys = null;
        
        try (
            Connection conn = DBConnection.conectar();
        ) {
            stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setInt(1, pedido.getId_cliente());
            stmtPedido.setDate(2, new java.sql.Date(pedido.getData_pedido().getTime()));
            stmtPedido.setDouble(3, pedido.getValor_total());
            stmtPedido.executeUpdate();

                        generatedKeys = stmtPedido.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pedidoId = generatedKeys.getInt(1);

                stmtItem = conn.prepareStatement(sqlItem);
                for (ItensPedido item : pedido.getItens()) {
                    stmtItem.setInt(1, pedidoId);
                    stmtItem.setInt(2, item.getId_produto());
                    stmtItem.setInt(3, item.getQuantidade());
                    stmtItem.setDouble(4, item.getValor_unitario());
                    stmtItem.addBatch();
                }

                stmtItem.executeBatch();
            } else {
                throw new SQLException("Falha ao obter ID do pedido.");
            }
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(Pedido pedido) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (
            Connection conn = DBConnection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, pedido.getId()); // id na cláusula WHERE

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar pedido. " + e.getMessage());
            return false;
        }
    }
}
