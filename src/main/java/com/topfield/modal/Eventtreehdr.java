/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "eventtreehdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventtreehdr.findAll", query = "SELECT e FROM Eventtreehdr e")
    , @NamedQuery(name = "Eventtreehdr.findByEventhdrId", query = "SELECT e FROM Eventtreehdr e WHERE e.eventhdrId = :eventhdrId")
    , @NamedQuery(name = "Eventtreehdr.findByEventName", query = "SELECT e FROM Eventtreehdr e WHERE e.eventName = :eventName")
    , @NamedQuery(name = "Eventtreehdr.findByEventType", query = "SELECT e FROM Eventtreehdr e WHERE e.eventType = :eventType")
    , @NamedQuery(name = "Eventtreehdr.findByIntialProbs", query = "SELECT e FROM Eventtreehdr e WHERE e.intialProbs = :intialProbs")
    , @NamedQuery(name = "Eventtreehdr.findByComments", query = "SELECT e FROM Eventtreehdr e WHERE e.comments = :comments")
    , @NamedQuery(name = "Eventtreehdr.findByComType", query = "SELECT e FROM Eventtreehdr e WHERE e.comType = :comType")
    , @NamedQuery(name = "Eventtreehdr.findByComRef", query = "SELECT e FROM Eventtreehdr e WHERE e.comRef = :comRef")
    , @NamedQuery(name = "Eventtreehdr.findByHazidRef", query = "SELECT e FROM Eventtreehdr e WHERE e.hazidRef = :hazidRef")
    , @NamedQuery(name = "Eventtreehdr.findByCreateddate", query = "SELECT e FROM Eventtreehdr e WHERE e.createddate = :createddate")})
public class Eventtreehdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EventhdrId")
    private Integer eventhdrId;
    @Column(name = "EventName")
    private String eventName;
    @Column(name = "EventType")
    private String eventType;
    @Basic(optional = false)
    @Column(name = "IntialProbs")
    private double intialProbs;
	@Basic(optional = false)
    @Column(name = "units")
    private String units;
    @Column(name = "Comments")
    private String comments;
    @Column(name = "ComType")
    private String comType;
    @Basic(optional = false)
    @Column(name = "ComRef")
    private int comRef;
    @Basic(optional = false)
    @Column(name = "HazidRef")
    private int hazidRef;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.DATE)
    private Date createddate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventHdr")
    private Collection<Eventtree> eventtreeCollection;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;

    public Eventtreehdr() {
    }

    public Eventtreehdr(Integer eventhdrId) {
        this.eventhdrId = eventhdrId;
    }

    public Eventtreehdr(Integer eventhdrId, double intialProbs, int comRef, int hazidRef, Date createddate) {
        this.eventhdrId = eventhdrId;
        this.intialProbs = intialProbs;
        this.comRef = comRef;
        this.hazidRef = hazidRef;
        this.createddate = createddate;
    }

    public Integer getEventhdrId() {
        return eventhdrId;
    }

    public void setEventhdrId(Integer eventhdrId) {
        this.eventhdrId = eventhdrId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public double getIntialProbs() {
        return intialProbs;
    }

    public void setIntialProbs(double intialProbs) {
        this.intialProbs = intialProbs;
    }
	public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    public int getComRef() {
        return comRef;
    }

    public void setComRef(int comRef) {
        this.comRef = comRef;
    }

    public int getHazidRef() {
        return hazidRef;
    }

    public void setHazidRef(int hazidRef) {
        this.hazidRef = hazidRef;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @XmlTransient
    public Collection<Eventtree> getEventtreeCollection() {
        return eventtreeCollection;
    }

    public void setEventtreeCollection(Collection<Eventtree> eventtreeCollection) {
        this.eventtreeCollection = eventtreeCollection;
    }

    public TclProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(TclProjects projectId) {
        this.projectId = projectId;
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
        hash += (eventhdrId != null ? eventhdrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventtreehdr)) {
            return false;
        }
        Eventtreehdr other = (Eventtreehdr) object;
        if ((this.eventhdrId == null && other.eventhdrId != null) || (this.eventhdrId != null && !this.eventhdrId.equals(other.eventhdrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Eventtreehdr[ eventhdrId=" + eventhdrId + " ]";
    }
    
}
