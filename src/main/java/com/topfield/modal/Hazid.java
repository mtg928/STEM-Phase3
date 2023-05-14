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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "hazid")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hazid.findAll", query = "SELECT h FROM Hazid h")
    , @NamedQuery(name = "Hazid.findByHazidId", query = "SELECT h FROM Hazid h WHERE h.hazidId = :hazidId")
    , @NamedQuery(name = "Hazid.findByHazidRef", query = "SELECT h FROM Hazid h WHERE h.hazidRef = :hazidRef")
    , @NamedQuery(name = "Hazid.findByDescription", query = "SELECT h FROM Hazid h WHERE h.description = :description")})
public class Hazid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hazidId")
    private Integer hazidId;
    @Column(name = "hazidRef")
    private String hazidRef;
    @Column(name = "description")
    private String description;

    public Hazid() {
    }

    public Hazid(Integer hazidId) {
        this.hazidId = hazidId;
    }

    public Integer getHazidId() {
        return hazidId;
    }

    public void setHazidId(Integer hazidId) {
        this.hazidId = hazidId;
    }

    public String getHazidRef() {
        return hazidRef;
    }

    public void setHazidRef(String hazidRef) {
        this.hazidRef = hazidRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazidId != null ? hazidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hazid)) {
            return false;
        }
        Hazid other = (Hazid) object;
        if ((this.hazidId == null && other.hazidId != null) || (this.hazidId != null && !this.hazidId.equals(other.hazidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Hazid[ hazidId=" + hazidId + " ]";
    }
    
}
