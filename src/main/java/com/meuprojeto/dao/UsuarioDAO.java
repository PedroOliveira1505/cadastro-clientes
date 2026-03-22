package com.meuprojeto.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;

public class UsuarioDAO {

    public void registrar(String usuario, String senha) throws Exception {
        String sql = "INSERT INTO usuarios (usuario, senha) VALUES (?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, hashSenha(senha));
            ps.executeUpdate();
        }
    }

    public boolean login(String usuario, String senha) throws Exception {
        String sql = "SELECT id FROM usuarios WHERE usuario = ? AND senha = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, hashSenha(senha));
            return ps.executeQuery().next();
        }
    }

    public boolean usuarioExiste(String usuario) throws Exception {
        String sql = "SELECT id FROM usuarios WHERE usuario = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            return ps.executeQuery().next();
        }
    }

    private String hashSenha(String senha) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(senha.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}