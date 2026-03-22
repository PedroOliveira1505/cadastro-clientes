package com.meuprojeto.dao;

import com.meuprojeto.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserir(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes (nome, email, telefone, cpf, rua, numero, bairro, cidade, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getCpf());
            ps.setString(5, c.getRua());
            ps.setString(6, c.getNumero());
            ps.setString(7, c.getBairro());
            ps.setString(8, c.getCidade());
            ps.setString(9, converterData(c.getDataNascimento()));
            ps.executeUpdate();
        }
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nome";
        try (Connection con = Conexao.getConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Cliente> buscarPorNome(String nome) throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public void atualizar(Cliente c) throws SQLException {
        String sql = "UPDATE clientes SET nome=?, email=?, telefone=?, cpf=?, rua=?, numero=?, bairro=?, cidade=?, data_nascimento=? WHERE id=?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getCpf());
            ps.setString(5, c.getRua());
            ps.setString(6, c.getNumero());
            ps.setString(7, c.getBairro());
            ps.setString(8, c.getCidade());
            ps.setString(9, converterData(c.getDataNascimento()));
            ps.setInt(10, c.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setEmail(rs.getString("email"));
        c.setTelefone(rs.getString("telefone"));
        c.setCpf(rs.getString("cpf"));
        c.setRua(rs.getString("rua"));
        c.setNumero(rs.getString("numero"));
        c.setBairro(rs.getString("bairro"));
        c.setCidade(rs.getString("cidade"));

        // Converte de yyyy-MM-dd para dd/MM/yyyy ao carregar do banco
        String dataBanco = rs.getString("data_nascimento");
        if (dataBanco != null && dataBanco.length() == 10) {
            String[] partes = dataBanco.split("-");
            c.setDataNascimento(partes[2] + "/" + partes[1] + "/" + partes[0]);
        } else {
            c.setDataNascimento(dataBanco);
        }

        return c;
    }

    // Converte de dd/MM/yyyy para yyyy-MM-dd (formato do MySQL)
    private String converterData(String data) {
        if (data == null || data.length() < 8) return null;
        String nums = data.replaceAll("[^0-9]", "");
        if (nums.length() < 8) return null;
        String dd   = nums.substring(0, 2);
        String mm   = nums.substring(2, 4);
        String yyyy = nums.substring(4, 8);
        return yyyy + "-" + mm + "-" + dd;
    }
}