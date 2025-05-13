/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.madufurini.loja.controller;

import br.madufurini.loja.dao.ProdutoDAO;
import br.madufurini.loja.model.Produto;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dudaf
 */
public class ProdutoController {
    private ProdutoDAO produtoDAO;

    public ProdutoController() {
        if (produtoDAO == null){
            this.produtoDAO = new ProdutoDAO(); 
        }
    }
    
    public List<Produto> index() {
        return produtoDAO.index();
    }
    
    public boolean store(String nome, double preco, int estoque) {
        return produtoDAO.store(nome, preco, estoque);
    }
    
    public boolean update(Produto produto, String nome, double preco, int estoque) {
        System.out.println("teste");
        return produtoDAO.update(produto, nome, preco, estoque);
    }
    
    public boolean delete(Produto produto) {
        return produtoDAO.delete(produto);
    }
    
    public DefaultTableModel gerarTabela() {
        List<Produto> produtos = this.index();
        String[] colunas = {"Nome", "Preço", "Estoque"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Produto produto : produtos) {            
            modelo.addRow(new Object[]{
                produto.getNome(), 
                produto.getPreco(),
                produto.getEstoque()
            });
        }

        return modelo;
    }
}
