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
@Table(name = "spchdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spchdr.findAll", query = "SELECT s FROM Spchdr s")
    , @NamedQuery(name = "Spchdr.findBySpchdrid", query = "SELECT s FROM Spchdr s WHERE s.spchdrid = :spchdrid")
    , @NamedQuery(name = "Spchdr.findByDescriptions", query = "SELECT s FROM Spchdr s WHERE s.descriptions = :descriptions")
    , @NamedQuery(name = "Spchdr.findByCalname", query = "SELECT s FROM Spchdr s WHERE s.calname = :calname")
    , @NamedQuery(name = "Spchdr.findByComments", query = "SELECT s FROM Spchdr s WHERE s.comments = :comments")
    , @NamedQuery(name = "Spchdr.findBySummary", query = "SELECT s FROM Spchdr s WHERE s.summary = :summary")
    , @NamedQuery(name = "Spchdr.findByCreateddate", query = "SELECT s FROM Spchdr s WHERE s.createddate = :createddate")})
public class Spchdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spchdrid")
    private Integer spchdrid;
    @Column(name = "descriptions")
    private String descriptions;
    @Basic(optional = false)
    @Column(name = "calname")
    private String calname;
    @Column(name = "comments")
    private String comments;
    @Column(name = "summary")
    private String summary;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @JoinColumn(name = "standard", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Standards standard;
    @JoinColumn(name = "spcid", referencedColumnName = "spc_id")
    @ManyToOne(optional = false)
    private SubProductComponents spcid;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users user;

    public Spchdr() {
    }

    public Spchdr(Integer spchdrid) {
        this.spchdrid = spchdrid;
    }

    public Spchdr(Integer spchdrid, String calname, Date createddate) {
        this.spchdrid = spchdrid;
        this.calname = calname;
        this.createddate = createddate;
    }

    public Integer getSpchdrid() {
        return spchdrid;
    }

    public void setSpchdrid(Integer spchdrid) {
        this.spchdrid = spchdrid;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getCalname() {
        return calname;
    }

    public void setCalname(String calname) {
        this.calname = calname;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Standards getStandard() {
        return standard;
    }

    public void setStandard(Standards standard) {
        this.standard = standard;
    }

    public SubProductComponents getSpcid() {
        return spcid;
    }

    public void setSpcid(SubProductComponents spcid) {
        this.spcid = spcid;
    }

    public TclProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(TclProjects projectId) {
        this.projectId = projectId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spchdrid != null ? spchdrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spchdr)) {
            return false;
        }
        Spchdr other = (Spchdr) object;
        if ((this.spchdrid == null && other.spchdrid != null) || (this.spchdrid != null && !this.spchdrid.equals(other.spchdrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Spchdr[ spchdrid=" + spchdrid + " ]";
    }
    
}
