/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swing.checkboxtree;



public class CheckBoxNodeData {

        private int id;
	private String text;
	private boolean checked;
        private boolean enabled =true;



	public CheckBoxNodeData(final String text, final boolean checked) {
		this.text = text;
		this.checked = checked;
	}
        
      /*  public CheckBoxNodeData(final int id,final String text, final boolean checked) {
		this.text = text;
		this.checked = checked;
                this.id = id;
	}*/
        
        public CheckBoxNodeData(final int id,final String text, final boolean checked,boolean enabled) {
		this.text = text;
		this.checked = checked;
                this.id = id;
                this.enabled = enabled;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[" + text + "/" + checked + "]";
	}

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
