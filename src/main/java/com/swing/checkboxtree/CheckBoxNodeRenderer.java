
package com.swing.checkboxtree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;


public class CheckBoxNodeRenderer implements TreeCellRenderer {

	private final CheckBoxNodePanel panel = new CheckBoxNodePanel();

	private final DefaultTreeCellRenderer defaultRenderer =
		new DefaultTreeCellRenderer();

	private final Color selectionForeground, selectionBackground;
	private final Color textForeground, textBackground;

	public/*protected*/ CheckBoxNodePanel getPanel() {
		return panel;
	}

	public CheckBoxNodeRenderer() {
		final Font fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) panel.label.setFont(fontValue);

		final Boolean focusPainted =
			(Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
		panel.check.setFocusPainted(focusPainted != null && focusPainted);

		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}

	// -- TreeCellRenderer methods --

	@Override
	public Component getTreeCellRendererComponent(final JTree tree,
		final Object value, final boolean selected, final boolean expanded,
		final boolean leaf, final int row, final boolean hasFocus)
	{
                CheckBoxNodeData childData = null;
		CheckBoxNodeData data = null;
		if (value instanceof DefaultMutableTreeNode) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			final Object userObject = node.getUserObject();
			if (userObject instanceof CheckBoxNodeData) {
				data = (CheckBoxNodeData) userObject;
			}
                       /* if (!node.isLeaf()){
                           for (int j = 0; j < node.getChildCount(); j++) {  
                            childData = (CheckBoxNodeData)((DefaultMutableTreeNode)node.getChildAt(j)).getUserObject();
                               try {
                                 if (childData != null) {
                                       childData.setChecked(data.isChecked());
                                       System.out.println("!!!!!!!!! - "+childData.getText());
                                 }
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }


                        }
                        }*/
		}

		final String stringValue =
			tree.convertValueToText(value, selected, expanded, leaf, row, false);
		panel.label.setText(stringValue);
		panel.check.setSelected(false);

		panel.setEnabled(tree.isEnabled());

		if (selected) {
			panel.setForeground(selectionForeground);
			panel.setBackground(selectionBackground);
			panel.label.setForeground(selectionForeground);
			panel.label.setBackground(selectionBackground);
		}
		else {
			panel.setForeground(textForeground);
			panel.setBackground(textBackground);
			panel.label.setForeground(textForeground);
			panel.label.setBackground(textBackground);
		}

		if (data == null) {
			// not a check box node; return default cell renderer
			return defaultRenderer.getTreeCellRendererComponent(tree, value,
				selected, expanded, leaf, row, hasFocus);
		}

		panel.label.setText(data.getText());
                panel.check.setSelected(data.isChecked());
                panel.check.setEnabled(data.isEnabled());
  
                //System.out.println(data.getText()+" - "+data.isEnabled());
                /*if (!data.isEnabled()) {
                  panel.check.setSelected(false);
                }*/
                
                /*panel.check.setEnabled(data.isEnabled());
                
                if (panel.check.isEnabled()) {
                  panel.check.setSelected(data.isChecked());
                }else{
                  panel.check.setSelected(false);
                }*/

		return panel;
	}

}