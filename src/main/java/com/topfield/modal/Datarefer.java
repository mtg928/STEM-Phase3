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
@Table(name = "datarefer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datarefer.findAll", query = "SELECT d FROM Datarefer d")
    , @NamedQuery(name = "Datarefer.findByRefid", query = "SELECT d FROM Datarefer d WHERE d.refid = :refid")
    , @NamedQuery(name = "Datarefer.findByType", query = "SELECT d FROM Datarefer d WHERE d.type = :type")
    , @NamedQuery(name = "Datarefer.findByItemId", query = "SELECT d FROM Datarefer d WHERE d.itemId = :itemId")
    , @NamedQuery(name = "Datarefer.findByDescription", query = "SELECT d FROM Datarefer d WHERE d.description = :description")
    , @NamedQuery(name = "Datarefer.findByRefurl", query = "SELECT d FROM Datarefer d WHERE d.refurl = :refurl")
    , @NamedQuery(name = "Datarefer.findByVersion", query = "SELECT d FROM Datarefer d WHERE d.version = :version")
    , @NamedQuery(name = "Datarefer.findByCaltype", query = "SELECT d FROM Datarefer d WHERE d.caltype = :caltype")})
public class Datarefer implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference")
    private Collection<Assumptions> assumptionsCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "refid")
    private Integer refid;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Column(name = "itemId")
    private String itemId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @Column(name = "section")
    private String section;
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @Column(name = "refurl")
    private String refurl;
    
    @Column(name = "version")
    private String version;
    @Column(name = "caltype")
    private String caltype;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference")
    private Collection<MainProductFunctions> mainProductFunctionsCollection;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference")
    private Collection<Calcfile> calcfileCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference")
    private Collection<Failuredata> failuredataCollection;

    public Datarefer() {
    }

    public Datarefer(Integer refid) {
        this.refid = refid;
    }

    public Datarefer(Integer refid, String type, String description, String refurl) {
        this.refid = refid;
        this.type = type;
        this.description = description;
        this.refurl = refurl;
    }

    public Integer getRefid() {
        return refid;
    }

    public void setRefid(Integer refid) {
        this.refid = refid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefurl() {
        return refurl;
    }

    public void setRefurl(String refurl) {
        this.refurl = refurl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCaltype() {
        return caltype;
    }

    public void setCaltype(String caltype) {
        this.caltype = caltype;
    }

    @XmlTransient
    public Collection<MainProductFunctions> getMainProductFunctionsCollection() {
        return mainProductFunctionsCollection;
    }

    public void setMainProductFunctionsCollection(Collection<MainProductFunctions> mainProductFunctionsCollection) {
        this.mainProductFunctionsCollection = mainProductFunctionsCollection;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    @XmlTransient
    public Collection<Calcfile> getCalcfileCollection() {
        return calcfileCollection;
    }

    public void setCalcfileCollection(Collection<Calcfile> calcfileCollection) {
        this.calcfileCollection = calcfileCollection;
    }

    @XmlTransient
    public Collection<Failuredata> getFailuredataCollection() {
        return failuredataCollection;
    }

    public void setFailuredataCollection(Collection<Failuredata> failuredataCollection) {
        this.failuredataCollection = failuredataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (refid != null ? refid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datarefer)) {
            return false;
        }
        Datarefer other = (Datarefer) object;
        if ((this.refid == null && other.refid != null) || (this.refid != null && !this.refid.equals(other.refid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Datarefer[ refid=" + refid + " ]";
    }

    @XmlTransient
    public Collection<Assumptions> getAssumptionsCollection() {
        return assumptionsCollection;
    }

    public void setAssumptionsCollection(Collection<Assumptions> assumptionsCollection) {
        this.assumptionsCollection = assumptionsCollection;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
