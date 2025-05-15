# 🛍️ Projeto Loja Java com Firebird

Este projeto é uma **aplicação simples de uma loja** desenvolvida em **Java**, com o objetivo de **estudar e integrar o banco de dados Firebird**.
Desenvolvido por: Maria Eduarda e Maria Luiza

## 🎯 Objetivo

O principal objetivo deste projeto é **aprender e demonstrar o uso do Firebird** como sistema de gerenciamento de banco de dados (SGBD), no contexto da disciplina de **Banco de Dados**.

Ele serve como uma prática para:
- Criar e conectar um banco Firebird em Java
- Executar operações básicas com SQL (CRUD)
- Entender a estrutura de uma aplicação com persistência de dados

---

## 🛠️ Tecnologias Utilizadas

- **Java SE**
- **JDBC (Java Database Connectivity)**
- **Firebird SQL**
- **Jaybird** (driver JDBC para Firebird)
- **IDE utilizada**: Netbeans

---

## 🧱 Funcionalidades

- Cadastro de produtos
- Consulta de produtos
- Atualização de preços/quantidades
- Remoção de produtos
- Registro de vendas

---

## 🔌 Requisitos

- Java 8 ou superior
- Firebird instalado (versão recomendada: 3.0+)
- Driver **Jaybird** configurado no classpath
- Banco de dados Firebird criado (arquivo `.fdb`)

---

## 🚀 Como executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/MaduFurini/JavaFirebird.git
   
2. Configure a conexão com o banco Firebird no seu código, utilizando o driver JDBC (Jaybird):
```java
  String url = "jdbc:firebirdsql://localhost:3050/C:/caminho/para/seubanco.fdb";
  String user = "SYSDBA";
  String password = "masterkey";
```

3. Execute a aplicação!

## 🖼️ Imagens do Projeto

### Interface 
![image](https://github.com/user-attachments/assets/bb503578-93a3-415d-bc14-4e88d8f4b6ee)

### Registro de venda
![image](https://github.com/user-attachments/assets/d37a8628-b43b-418e-bbb5-3700dda37a38)
