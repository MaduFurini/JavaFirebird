/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.madufurini.loja.controller;

import br.madufurini.loja.dao.PedidoDAO;
import br.madufurini.loja.model.Cliente;
import br.madufurini.loja.model.ItensPedido;
import br.madufurini.loja.model.Pedido;
import br.madufurini.loja.model.Produto;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dudaf
 */
public class PedidoController {
    private PedidoDAO pedidoDAO;

    public PedidoController() {
        if (pedidoDAO == null){
            this.pedidoDAO = new PedidoDAO(); 
        }
    }
    
    public List<Pedido> index() {
        return pedidoDAO.index();
    }
    
    public boolean store(Pedido pedido) {
        return pedidoDAO.store(pedido);
    }
    
    public DefaultTableModel gerarTabela() {
        List<Pedido> pedidos = this.index();
        String[] colunas = {"Cliente", "Data", "Valor", "Produtos", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Pedido pedido : pedidos) {            
            Cliente cliente = pedido.getCliente(); 
            String clienteNome = cliente != null ? cliente.getNome() : "Desconhecido";

            String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(pedido.getData_pedido());

            StringBuilder produtosStr = new StringBuilder();
            double valorTotal = 0.0;

            for (ItensPedido item : pedido.getItens()) {
                Produto produto = item.getProduto();
                String nomeProduto = produto != null ? produto.getNome() : "Produto";
                double valorItem = item.getValor_unitario();
                valorTotal += valorItem;

                produtosStr.append(nomeProduto)
                           .append(" - Qtd: ")
                           .append(item.getQuantidade())
                           .append(" - R$")
                           .append(String.format("%.2f", valorItem))
                           .append("\n");
            }

            String produtosFormatado = produtosStr.toString().trim();

            modelo.addRow(new Object[] {
                clienteNome,
                dataFormatada,
                String.format("R$%.2f", valorTotal),
                produtosFormatado,
                pedido.getStatus()
            });
        }

        return modelo;
    }
    
    public boolean delete(Pedido pedido) {
        return pedidoDAO.delete(pedido);
    }
}
