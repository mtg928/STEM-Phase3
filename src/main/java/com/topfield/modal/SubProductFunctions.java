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
@Table(name = "sub_product_functions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubProductFunctions.findAll", query = "SELECT s FROM SubProductFunctions s")
    , @NamedQuery(name = "SubProductFunctions.findByFunId", query = "SELECT s FROM SubProductFunctions s WHERE s.funId = :funId")
    , @NamedQuery(name = "SubProductFunctions.findByMpgId", query = "SELECT s FROM SubProductFunctions s WHERE s.mpgId = :mpgId")
    , @NamedQuery(name = "SubProductFunctions.findByFunctions", query = "SELECT s FROM SubProductFunctions s WHERE s.functions = :functions")
    , @NamedQuery(name = "SubProductFunctions.findBySilRms", query = "SELECT s FROM SubProductFunctions s WHERE s.silRms = :silRms")
    , @NamedQuery(name = "SubProductFunctions.findBySilRmsGoa4", query = "SELECT s FROM SubProductFunctions s WHERE s.silRmsGoa4 = :silRmsGoa4")
    , @NamedQuery(name = "SubProductFunctions.findBySilRmsManual", query = "SELECT s FROM SubProductFunctions s WHERE s.silRmsManual = :silRmsManual")
    , @NamedQuery(name = "SubProductFunctions.findByDesignImplementation", query = "SELECT s FROM SubProductFunctions s WHERE s.designImplementation = :designImplementation")})
public class SubProductFunctions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fun_id")
    private Integer funId;
    @Column(name = "mpg_id")
    private Integer mpgId;
    @Column(name = "functions")
    private String functions;
    @Column(name = "sil_rms")
    private String silRms;
    @Column(name = "sil_rms_goa4")
    private String silRmsGoa4;
    @Column(name = "sil_rms_manual")
    private String silRmsManual;
    @Column(name = "design_implementation")
    private String designImplementation;
    @JoinColumn(name = "spg_id", referencedColumnName = "spg_id")
    @ManyToOne
    private SubProductGroup spgId;

    public SubProductFunctions() {
    }

    public SubProductFunctions(Integer funId) {
        this.funId = funId;
    }

    public Integer getFunId() {
        return funId;
    }

    public void setFunId(Integer funId) {
        this.funId = funId;
    }

    public Integer getMpgId() {
        return mpgId;
    }

    public void setMpgId(Integer mpgId) {
        this.mpgId = mpgId;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getSilRms() {
        return silRms;
    }

    public void setSilRms(String silRms) {
        this.silRms = silRms;
    }

    public String getSilRmsGoa4() {
        return silRmsGoa4;
    }

    public void setSilRmsGoa4(String silRmsGoa4) {
        this.silRmsGoa4 = silRmsGoa4;
    }

    public String getSilRmsManual() {
        return silRmsManual;
    }

    public void setSilRmsManual(String silRmsManual) {
        this.silRmsManual = silRmsManual;
    }

    public String getDesignImplementation() {
        return designImplementation;
    }

    public void setDesignImplementation(String designImplementation) {
        this.designImplementation = designImplementation;
    }

    public SubProductGroup getSpgId() {
        return spgId;
    }

    public void setSpgId(SubProductGroup spgId) {
        this.spgId = spgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funId != null ? funId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubProductFunctions)) {
            return false;
        }
        SubProductFunctions other = (SubProductFunctions) object;
        if ((this.funId == null && other.funId != null) || (this.funId != null && !this.funId.equals(other.funId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.SubProductFunctions[ funId=" + funId + " ]";
    }
    
}
