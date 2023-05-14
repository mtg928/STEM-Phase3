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
@Table(name = "failuremodes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Failuremodes.findAll", query = "SELECT f FROM Failuremodes f")
    , @NamedQuery(name = "Failuremodes.findByModeid", query = "SELECT f FROM Failuremodes f WHERE f.modeid = :modeid")
    , @NamedQuery(name = "Failuremodes.findByFailuremode", query = "SELECT f FROM Failuremodes f WHERE f.failuremode = :failuremode")
    , @NamedQuery(name = "Failuremodes.findByFailuremodeprobability", query = "SELECT f FROM Failuremodes f WHERE f.failuremodeprobability = :failuremodeprobability")
    , @NamedQuery(name = "Failuremodes.findByDescription", query = "SELECT f FROM Failuremodes f WHERE f.description = :description")})
public class Failuremodes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "modeid")
    private Integer modeid;
    @Basic(optional = false)
    @Column(name = "failuremode")
    private String failuremode;
    @Basic(optional = false)
    @Column(name = "failuremodeprobability")
    private double failuremodeprobability;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "failuredata", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Failuredata failuredata;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;

    public Failuremodes() {
    }

    public Failuremodes(Integer modeid) {
        this.modeid = modeid;
    }

    public Failuremodes(Integer modeid, String failuremode, double failuremodeprobability) {
        this.modeid = modeid;
        this.failuremode = failuremode;
        this.failuremodeprobability = failuremodeprobability;
    }

    public Integer getModeid() {
        return modeid;
    }

    public void setModeid(Integer modeid) {
        this.modeid = modeid;
    }

    public String getFailuremode() {
        return failuremode;
    }

    public void setFailuremode(String failuremode) {
        this.failuremode = failuremode;
    }

    public double getFailuremodeprobability() {
        return failuremodeprobability;
    }

    public void setFailuremodeprobability(double failuremodeprobability) {
        this.failuremodeprobability = failuremodeprobability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Failuredata getFailuredata() {
        return failuredata;
    }

    public void setFailuredata(Failuredata failuredata) {
        this.failuredata = failuredata;
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
        hash += (modeid != null ? modeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Failuremodes)) {
            return false;
        }
        Failuremodes other = (Failuremodes) object;
        if ((this.modeid == null && other.modeid != null) || (this.modeid != null && !this.modeid.equals(other.modeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Failuremodes[ modeid=" + modeid + " ]";
    }
    
}
