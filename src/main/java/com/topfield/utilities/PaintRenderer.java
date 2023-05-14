/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Murali
 */
public class PaintRenderer extends DefaultTreeCellRenderer {
        @Override
        public void paintComponent(Graphics g) {
            Color bColor;

            if (selected) {
                bColor = Color.RED;
            } else {
                bColor = getBackgroundNonSelectionColor();
                if (bColor == null) {
                    bColor = getBackground();
                }
            }

            int imageOffset = -1;
            if (bColor != null) {
                imageOffset = getLabelStart();
                g.setColor(bColor);
                if(getComponentOrientation().isLeftToRight()) {
                    g.fillRect(imageOffset, 0, getWidth() - imageOffset, getHeight());
                } else {
                    g.fillRect(0, 0, getWidth() - imageOffset, getHeight());
                }
            }

            if (hasFocus) {
                if (imageOffset == -1) {
                    imageOffset = getLabelStart();
                }

                if(getComponentOrientation().isLeftToRight()) {
                    paintFocus(g, imageOffset, 0, getWidth() - imageOffset, getHeight());
                } else {
                    paintFocus(g, 0, 0, getWidth() - imageOffset, getHeight());
                }
            }
            super.paintComponent(g);
        }

        private void paintFocus(Graphics g, int x, int y, int w, int h) {
            Color bsColor = Color.RED;

            if (bsColor != null && selected) {
                g.setColor(bsColor);
                g.drawRect(x, y, w - 1, h - 1);
            }
        }

        private int getLabelStart() {
            Icon currentI = getIcon();
            if(currentI != null && getText() != null) {
                return currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
            }
            return 0;
        }
    }

