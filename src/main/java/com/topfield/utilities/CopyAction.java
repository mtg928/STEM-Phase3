/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.JTable;

/**
 *
 * @author Murali
 */
 public class CopyAction extends AbstractAction {

        private JTable table;
        private ExcelAdapter adapter;

        public CopyAction(JTable table,ExcelAdapter adapter) {
            this.table = table;
            this.adapter = adapter;
            putValue(NAME, "Copy (Ctrl+C)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           /* int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            cb.setContents(new CellTransferable(table.getValueAt(row, col)), null);*/
           adapter.copyFuntion();

        }

    }
