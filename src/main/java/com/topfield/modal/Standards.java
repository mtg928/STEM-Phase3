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
@Table(name = "standards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Standards.findAll", query = "SELECT s FROM Standards s")
    , @NamedQuery(name = "Standards.findById", query = "SELECT s FROM Standards s WHERE s.id = :id")
    , @NamedQuery(name = "Standards.findByCode", query = "SELECT s FROM Standards s WHERE s.code = :code")
    , @NamedQuery(name = "Standards.findByCalculatorType", query = "SELECT s FROM Standards s WHERE s.calculatorType = :calculatorType")
    , @NamedQuery(name = "Standards.findByDescription", query = "SELECT s FROM Standards s WHERE s.description = :description")})
public class Standards implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standard")
    private Collection<Calchdr> calchdrCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @Column(name = "calculatorType")
    private String calculatorType;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standard")
    private Collection<Spchdr> spchdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standard")
    private Collection<Mpghdr> mpghdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standard")
    private Collection<Spghdr> spghdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standard")
    private Collection<Spfhdr> spfhdrCollection;

    public Standards() {
    }

    public Standards(Integer id) {
        this.id = id;
    }

    public Standards(Integer id, String code, String calculatorType) {
        this.id = id;
        this.code = code;
        this.calculatorType = calculatorType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCalculatorType() {
        return calculatorType;
    }

    public void setCalculatorType(String calculatorType) {
        this.calculatorType = calculatorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Spchdr> getSpchdrCollection() {
        return spchdrCollection;
    }

    public void setSpchdrCollection(Collection<Spchdr> spchdrCollection) {
        this.spchdrCollection = spchdrCollection;
    }

    @XmlTransient
    public Collection<Mpghdr> getMpghdrCollection() {
        return mpghdrCollection;
    }

    public void setMpghdrCollection(Collection<Mpghdr> mpghdrCollection) {
        this.mpghdrCollection = mpghdrCollection;
    }

    @XmlTransient
    public Collection<Spghdr> getSpghdrCollection() {
        return spghdrCollection;
    }

    public void setSpghdrCollection(Collection<Spghdr> spghdrCollection) {
        this.spghdrCollection = spghdrCollection;
    }

    @XmlTransient
    public Collection<Spfhdr> getSpfhdrCollection() {
        return spfhdrCollection;
    }

    public void setSpfhdrCollection(Collection<Spfhdr> spfhdrCollection) {
        this.spfhdrCollection = spfhdrCollection;
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
        if (!(object instanceof Standards)) {
            return false;
        }
        Standards other = (Standards) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Standards[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Calchdr> getCalchdrCollection() {
        return calchdrCollection;
    }

    public void setCalchdrCollection(Collection<Calchdr> calchdrCollection) {
        this.calchdrCollection = calchdrCollection;
    }
    
}
