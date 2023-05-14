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
@Table(name = "hazards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hazards.findAll", query = "SELECT h FROM Hazards h")
    , @NamedQuery(name = "Hazards.findByHazardId", query = "SELECT h FROM Hazards h WHERE h.hazardId = :hazardId")
    , @NamedQuery(name = "Hazards.findByHazardName", query = "SELECT h FROM Hazards h WHERE h.hazardName = :hazardName")
    , @NamedQuery(name = "Hazards.findByHazardscode", query = "SELECT h FROM Hazards h WHERE h.hazardscode = :hazardscode")
    , @NamedQuery(name = "Hazards.findByHazardType", query = "SELECT h FROM Hazards h WHERE h.hazardType = :hazardType")
    , @NamedQuery(name = "Hazards.findByHazidRef", query = "SELECT h FROM Hazards h WHERE h.hazidRef = :hazidRef")
    , @NamedQuery(name = "Hazards.findByHazidLinked", query = "SELECT h FROM Hazards h WHERE h.hazidLinked = :hazidLinked")
    , @NamedQuery(name = "Hazards.findByCivils", query = "SELECT h FROM Hazards h WHERE h.civils = :civils")
    , @NamedQuery(name = "Hazards.findBySignaling", query = "SELECT h FROM Hazards h WHERE h.signaling = :signaling")
    , @NamedQuery(name = "Hazards.findByPlatformScreenDoors", query = "SELECT h FROM Hazards h WHERE h.platformScreenDoors = :platformScreenDoors")
    , @NamedQuery(name = "Hazards.findByOperation", query = "SELECT h FROM Hazards h WHERE h.operation = :operation")
    , @NamedQuery(name = "Hazards.findByMaintenance", query = "SELECT h FROM Hazards h WHERE h.maintenance = :maintenance")})
public class Hazards implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "HazardId")
    private Integer hazardId;
    @Column(name = "HazardName")
    private String hazardName;
    @Column(name = "hazardscode")
    private String hazardscode;
    @Column(name = "HazardType")
    private String hazardType;
    @Column(name = "HazidRef")
    private String hazidRef;
    @Column(name = "HazidLinked")
    private String hazidLinked;
    @Column(name = "Civils")
    private String civils;
    @Column(name = "Signaling")
    private String signaling;
    @Column(name = "PlatformScreenDoors")
    private String platformScreenDoors;
    @Column(name = "Operation")
    private String operation;
    @Column(name = "Maintenance")
    private String maintenance;
    @JoinColumn(name = "createdBy", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users createdBy;

    public Hazards() {
    }

    public Hazards(Integer hazardId) {
        this.hazardId = hazardId;
    }

    public Integer getHazardId() {
        return hazardId;
    }

    public void setHazardId(Integer hazardId) {
        this.hazardId = hazardId;
    }

    public String getHazardName() {
        return hazardName;
    }

    public void setHazardName(String hazardName) {
        this.hazardName = hazardName;
    }

    public String getHazardscode() {
        return hazardscode;
    }

    public void setHazardscode(String hazardscode) {
        this.hazardscode = hazardscode;
    }

    public String getHazardType() {
        return hazardType;
    }

    public void setHazardType(String hazardType) {
        this.hazardType = hazardType;
    }

    public String getHazidRef() {
        return hazidRef;
    }

    public void setHazidRef(String hazidRef) {
        this.hazidRef = hazidRef;
    }

    public String getHazidLinked() {
        return hazidLinked;
    }

    public void setHazidLinked(String hazidLinked) {
        this.hazidLinked = hazidLinked;
    }

    public String getCivils() {
        return civils;
    }

    public void setCivils(String civils) {
        this.civils = civils;
    }

    public String getSignaling() {
        return signaling;
    }

    public void setSignaling(String signaling) {
        this.signaling = signaling;
    }

    public String getPlatformScreenDoors() {
        return platformScreenDoors;
    }

    public void setPlatformScreenDoors(String platformScreenDoors) {
        this.platformScreenDoors = platformScreenDoors;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hazards)) {
            return false;
        }
        Hazards other = (Hazards) object;
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Hazards[ hazardId=" + hazardId + " ]";
    }
    
}
