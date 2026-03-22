package com.meuprojeto.view;

import javax.swing.text.*;

public class MascaraData extends DocumentFilter {

    @Override
    public void replace(FilterBypass fb, int offset, int length,
                        String text, AttributeSet attr)
            throws BadLocationException {
        StringBuilder sb = new StringBuilder(
                fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);
        fb.replace(0, fb.getDocument().getLength(),
                formatar(sb.toString()), attr);
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        StringBuilder sb = new StringBuilder(
                fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.delete(offset, offset + length);
        fb.replace(0, fb.getDocument().getLength(),
                formatar(sb.toString()), null);
    }

    private String formatar(String texto) {
        String nums = texto.replaceAll("[^0-9]", "");
        if (nums.length() > 8) nums = nums.substring(0, 8);

        StringBuilder sb = new StringBuilder(nums);
        if (nums.length() > 4) sb.insert(4, '/');
        if (nums.length() > 2) sb.insert(2, '/');

        return sb.toString();
    }
}