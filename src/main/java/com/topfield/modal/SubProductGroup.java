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
@Table(name = "sub_product_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubProductGroup.findAll", query = "SELECT s FROM SubProductGroup s")
    , @NamedQuery(name = "SubProductGroup.findBySpgId", query = "SELECT s FROM SubProductGroup s WHERE s.spgId = :spgId")
    , @NamedQuery(name = "SubProductGroup.findBySil", query = "SELECT s FROM SubProductGroup s WHERE s.sil = :sil")})
public class SubProductGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spg_id")
    private Integer spgId;
    @Lob
    @Column(name = "spg_ref")
    private String spgRef;
    @Lob
    @Column(name = "spg_description")
    private String spgDescription;
    @Basic(optional = false)
    @Column(name = "sil")
    private String sil;
    @JoinColumn(name = "mpg_ref", referencedColumnName = "mpg_id")
    @ManyToOne
    private MainProductGroup mpgRef;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spgRef")
    private Collection<SubProductComponents> subProductComponentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sILSubComponent")
    private Collection<Sil> silCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spgid")
    private Collection<Spghdr> spghdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccfSubComponent")
    private Collection<Ccfhdr> ccfhdrCollection;
    @OneToMany(mappedBy = "spgId")
    private Collection<SubProductFunctions> subProductFunctionsCollection;

    public SubProductGroup() {
    }

    public SubProductGroup(Integer spgId) {
        this.spgId = spgId;
    }

    public SubProductGroup(Integer spgId, String sil) {
        this.spgId = spgId;
        this.sil = sil;
    }

    public Integer getSpgId() {
        return spgId;
    }

    public void setSpgId(Integer spgId) {
        this.spgId = spgId;
    }

    public String getSpgRef() {
        return spgRef;
    }

    public void setSpgRef(String spgRef) {
        this.spgRef = spgRef;
    }

    public String getSpgDescription() {
        return spgDescription;
    }

    public void setSpgDescription(String spgDescription) {
        this.spgDescription = spgDescription;
    }

    public String getSil() {
        return sil;
    }

    public void setSil(String sil) {
        this.sil = sil;
    }

    public MainProductGroup getMpgRef() {
        return mpgRef;
    }

    public void setMpgRef(MainProductGroup mpgRef) {
        this.mpgRef = mpgRef;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    @XmlTransient
    public Collection<SubProductComponents> getSubProductComponentsCollection() {
        return subProductComponentsCollection;
    }

    public void setSubProductComponentsCollection(Collection<SubProductComponents> subProductComponentsCollection) {
        this.subProductComponentsCollection = subProductComponentsCollection;
    }

    @XmlTransient
    public Collection<Sil> getSilCollection() {
        return silCollection;
    }

    public void setSilCollection(Collection<Sil> silCollection) {
        this.silCollection = silCollection;
    }

    @XmlTransient
    public Collection<Spghdr> getSpghdrCollection() {
        return spghdrCollection;
    }

    public void setSpghdrCollection(Collection<Spghdr> spghdrCollection) {
        this.spghdrCollection = spghdrCollection;
    }

    @XmlTransient
    public Collection<Ccfhdr> getCcfhdrCollection() {
        return ccfhdrCollection;
    }

    public void setCcfhdrCollection(Collection<Ccfhdr> ccfhdrCollection) {
        this.ccfhdrCollection = ccfhdrCollection;
    }

    @XmlTransient
    public Collection<SubProductFunctions> getSubProductFunctionsCollection() {
        return subProductFunctionsCollection;
    }

    public void setSubProductFunctionsCollection(Collection<SubProductFunctions> subProductFunctionsCollection) {
        this.subProductFunctionsCollection = subProductFunctionsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spgId != null ? spgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubProductGroup)) {
            return false;
        }
        SubProductGroup other = (SubProductGroup) object;
        if ((this.spgId == null && other.spgId != null) || (this.spgId != null && !this.spgId.equals(other.spgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.SubProductGroup[ spgId=" + spgId + " ]";
    }
    
}
