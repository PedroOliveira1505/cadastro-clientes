package com.meuprojeto.view;

import javax.swing.text.*;

public class CapitalizarFilter extends DocumentFilter {

    @Override
    public void replace(FilterBypass fb, int offset, int length,
                        String text, AttributeSet attr)
            throws BadLocationException {

        if (text == null || text.isEmpty()) {
            super.replace(fb, offset, length, text, attr);
            return;
        }

        // Pega o texto que já existe no campo antes do cursor
        String textoAtual = fb.getDocument()
                .getText(0, fb.getDocument().getLength());

        // Verifica se o caractere anterior é um espaço (ou se é o início)
        boolean deveCapitalizar = offset == 0
                || textoAtual.charAt(offset - 1) == ' ';

        if (deveCapitalizar) {
            text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
        }

        super.replace(fb, offset, length, text, attr);
    }
}