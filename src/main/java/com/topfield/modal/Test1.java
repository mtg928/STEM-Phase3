/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "test1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Test1.findAll", query = "SELECT t FROM Test1 t")
    , @NamedQuery(name = "Test1.findById", query = "SELECT t FROM Test1 t WHERE t.id = :id")
    , @NamedQuery(name = "Test1.findByColumn1", query = "SELECT t FROM Test1 t WHERE t.column1 = :column1")
    , @NamedQuery(name = "Test1.findByColumn2", query = "SELECT t FROM Test1 t WHERE t.column2 = :column2")
    , @NamedQuery(name = "Test1.findByColumn3", query = "SELECT t FROM Test1 t WHERE t.column3 = :column3")
    , @NamedQuery(name = "Test1.findByColumn4", query = "SELECT t FROM Test1 t WHERE t.column4 = :column4")})
public class Test1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "column1")
    private String column1;
    @Basic(optional = false)
    @Column(name = "column2")
    private String column2;
    @Basic(optional = false)
    @Column(name = "column3")
    private String column3;
    @Basic(optional = false)
    @Column(name = "column4")
    private double column4;

    public Test1() {
    }

    public Test1(String id) {
        this.id = id;
    }

    public Test1(String id, String column1, String column2, String column3, double column4) {
        this.id = id;
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.column4 = column4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public double getColumn4() {
        return column4;
    }

    public void setColumn4(double column4) {
        this.column4 = column4;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test1)) {
            return false;
        }
        Test1 other = (Test1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Test1[ id=" + id + " ]";
    }
    
}
