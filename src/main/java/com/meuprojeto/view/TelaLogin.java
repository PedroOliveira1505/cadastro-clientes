package com.meuprojeto.view;

import com.meuprojeto.dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaLogin extends JFrame {

    private static final Color COR_PRIMARIA = new Color(63, 81, 181);
    private static final Color COR_FUNDO    = new Color(245, 245, 250);
    private static final Color COR_LABEL    = new Color(90, 90, 120);
    private static final Color COR_TEXTO    = new Color(33, 33, 33);

    private JTextField     txtUsuario;
    private JPasswordField txtSenha;
    private UsuarioDAO     dao = new UsuarioDAO();

    public TelaLogin() {
        setTitle("Login — Cadastro de Clientes");
        setSize(400, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout());

        add(criarCabecalho(),  BorderLayout.NORTH);
        add(criarFormulario(), BorderLayout.CENTER);
        add(criarRodape(),     BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel criarCabecalho() {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(COR_PRIMARIA);
        p.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Bem-vindo!", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Faça login para continuar", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(200, 210, 255));

        p.add(titulo);
        p.add(sub);
        return p;
    }

    private JPanel criarFormulario() {
        JPanel p = new JPanel(new GridLayout(5, 1, 0, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(24, 30, 24, 30));

        JPanel grpUsuario = new JPanel(new BorderLayout(0, 4));
        grpUsuario.setOpaque(false);
        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setForeground(COR_LABEL);
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuario.setForeground(COR_TEXTO);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        grpUsuario.add(lblUsuario, BorderLayout.NORTH);
        grpUsuario.add(txtUsuario, BorderLayout.CENTER);

        JPanel grpSenha = new JPanel(new BorderLayout(0, 4));
        grpSenha.setOpaque(false);
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSenha.setForeground(COR_LABEL);
        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        grpSenha.add(lblSenha, BorderLayout.NORTH);
        grpSenha.add(txtSenha, BorderLayout.CENTER);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEntrar.setBackground(COR_PRIMARIA);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel separador = new JLabel("Não tem conta?", SwingConstants.CENTER);
        separador.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        separador.setForeground(COR_LABEL);

        JButton btnRegistrar = new JButton("Criar conta");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistrar.setBackground(new Color(245, 245, 250));
        btnRegistrar.setForeground(COR_PRIMARIA);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_PRIMARIA, 1),
                new EmptyBorder(8, 0, 8, 0)
        ));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        p.add(grpUsuario);
        p.add(grpSenha);
        p.add(btnEntrar);
        p.add(separador);
        p.add(btnRegistrar);

        btnEntrar.addActionListener(e -> entrar());
        btnRegistrar.addActionListener(e -> { dispose(); new TelaRegistro(); });
        txtSenha.addActionListener(e -> entrar());

        return p;
    }

    private JPanel criarRodape() {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(COR_FUNDO);
        p.setBorder(new EmptyBorder(8, 0, 12, 0));

        JLabel label = new JLabel("Sistema de Cadastro de Clientes", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(new Color(160, 160, 180));

        JLabel autor = new JLabel("Feito por Pedro Oliveira", SwingConstants.CENTER);
        autor.setFont(new Font("Segoe UI", Font.BOLD, 11));
        autor.setForeground(COR_PRIMARIA);

        p.add(label);
        p.add(autor);
        return p;
    }

    private void entrar() {
        String usuario = txtUsuario.getText().trim();
        String senha   = new String(txtSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha usuário e senha!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (dao.login(usuario, senha)) {
                dispose();
                new TelaPrincipal();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuário ou senha incorretos!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}