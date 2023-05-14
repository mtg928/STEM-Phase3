/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "fault_tree_analysis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FaultTreeAnalysis.findAll", query = "SELECT f FROM FaultTreeAnalysis f")
    , @NamedQuery(name = "FaultTreeAnalysis.findByFTid", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.fTid = :fTid")
    , @NamedQuery(name = "FaultTreeAnalysis.findByFTType", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.fTType = :fTType")
    , @NamedQuery(name = "FaultTreeAnalysis.findByFTName", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.fTName = :fTName")
    , @NamedQuery(name = "FaultTreeAnalysis.findByDataRef", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.dataRef = :dataRef")
    , @NamedQuery(name = "FaultTreeAnalysis.findByDescription", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.description = :description")
    , @NamedQuery(name = "FaultTreeAnalysis.findByFrequency", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.frequency = :frequency")
    , @NamedQuery(name = "FaultTreeAnalysis.findByProbability", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.probability = :probability")
    , @NamedQuery(name = "FaultTreeAnalysis.findByRepairTime", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.repairTime = :repairTime")
    , @NamedQuery(name = "FaultTreeAnalysis.findByDataType", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.dataType = :dataType")
    , @NamedQuery(name = "FaultTreeAnalysis.findByDistribution", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.distribution = :distribution")
    , @NamedQuery(name = "FaultTreeAnalysis.findBySpread", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.spread = :spread")
    , @NamedQuery(name = "FaultTreeAnalysis.findByFTPage", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.fTPage = :fTPage")
    , @NamedQuery(name = "FaultTreeAnalysis.findByVersion", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.version = :version")
    , @NamedQuery(name = "FaultTreeAnalysis.findByCreateDate", query = "SELECT f FROM FaultTreeAnalysis f WHERE f.createDate = :createDate")})
public class FaultTreeAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FT_id")
    private Integer fTid;
    @Basic(optional = false)
    @Column(name = "FT_Type")
    private String fTType;
    @Basic(optional = false)
    @Column(name = "FT_Name")
    private String fTName;
    @Basic(optional = false)
    @Column(name = "DataRef")
    private String dataRef;
    @Basic(optional = false)
    @Column(name = "Description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Frequency")
    private Double frequency;
    @Column(name = "Probability")
    private Double probability;
    @Column(name = "Repair_Time")
    private Double repairTime;
    @Basic(optional = false)
    @Column(name = "Data_Type")
    private String dataType;
    @Basic(optional = false)
    @Column(name = "Distribution")
    private String distribution;
    @Column(name = "Spread")
    private Double spread;
    @Basic(optional = false)
    @Column(name = "FT_Page")
    private String fTPage;
    @Column(name = "version")
    private String version;
    @Column(name = "CreateDate")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    public FaultTreeAnalysis() {
    }

    public FaultTreeAnalysis(Integer fTid) {
        this.fTid = fTid;
    }

    public FaultTreeAnalysis(Integer fTid, String fTType, String fTName, String dataRef, String description, String dataType, String distribution, String fTPage) {
        this.fTid = fTid;
        this.fTType = fTType;
        this.fTName = fTName;
        this.dataRef = dataRef;
        this.description = description;
        this.dataType = dataType;
        this.distribution = distribution;
        this.fTPage = fTPage;
    }

    public Integer getFTid() {
        return fTid;
    }

    public void setFTid(Integer fTid) {
        this.fTid = fTid;
    }

    public String getFTType() {
        return fTType;
    }

    public void setFTType(String fTType) {
        this.fTType = fTType;
    }

    public String getFTName() {
        return fTName;
    }

    public void setFTName(String fTName) {
        this.fTName = fTName;
    }

    public String getDataRef() {
        return dataRef;
    }

    public void setDataRef(String dataRef) {
        this.dataRef = dataRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Double repairTime) {
        this.repairTime = repairTime;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public String getFTPage() {
        return fTPage;
    }

    public void setFTPage(String fTPage) {
        this.fTPage = fTPage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fTid != null ? fTid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FaultTreeAnalysis)) {
            return false;
        }
        FaultTreeAnalysis other = (FaultTreeAnalysis) object;
        if ((this.fTid == null && other.fTid != null) || (this.fTid != null && !this.fTid.equals(other.fTid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.FaultTreeAnalysis[ fTid=" + fTid + " ]";
    }
    
}
