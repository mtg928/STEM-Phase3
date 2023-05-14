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
@Table(name = "components")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Components.findAll", query = "SELECT c FROM Components c")
    , @NamedQuery(name = "Components.findByComId", query = "SELECT c FROM Components c WHERE c.comId = :comId")
    , @NamedQuery(name = "Components.findByLamda", query = "SELECT c FROM Components c WHERE c.lamda = :lamda")
    , @NamedQuery(name = "Components.findByCcf", query = "SELECT c FROM Components c WHERE c.ccf = :ccf")
    , @NamedQuery(name = "Components.findBySafetyRelevantFactor", query = "SELECT c FROM Components c WHERE c.safetyRelevantFactor = :safetyRelevantFactor")
    , @NamedQuery(name = "Components.findByDc", query = "SELECT c FROM Components c WHERE c.dc = :dc")
    , @NamedQuery(name = "Components.findByMttr", query = "SELECT c FROM Components c WHERE c.mttr = :mttr")
    , @NamedQuery(name = "Components.findByPti", query = "SELECT c FROM Components c WHERE c.pti = :pti")})
public class Components implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "com_id")
    private Integer comId;
    @Lob
    @Column(name = "com_type")
    private String comType;
    @Lob
    @Column(name = "com_description")
    private String comDescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Lamda")
    private Double lamda;
    @Column(name = "CCF")
    private Double ccf;
    @Column(name = "Safety_Relevant_Factor")
    private Double safetyRelevantFactor;
    @Column(name = "DC")
    private Double dc;
    @Column(name = "MTTR")
    private Double mttr;
    @Column(name = "PTI")
    private Double pti;
    @Lob
    @Column(name = "failure_rate")
    private String failureRate;
    @Lob
    @Column(name = "manufacturer")
    private String manufacturer;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;

    public Components() {
    }

    public Components(Integer comId) {
        this.comId = comId;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    public String getComDescription() {
        return comDescription;
    }

    public void setComDescription(String comDescription) {
        this.comDescription = comDescription;
    }

    public Double getLamda() {
        return lamda;
    }

    public void setLamda(Double lamda) {
        this.lamda = lamda;
    }

    public Double getCcf() {
        return ccf;
    }

    public void setCcf(Double ccf) {
        this.ccf = ccf;
    }

    public Double getSafetyRelevantFactor() {
        return safetyRelevantFactor;
    }

    public void setSafetyRelevantFactor(Double safetyRelevantFactor) {
        this.safetyRelevantFactor = safetyRelevantFactor;
    }

    public Double getDc() {
        return dc;
    }

    public void setDc(Double dc) {
        this.dc = dc;
    }

    public Double getMttr() {
        return mttr;
    }

    public void setMttr(Double mttr) {
        this.mttr = mttr;
    }

    public Double getPti() {
        return pti;
    }

    public void setPti(Double pti) {
        this.pti = pti;
    }

    public String getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(String failureRate) {
        this.failureRate = failureRate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
        hash += (comId != null ? comId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Components)) {
            return false;
        }
        Components other = (Components) object;
        if ((this.comId == null && other.comId != null) || (this.comId != null && !this.comId.equals(other.comId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Components[ comId=" + comId + " ]";
    }
    
}
