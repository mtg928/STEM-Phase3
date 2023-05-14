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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sil.findAll", query = "SELECT s FROM Sil s")
    , @NamedQuery(name = "Sil.findBySilId", query = "SELECT s FROM Sil s WHERE s.silId = :silId")
    , @NamedQuery(name = "Sil.findBySilFunction", query = "SELECT s FROM Sil s WHERE s.silFunction = :silFunction")
    , @NamedQuery(name = "Sil.findByArchitecture", query = "SELECT s FROM Sil s WHERE s.architecture = :architecture")
    , @NamedQuery(name = "Sil.findBySensors", query = "SELECT s FROM Sil s WHERE s.sensors = :sensors")
    , @NamedQuery(name = "Sil.findBySensorsScore", query = "SELECT s FROM Sil s WHERE s.sensorsScore = :sensorsScore")
    , @NamedQuery(name = "Sil.findByController", query = "SELECT s FROM Sil s WHERE s.controller = :controller")
    , @NamedQuery(name = "Sil.findByControllerScore", query = "SELECT s FROM Sil s WHERE s.controllerScore = :controllerScore")
    , @NamedQuery(name = "Sil.findByFinalElement", query = "SELECT s FROM Sil s WHERE s.finalElement = :finalElement")
    , @NamedQuery(name = "Sil.findByFinalElementScore", query = "SELECT s FROM Sil s WHERE s.finalElementScore = :finalElementScore")
    , @NamedQuery(name = "Sil.findByTotalScore", query = "SELECT s FROM Sil s WHERE s.totalScore = :totalScore")
    , @NamedQuery(name = "Sil.findByCreateddate", query = "SELECT s FROM Sil s WHERE s.createddate = :createddate")})
public class Sil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SIL_ID")
    private Integer silId;
    @Column(name = "silFunction")
    private String silFunction;
    @Column(name = "Architecture")
    private String architecture;
    @Column(name = "Sensors")
    private String sensors;
    @Column(name = "SensorsScore")
    private String sensorsScore;
    @Column(name = "Controller")
    private String controller;
    @Column(name = "ControllerScore")
    private String controllerScore;
    @Column(name = "FinalElement")
    private String finalElement;
    @Column(name = "FinalElementScore")
    private String finalElementScore;
    @Column(name = "TotalScore")
    private String totalScore;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.DATE)
    private Date createddate;
    @JoinColumn(name = "SIL_SubComponent", referencedColumnName = "spg_id")
    @ManyToOne(optional = false)
    private SubProductGroup sILSubComponent;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "siluser", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users siluser;

    public Sil() {
    }

    public Sil(Integer silId) {
        this.silId = silId;
    }

    public Sil(Integer silId, Date createddate) {
        this.silId = silId;
        this.createddate = createddate;
    }

    public Integer getSilId() {
        return silId;
    }

    public void setSilId(Integer silId) {
        this.silId = silId;
    }

    public String getSilFunction() {
        return silFunction;
    }

    public void setSilFunction(String silFunction) {
        this.silFunction = silFunction;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getSensors() {
        return sensors;
    }

    public void setSensors(String sensors) {
        this.sensors = sensors;
    }

    public String getSensorsScore() {
        return sensorsScore;
    }

    public void setSensorsScore(String sensorsScore) {
        this.sensorsScore = sensorsScore;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getControllerScore() {
        return controllerScore;
    }

    public void setControllerScore(String controllerScore) {
        this.controllerScore = controllerScore;
    }

    public String getFinalElement() {
        return finalElement;
    }

    public void setFinalElement(String finalElement) {
        this.finalElement = finalElement;
    }

    public String getFinalElementScore() {
        return finalElementScore;
    }

    public void setFinalElementScore(String finalElementScore) {
        this.finalElementScore = finalElementScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public SubProductGroup getSILSubComponent() {
        return sILSubComponent;
    }

    public void setSILSubComponent(SubProductGroup sILSubComponent) {
        this.sILSubComponent = sILSubComponent;
    }

    public TclProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(TclProjects projectId) {
        this.projectId = projectId;
    }

    public Users getSiluser() {
        return siluser;
    }

    public void setSiluser(Users siluser) {
        this.siluser = siluser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (silId != null ? silId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sil)) {
            return false;
        }
        Sil other = (Sil) object;
        if ((this.silId == null && other.silId != null) || (this.silId != null && !this.silId.equals(other.silId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Sil[ silId=" + silId + " ]";
    }
    
}
