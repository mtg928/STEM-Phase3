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
@Table(name = "eventtree")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventtree.findAll", query = "SELECT e FROM Eventtree e")
    , @NamedQuery(name = "Eventtree.findByEventId", query = "SELECT e FROM Eventtree e WHERE e.eventId = :eventId")
    , @NamedQuery(name = "Eventtree.findByEventLevel", query = "SELECT e FROM Eventtree e WHERE e.eventLevel = :eventLevel")
    , @NamedQuery(name = "Eventtree.findByEventName", query = "SELECT e FROM Eventtree e WHERE e.eventName = :eventName")
    , @NamedQuery(name = "Eventtree.findByEventType", query = "SELECT e FROM Eventtree e WHERE e.eventType = :eventType")
    , @NamedQuery(name = "Eventtree.findByEventParent", query = "SELECT e FROM Eventtree e WHERE e.eventParent = :eventParent")
    , @NamedQuery(name = "Eventtree.findByTrueProbs", query = "SELECT e FROM Eventtree e WHERE e.trueProbs = :trueProbs")
    , @NamedQuery(name = "Eventtree.findByChild", query = "SELECT e FROM Eventtree e WHERE e.child = :child")
    , @NamedQuery(name = "Eventtree.findByDirections", query = "SELECT e FROM Eventtree e WHERE e.directions = :directions")
    , @NamedQuery(name = "Eventtree.findByComments", query = "SELECT e FROM Eventtree e WHERE e.comments = :comments")})
public class Eventtree implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EventId")
    private Integer eventId;
    @Basic(optional = false)
    @Column(name = "EventLevel")
    private int eventLevel;
    @Column(name = "EventName")
    private String eventName;
    @Column(name = "EventType")
    private String eventType;
    @Basic(optional = false)
    @Column(name = "EventParent")
    private int eventParent;
    @Column(name = "TrueProbs")
    private String trueProbs;
    @Column(name = "TrueProb")
    private Double trueProb;
    @Column(name = "child")
    private String child;
    @Column(name = "directions")
    private String directions;
    @Column(name = "Comments")
    private String comments;
    @JoinColumn(name = "eventHdr", referencedColumnName = "EventhdrId")
    @ManyToOne(optional = false)
    private Eventtreehdr eventHdr;

    public Eventtree() {
    }

    public Eventtree(Integer eventId) {
        this.eventId = eventId;
    }

    public Eventtree(Integer eventId, int eventLevel, int eventParent) {
        this.eventId = eventId;
        this.eventLevel = eventLevel;
        this.eventParent = eventParent;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public int getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(int eventLevel) {
        this.eventLevel = eventLevel;
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

    public int getEventParent() {
        return eventParent;
    }

    public void setEventParent(int eventParent) {
        this.eventParent = eventParent;
    }

    public String getTrueProbs() {
        return trueProbs;
    }

    public void setTrueProbs(String trueProbs) {
        this.trueProbs = trueProbs;
    }

    public Double getProbs() {
        return trueProb;
    }

    public void setProbs(Double probs) {
        this.trueProb = probs;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Eventtreehdr getEventHdr() {
        return eventHdr;
    }

    public void setEventHdr(Eventtreehdr eventHdr) {
        this.eventHdr = eventHdr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventtree)) {
            return false;
        }
        Eventtree other = (Eventtree) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Eventtree[ eventId=" + eventId + " ]";
    }
    
}
