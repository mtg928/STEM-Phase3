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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "main_product_components")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MainProductComponents.findAll", query = "SELECT m FROM MainProductComponents m")
    , @NamedQuery(name = "MainProductComponents.findByMpcId", query = "SELECT m FROM MainProductComponents m WHERE m.mpcId = :mpcId")})
public class MainProductComponents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mpc_id")
    private Integer mpcId;
    @Lob
    @Column(name = "mpc_description")
    private String mpcDescription;
    @JoinColumn(name = "mpg_ref", referencedColumnName = "mpg_id")
    @ManyToOne(optional = false)
    private MainProductGroup mpgRef;

    public MainProductComponents() {
    }

    public MainProductComponents(Integer mpcId) {
        this.mpcId = mpcId;
    }

    public Integer getMpcId() {
        return mpcId;
    }

    public void setMpcId(Integer mpcId) {
        this.mpcId = mpcId;
    }

    public String getMpcDescription() {
        return mpcDescription;
    }

    public void setMpcDescription(String mpcDescription) {
        this.mpcDescription = mpcDescription;
    }

    public MainProductGroup getMpgRef() {
        return mpgRef;
    }

    public void setMpgRef(MainProductGroup mpgRef) {
        this.mpgRef = mpgRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mpcId != null ? mpcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MainProductComponents)) {
            return false;
        }
        MainProductComponents other = (MainProductComponents) object;
        if ((this.mpcId == null && other.mpcId != null) || (this.mpcId != null && !this.mpcId.equals(other.mpcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.MainProductComponents[ mpcId=" + mpcId + " ]";
    }
    
}
