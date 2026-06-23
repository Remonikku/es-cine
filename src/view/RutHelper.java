package view;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class RutHelper {

    /**
     * Configura un JTextField para aplicar la máscara de RUT chileno en tiempo real y al perder el foco.
     */
    public static void setupRutField(final JTextField txtRut) {
        txtRut.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Permitimos dígitos, k/K, puntos, guiones y teclas de control básicas
                if (!Character.isDigit(c) && c != 'k' && c != 'K' && c != '.' && c != '-' && 
                    c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                // No formatear para teclas de navegación/borrado directo para no entorpecer la edición intermedia
                if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT ||
                    code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN ||
                    code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE) {
                    return;
                }
                formatField(txtRut);
            }
        });

        txtRut.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                formatField(txtRut);
            }
        });
    }

    private static void formatField(JTextField txtRut) {
        String text = txtRut.getText();
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        // Limpiar: conservar solo dígitos y K/k
        String clean = text.replaceAll("[^0-9kK]", "").toUpperCase();
        if (clean.isEmpty()) {
            txtRut.setText("");
            return;
        }

        // Limitar a máximo 9 caracteres alfanuméricos
        if (clean.length() > 9) {
            clean = clean.substring(0, 9);
        }

        // Enforce rule: solo el último dígito puede ser K, los anteriores deben ser números
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clean.length(); i++) {
            char c = clean.charAt(i);
            if (i == clean.length() - 1) {
                if (Character.isDigit(c) || c == 'K') {
                    sb.append(c);
                }
            } else {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
        }
        
        String digits = sb.toString();
        if (digits.isEmpty()) {
            txtRut.setText("");
            return;
        }

        // Formatear estructuralmente
        String formatted;
        if (digits.length() == 1) {
            formatted = digits;
        } else {
            String verifier = digits.substring(digits.length() - 1);
            String body = digits.substring(0, digits.length() - 1);
            
            StringBuilder formattedBody = new StringBuilder();
            int count = 0;
            for (int i = body.length() - 1; i >= 0; i--) {
                formattedBody.insert(0, body.charAt(i));
                count++;
                if (count % 3 == 0 && i > 0) {
                    formattedBody.insert(0, '.');
                }
            }
            formatted = formattedBody.toString() + "-" + verifier;
        }

        int caretPos = txtRut.getCaretPosition();
        int oldLength = text.length();
        txtRut.setText(formatted);
        
        // Ajustar posición del cursor
        int newLength = formatted.length();
        int diff = newLength - oldLength;
        int newCaretPos = caretPos + diff;
        if (newCaretPos >= 0 && newCaretPos <= newLength) {
            txtRut.setCaretPosition(newCaretPos);
        } else {
            txtRut.setCaretPosition(newLength);
        }
    }

    /**
     * Valida si el RUT ingresado tiene una estructura válida chilena.
     * Soporta formatos: XX.XXX.XXX-X, X.XXX.XXX-X o sus versiones sin puntos (XXXXXXXX-X).
     */
    public static boolean isValidRut(String rut) {
        if (rut == null) return false;
        String trimmed = rut.trim();
        return trimmed.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[0-9kK]$") || trimmed.matches("^\\d{7,8}-[0-9kK]$");
    }

    /**
     * Valida si el correo electrónico contiene el carácter '@'.
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}
