/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.madufurini.loja;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dudaf
 */
public class DBConnection {
    private static final String URL = "jdbc:firebirdsql://localhost:3050/C:/firebird_databases/loja.fdb";
    private static final String USUARIO = "SYSDBA";
    private static final String SENHA = "masterkey";

    public static Connection conectar() {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC do Firebird não encontrado.");
            return null;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco Firebird.");
            return null;
        }
    }
}
