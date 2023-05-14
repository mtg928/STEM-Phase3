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
@Table(name = "calcfile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calcfile.findAll", query = "SELECT c FROM Calcfile c")
    , @NamedQuery(name = "Calcfile.findByCalcId", query = "SELECT c FROM Calcfile c WHERE c.calcId = :calcId")
    , @NamedQuery(name = "Calcfile.findByCompId", query = "SELECT c FROM Calcfile c WHERE c.compId = :compId")
    , @NamedQuery(name = "Calcfile.findByComponents", query = "SELECT c FROM Calcfile c WHERE c.components = :components")
    , @NamedQuery(name = "Calcfile.findByFailureRate", query = "SELECT c FROM Calcfile c WHERE c.failureRate = :failureRate")
    , @NamedQuery(name = "Calcfile.findByFailureMode", query = "SELECT c FROM Calcfile c WHERE c.failureMode = :failureMode")
    , @NamedQuery(name = "Calcfile.findByFMPercentage", query = "SELECT c FROM Calcfile c WHERE c.fMPercentage = :fMPercentage")
    , @NamedQuery(name = "Calcfile.findByURPercentage", query = "SELECT c FROM Calcfile c WHERE c.uRPercentage = :uRPercentage")
    , @NamedQuery(name = "Calcfile.findByRemarks", query = "SELECT c FROM Calcfile c WHERE c.remarks = :remarks")})
public class Calcfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calcId")
    private Integer calcId;
    @Column(name = "compId")
    private String compId;
    @Column(name = "components")
    private String components;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "failureRate")
    private Double failureRate;
    @Column(name = "probability")
    private Double probability;
    @Column(name = "FailurerateUnit")
    private String failurerateUnit;
    @Column(name = "failureMode")
    private String failureMode;
    @Column(name = "FMPercentage")
    private Double fMPercentage;
    @Column(name = "URPercentage")
    private Double uRPercentage;
    @Column(name = "Remarks")
    private String remarks;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calcComp")
    private Collection<Fmeca> fmecaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calcComp")
    private Collection<Fmea> fmeaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calcComp")
    private Collection<Faultdata> faultdataCollection;
    @JoinColumn(name = "calcHeader", referencedColumnName = "calcFunId")
    @ManyToOne(optional = false)
    private CalcFunctions calcHeader;
    @JoinColumn(name = "reference", referencedColumnName = "refid")
    @ManyToOne(optional = false)
    private Datarefer reference;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "calccomp")
    private Collection<Graphvertex> graphvertexCollection;

    public Calcfile() {
    }

    public Calcfile(Integer calcId) {
        this.calcId = calcId;
    }

    public Integer getCalcId() {
        return calcId;
    }

    public void setCalcId(Integer calcId) {
        this.calcId = calcId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public Double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(Double failureRate) {
        this.failureRate = failureRate;
    }
     public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public String getFailurerateUnit() {
        return failurerateUnit;
    }

    public void setFailurerateUnit(String failurerateUnit) {
        this.failurerateUnit = failurerateUnit;
    }

    public String getFailureMode() {
        return failureMode;
    }

    public void setFailureMode(String failureMode) {
        this.failureMode = failureMode;
    }

    public Double getFMPercentage() {
        return fMPercentage;
    }

    public void setFMPercentage(Double fMPercentage) {
        this.fMPercentage = fMPercentage;
    }

    public Double getURPercentage() {
        return uRPercentage;
    }

    public void setURPercentage(Double uRPercentage) {
        this.uRPercentage = uRPercentage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlTransient
    public Collection<Fmeca> getFmecaCollection() {
        return fmecaCollection;
    }

    public void setFmecaCollection(Collection<Fmeca> fmecaCollection) {
        this.fmecaCollection = fmecaCollection;
    }

    @XmlTransient
    public Collection<Fmea> getFmeaCollection() {
        return fmeaCollection;
    }

    public void setFmeaCollection(Collection<Fmea> fmeaCollection) {
        this.fmeaCollection = fmeaCollection;
    }

    @XmlTransient
    public Collection<Faultdata> getFaultdataCollection() {
        return faultdataCollection;
    }

    public void setFaultdataCollection(Collection<Faultdata> faultdataCollection) {
        this.faultdataCollection = faultdataCollection;
    }

    public CalcFunctions getCalcHeader() {
        return calcHeader;
    }

    public void setCalcHeader(CalcFunctions calcHeader) {
        this.calcHeader = calcHeader;
    }

    public Datarefer getReference() {
        return reference;
    }

    public void setReference(Datarefer reference) {
        this.reference = reference;
    }
    
    @XmlTransient
    public Collection<Graphvertex> getGraphvertexCollection() {
        return graphvertexCollection;
    }

    public void setGraphvertexCollection(Collection<Graphvertex> graphvertexCollection) {
        this.graphvertexCollection = graphvertexCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calcId != null ? calcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calcfile)) {
            return false;
        }
        Calcfile other = (Calcfile) object;
        if ((this.calcId == null && other.calcId != null) || (this.calcId != null && !this.calcId.equals(other.calcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Calcfile[ calcId=" + calcId + " ]";
    }
    
}
