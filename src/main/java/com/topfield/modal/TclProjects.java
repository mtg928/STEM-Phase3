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
import javax.persistence.Lob;
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
@Table(name = "tcl_projects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TclProjects.findAll", query = "SELECT t FROM TclProjects t")
    , @NamedQuery(name = "TclProjects.findByProId", query = "SELECT t FROM TclProjects t WHERE t.proId = :proId")
    , @NamedQuery(name = "TclProjects.findByProType", query = "SELECT t FROM TclProjects t WHERE t.proType = :proType")
    , @NamedQuery(name = "TclProjects.findByFilePath", query = "SELECT t FROM TclProjects t WHERE t.filePath = :filePath")
    , @NamedQuery(name = "TclProjects.findByCreatedDate", query = "SELECT t FROM TclProjects t WHERE t.createdDate = :createdDate")})
public class TclProjects implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Calchdr> calchdrCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Assumptions> assumptionsCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pro_id")
    private Integer proId;
    @Lob
    @Column(name = "pro_name")
    private String proName;
    @Column(name = "pro_type")
    private String proType;
    @Column(name = "abbreviation")
    private String abbreviation;
    @Lob
    @Column(name = "pro_description")
    private String proDescription;
    @Lob
    @Column(name = "system_scope")
    private String systemScope;
    @Lob
    @Column(name = "calculation_scope")
    private String calculationScope;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Spchdr> spchdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Mpghdr> mpghdrCollection;
    @JoinColumn(name = "created_by", referencedColumnName = "username")
    @ManyToOne
    private Users createdBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Sil> silCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Spghdr> spghdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Ccfhdr> ccfhdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Faulttree> faulttreeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Eventtreehdr> eventtreehdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Spfhdr> spfhdrCollection;

    public TclProjects() {
    }

    public TclProjects(Integer proId) {
        this.proId = proId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getProDescription() {
        return proDescription;
    }

    public void setProDescription(String proDescription) {
        this.proDescription = proDescription;
    }

    public String getSystemScope() {
        return systemScope;
    }

    public void setSystemScope(String systemScope) {
        this.systemScope = systemScope;
    }

    public String getCalculationScope() {
        return calculationScope;
    }

    public void setCalculationScope(String calculationScope) {
        this.calculationScope = calculationScope;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    public Collection<Spchdr> getSpchdrCollection() {
        return spchdrCollection;
    }

    public void setSpchdrCollection(Collection<Spchdr> spchdrCollection) {
        this.spchdrCollection = spchdrCollection;
    }

    @XmlTransient
    public Collection<Mpghdr> getMpghdrCollection() {
        return mpghdrCollection;
    }

    public void setMpghdrCollection(Collection<Mpghdr> mpghdrCollection) {
        this.mpghdrCollection = mpghdrCollection;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    @XmlTransient
    public Collection<Sil> getSilCollection() {
        return silCollection;
    }

    public void setSilCollection(Collection<Sil> silCollection) {
        this.silCollection = silCollection;
    }

    @XmlTransient
    public Collection<Spghdr> getSpghdrCollection() {
        return spghdrCollection;
    }

    public void setSpghdrCollection(Collection<Spghdr> spghdrCollection) {
        this.spghdrCollection = spghdrCollection;
    }

    @XmlTransient
    public Collection<Ccfhdr> getCcfhdrCollection() {
        return ccfhdrCollection;
    }

    public void setCcfhdrCollection(Collection<Ccfhdr> ccfhdrCollection) {
        this.ccfhdrCollection = ccfhdrCollection;
    }

    @XmlTransient
    public Collection<Faulttree> getFaulttreeCollection() {
        return faulttreeCollection;
    }

    public void setFaulttreeCollection(Collection<Faulttree> faulttreeCollection) {
        this.faulttreeCollection = faulttreeCollection;
    }

    @XmlTransient
    public Collection<Eventtreehdr> getEventtreehdrCollection() {
        return eventtreehdrCollection;
    }

    public void setEventtreehdrCollection(Collection<Eventtreehdr> eventtreehdrCollection) {
        this.eventtreehdrCollection = eventtreehdrCollection;
    }

    @XmlTransient
    public Collection<Spfhdr> getSpfhdrCollection() {
        return spfhdrCollection;
    }

    public void setSpfhdrCollection(Collection<Spfhdr> spfhdrCollection) {
        this.spfhdrCollection = spfhdrCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proId != null ? proId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TclProjects)) {
            return false;
        }
        TclProjects other = (TclProjects) object;
        if ((this.proId == null && other.proId != null) || (this.proId != null && !this.proId.equals(other.proId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.TclProjects[ proId=" + proId + " ]";
    }

    @XmlTransient
    public Collection<Assumptions> getAssumptionsCollection() {
        return assumptionsCollection;
    }

    public void setAssumptionsCollection(Collection<Assumptions> assumptionsCollection) {
        this.assumptionsCollection = assumptionsCollection;
    }

    @XmlTransient
    public Collection<Calchdr> getCalchdrCollection() {
        return calchdrCollection;
    }

    public void setCalchdrCollection(Collection<Calchdr> calchdrCollection) {
        this.calchdrCollection = calchdrCollection;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
}
