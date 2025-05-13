/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.madufurini.loja.controller;

import br.madufurini.loja.dao.ClienteDAO;
import br.madufurini.loja.model.Cliente;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dudaf
 */
public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        if (clienteDAO == null){
            this.clienteDAO = new ClienteDAO(); 
        }
    }
    
    public List<Cliente> index() {
        return clienteDAO.index();
    }
    
    public boolean store(String nome, String telefone) {
        return clienteDAO.store(nome, telefone);
    }
    
    public boolean update(Cliente cliente, String nome, String telefone) {
        return clienteDAO.update(cliente, nome, telefone);
    }
    
    public boolean delete(Cliente cliente) {
        return clienteDAO.delete(cliente);
    }
    
    public DefaultTableModel gerarTabela() {
        List<Cliente> clientes = this.index();
        String[] colunas = {"Nome", "Telefone"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Cliente cliente : clientes) {            
            modelo.addRow(new Object[]{
                cliente.getNome(), 
                cliente.getTelefone(),
            });
        }

        return modelo;
    }
}
