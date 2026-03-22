package com.meuprojeto.view;

import com.meuprojeto.dao.ClienteDAO;
import com.meuprojeto.model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    // Paleta de cores
    private static final Color COR_FUNDO        = new Color(245, 245, 250);
    private static final Color COR_PAINEL        = Color.WHITE;
    private static final Color COR_PRIMARIA      = new Color(63, 81, 181);
    private static final Color COR_PRIMARIA_DARK = new Color(40, 53, 147);
    private static final Color COR_DELETAR       = new Color(211, 47, 47);
    private static final Color COR_TEXTO         = new Color(33, 33, 33);
    private static final Color COR_LABEL         = new Color(90, 90, 120);
    private static final Color COR_TABELA_PAR    = new Color(232, 234, 246);
    private static final Color COR_HEADER        = new Color(63, 81, 181);

    private JTextField txtNome, txtEmail, txtTelefone, txtCpf;
    private JTextField txtRua, txtNumero, txtBairro, txtCidade;
    private JTextField txtDataNasc, txtBusca;
    private JTable tabela;
    private DefaultTableModel modelo;
    private ClienteDAO dao = new ClienteDAO();
    private int idSelecionado = -1;

    public TelaPrincipal() {
        setTitle("Cadastro de Clientes");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(12, 12));

        add(criarTitulo(),    BorderLayout.NORTH);
        add(criarFormulario(), BorderLayout.WEST);
        add(criarPainelTabela(), BorderLayout.CENTER);

        carregarTabela();
        setVisible(true);
    }

    // ── Título no topo ──────────────────────────────────────────
    private JPanel criarTitulo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COR_PRIMARIA);
        p.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel titulo = new JLabel("Cadastro de Clientes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Gerencie seus clientes");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(200, 210, 255));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(sub);
        p.add(textos, BorderLayout.WEST);
        return p;
    }

    // ── Formulário lado esquerdo ─────────────────────────────────
    private JPanel criarFormulario() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(new EmptyBorder(10, 12, 10, 6));
        painel.setPreferredSize(new Dimension(340, 0));

        // Campos
        JPanel campos = new JPanel(new GridLayout(10, 1, 0, 8));
        campos.setBackground(COR_PAINEL);
        campos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 235), 1),
                new EmptyBorder(16, 16, 16, 16)
        ));

        txtNome      = criarCampo(campos, "Nome completo");
        txtEmail     = criarCampo(campos, "Email");
        txtTelefone  = criarCampo(campos, "Telefone");
        txtCpf       = criarCampo(campos, "CPF");
        txtRua       = criarCampo(campos, "Rua");
        txtNumero    = criarCampo(campos, "Número");
        txtBairro    = criarCampo(campos, "Bairro");
        txtCidade    = criarCampo(campos, "Cidade");
        txtDataNasc  = criarCampo(campos, "Data Nasc. (dd/mm/aaaa)");

        // Aplicar filtros
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new CapitalizarFilter());
        ((AbstractDocument) txtRua.getDocument()).setDocumentFilter(new CapitalizarFilter());
        ((AbstractDocument) txtBairro.getDocument()).setDocumentFilter(new CapitalizarFilter());
        ((AbstractDocument) txtCidade.getDocument()).setDocumentFilter(new CapitalizarFilter());
        ((AbstractDocument) txtCpf.getDocument()).setDocumentFilter(new MascaraCPF());
        ((AbstractDocument) txtDataNasc.getDocument()).setDocumentFilter(new MascaraData());

        // Botões
        JPanel botoes = new JPanel(new GridLayout(2, 2, 8, 8));
        botoes.setOpaque(false);
        botoes.setBorder(new EmptyBorder(4, 0, 0, 0));

        JButton btnSalvar    = criarBotao("Salvar",     COR_PRIMARIA,      Color.WHITE);
        JButton btnAtualizar = criarBotao("Atualizar",  COR_PRIMARIA_DARK, Color.WHITE);
        JButton btnDeletar   = criarBotao("Deletar",    COR_DELETAR,       Color.WHITE);
        JButton btnLimpar    = criarBotao("Limpar",     new Color(117,117,117), Color.WHITE);

        botoes.add(btnSalvar);
        botoes.add(btnAtualizar);
        botoes.add(btnDeletar);
        botoes.add(btnLimpar);

        btnSalvar.addActionListener(e    -> salvar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnDeletar.addActionListener(e   -> deletar());
        btnLimpar.addActionListener(e    -> limpar());

        painel.add(campos, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private JTextField criarCampo(JPanel pai, String placeholder) {
        JPanel grupo = new JPanel(new BorderLayout(0, 2));
        grupo.setOpaque(false);

        JLabel label = new JLabel(placeholder);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(COR_LABEL);

        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(COR_TEXTO);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                new EmptyBorder(4, 8, 4, 8)
        ));

        grupo.add(label, BorderLayout.NORTH);
        grupo.add(field, BorderLayout.CENTER);
        pai.add(grupo);
        return field;
    }

    private JButton criarBotao(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 12, 8, 12));
        return btn;
    }

    // ── Painel direito com busca + tabela ────────────────────────
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(new EmptyBorder(10, 6, 10, 12));

        // Busca
        JPanel buscaPanel = new JPanel(new BorderLayout(8, 0));
        buscaPanel.setBackground(COR_PAINEL);
        buscaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 235), 1),
                new EmptyBorder(10, 14, 10, 14)
        ));

        JLabel lblBusca = new JLabel("Buscar por nome:");
        lblBusca.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblBusca.setForeground(COR_LABEL);

        txtBusca = new JTextField();
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                new EmptyBorder(4, 8, 4, 8)
        ));

        JButton btnBuscar      = criarBotao("Buscar",      COR_PRIMARIA, Color.WHITE);
        JButton btnListarTodos = criarBotao("Listar Todos", new Color(76,175,80), Color.WHITE);

        JPanel btnsBusca = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnsBusca.setOpaque(false);
        btnsBusca.add(btnBuscar);
        btnsBusca.add(btnListarTodos);

        buscaPanel.add(lblBusca,  BorderLayout.WEST);
        buscaPanel.add(txtBusca,  BorderLayout.CENTER);
        buscaPanel.add(btnsBusca, BorderLayout.EAST);

        btnBuscar.addActionListener(e      -> buscar());
        btnListarTodos.addActionListener(e -> carregarTabela());

        // Tabela
        modelo = new DefaultTableModel(
                new String[]{"ID","Nome","Email","Telefone","CPF",
                        "Rua","Número","Bairro","Cidade","Data Nasc."}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabela.setRowHeight(28);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setSelectionBackground(new Color(197, 202, 233));
        tabela.setSelectionForeground(COR_TEXTO);
        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.getColumnModel().getColumn(0).setMaxWidth(40);

        // Header da tabela
        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(COR_HEADER);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        // Linhas alternadas
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                                                           boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? Color.WHITE : COR_TABELA_PAR);
                    setForeground(COR_TEXTO);
                }
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        tabela.getSelectionModel().addListSelectionListener(e -> preencherFormulario());

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 235), 1));
        scroll.getViewport().setBackground(Color.WHITE);

        painel.add(buscaPanel, BorderLayout.NORTH);
        painel.add(scroll,     BorderLayout.CENTER);
        return painel;
    }

    // ── Lógica ──────────────────────────────────────────────────
    private void salvar() {
        try {
            dao.inserir(getClienteDoForm());
            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
            limpar(); carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void atualizar() {
        if (idSelecionado == -1) { JOptionPane.showMessageDialog(this, "Selecione um cliente!"); return; }
        try {
            Cliente c = getClienteDoForm();
            c.setId(idSelecionado);
            dao.atualizar(c);
            JOptionPane.showMessageDialog(this, "Cliente atualizado!");
            limpar(); carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void deletar() {
        if (idSelecionado == -1) { JOptionPane.showMessageDialog(this, "Selecione um cliente!"); return; }
        if (JOptionPane.showConfirmDialog(this, "Tem certeza?") == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(idSelecionado);
                JOptionPane.showMessageDialog(this, "Cliente deletado!");
                limpar(); carregarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }

    private void buscar() {
        try { popularTabela(dao.buscarPorNome(txtBusca.getText())); }
        catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void carregarTabela() {
        try { popularTabela(dao.listarTodos()); }
        catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void popularTabela(List<Cliente> lista) {
        modelo.setRowCount(0);
        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getEmail(), c.getTelefone(), c.getCpf(),
                    c.getRua(), c.getNumero(), c.getBairro(), c.getCidade(), c.getDataNascimento()
            });
        }
    }

    private void preencherFormulario() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            idSelecionado = (int) modelo.getValueAt(row, 0);
            txtNome.setText((String)     modelo.getValueAt(row, 1));
            txtEmail.setText((String)    modelo.getValueAt(row, 2));
            txtTelefone.setText((String) modelo.getValueAt(row, 3));
            txtCpf.setText((String)      modelo.getValueAt(row, 4));
            txtRua.setText((String)      modelo.getValueAt(row, 5));
            txtNumero.setText((String)   modelo.getValueAt(row, 6));
            txtBairro.setText((String)   modelo.getValueAt(row, 7));
            txtCidade.setText((String)   modelo.getValueAt(row, 8));
            txtDataNasc.setText((String) modelo.getValueAt(row, 9));
        }
    }

    private Cliente getClienteDoForm() {
        Cliente c = new Cliente();
        c.setNome(txtNome.getText());
        c.setEmail(txtEmail.getText());
        c.setTelefone(txtTelefone.getText());
        c.setCpf(txtCpf.getText());
        c.setRua(txtRua.getText());
        c.setNumero(txtNumero.getText());
        c.setBairro(txtBairro.getText());
        c.setCidade(txtCidade.getText());
        c.setDataNascimento(txtDataNasc.getText());
        return c;
    }

    private void limpar() {
        txtNome.setText(""); txtEmail.setText("");
        txtTelefone.setText(""); txtCpf.setText("");
        txtRua.setText(""); txtNumero.setText("");
        txtBairro.setText(""); txtCidade.setText("");
        txtDataNasc.setText("");
        idSelecionado = -1;
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}