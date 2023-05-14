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
@Table(name = "mpghdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mpghdr.findAll", query = "SELECT m FROM Mpghdr m")
    , @NamedQuery(name = "Mpghdr.findByMpghdrid", query = "SELECT m FROM Mpghdr m WHERE m.mpghdrid = :mpghdrid")
    , @NamedQuery(name = "Mpghdr.findByDescriptions", query = "SELECT m FROM Mpghdr m WHERE m.descriptions = :descriptions")
    , @NamedQuery(name = "Mpghdr.findByCalname", query = "SELECT m FROM Mpghdr m WHERE m.calname = :calname")
    , @NamedQuery(name = "Mpghdr.findByMpgType", query = "SELECT m FROM Mpghdr m WHERE m.mpgType = :mpgType")
    , @NamedQuery(name = "Mpghdr.findByComments", query = "SELECT m FROM Mpghdr m WHERE m.comments = :comments")
    , @NamedQuery(name = "Mpghdr.findBySummary", query = "SELECT m FROM Mpghdr m WHERE m.summary = :summary")
    , @NamedQuery(name = "Mpghdr.findByCreateddate", query = "SELECT m FROM Mpghdr m WHERE m.createddate = :createddate")})
public class Mpghdr implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mpghdrid")
    private Integer mpghdrid;
    @Column(name = "descriptions")
    private String descriptions;
    @Basic(optional = false)
    @Column(name = "calname")
    private String calname;
    @Column(name = "mpgType")
    private String mpgType;
    @Column(name = "comments")
    private String comments;
    @Column(name = "summary")
    private String summary;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @JoinColumn(name = "Fmea_Component", referencedColumnName = "mpg_id")
    @ManyToOne(optional = false)
    private MainProductGroup fmeaComponent;
    @JoinColumn(name = "standard", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Standards standard;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmecaHeader")
    private Collection<Fmeca> fmecaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmeaHeader")
    private Collection<Fmea> fmeaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mpgheader")
    private Collection<CalcFunctions> calcFunctionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mpghdr")
    private Collection<Calchdr> calchdrCollection;
    public Mpghdr() {
    }

    public Mpghdr(Integer mpghdrid) {
        this.mpghdrid = mpghdrid;
    }

    public Mpghdr(Integer mpghdrid, String calname, Date createddate) {
        this.mpghdrid = mpghdrid;
        this.calname = calname;
        this.createddate = createddate;
    }

    public Integer getMpghdrid() {
        return mpghdrid;
    }

    public void setMpghdrid(Integer mpghdrid) {
        this.mpghdrid = mpghdrid;
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

    public String getMpgType() {
        return mpgType;
    }

    public void setMpgType(String mpgType) {
        this.mpgType = mpgType;
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

    public MainProductGroup getFmeaComponent() {
        return fmeaComponent;
    }

    public void setFmeaComponent(MainProductGroup fmeaComponent) {
        this.fmeaComponent = fmeaComponent;
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

    @XmlTransient
    public Collection<Fmeca> getFmecaCollection() {
        return fmecaCollection;
    }

    public void setFmecaCollection(Collection<Fmeca> fmecaCollection) {
        this.fmecaCollection = fmecaCollection;
    }

    @XmlTransient
    public Collection<Fmea> getFmeaCollection() {
        return fmeaCollection;
    }

    public void setFmeaCollection(Collection<Fmea> fmeaCollection) {
        this.fmeaCollection = fmeaCollection;
    }

    @XmlTransient
    public Collection<CalcFunctions> getCalcFunctionsCollection() {
        return calcFunctionsCollection;
    }

    public void setCalcFunctionsCollection(Collection<CalcFunctions> calcFunctionsCollection) {
        this.calcFunctionsCollection = calcFunctionsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mpghdrid != null ? mpghdrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mpghdr)) {
            return false;
        }
        Mpghdr other = (Mpghdr) object;
        if ((this.mpghdrid == null && other.mpghdrid != null) || (this.mpghdrid != null && !this.mpghdrid.equals(other.mpghdrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Mpghdr[ mpghdrid=" + mpghdrid + " ]";
    }

    @XmlTransient
    public Collection<Calchdr> getCalchdrCollection() {
        return calchdrCollection;
    }

    public void setCalchdrCollection(Collection<Calchdr> calchdrCollection) {
        this.calchdrCollection = calchdrCollection;
    }
    
}
