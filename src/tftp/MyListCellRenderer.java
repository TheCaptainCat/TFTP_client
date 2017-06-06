package tftp;

import java.awt.Color;
import java.awt.Component;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author p1609594
 */
public class MyListCellRenderer extends JLabel implements ListCellRenderer {

    private List<String> errorMessages;

    public MyListCellRenderer() {
        setOpaque(true);
        errorMessages = new LinkedList<>();
        errorMessages.add("Not defined, see error message (if any)");
        errorMessages.add("File not found");
        errorMessages.add("Access violation");
        errorMessages.add("Disk full or allocation exceeded");
        errorMessages.add("Illegal TFTP operation");
        errorMessages.add("Unknown transfer ID");
        errorMessages.add("File already exists");
        errorMessages.add("No such user");
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());

        // based on the index you set the color.  This produces the every other effect.
        if (isAnErrorMessage(value.toString())) {
            setForeground(Color.RED);
        } else {
            setForeground(new Color(45, 117, 36));
        }

        return this;
    }

    private boolean isAnErrorMessage(String s) {
        for (String errorMessage : errorMessages) {
            if (s.equals(errorMessage))
                return true;
        }
        return false;
    }
}
