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
@Table(name = "functionalfailures")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Functionalfailures.findAll", query = "SELECT f FROM Functionalfailures f")
    , @NamedQuery(name = "Functionalfailures.findByFailureId", query = "SELECT f FROM Functionalfailures f WHERE f.failureId = :failureId")
    , @NamedQuery(name = "Functionalfailures.findByImportance", query = "SELECT f FROM Functionalfailures f WHERE f.importance = :importance")
    , @NamedQuery(name = "Functionalfailures.findByFailureRate", query = "SELECT f FROM Functionalfailures f WHERE f.failureRate = :failureRate")})
public class Functionalfailures implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "failureId")
    private Integer failureId;
    @Lob
    @Column(name = "Projecttype")
    private String projecttype;
    @Lob
    @Column(name = "failuretype")
    private String failuretype;
    @Lob
    @Column(name = "description")
    private String description;
    @Lob
    @Column(name = "function")
    private String function;
    @Lob
    @Column(name = "phase")
    private String phase;
    @Lob
    @Column(name = "failureMode")
    private String failureMode;
    @Lob
    @Column(name = "failureCause")
    private String failureCause;
    @Lob
    @Column(name = "localFailureEffect")
    private String localFailureEffect;
    @Lob
    @Column(name = "subsystemFailureEffect")
    private String subsystemFailureEffect;
    @Lob
    @Column(name = "trainFailureEffect")
    private String trainFailureEffect;
    @Lob
    @Column(name = "failureDetection")
    private String failureDetection;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Importance")
    private Double importance;
    @Column(name = "failureRate")
    private Double failureRate;
    @Lob
    @Column(name = "preventiveMeasures")
    private String preventiveMeasures;
    @JoinColumn(name = "subComponent", referencedColumnName = "spc_id")
    @ManyToOne
    private SubProductComponents subComponent;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;

    public Functionalfailures() {
    }

    public Functionalfailures(Integer failureId) {
        this.failureId = failureId;
    }

    public Integer getFailureId() {
        return failureId;
    }

    public void setFailureId(Integer failureId) {
        this.failureId = failureId;
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype;
    }

    public String getFailuretype() {
        return failuretype;
    }

    public void setFailuretype(String failuretype) {
        this.failuretype = failuretype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getFailureMode() {
        return failureMode;
    }

    public void setFailureMode(String failureMode) {
        this.failureMode = failureMode;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

    public String getLocalFailureEffect() {
        return localFailureEffect;
    }

    public void setLocalFailureEffect(String localFailureEffect) {
        this.localFailureEffect = localFailureEffect;
    }

    public String getSubsystemFailureEffect() {
        return subsystemFailureEffect;
    }

    public void setSubsystemFailureEffect(String subsystemFailureEffect) {
        this.subsystemFailureEffect = subsystemFailureEffect;
    }

    public String getTrainFailureEffect() {
        return trainFailureEffect;
    }

    public void setTrainFailureEffect(String trainFailureEffect) {
        this.trainFailureEffect = trainFailureEffect;
    }

    public String getFailureDetection() {
        return failureDetection;
    }

    public void setFailureDetection(String failureDetection) {
        this.failureDetection = failureDetection;
    }

    public Double getImportance() {
        return importance;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }

    public Double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(Double failureRate) {
        this.failureRate = failureRate;
    }

    public String getPreventiveMeasures() {
        return preventiveMeasures;
    }

    public void setPreventiveMeasures(String preventiveMeasures) {
        this.preventiveMeasures = preventiveMeasures;
    }

    public SubProductComponents getSubComponent() {
        return subComponent;
    }

    public void setSubComponent(SubProductComponents subComponent) {
        this.subComponent = subComponent;
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
        hash += (failureId != null ? failureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Functionalfailures)) {
            return false;
        }
        Functionalfailures other = (Functionalfailures) object;
        if ((this.failureId == null && other.failureId != null) || (this.failureId != null && !this.failureId.equals(other.failureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Functionalfailures[ failureId=" + failureId + " ]";
    }
    
}
