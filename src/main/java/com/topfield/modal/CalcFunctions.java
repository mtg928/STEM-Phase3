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
@Table(name = "calcFunctions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalcFunctions.findAll", query = "SELECT c FROM CalcFunctions c")
    , @NamedQuery(name = "CalcFunctions.findByCalcFunId", query = "SELECT c FROM CalcFunctions c WHERE c.calcFunId = :calcFunId")
    , @NamedQuery(name = "CalcFunctions.findByCalcFunType", query = "SELECT c FROM CalcFunctions c WHERE c.calcFunType = :calcFunType")
    , @NamedQuery(name = "CalcFunctions.findByFunId", query = "SELECT c FROM CalcFunctions c WHERE c.funId = :funId")
    , @NamedQuery(name = "CalcFunctions.findByCalcFunction", query = "SELECT c FROM CalcFunctions c WHERE c.calcFunction = :calcFunction")
    , @NamedQuery(name = "CalcFunctions.findBySil", query = "SELECT c FROM CalcFunctions c WHERE c.sil = :sil")
    , @NamedQuery(name = "CalcFunctions.findBySilTargetFrom", query = "SELECT c FROM CalcFunctions c WHERE c.silTargetFrom = :silTargetFrom")
    , @NamedQuery(name = "CalcFunctions.findBySilTargetTo", query = "SELECT c FROM CalcFunctions c WHERE c.silTargetTo = :silTargetTo")
    , @NamedQuery(name = "CalcFunctions.findByRemarks", query = "SELECT c FROM CalcFunctions c WHERE c.remarks = :remarks")})
public class CalcFunctions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calcFunId")
    private Integer calcFunId;
    @Column(name = "calcFunType")
    private String calcFunType;
    @Column(name = "funId")
    private String funId;
    @Column(name = "calcFunction")
    private String calcFunction;
    @Column(name = "sil")
    private String sil;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "silTargetFrom")
    private Double silTargetFrom;
    @Column(name = "silTargetTo")
    private Double silTargetTo;
    @Column(name = "Remarks")
    private String remarks;
    @JoinColumn(name = "mpgheader", referencedColumnName = "mpghdrid")
    @ManyToOne(optional = false)
    private Mpghdr mpgheader;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calcHeader")
    private Collection<Calcfile> calcfileCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spfid")
    private Collection<Spfhdr> spfhdrCollection;

    public CalcFunctions() {
    }

    public CalcFunctions(Integer calcFunId) {
        this.calcFunId = calcFunId;
    }

    public Integer getCalcFunId() {
        return calcFunId;
    }

    public void setCalcFunId(Integer calcFunId) {
        this.calcFunId = calcFunId;
    }

    public String getCalcFunType() {
        return calcFunType;
    }

    public void setCalcFunType(String calcFunType) {
        this.calcFunType = calcFunType;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public String getCalcFunction() {
        return calcFunction;
    }

    public void setCalcFunction(String calcFunction) {
        this.calcFunction = calcFunction;
    }

    public String getSil() {
        return sil;
    }

    public void setSil(String sil) {
        this.sil = sil;
    }

    public Double getSilTargetFrom() {
        return silTargetFrom;
    }

    public void setSilTargetFrom(Double silTargetFrom) {
        this.silTargetFrom = silTargetFrom;
    }

    public Double getSilTargetTo() {
        return silTargetTo;
    }

    public void setSilTargetTo(Double silTargetTo) {
        this.silTargetTo = silTargetTo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Mpghdr getMpgheader() {
        return mpgheader;
    }

    public void setMpgheader(Mpghdr mpgheader) {
        this.mpgheader = mpgheader;
    }

    @XmlTransient
    public Collection<Calcfile> getCalcfileCollection() {
        return calcfileCollection;
    }

    public void setCalcfileCollection(Collection<Calcfile> calcfileCollection) {
        this.calcfileCollection = calcfileCollection;
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
        hash += (calcFunId != null ? calcFunId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalcFunctions)) {
            return false;
        }
        CalcFunctions other = (CalcFunctions) object;
        if ((this.calcFunId == null && other.calcFunId != null) || (this.calcFunId != null && !this.calcFunId.equals(other.calcFunId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.CalcFunctions[ calcFunId=" + calcFunId + " ]";
    }
    
}
