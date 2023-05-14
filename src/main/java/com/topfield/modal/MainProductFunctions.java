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
@Table(name = "main_product_functions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MainProductFunctions.findAll", query = "SELECT m FROM MainProductFunctions m")
    , @NamedQuery(name = "MainProductFunctions.findByMpfId", query = "SELECT m FROM MainProductFunctions m WHERE m.mpfId = :mpfId")
    , @NamedQuery(name = "MainProductFunctions.findByFunId", query = "SELECT m FROM MainProductFunctions m WHERE m.funId = :funId")
    , @NamedQuery(name = "MainProductFunctions.findByFunctions", query = "SELECT m FROM MainProductFunctions m WHERE m.functions = :functions")
    , @NamedQuery(name = "MainProductFunctions.findBySilrms", query = "SELECT m FROM MainProductFunctions m WHERE m.silrms = :silrms")
    , @NamedQuery(name = "MainProductFunctions.findByContractSilTarget", query = "SELECT m FROM MainProductFunctions m WHERE m.contractSilTarget = :contractSilTarget")
    , @NamedQuery(name = "MainProductFunctions.findByLowdemandLower", query = "SELECT m FROM MainProductFunctions m WHERE m.lowdemandLower = :lowdemandLower")
    , @NamedQuery(name = "MainProductFunctions.findByLowdemandHigher", query = "SELECT m FROM MainProductFunctions m WHERE m.lowdemandHigher = :lowdemandHigher")
    , @NamedQuery(name = "MainProductFunctions.findByTHRLower", query = "SELECT m FROM MainProductFunctions m WHERE m.tHRLower = :tHRLower")
    , @NamedQuery(name = "MainProductFunctions.findByTHRHigher", query = "SELECT m FROM MainProductFunctions m WHERE m.tHRHigher = :tHRHigher")
    , @NamedQuery(name = "MainProductFunctions.findByDesignImplementation", query = "SELECT m FROM MainProductFunctions m WHERE m.designImplementation = :designImplementation")})
public class MainProductFunctions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mpfId")
    private Integer mpfId;
    @Column(name = "funId")
    private String funId;
    @Column(name = "functions")
    private String functions;
    @Column(name = "silrms")
    private String silrms;
    @Column(name = "contractSilTarget")
    private String contractSilTarget;
    @Column(name = "LowdemandLower")
    private String lowdemandLower;
    @Column(name = "LowdemandHigher")
    private String lowdemandHigher;
    @Column(name = "THRLower")
    private String tHRLower;
    @Column(name = "THRHigher")
    private String tHRHigher;
    @Column(name = "design_implementation")
    private String designImplementation;
    @JoinColumn(name = "reference", referencedColumnName = "refid")
    @ManyToOne(optional = false)
    private Datarefer reference;
    @JoinColumn(name = "mpg", referencedColumnName = "mpg_id")
    @ManyToOne(optional = false)
    private MainProductGroup mpg;

    public MainProductFunctions() {
    }

    public MainProductFunctions(Integer mpfId) {
        this.mpfId = mpfId;
    }

    public Integer getMpfId() {
        return mpfId;
    }

    public void setMpfId(Integer mpfId) {
        this.mpfId = mpfId;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getSilrms() {
        return silrms;
    }

    public void setSilrms(String silrms) {
        this.silrms = silrms;
    }

    public String getContractSilTarget() {
        return contractSilTarget;
    }

    public void setContractSilTarget(String contractSilTarget) {
        this.contractSilTarget = contractSilTarget;
    }

    public String getLowdemandLower() {
        return lowdemandLower;
    }

    public void setLowdemandLower(String lowdemandLower) {
        this.lowdemandLower = lowdemandLower;
    }

    public String getLowdemandHigher() {
        return lowdemandHigher;
    }

    public void setLowdemandHigher(String lowdemandHigher) {
        this.lowdemandHigher = lowdemandHigher;
    }

    public String getTHRLower() {
        return tHRLower;
    }

    public void setTHRLower(String tHRLower) {
        this.tHRLower = tHRLower;
    }

    public String getTHRHigher() {
        return tHRHigher;
    }

    public void setTHRHigher(String tHRHigher) {
        this.tHRHigher = tHRHigher;
    }

    public String getDesignImplementation() {
        return designImplementation;
    }

    public void setDesignImplementation(String designImplementation) {
        this.designImplementation = designImplementation;
    }

    public Datarefer getReference() {
        return reference;
    }

    public void setReference(Datarefer reference) {
        this.reference = reference;
    }

    public MainProductGroup getMpg() {
        return mpg;
    }

    public void setMpg(MainProductGroup mpg) {
        this.mpg = mpg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mpfId != null ? mpfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MainProductFunctions)) {
            return false;
        }
        MainProductFunctions other = (MainProductFunctions) object;
        if ((this.mpfId == null && other.mpfId != null) || (this.mpfId != null && !this.mpfId.equals(other.mpfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.MainProductFunctions[ mpfId=" + mpfId + " ]";
    }
    
}
