package com.meuprojeto.view;

import com.meuprojeto.dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaRegistro extends JFrame {

    private static final Color COR_PRIMARIA = new Color(63, 81, 181);
    private static final Color COR_FUNDO    = new Color(245, 245, 250);
    private static final Color COR_LABEL    = new Color(90, 90, 120);

    private JTextField     txtUsuario;
    private JPasswordField txtSenha, txtConfirmar;
    private UsuarioDAO     dao = new UsuarioDAO();

    public TelaRegistro() {
        setTitle("Criar Conta");
        setSize(400, 520);
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

        JLabel titulo = new JLabel("Criar Conta", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Preencha os dados para se registrar", SwingConstants.CENTER);
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

        // Usuário
        JPanel grpUsuario = new JPanel(new BorderLayout(0, 4));
        grpUsuario.setOpaque(false);
        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setForeground(COR_LABEL);
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        grpUsuario.add(lblUsuario,  BorderLayout.NORTH);
        grpUsuario.add(txtUsuario,  BorderLayout.CENTER);

        // Senha
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

        // Confirmar senha
        JPanel grpConfirmar = new JPanel(new BorderLayout(0, 4));
        grpConfirmar.setOpaque(false);
        JLabel lblConfirmar = new JLabel("Confirmar senha");
        lblConfirmar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblConfirmar.setForeground(COR_LABEL);
        txtConfirmar = new JPasswordField();
        txtConfirmar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtConfirmar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        grpConfirmar.add(lblConfirmar, BorderLayout.NORTH);
        grpConfirmar.add(txtConfirmar, BorderLayout.CENTER);

        // Botão criar
        JButton btnCriar = new JButton("Criar conta");
        btnCriar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCriar.setBackground(COR_PRIMARIA);
        btnCriar.setForeground(Color.WHITE);
        btnCriar.setFocusPainted(false);
        btnCriar.setBorderPainted(false);
        btnCriar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCriar.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Botão voltar
        JButton btnVoltar = new JButton("Voltar ao login");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(COR_FUNDO);
        btnVoltar.setForeground(COR_PRIMARIA);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_PRIMARIA, 1),
                new EmptyBorder(8, 0, 8, 0)
        ));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        p.add(grpUsuario);
        p.add(grpSenha);
        p.add(grpConfirmar);
        p.add(btnCriar);
        p.add(btnVoltar);

        btnCriar.addActionListener(e  -> registrar());
        btnVoltar.addActionListener(e -> { dispose(); new TelaLogin(); });

        return p;
    }

    private JPanel criarRodape() {
        JPanel p = new JPanel();
        p.setBackground(COR_FUNDO);
        p.setBorder(new EmptyBorder(8, 0, 8, 0));
        JLabel label = new JLabel("Sistema de Cadastro de Clientes");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(new Color(160, 160, 180));
        p.add(label);
        return p;
    }

    private void registrar() {
        String usuario   = txtUsuario.getText().trim();
        String senha     = new String(txtSenha.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "A senha deve ter pelo menos 6 caracteres!", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!senha.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                    "As senhas não coincidem!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (dao.usuarioExiste(usuario)) {
                JOptionPane.showMessageDialog(this,
                        "Usuário já existe!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dao.registrar(usuario, senha);
            JOptionPane.showMessageDialog(this,
                    "Conta criada com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new TelaLogin();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}