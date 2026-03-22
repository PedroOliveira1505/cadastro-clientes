package com.meuprojeto.view;

import javax.swing.text.*;

public class MascaraCPF extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset,
                             String text, AttributeSet attr)
        throws BadLocationException {
        StringBuilder sb = new StringBuilder(fb.getDocument()
                .getText(0, fb.getDocument().getLength()));
        sb.insert(offset, text);
        fb.replace(0, fb.getDocument().getLength(),
                formatar(sb.toString()), attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length,
                        String text, AttributeSet attr)
            throws BadLocationException {
        StringBuilder sb = new StringBuilder(fb.getDocument()
                .getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);
        fb.replace(0, fb.getDocument().getLength(),
                formatar(sb.toString()), attr);
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        StringBuilder sb = new StringBuilder(fb.getDocument()
                .getText(0, fb.getDocument().getLength()));
        sb.delete(offset, offset + length);
        fb.replace(0, fb.getDocument().getLength(),
                formatar(sb.toString()), null);
    }

    private String formatar(String texto) {
        // Remove tudo que não é número
        String nums = texto.replaceAll("[^0-9]", "");

        // Limita a 11 dígitos
        if (nums.length() > 11) nums = nums.substring(0, 11);

        // Aplica a máscara progressivamente
        StringBuilder sb = new StringBuilder(nums);
        if (nums.length() > 9) {
            sb.insert(9, '-');
            sb.insert(6, '.');
            sb.insert(3, '.');
        } else if (nums.length() > 6) {
            sb.insert(6, '.');
            sb.insert(3, '.');
        } else if (nums.length() > 3) {
            sb.insert(3, '.');
        }
        return sb.toString();
    }
}
