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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username")
    , @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password")
    , @NamedQuery(name = "Users.findByEnabled", query = "SELECT u FROM Users u WHERE u.enabled = :enabled")
    , @NamedQuery(name = "Users.findByCreatedBy", query = "SELECT u FROM Users u WHERE u.createdBy = :createdBy")
    , @NamedQuery(name = "Users.findByCreatedDate", query = "SELECT u FROM Users u WHERE u.createdDate = :createdDate")
    , @NamedQuery(name = "Users.findByLicensestartdate", query = "SELECT u FROM Users u WHERE u.licensestartdate = :licensestartdate")
    , @NamedQuery(name = "Users.findByLicenseEnddate", query = "SELECT u FROM Users u WHERE u.licenseEnddate = :licenseEnddate")})
public class Users implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Calchdr> calchdrCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    private Collection<Hazards> hazardsCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "enabled")
    private short enabled;
    @Basic(optional = false)
    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "licensestartdate")
    @Temporal(TemporalType.DATE)
    private Date licensestartdate;
    @Column(name = "licenseEnddate")
    @Temporal(TemporalType.DATE)
    private Date licenseEnddate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Components> componentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Spchdr> spchdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Mpghdr> mpghdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<SubProductGroup> subProductGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Datarefer> datareferCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Failuremodes> failuremodesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<SubProductComponents> subProductComponentsCollection;
    @OneToMany(mappedBy = "createdBy")
    private Collection<TclProjects> tclProjectsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siluser")
    private Collection<Sil> silCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Spghdr> spghdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Ccfhdr> ccfhdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Functionalfailures> functionalfailuresCollection;
    @JoinColumn(name = "package", referencedColumnName = "pid")
    @ManyToOne(optional = false)
    private Packages package1;
    @JoinColumn(name = "user_role", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private UserRoles userRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Faulttree> faulttreeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Failuredata> failuredataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Eventtreehdr> eventtreehdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Spfhdr> spfhdrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<MainProductGroup> mainProductGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preuser")
    private Collection<Userpreferences> userpreferencesCollection;

    public Users() {
    }

    public Users(String username) {
        this.username = username;
    }

    public Users(String username, String password, short enabled, int createdBy) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.createdBy = createdBy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLicensestartdate() {
        return licensestartdate;
    }

    public void setLicensestartdate(Date licensestartdate) {
        this.licensestartdate = licensestartdate;
    }

    public Date getLicenseEnddate() {
        return licenseEnddate;
    }

    public void setLicenseEnddate(Date licenseEnddate) {
        this.licenseEnddate = licenseEnddate;
    }

    @XmlTransient
    public Collection<Components> getComponentsCollection() {
        return componentsCollection;
    }

    public void setComponentsCollection(Collection<Components> componentsCollection) {
        this.componentsCollection = componentsCollection;
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

    @XmlTransient
    public Collection<SubProductGroup> getSubProductGroupCollection() {
        return subProductGroupCollection;
    }

    public void setSubProductGroupCollection(Collection<SubProductGroup> subProductGroupCollection) {
        this.subProductGroupCollection = subProductGroupCollection;
    }

    @XmlTransient
    public Collection<Datarefer> getDatareferCollection() {
        return datareferCollection;
    }

    public void setDatareferCollection(Collection<Datarefer> datareferCollection) {
        this.datareferCollection = datareferCollection;
    }

    @XmlTransient
    public Collection<Failuremodes> getFailuremodesCollection() {
        return failuremodesCollection;
    }

    public void setFailuremodesCollection(Collection<Failuremodes> failuremodesCollection) {
        this.failuremodesCollection = failuremodesCollection;
    }

    @XmlTransient
    public Collection<SubProductComponents> getSubProductComponentsCollection() {
        return subProductComponentsCollection;
    }

    public void setSubProductComponentsCollection(Collection<SubProductComponents> subProductComponentsCollection) {
        this.subProductComponentsCollection = subProductComponentsCollection;
    }

    @XmlTransient
    public Collection<TclProjects> getTclProjectsCollection() {
        return tclProjectsCollection;
    }

    public void setTclProjectsCollection(Collection<TclProjects> tclProjectsCollection) {
        this.tclProjectsCollection = tclProjectsCollection;
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
    public Collection<Functionalfailures> getFunctionalfailuresCollection() {
        return functionalfailuresCollection;
    }

    public void setFunctionalfailuresCollection(Collection<Functionalfailures> functionalfailuresCollection) {
        this.functionalfailuresCollection = functionalfailuresCollection;
    }

    public Packages getPackage1() {
        return package1;
    }

    public void setPackage1(Packages package1) {
        this.package1 = package1;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    @XmlTransient
    public Collection<Faulttree> getFaulttreeCollection() {
        return faulttreeCollection;
    }

    public void setFaulttreeCollection(Collection<Faulttree> faulttreeCollection) {
        this.faulttreeCollection = faulttreeCollection;
    }

    @XmlTransient
    public Collection<Failuredata> getFailuredataCollection() {
        return failuredataCollection;
    }

    public void setFailuredataCollection(Collection<Failuredata> failuredataCollection) {
        this.failuredataCollection = failuredataCollection;
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

    @XmlTransient
    public Collection<MainProductGroup> getMainProductGroupCollection() {
        return mainProductGroupCollection;
    }

    public void setMainProductGroupCollection(Collection<MainProductGroup> mainProductGroupCollection) {
        this.mainProductGroupCollection = mainProductGroupCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Users[ username=" + username + " ]";
    }

    @XmlTransient
    public Collection<Hazards> getHazardsCollection() {
        return hazardsCollection;
    }

    public void setHazardsCollection(Collection<Hazards> hazardsCollection) {
        this.hazardsCollection = hazardsCollection;
    }

    @XmlTransient
    public Collection<Calchdr> getCalchdrCollection() {
        return calchdrCollection;
    }

    public void setCalchdrCollection(Collection<Calchdr> calchdrCollection) {
        this.calchdrCollection = calchdrCollection;
    }
    
    @XmlTransient
    public Collection<Userpreferences> getUserpreferencesCollection() {
        return userpreferencesCollection;
    }

    public void setUserpreferencesCollection(Collection<Userpreferences> userpreferencesCollection) {
        this.userpreferencesCollection = userpreferencesCollection;
    }
}
