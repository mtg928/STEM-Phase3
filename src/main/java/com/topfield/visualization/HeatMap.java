/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Murali
 */
public class HeatMap extends JPanel {

    private DefaultTableModel model = new DefaultTableModel();
    private JTable heatMapTable;
    private HeatMap heatMap;

    public HeatMap() {
        heatMap = this;

        //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("<html><center> <h3>Heat Map</h3> </center> </html>", SwingConstants.CENTER);
        heading.setOpaque(true);
        heading.setBackground(Color.BLACK);
        heading.setForeground(Color.WHITE);
        //heading.setText("<html><center> <h1>Heading</h1> </center> </html>");
        //heading.setFont(new Font("Myriad Pro",Font.PLAIN,15));
        heading.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JPanel riskMatrix = new RiskMatrix();
        JPanel riskMatrixTable = new RiskMatrixTable();

        add(heading, BorderLayout.NORTH);
        add(riskMatrixTable, BorderLayout.WEST);
        add(riskMatrix, BorderLayout.CENTER);

    }

    public class RiskMatrixTable extends JPanel implements TableColumnModelListener {

        public RiskMatrixTable() {
            //setBackground(Color.blue);

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            Object[] colnames = new Object[]{"ID", "Risk", "I", "L", "V", "S"};
            String[][] data = new String[][]{
                {"1", "Supply chain disruption", "4.8", "3.7", "3.8", "4"},
                {"2", "Customer preference shift", "4.1", "3.3", "3.5", "2"},
                {"3", "Copper price rise > 10%", "4.3", "4.7", "2.3", "4"},
                {"4", "Work stoppage > 1 week", "4.4", "4.5", "4.1", "3"},
                {"5", "Economic downturn", "3.8", "4.2", "3.2", "1"},
                {"6", "Suppler Consolidation", "3.9", "4.5", "3.6", "1"},
                {"7", "Local Competitors enter", "4.5", "3.6", "4.2", "1"},
                {"8", "New substitutes available", "2.9", "4.0", "2.9", "3"},
                {"9", "Cost of capital rise > 5%", "3.4", "4.3", "2.9", "1"},
                {"10", "Tighter emission standards", "3.4", "4.6", "2.9", "1"},
                {"11", "FCPA violation", "4.0", "4.0", "3.3", "5"},
                {"12", "Exchange rate fluctuations", "2.7", "4.1", "2.7", "4"},
                {"13", "Impairments of assets", "1.6", "2.7", "1.6", "1"}};
            model.setDataVector(data, colnames);

            JTableSettings( model);
            add(new JScrollPane(heatMapTable));

            JPanel tableControls = new JPanel();
            tableControls.setBackground(Color.WHITE);
            tableControls.setLayout(new BorderLayout());

            JLabel info = new JLabel("<html> Dots repesent risk #1 - #n <br>"
                    + " Dot size reflects speed of onset <br><br>"
                    + "<i>I =Impact &nbsp  L =Likelihood &nbsp V =Vulnerability <br>  S =Speed of on Set </i> <p> </p>"
                    + "<table border=\"1\"><tr>\n"
                    + "<td bgcolor=\"#ff0000\">Very Low</td>\n"
                    + "<td bgcolor=\"rgb(0, 0, 255)\"> <font color=\"white\"> Low </font> </td>\n"
                    + "<td bgcolor=\"rgb(0, 255, 255)\">Medium</td>\n"
                    + "<td bgcolor=\"rgb(0, 128, 0)\"> High</td>\n"
                    + "<td bgcolor=\"rgb(212, 172, 13)\"> Very High</td>\n"
                    + "</tr></table>\n"
                    + "</html>", SwingConstants.LEFT);
            //info.setBackground(Color.black);
            info.setOpaque(true);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            JButton refresh = new JButton("Refresh the Heat Map");
            //refresh.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            refresh.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // display/center the jdialog when the button is pressed
                    /*JDialog d = new JDialog(MainFrame.mainFrame, "Hello " , true);
                d.setLocationRelativeTo(null);
                d.setVisible(true);*/

                    heatMap.repaint();
                    //heatMapTable.clearSelection();
                    
                    if (heatMapTable.isEditing()) {
                    // Get the current editor
                    TableCellEditor editor = heatMapTable.getCellEditor();
                            if (editor != null) {
                                // Try and stop the cell editing process
                                if (!editor.stopCellEditing()) {
                                    // Cancel the editing if can't be stopped...
                                    // You could handle an error state here instead...
                                    editor.cancelCellEditing();
                                }
                            }
                        }
                }
            });

            buttonPanel.add(refresh);
            
            JButton add     = new JButton("             Add Data           ");
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // display/center the jdialog when the button is pressed
                    /*JDialog d = new JDialog(MainFrame.mainFrame, "Hello " , true);
                d.setLocationRelativeTo(null);
                d.setVisible(true);*/

                    //heatMap.repaint();
                     model.addRow(new String[] {(model.getRowCount()+1)+"", "", " ", " ", "", "1"});
                     //heatMapTable.getColumn("Title 1").setCellRenderer(new brmcellrender());
                    // heatMapTable.setModel(model);
                     //DefaultTableModel a = new DefaultTableModel();
                     //JTableSettings( a);
                }
            });

            buttonPanel.add(refresh);
            JButton delete  = new JButton("          Delete Data         ");
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        try{
                         int SelectedRowIndex = heatMapTable.getSelectedRow();
                          if(SelectedRowIndex <0){
                              JOptionPane.showMessageDialog(null, "Please select a row to delete");
                          }else{
                          model.removeRow(SelectedRowIndex);
                          }
                         }catch(Exception ex)
                         {
                             JOptionPane.showMessageDialog(null, ex);
                         }
                     }
            });

            buttonPanel.add(refresh);
            buttonPanel.add(add);
            buttonPanel.add(delete);

            tableControls.add(info, BorderLayout.WEST);
            tableControls.add(buttonPanel, BorderLayout.EAST);

            add(tableControls);

        }

        public void columnAdded(TableColumnModelEvent e) {
            System.out.println("Added");
        }

        public void columnMarginChanged(ChangeEvent e) {
            System.out.println("Margin");
        }

        public void columnMoved(TableColumnModelEvent e) {
            System.out.println("Moved");
        }

        public void columnRemoved(TableColumnModelEvent e) {
            System.out.println("Removed");
        }

        public void columnSelectionChanged(ListSelectionEvent e) {
            System.out.println("Selected");
        }

        public void setValueCellColor() {

            for (int i = 0; i < model.getRowCount(); i++) {
                // j.getColumnModel().getColumn(0).set
            }

        }

        public Color colorByValue(double value) {
            Color c = Color.BLACK;

            if (value <= 1) {
                c = new Color(0, 0, 255);
            } else if (value <= 2) {
                c = new Color(0, 255, 255);
            } else if (value <= 3) {
                c = new Color(255, 0, 255);
            } else if (value <= 4) {
                c = new Color(0, 128, 0);
            } else if (value <= 5) {
                c = new Color(212, 172, 13);
            }

            return c;
        }
        
        
        public void JTableSettings(DefaultTableModel model){
        
            
            heatMapTable = new JTable(model) {

                public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
                    Component comp = super.prepareRenderer(renderer, rowIndex, columnIndex);

                    if (getSelectedRow() == rowIndex) {
                        if (getValueAt(rowIndex, 5).toString().equals("1")) {
                            comp.setBackground(colorByValue(1.0));
                            comp.setForeground(Color.white);
                        } else if (getValueAt(rowIndex, 5).toString().equals("2")) {
                            comp.setBackground(colorByValue(2.0));
                            comp.setForeground(Color.BLACK);
                        } else if (getValueAt(rowIndex, 5).toString().equals("3")) {
                            comp.setBackground(colorByValue(3.0));
                            comp.setForeground(Color.BLACK);
                        } else if (getValueAt(rowIndex, 5).toString().equals("4")) {
                            comp.setBackground(colorByValue(4.0));
                            comp.setForeground(Color.BLACK);
                        } else if (getValueAt(rowIndex, 5).toString().equals("5")) {
                            comp.setBackground(colorByValue(5.0));
                            comp.setForeground(Color.BLACK);
                        } else {
                            comp.setBackground(Color.white);
                            comp.setForeground(Color.BLACK);
                        }
                    } else {
                        comp.setBackground(Color.white);
                        comp.setForeground(Color.BLACK);
                    }
                    return comp;
                }
                /* if(getValueAt(rowIndex, 5).toString().equals("1")) {  
                  componenet.getComponentAt(rowIndex, 5).setBackground(colorByValue(1.0));  
                } else if(getValueAt(rowIndex, 5).toString().equals("2")) {
                  componenet.getComponentAt(rowIndex, 5).setBackground(colorByValue(2.0));
                }else if(getValueAt(rowIndex, 5).toString().equals("3")) {
                   componenet.getComponentAt(rowIndex, 5).setBackground(colorByValue(3.0));
                }else if(getValueAt(rowIndex, 5).toString().equals("4")) {
                   componenet.getComponentAt(rowIndex, 5).setBackground(colorByValue(4.0));
                }else if(getValueAt(rowIndex, 5).toString().equals("5")) {
                   componenet.getComponentAt(rowIndex, 5).setBackground(colorByValue(5.0));
                }
                return componenet;
                } */

            };
            
            heatMapTable.setOpaque(true);
            
            JTableHeader th = heatMapTable.getTableHeader();
            th.setOpaque(true);
            th.setBackground(Color.BLACK);
            th.setForeground(Color.WHITE);
            th.setFont(new Font("arial", Font.BOLD, 12));

            heatMapTable.getColumnModel().getColumn(1).setPreferredWidth(250);
            heatMapTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            heatMapTable.setGridColor(Color.LIGHT_GRAY);
            //j.setForeground(Color.LIGHT_GRAY);
        
        }

    }

    public class RiskMatrix extends JPanel {

        String[] celltextLatest = {"", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", ""};

        Color[] bgRiskLatest = {new Color(255, 214, 48), new Color(120, 40, 31), new Color(120, 40, 31), new Color(120, 40, 31),
            new Color(20, 90, 50), new Color(255, 214, 48), new Color(120, 40, 31), new Color(120, 40, 31),
            new Color(20, 90, 50), new Color(20, 90, 50), new Color(255, 214, 48), new Color(120, 40, 31),
            new Color(20, 90, 50), new Color(20, 90, 50), new Color(20, 90, 50), new Color(255, 214, 48)};

        public RiskMatrix() {
            //setBackground(new Color(20, 90, 50  ));
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            add(new com.topfield.visualization.RiskMatrix(celltextLatest, bgRiskLatest, 4, 4));
            //setLayout(null);

        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Ellipse2D.Double circle = new Ellipse2D.Double(10, 10, 10, 10);

            g2d.setColor(Color.GRAY);
            g2d.fill(circle);

        }

        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.white);
            String[][] sortedArray;

            Font f = new Font("Monospaced", Font.BOLD, 14); //Font.ITALIC | Font.BOLD
            g2d.setFont(f);
            g2d.drawString("5", 5, 10);
            g2d.drawString("4", 5, (int) yByValue(1.0, 4) + 10);
            g2d.drawString("3", 5, (int) yByValue(2.0, 4) + 10);
            g2d.drawString("2", 5, (int) yByValue(3.0, 4) + 10);
            g2d.drawString("1", 5, (int) yByValue(4.0, 4) - 10);
            //g2d.drawString("Likelihood",5, (int) yByValue(2.5,4)-10);
            System.out.println("" + xByValue(4.0, 4));

            String team = "Likelihood";
            FontMetrics fm = g2d.getFontMetrics();
            int ypos = (int) yByValue(1.5, 4) - 10;
            for (int i = 0; i < team.length(); i++) {
                int x = 20 - (fm.charWidth(team.charAt(i)) / 2);
                g2d.drawString(Character.toString(team.charAt(i)), x, ypos);
                ypos += fm.getHeight();
            }

            //g2d.drawString("1", 10,this.getHeight()-10);                    
            g2d.drawString("2", (int) xByValue(1.0, 4) - 10, this.getHeight() - 10);
            g2d.drawString("3", (int) xByValue(2.0, 4) - 10, this.getHeight() - 10);
            g2d.drawString("4", (int) xByValue(3.0, 4) - 10, this.getHeight() - 10);
            g2d.drawString("5", (int) xByValue(4.0, 4) - 10, this.getHeight() - 10);
            g2d.setColor(Color.white);
            g2d.drawString("Impact", (int) xByValue(2.2, 4) - 10, this.getHeight() - 10);

            sortedArray = getSortListByTable();

            for (int i = 0; i < sortedArray.length; i++) {

                //System.out.println(""+model.getValueAt(i, 2));
                //drawCircle(model.getValueAt(i, 0)+"",Double.parseDouble(model.getValueAt(i, 5)+"")*10, Double.parseDouble(model.getValueAt(i, 3)+""), Double.parseDouble(model.getValueAt(i, 4)+""), colorByValue(Double.parseDouble(model.getValueAt(i, 5)+"")), g2d);
                try {
                 drawCircle(sortedArray[i][0] + "", Double.parseDouble(sortedArray[i][5] + "") * 10, Double.parseDouble(sortedArray[i][2] + ""), Double.parseDouble(sortedArray[i][3] + ""), colorByValue(Double.parseDouble(sortedArray[i][5] + "")), g2d);                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please fill all the data before refresh");
                }

            }

        }

        public void drawCircle(String id, double radius, double x, double y, Color color, Graphics2D g2d) {

            Ellipse2D.Double circle = new Ellipse2D.Double(xByValue(x - 1, 4), (this.getHeight() - yByValue(y - 1, 4)), radius, radius);
            g2d.setColor(color);
            g2d.fill(circle);
            g2d.drawString(id, (int) xByValue(x - 1, 4), (int) (this.getHeight() - yByValue(y - 1, 4)));
            g2d.setColor(Color.BLACK);
            g2d.draw(circle);
            //System.out.println("Height - "+this.getHeight()+" Width "+this.getWidth());

        }

        public Color colorByValue(double value) {
            Color c = Color.BLACK;

            if (value <= 1) {
                c = new Color(0, 0, 255);
            } else if (value <= 2) {
                c = new Color(0, 255, 255);
            } else if (value <= 3) {
                c = new Color(255, 0, 255);
            } else if (value <= 4) {
                c = new Color(0, 128, 0);
            } else if (value <= 5) {
                c = new Color(212, 172, 13);
            }

            return c;
        }

        public double yByValue(double value, int grid) {
            double d = 0.0;

            d = (value / grid) * this.getHeight();
            System.out.println(" value " + value + " grid " + grid + " this.getHeight() " + this.getHeight());
            return d;
        }

        public double xByValue(double value, int grid) {
            double d = 0.0;

            d = (value / grid) * this.getWidth();

            return d;
        }

        public String[][] getSortListByTable() {
            String[][] res = new String[model.getRowCount()][model.getColumnCount()];

            for (int i = 0; i < model.getRowCount(); i++) {

                for (int j = 0; j < model.getColumnCount(); j++) {
                    res[i][j] = model.getValueAt(i, j) + "";
                    System.out.println("i " + i + " j " + j + " " + model.getValueAt(i, j) + " " + model.getRowCount());
                }
            }

            Arrays.sort(res, new Comparator<String[]>() {
                @Override
                public int compare(final String[] entry1, final String[] entry2) {
                    final String time1 = entry1[5];
                    final String time2 = entry2[5];
                    return time1.compareTo(time2) * -1;
                }
            });

            for (final String[] s : res) {
                System.out.println(s[0] + " " + s[5]);
            }

            return res;
        }

    }

}
