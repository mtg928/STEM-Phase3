/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "sub_product_components")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubProductComponents.findAll", query = "SELECT s FROM SubProductComponents s")
    , @NamedQuery(name = "SubProductComponents.findBySpcId", query = "SELECT s FROM SubProductComponents s WHERE s.spcId = :spcId")})
public class SubProductComponents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spc_id")
    private Integer spcId;
    @Lob
    @Column(name = "spc_description")
    private String spcDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spcid")
    private Collection<Spchdr> spchdrCollection;
    @JoinColumn(name = "spg_ref", referencedColumnName = "spg_id")
    @ManyToOne(optional = false)
    private SubProductGroup spgRef;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;
    @OneToMany(mappedBy = "subComponent")
    private Collection<Functionalfailures> functionalfailuresCollection;

    public SubProductComponents() {
    }

    public SubProductComponents(Integer spcId) {
        this.spcId = spcId;
    }

    public Integer getSpcId() {
        return spcId;
    }

    public void setSpcId(Integer spcId) {
        this.spcId = spcId;
    }

    public String getSpcDescription() {
        return spcDescription;
    }

    public void setSpcDescription(String spcDescription) {
        this.spcDescription = spcDescription;
    }

    @XmlTransient
    public Collection<Spchdr> getSpchdrCollection() {
        return spchdrCollection;
    }

    public void setSpchdrCollection(Collection<Spchdr> spchdrCollection) {
        this.spchdrCollection = spchdrCollection;
    }

    public SubProductGroup getSpgRef() {
        return spgRef;
    }

    public void setSpgRef(SubProductGroup spgRef) {
        this.spgRef = spgRef;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    @XmlTransient
    public Collection<Functionalfailures> getFunctionalfailuresCollection() {
        return functionalfailuresCollection;
    }

    public void setFunctionalfailuresCollection(Collection<Functionalfailures> functionalfailuresCollection) {
        this.functionalfailuresCollection = functionalfailuresCollection;
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
        if (!(object instanceof SubProductComponents)) {
            return false;
        }
        SubProductComponents other = (SubProductComponents) object;
        if ((this.spcId == null && other.spcId != null) || (this.spcId != null && !this.spcId.equals(other.spcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.SubProductComponents[ spcId=" + spcId + " ]";
    }
    
}
