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
 * @author MalcolmH
 * Multiple named queries in FMEA table
 */
@Entity
@Table(name = "fmea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fmea.findAll", query = "SELECT f FROM Fmea f")
    , @NamedQuery(name = "Fmea.findByFmeaId", query = "SELECT f FROM Fmea f WHERE f.fmeaId = :fmeaId")
    , @NamedQuery(name = "Fmea.findByFISubsysBreakdownCode", query = "SELECT f FROM Fmea f WHERE f.fISubsysBreakdownCode = :fISubsysBreakdownCode")
    , @NamedQuery(name = "Fmea.findByFISubsysComponent", query = "SELECT f FROM Fmea f WHERE f.fISubsysComponent = :fISubsysComponent")
    , @NamedQuery(name = "Fmea.findByFIFunctions", query = "SELECT f FROM Fmea f WHERE f.fIFunctions = :fIFunctions")
    , @NamedQuery(name = "Fmea.findByFIPhase", query = "SELECT f FROM Fmea f WHERE f.fIPhase = :fIPhase")
    , @NamedQuery(name = "Fmea.findByFIFailuremode", query = "SELECT f FROM Fmea f WHERE f.fIFailuremode = :fIFailuremode")
    , @NamedQuery(name = "Fmea.findByFIFailureCause", query = "SELECT f FROM Fmea f WHERE f.fIFailureCause = :fIFailureCause")
    , @NamedQuery(name = "Fmea.findByFELocalSystemLevel", query = "SELECT f FROM Fmea f WHERE f.fELocalSystemLevel = :fELocalSystemLevel")
    , @NamedQuery(name = "Fmea.findByFETrainSubsystemLevel", query = "SELECT f FROM Fmea f WHERE f.fETrainSubsystemLevel = :fETrainSubsystemLevel")
    , @NamedQuery(name = "Fmea.findByFETrainLevel", query = "SELECT f FROM Fmea f WHERE f.fETrainLevel = :fETrainLevel")
    , @NamedQuery(name = "Fmea.findByFEImportanceB", query = "SELECT f FROM Fmea f WHERE f.fEImportanceB = :fEImportanceB")
    , @NamedQuery(name = "Fmea.findByDRMFailuredetection", query = "SELECT f FROM Fmea f WHERE f.dRMFailuredetection = :dRMFailuredetection")
    , @NamedQuery(name = "Fmea.findByDRMPrevAndcompenmeasures", query = "SELECT f FROM Fmea f WHERE f.dRMPrevAndcompenmeasures = :dRMPrevAndcompenmeasures")
    , @NamedQuery(name = "Fmea.findByDRMOccurrenceA", query = "SELECT f FROM Fmea f WHERE f.dRMOccurrenceA = :dRMOccurrenceA")
    , @NamedQuery(name = "Fmea.findByDRMDetectionmeasures", query = "SELECT f FROM Fmea f WHERE f.dRMDetectionmeasures = :dRMDetectionmeasures")
    , @NamedQuery(name = "Fmea.findByDRMDetectionE", query = "SELECT f FROM Fmea f WHERE f.dRMDetectionE = :dRMDetectionE")
    , @NamedQuery(name = "Fmea.findByDrmRpz", query = "SELECT f FROM Fmea f WHERE f.drmRpz = :drmRpz")
    , @NamedQuery(name = "Fmea.findByDRMImprovementmeasures", query = "SELECT f FROM Fmea f WHERE f.dRMImprovementmeasures = :dRMImprovementmeasures")
    , @NamedQuery(name = "Fmea.findByDRMResponsibleDate", query = "SELECT f FROM Fmea f WHERE f.dRMResponsibleDate = :dRMResponsibleDate")
    , @NamedQuery(name = "Fmea.findByIRUndertakenmeasures", query = "SELECT f FROM Fmea f WHERE f.iRUndertakenmeasures = :iRUndertakenmeasures")
    , @NamedQuery(name = "Fmea.findByIRImportanceB", query = "SELECT f FROM Fmea f WHERE f.iRImportanceB = :iRImportanceB")
    , @NamedQuery(name = "Fmea.findByIROccurrenceA", query = "SELECT f FROM Fmea f WHERE f.iROccurrenceA = :iROccurrenceA")
    , @NamedQuery(name = "Fmea.findByIRDetectionE", query = "SELECT f FROM Fmea f WHERE f.iRDetectionE = :iRDetectionE")
    , @NamedQuery(name = "Fmea.findByIrRpz", query = "SELECT f FROM Fmea f WHERE f.irRpz = :irRpz")
    , @NamedQuery(name = "Fmea.findByRemarks", query = "SELECT f FROM Fmea f WHERE f.remarks = :remarks")
    , @NamedQuery(name = "Fmea.findByComType", query = "SELECT f FROM Fmea f WHERE f.comType = :comType")
    , @NamedQuery(name = "Fmea.findByComId", query = "SELECT f FROM Fmea f WHERE f.comId = :comId")})
public class Fmea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FMEA_ID")
    private Integer fmeaId;
    @Column(name = "FI_Subsys_Breakdown_Code")
    private String fISubsysBreakdownCode;
    @Column(name = "FI_Subsys_Component")
    private String fISubsysComponent;
    @Column(name = "FI_Functions")
    private String fIFunctions;
    @Column(name = "FI_Phase")
    private String fIPhase;
    @Column(name = "FI_Failure_mode")
    private String fIFailuremode;
    @Column(name = "FI_Failure_Cause")
    private String fIFailureCause;
    @Column(name = "FE_Local_System_Level")
    private String fELocalSystemLevel;
    @Column(name = "FE_Train_Subsystem_Level")
    private String fETrainSubsystemLevel;
    @Column(name = "FE_Train_Level")
    private String fETrainLevel;
    @Column(name = "FE_Importance_B")
    private Integer fEImportanceB;
    @Column(name = "DRM_Failure_detection")
    private String dRMFailuredetection;
    @Column(name = "DRM_Prev_And_compen_measures")
    private String dRMPrevAndcompenmeasures;
    @Column(name = "DRM_Occurrence_A")
    private Integer dRMOccurrenceA;
    @Column(name = "DRM_Detection_measures")
    private String dRMDetectionmeasures;
    @Column(name = "DRM_Detection_E")
    private Integer dRMDetectionE;
    @Column(name = "DRM_RPZ")
    private Integer drmRpz;
    @Column(name = "DRM_Improvement_measures")
    private String dRMImprovementmeasures;
    @Column(name = "DRM_Responsible_Date")
    private String dRMResponsibleDate;
    @Column(name = "IR_Undertaken_measures")
    private String iRUndertakenmeasures;
    @Column(name = "IR_Importance_B")
    private Integer iRImportanceB;
    @Column(name = "IR_Occurrence_A")
    private Integer iROccurrenceA;
    @Column(name = "IR_Detection_E")
    private Integer iRDetectionE;
    @Column(name = "IR_RPZ")
    private Integer irRpz;
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
    @JoinColumn(name = "FmeaHeader", referencedColumnName = "calchdrid")
    @ManyToOne(optional = false)
    private Calchdr fmeaHeader;

    public Fmea() {
    }

    public Fmea(Integer fmeaId) {
        this.fmeaId = fmeaId;
    }

    public Fmea(Integer fmeaId, String comType, int comId) {
        this.fmeaId = fmeaId;
        this.comType = comType;
        this.comId = comId;
    }

    public Integer getFmeaId() {
        return fmeaId;
    }

    public void setFmeaId(Integer fmeaId) {
        this.fmeaId = fmeaId;
    }

    public String getFISubsysBreakdownCode() {
        return fISubsysBreakdownCode;
    }

    public void setFISubsysBreakdownCode(String fISubsysBreakdownCode) {
        this.fISubsysBreakdownCode = fISubsysBreakdownCode;
    }

    public String getFISubsysComponent() {
        return fISubsysComponent;
    }

    public void setFISubsysComponent(String fISubsysComponent) {
        this.fISubsysComponent = fISubsysComponent;
    }

    public String getFIFunctions() {
        return fIFunctions;
    }

    public void setFIFunctions(String fIFunctions) {
        this.fIFunctions = fIFunctions;
    }

    public String getFIPhase() {
        return fIPhase;
    }

    public void setFIPhase(String fIPhase) {
        this.fIPhase = fIPhase;
    }

    public String getFIFailuremode() {
        return fIFailuremode;
    }

    public void setFIFailuremode(String fIFailuremode) {
        this.fIFailuremode = fIFailuremode;
    }

    public String getFIFailureCause() {
        return fIFailureCause;
    }

    public void setFIFailureCause(String fIFailureCause) {
        this.fIFailureCause = fIFailureCause;
    }

    public String getFELocalSystemLevel() {
        return fELocalSystemLevel;
    }

    public void setFELocalSystemLevel(String fELocalSystemLevel) {
        this.fELocalSystemLevel = fELocalSystemLevel;
    }

    public String getFETrainSubsystemLevel() {
        return fETrainSubsystemLevel;
    }

    public void setFETrainSubsystemLevel(String fETrainSubsystemLevel) {
        this.fETrainSubsystemLevel = fETrainSubsystemLevel;
    }

    public String getFETrainLevel() {
        return fETrainLevel;
    }

    public void setFETrainLevel(String fETrainLevel) {
        this.fETrainLevel = fETrainLevel;
    }

    public Integer getFEImportanceB() {
        return fEImportanceB;
    }

    public void setFEImportanceB(Integer fEImportanceB) {
        this.fEImportanceB = fEImportanceB;
    }

    public String getDRMFailuredetection() {
        return dRMFailuredetection;
    }

    public void setDRMFailuredetection(String dRMFailuredetection) {
        this.dRMFailuredetection = dRMFailuredetection;
    }

    public String getDRMPrevAndcompenmeasures() {
        return dRMPrevAndcompenmeasures;
    }

    public void setDRMPrevAndcompenmeasures(String dRMPrevAndcompenmeasures) {
        this.dRMPrevAndcompenmeasures = dRMPrevAndcompenmeasures;
    }

    public Integer getDRMOccurrenceA() {
        return dRMOccurrenceA;
    }

    public void setDRMOccurrenceA(Integer dRMOccurrenceA) {
        this.dRMOccurrenceA = dRMOccurrenceA;
    }

    public String getDRMDetectionmeasures() {
        return dRMDetectionmeasures;
    }

    public void setDRMDetectionmeasures(String dRMDetectionmeasures) {
        this.dRMDetectionmeasures = dRMDetectionmeasures;
    }

    public Integer getDRMDetectionE() {
        return dRMDetectionE;
    }

    public void setDRMDetectionE(Integer dRMDetectionE) {
        this.dRMDetectionE = dRMDetectionE;
    }

    public Integer getDrmRpz() {
        return drmRpz;
    }

    public void setDrmRpz(Integer drmRpz) {
        this.drmRpz = drmRpz;
    }

    public String getDRMImprovementmeasures() {
        return dRMImprovementmeasures;
    }

    public void setDRMImprovementmeasures(String dRMImprovementmeasures) {
        this.dRMImprovementmeasures = dRMImprovementmeasures;
    }

    public String getDRMResponsibleDate() {
        return dRMResponsibleDate;
    }

    public void setDRMResponsibleDate(String dRMResponsibleDate) {
        this.dRMResponsibleDate = dRMResponsibleDate;
    }

    public String getIRUndertakenmeasures() {
        return iRUndertakenmeasures;
    }

    public void setIRUndertakenmeasures(String iRUndertakenmeasures) {
        this.iRUndertakenmeasures = iRUndertakenmeasures;
    }

    public Integer getIRImportanceB() {
        return iRImportanceB;
    }

    public void setIRImportanceB(Integer iRImportanceB) {
        this.iRImportanceB = iRImportanceB;
    }

    public Integer getIROccurrenceA() {
        return iROccurrenceA;
    }

    public void setIROccurrenceA(Integer iROccurrenceA) {
        this.iROccurrenceA = iROccurrenceA;
    }

    public Integer getIRDetectionE() {
        return iRDetectionE;
    }

    public void setIRDetectionE(Integer iRDetectionE) {
        this.iRDetectionE = iRDetectionE;
    }

    public Integer getIrRpz() {
        return irRpz;
    }

    public void setIrRpz(Integer irRpz) {
        this.irRpz = irRpz;
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

    public Calchdr getFmeaHeader() {
        return fmeaHeader;
    }

    public void setFmeaHeader(Calchdr fmeaHeader) {
        this.fmeaHeader = fmeaHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fmeaId != null ? fmeaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fmea)) {
            return false;
        }
        Fmea other = (Fmea) object;
        if ((this.fmeaId == null && other.fmeaId != null) || (this.fmeaId != null && !this.fmeaId.equals(other.fmeaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Fmea[ fmeaId=" + fmeaId + " ]";
    }
    
}
