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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "function")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Function1.findAll", query = "SELECT f FROM Function1 f")
    , @NamedQuery(name = "Function1.findBySpcId", query = "SELECT f FROM Function1 f WHERE f.spcId = :spcId")
    , @NamedQuery(name = "Function1.findBySpgRef", query = "SELECT f FROM Function1 f WHERE f.spgRef = :spgRef")})
public class Function1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spc_id")
    private Integer spcId;
    @Column(name = "spg_ref")
    private Integer spgRef;
    @Lob
    @Column(name = "spc_description")
    private String spcDescription;

    public Function1() {
    }

    public Function1(Integer spcId) {
        this.spcId = spcId;
    }

    public Integer getSpcId() {
        return spcId;
    }

    public void setSpcId(Integer spcId) {
        this.spcId = spcId;
    }

    public Integer getSpgRef() {
        return spgRef;
    }

    public void setSpgRef(Integer spgRef) {
        this.spgRef = spgRef;
    }

    public String getSpcDescription() {
        return spcDescription;
    }

    public void setSpcDescription(String spcDescription) {
        this.spcDescription = spcDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spcId != null ? spcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Function1)) {
            return false;
        }
        Function1 other = (Function1) object;
        if ((this.spcId == null && other.spcId != null) || (this.spcId != null && !this.spcId.equals(other.spcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Function1[ spcId=" + spcId + " ]";
    }
    
}
