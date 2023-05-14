/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.JTable;

/**
 *
 * @author Murali
 */
public class PasteAction extends AbstractAction {

        private JTable table;
        private ExcelAdapter adapter;

        public PasteAction(JTable tbl,ExcelAdapter adapter) {

            putValue(NAME, "Paste (Ctrl+V)");
            this.adapter = adapter; 
            table = tbl;

            
           final Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

            cb.addFlavorListener(new FlavorListener() {
                @Override
                public void flavorsChanged(FlavorEvent e) {
                   // setEnabled(cb.isDataFlavorAvailable(CellTransferable.CELL_DATA_FLAVOR));
                   
                }
            });
           // setEnabled(cb.isDataFlavorAvailable(CellTransferable.CELL_DATA_FLAVOR));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            adapter.pasteFuntion();
            
           /* int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (cb.isDataFlavorAvailable(CellTransferable.CELL_DATA_FLAVOR)) {
                try {
                    Object value = cb.getData(CellTransferable.CELL_DATA_FLAVOR);
                    System.out.println(value);
                    table.setValueAt(value, row, col);

                } catch (UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                }
            }*/
        }

    }