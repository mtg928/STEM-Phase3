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
@Table(name = "main_product_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MainProductGroup.findAll", query = "SELECT m FROM MainProductGroup m")
    , @NamedQuery(name = "MainProductGroup.findByMpgId", query = "SELECT m FROM MainProductGroup m WHERE m.mpgId = :mpgId")
    , @NamedQuery(name = "MainProductGroup.findByCriModePro", query = "SELECT m FROM MainProductGroup m WHERE m.criModePro = :criModePro")})
public class MainProductGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mpg_id")
    private Integer mpgId;
    @Lob
    @Column(name = "mpg_ref")
    private String mpgRef;
    @Lob
    @Column(name = "mpg_description")
    private String mpgDescription;
    @Lob
    @Column(name = "fss")
    private String fss;
    @Lob
    @Column(name = "type")
    private String type;
    @Column(name = "Cri_Mode_Pro")
    private String criModePro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mpg")
    private Collection<MainProductFunctions> mainProductFunctionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmeaComponent")
    private Collection<Mpghdr> mpghdrCollection;
    @OneToMany(mappedBy = "mpgRef")
    private Collection<SubProductGroup> subProductGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mpgRef")
    private Collection<MainProductComponents> mainProductComponentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faultComponent")
    private Collection<Faulttree> faulttreeCollection;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;

    public MainProductGroup() {
    }

    public MainProductGroup(Integer mpgId) {
        this.mpgId = mpgId;
    }

    public Integer getMpgId() {
        return mpgId;
    }

    public void setMpgId(Integer mpgId) {
        this.mpgId = mpgId;
    }

    public String getMpgRef() {
        return mpgRef;
    }

    public void setMpgRef(String mpgRef) {
        this.mpgRef = mpgRef;
    }

    public String getMpgDescription() {
        return mpgDescription;
    }

    public void setMpgDescription(String mpgDescription) {
        this.mpgDescription = mpgDescription;
    }

    public String getFss() {
        return fss;
    }

    public void setFss(String fss) {
        this.fss = fss;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCriModePro() {
        return criModePro;
    }

    public void setCriModePro(String criModePro) {
        this.criModePro = criModePro;
    }

    @XmlTransient
    public Collection<MainProductFunctions> getMainProductFunctionsCollection() {
        return mainProductFunctionsCollection;
    }

    public void setMainProductFunctionsCollection(Collection<MainProductFunctions> mainProductFunctionsCollection) {
        this.mainProductFunctionsCollection = mainProductFunctionsCollection;
    }

    @XmlTransient
    public Collection<Mpghdr> getMpghdrCollection() {
        return mpghdrCollection;
    }

    public void setMpghdrCollection(Collection<Mpghdr> mpghdrCollection) {
        this.mpghdrCollection = mpghdrCollection;
    }

    @XmlTransient
    public Collection<SubProductGroup> getSubProductGroupCollection() {
        return subProductGroupCollection;
    }

    public void setSubProductGroupCollection(Collection<SubProductGroup> subProductGroupCollection) {
        this.subProductGroupCollection = subProductGroupCollection;
    }

    @XmlTransient
    public Collection<MainProductComponents> getMainProductComponentsCollection() {
        return mainProductComponentsCollection;
    }

    public void setMainProductComponentsCollection(Collection<MainProductComponents> mainProductComponentsCollection) {
        this.mainProductComponentsCollection = mainProductComponentsCollection;
    }

    @XmlTransient
    public Collection<Faulttree> getFaulttreeCollection() {
        return faulttreeCollection;
    }

    public void setFaulttreeCollection(Collection<Faulttree> faulttreeCollection) {
        this.faulttreeCollection = faulttreeCollection;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mpgId != null ? mpgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MainProductGroup)) {
            return false;
        }
        MainProductGroup other = (MainProductGroup) object;
        if ((this.mpgId == null && other.mpgId != null) || (this.mpgId != null && !this.mpgId.equals(other.mpgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.MainProductGroup[ mpgId=" + mpgId + " ]";
    }
    
}
