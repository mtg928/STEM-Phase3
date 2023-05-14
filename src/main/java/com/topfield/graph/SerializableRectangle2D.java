/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public  class SerializableRectangle2D extends Rectangle2D.Double
			implements Serializable {

		public SerializableRectangle2D() {
			super();
		}

		public SerializableRectangle2D(double x, double y, double width,
				double height) {
			super(x, y, width, height);
		}

		public void setX(double x) {
			setFrame(x, getY(), getWidth(), getHeight());
		}

		public void setY(double y) {
			setFrame(getX(), y, getWidth(), getHeight());
		}

		public void setWidth(double width) {
			setFrame(getX(), getY(), width, getHeight());
		}

		public void setHeight(double height) {
			setFrame(getX(), getY(), getWidth(), height);
		}

		private void writeObject(ObjectOutputStream out) throws IOException {
			out.defaultWriteObject();
			out.writeObject(new java.lang.Double(getX()));
			out.writeObject(new java.lang.Double(getY()));
			out.writeObject(new java.lang.Double(getWidth()));
			out.writeObject(new java.lang.Double(getHeight()));
		}

		private void readObject(ObjectInputStream in) throws IOException,
				ClassNotFoundException {
			in.defaultReadObject();
			java.lang.Double x = (java.lang.Double) in.readObject();
			java.lang.Double y = (java.lang.Double) in.readObject();
			java.lang.Double width = (java.lang.Double) in.readObject();
			java.lang.Double height = (java.lang.Double) in.readObject();
			setFrame(x.doubleValue(), y.doubleValue(), width.doubleValue(),
					height.doubleValue());
		}
	}
