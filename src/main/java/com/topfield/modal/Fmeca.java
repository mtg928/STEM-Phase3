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
@Table(name = "fmeca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fmeca.findAll", query = "SELECT f FROM Fmeca f")
    , @NamedQuery(name = "Fmeca.findByFmecaId", query = "SELECT f FROM Fmeca f WHERE f.fmecaId = :fmecaId")
    , @NamedQuery(name = "Fmeca.findByFISubsysBreakCode", query = "SELECT f FROM Fmeca f WHERE f.fISubsysBreakCode = :fISubsysBreakCode")
    , @NamedQuery(name = "Fmeca.findByFISubsysComp", query = "SELECT f FROM Fmeca f WHERE f.fISubsysComp = :fISubsysComp")
    , @NamedQuery(name = "Fmeca.findByFIFun", query = "SELECT f FROM Fmeca f WHERE f.fIFun = :fIFun")
    , @NamedQuery(name = "Fmeca.findByFIPhases", query = "SELECT f FROM Fmeca f WHERE f.fIPhases = :fIPhases")
    , @NamedQuery(name = "Fmeca.findByFIFailmode", query = "SELECT f FROM Fmeca f WHERE f.fIFailmode = :fIFailmode")
    , @NamedQuery(name = "Fmeca.findByFIFailCause", query = "SELECT f FROM Fmeca f WHERE f.fIFailCause = :fIFailCause")
    , @NamedQuery(name = "Fmeca.findBySeverityclass", query = "SELECT f FROM Fmeca f WHERE f.severityclass = :severityclass")
    , @NamedQuery(name = "Fmeca.findByFELocSysLevel", query = "SELECT f FROM Fmeca f WHERE f.fELocSysLevel = :fELocSysLevel")
    , @NamedQuery(name = "Fmeca.findByFETrainSubsysLevel", query = "SELECT f FROM Fmeca f WHERE f.fETrainSubsysLevel = :fETrainSubsysLevel")
    , @NamedQuery(name = "Fmeca.findByFETrainLvl", query = "SELECT f FROM Fmeca f WHERE f.fETrainLvl = :fETrainLvl")
    , @NamedQuery(name = "Fmeca.findByDRMFaildetection", query = "SELECT f FROM Fmeca f WHERE f.dRMFaildetection = :dRMFaildetection")
    , @NamedQuery(name = "Fmeca.findByDRMPrevAndcompenmeas", query = "SELECT f FROM Fmeca f WHERE f.dRMPrevAndcompenmeas = :dRMPrevAndcompenmeas")
    , @NamedQuery(name = "Fmeca.findByFCFailProb", query = "SELECT f FROM Fmeca f WHERE f.fCFailProb = :fCFailProb")
    , @NamedQuery(name = "Fmeca.findByFCFailEffProb", query = "SELECT f FROM Fmeca f WHERE f.fCFailEffProb = :fCFailEffProb")
    , @NamedQuery(name = "Fmeca.findByFCFailModeRatio", query = "SELECT f FROM Fmeca f WHERE f.fCFailModeRatio = :fCFailModeRatio")
    , @NamedQuery(name = "Fmeca.findByFCFailureRate", query = "SELECT f FROM Fmeca f WHERE f.fCFailureRate = :fCFailureRate")
    , @NamedQuery(name = "Fmeca.findByFCUnrevealedfailurePro", query = "SELECT f FROM Fmeca f WHERE f.fCUnrevealedfailurePro = :fCUnrevealedfailurePro")
    , @NamedQuery(name = "Fmeca.findByFCOperatingTime", query = "SELECT f FROM Fmeca f WHERE f.fCOperatingTime = :fCOperatingTime")
    , @NamedQuery(name = "Fmeca.findByFCFailModeCri", query = "SELECT f FROM Fmeca f WHERE f.fCFailModeCri = :fCFailModeCri")
    , @NamedQuery(name = "Fmeca.findByUnrevealedfailure", query = "SELECT f FROM Fmeca f WHERE f.unrevealedfailure = :unrevealedfailure")
    , @NamedQuery(name = "Fmeca.findByFCFailItemCri", query = "SELECT f FROM Fmeca f WHERE f.fCFailItemCri = :fCFailItemCri")
    , @NamedQuery(name = "Fmeca.findByRemarks", query = "SELECT f FROM Fmeca f WHERE f.remarks = :remarks")
    , @NamedQuery(name = "Fmeca.findByComType", query = "SELECT f FROM Fmeca f WHERE f.comType = :comType")
    , @NamedQuery(name = "Fmeca.findByComId", query = "SELECT f FROM Fmeca f WHERE f.comId = :comId")})
public class Fmeca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FMECA_ID")
    private Integer fmecaId;
    @Column(name = "FI_Subsys_Break_Code")
    private String fISubsysBreakCode;
    @Column(name = "FI_Subsys_Comp")
    private String fISubsysComp;
    @Column(name = "FI_Fun")
    private String fIFun;
    @Column(name = "FI_Phases")
    private String fIPhases;
    @Column(name = "FI_Fail_mode")
    private String fIFailmode;
    @Column(name = "FI_Fail_Cause")
    private String fIFailCause;
    @Column(name = "severityclass")
    private String severityclass;
    @Column(name = "FE_Loc_Sys_Level")
    private String fELocSysLevel;
    @Column(name = "FE_Train_Subsys_Level")
    private String fETrainSubsysLevel;
    @Column(name = "FE_Train_Lvl")
    private String fETrainLvl;
    @Column(name = "DRM_Fail_detection")
    private String dRMFaildetection;
    @Column(name = "DRM_Prev_And_compen_meas")
    private String dRMPrevAndcompenmeas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   
    @Column(name = "FC_Fail_Prob")
    private Double fCFailProb;
    
    @Column(name = "failure_probability")
    private String failureProbability;
    
    @Column(name = "FC_Fail_Eff_Prob")
    private Double fCFailEffProb;
    @Column(name = "FC_Fail_Mode_Ratio")
    private Double fCFailModeRatio;
    @Column(name = "FC_Failure_Rate")
    private Double fCFailureRate;
    @Column(name = "FC_UnrevealedfailurePro")
    private Double fCUnrevealedfailurePro;
    @Column(name = "FC_Operating_Time")
    private Double fCOperatingTime;
    @Column(name = "FC_Fail_Mode_Cri")
    private Double fCFailModeCri;
    @Column(name = "Unrevealedfailure")
    private Double unrevealedfailure;
    @Column(name = "FC_Fail_Item_Cri")
    private Double fCFailItemCri;
    @Column(name = "remarks")
    private String remarks;
    @Basic(optional = false)
    @Column(name = "comType")
    private String comType;
    @Basic(optional = false)
    @Column(name = "comId")
    private int comId;
    @JoinColumn(name = "calcComp", referencedColumnName = "calcId")
    @ManyToOne(optional = false)
    private Calcfile calcComp;
    @JoinColumn(name = "FmecaHeader", referencedColumnName = "calchdrid")
    @ManyToOne(optional = false)
    private Calchdr fmecaHeader;

    public Fmeca() {
    }

    public Fmeca(Integer fmecaId) {
        this.fmecaId = fmecaId;
    }

    public Fmeca(Integer fmecaId, String comType, int comId) {
        this.fmecaId = fmecaId;
        this.comType = comType;
        this.comId = comId;
    }

    public Integer getFmecaId() {
        return fmecaId;
    }

    public void setFmecaId(Integer fmecaId) {
        this.fmecaId = fmecaId;
    }

    public String getFISubsysBreakCode() {
        return fISubsysBreakCode;
    }

    public void setFISubsysBreakCode(String fISubsysBreakCode) {
        this.fISubsysBreakCode = fISubsysBreakCode;
    }

    public String getFISubsysComp() {
        return fISubsysComp;
    }

    public void setFISubsysComp(String fISubsysComp) {
        this.fISubsysComp = fISubsysComp;
    }

    public String getFIFun() {
        return fIFun;
    }

    public void setFIFun(String fIFun) {
        this.fIFun = fIFun;
    }

    public String getFIPhases() {
        return fIPhases;
    }

    public void setFIPhases(String fIPhases) {
        this.fIPhases = fIPhases;
    }

    public String getFIFailmode() {
        return fIFailmode;
    }

    public void setFIFailmode(String fIFailmode) {
        this.fIFailmode = fIFailmode;
    }

    public String getFIFailCause() {
        return fIFailCause;
    }

    public void setFIFailCause(String fIFailCause) {
        this.fIFailCause = fIFailCause;
    }

    public String getSeverityclass() {
        return severityclass;
    }

    public void setSeverityclass(String severityclass) {
        this.severityclass = severityclass;
    }

    public String getFELocSysLevel() {
        return fELocSysLevel;
    }

    public void setFELocSysLevel(String fELocSysLevel) {
        this.fELocSysLevel = fELocSysLevel;
    }

    public String getFETrainSubsysLevel() {
        return fETrainSubsysLevel;
    }

    public void setFETrainSubsysLevel(String fETrainSubsysLevel) {
        this.fETrainSubsysLevel = fETrainSubsysLevel;
    }

    public String getFETrainLvl() {
        return fETrainLvl;
    }

    public void setFETrainLvl(String fETrainLvl) {
        this.fETrainLvl = fETrainLvl;
    }

    public String getDRMFaildetection() {
        return dRMFaildetection;
    }

    public void setDRMFaildetection(String dRMFaildetection) {
        this.dRMFaildetection = dRMFaildetection;
    }

    public String getDRMPrevAndcompenmeas() {
        return dRMPrevAndcompenmeas;
    }

    public void setDRMPrevAndcompenmeas(String dRMPrevAndcompenmeas) {
        this.dRMPrevAndcompenmeas = dRMPrevAndcompenmeas;
    }

  /*  public Double getFCFailProb() {
        return fCFailProb;
    }

    public void setFCFailProb(Double fCFailProb) {
        this.fCFailProb = fCFailProb;
    }*/
    
        /**
     * @return the failureProbability
     */
    public String getFailureProbability() {
        return failureProbability;
    }

    /**
     * @param failureProbability the failureProbability to set
     */
    public void setFailureProbability(String failureProbability) {
        this.failureProbability = failureProbability;
    }

    public Double getFCFailEffProb() {
        return fCFailEffProb;
    }

    public void setFCFailEffProb(Double fCFailEffProb) {
        this.fCFailEffProb = fCFailEffProb;
    }

    public Double getFCFailModeRatio() {
        return fCFailModeRatio;
    }

    public void setFCFailModeRatio(Double fCFailModeRatio) {
        this.fCFailModeRatio = fCFailModeRatio;
    }

    public Double getFCFailureRate() {
        return fCFailureRate;
    }

    public void setFCFailureRate(Double fCFailureRate) {
        this.fCFailureRate = fCFailureRate;
    }

    public Double getFCUnrevealedfailurePro() {
        return fCUnrevealedfailurePro;
    }

    public void setFCUnrevealedfailurePro(Double fCUnrevealedfailurePro) {
        this.fCUnrevealedfailurePro = fCUnrevealedfailurePro;
    }

    public Double getFCOperatingTime() {
        return fCOperatingTime;
    }

    public void setFCOperatingTime(Double fCOperatingTime) {
        this.fCOperatingTime = fCOperatingTime;
    }

    public Double getFCFailModeCri() {
        return fCFailModeCri;
    }

    public void setFCFailModeCri(Double fCFailModeCri) {
        this.fCFailModeCri = fCFailModeCri;
    }

    public Double getUnrevealedfailure() {
        return unrevealedfailure;
    }

    public void setUnrevealedfailure(Double unrevealedfailure) {
        this.unrevealedfailure = unrevealedfailure;
    }

    public Double getFCFailItemCri() {
        return fCFailItemCri;
    }

    public void setFCFailItemCri(Double fCFailItemCri) {
        this.fCFailItemCri = fCFailItemCri;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public Calcfile getCalcComp() {
        return calcComp;
    }

    public void setCalcComp(Calcfile calcComp) {
        this.calcComp = calcComp;
    }

    public Calchdr getFmecaHeader() {
        return fmecaHeader;
    }

    public void setFmecaHeader(Calchdr fmecaHeader) {
        this.fmecaHeader = fmecaHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fmecaId != null ? fmecaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fmeca)) {
            return false;
        }
        Fmeca other = (Fmeca) object;
        if ((this.fmecaId == null && other.fmecaId != null) || (this.fmecaId != null && !this.fmecaId.equals(other.fmecaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Fmeca[ fmecaId=" + fmecaId + " ]";
    }


    
}
