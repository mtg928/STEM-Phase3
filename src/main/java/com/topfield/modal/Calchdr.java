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
@Table(name = "calchdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calchdr.findAll", query = "SELECT c FROM Calchdr c")
    , @NamedQuery(name = "Calchdr.findByCalchdrid", query = "SELECT c FROM Calchdr c WHERE c.calchdrid = :calchdrid")
    , @NamedQuery(name = "Calchdr.findByDescriptions", query = "SELECT c FROM Calchdr c WHERE c.descriptions = :descriptions")
    , @NamedQuery(name = "Calchdr.findByCalname", query = "SELECT c FROM Calchdr c WHERE c.calname = :calname")
    , @NamedQuery(name = "Calchdr.findByCalcType", query = "SELECT c FROM Calchdr c WHERE c.calcType = :calcType")
    , @NamedQuery(name = "Calchdr.findByComments", query = "SELECT c FROM Calchdr c WHERE c.comments = :comments")
    , @NamedQuery(name = "Calchdr.findBySummary", query = "SELECT c FROM Calchdr c WHERE c.summary = :summary")
    , @NamedQuery(name = "Calchdr.findByCreateddate", query = "SELECT c FROM Calchdr c WHERE c.createddate = :createddate")})
public class Calchdr implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmecaHeader")
    private Collection<Fmeca> fmecaCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmeaHeader")
    private Collection<Fmea> fmeaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calchdrid")
    private Integer calchdrid;
    @Column(name = "descriptions")
    private String descriptions;
    @Basic(optional = false)
    @Column(name = "calname")
    private String calname;
    @Column(name = "calcType")
    private String calcType;
    @Column(name = "comments")
    private String comments;
    @Column(name = "summary")
    private String summary;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @Basic(optional = false)
    @Column(name = "approach")
    private int approach;
    @JoinColumn(name = "mpghdr", referencedColumnName = "mpghdrid")
    @ManyToOne(optional = false)
    private Mpghdr mpghdr;
    @JoinColumn(name = "standard", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Standards standard;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users user;

    public Calchdr() {
    }

    public Calchdr(Integer calchdrid) {
        this.calchdrid = calchdrid;
    }

    public Calchdr(Integer calchdrid, String calname, Date createddate) {
        this.calchdrid = calchdrid;
        this.calname = calname;
        this.createddate = createddate;
    }

    public Integer getCalchdrid() {
        return calchdrid;
    }

    public void setCalchdrid(Integer calchdrid) {
        this.calchdrid = calchdrid;
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

    public String getCalcType() {
        return calcType;
    }

    public void setCalcType(String calcType) {
        this.calcType = calcType;
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
    
    public int getApproach() {
        return approach;
    }

    public void setApproach(int approach) {
        this.approach = approach;
    }

    public Mpghdr getMpghdr() {
        return mpghdr;
    }

    public void setMpghdr(Mpghdr mpghdr) {
        this.mpghdr = mpghdr;
    }

    public Standards getStandard() {
        return standard;
    }

    public void setStandard(Standards standard) {
        this.standard = standard;
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
        hash += (calchdrid != null ? calchdrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calchdr)) {
            return false;
        }
        Calchdr other = (Calchdr) object;
        if ((this.calchdrid == null && other.calchdrid != null) || (this.calchdrid != null && !this.calchdrid.equals(other.calchdrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Calchdr[ calchdrid=" + calchdrid + " ]";
    }

    @XmlTransient
    public Collection<Fmea> getFmeaCollection() {
        return fmeaCollection;
    }

    public void setFmeaCollection(Collection<Fmea> fmeaCollection) {
        this.fmeaCollection = fmeaCollection;
    }

    @XmlTransient
    public Collection<Fmeca> getFmecaCollection() {
        return fmecaCollection;
    }

    public void setFmecaCollection(Collection<Fmeca> fmecaCollection) {
        this.fmecaCollection = fmecaCollection;
    }
    
}
