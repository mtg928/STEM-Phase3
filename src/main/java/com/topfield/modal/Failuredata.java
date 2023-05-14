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
@Table(name = "failuredata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Failuredata.findAll", query = "SELECT f FROM Failuredata f")
    , @NamedQuery(name = "Failuredata.findById", query = "SELECT f FROM Failuredata f WHERE f.id = :id")
    , @NamedQuery(name = "Failuredata.findBySystems", query = "SELECT f FROM Failuredata f WHERE f.systems = :systems")
    , @NamedQuery(name = "Failuredata.findByComponents", query = "SELECT f FROM Failuredata f WHERE f.components = :components")
    , @NamedQuery(name = "Failuredata.findByFailureratelow", query = "SELECT f FROM Failuredata f WHERE f.failureratelow = :failureratelow")
    , @NamedQuery(name = "Failuredata.findByFailureratemedium", query = "SELECT f FROM Failuredata f WHERE f.failureratemedium = :failureratemedium")
    , @NamedQuery(name = "Failuredata.findByFailureratehigh", query = "SELECT f FROM Failuredata f WHERE f.failureratehigh = :failureratehigh")
    , @NamedQuery(name = "Failuredata.findByFailureprobability", query = "SELECT f FROM Failuredata f WHERE f.failureprobability = :failureprobability")
    , @NamedQuery(name = "Failuredata.findByTimeatrisk", query = "SELECT f FROM Failuredata f WHERE f.timeatrisk = :timeatrisk")
    , @NamedQuery(name = "Failuredata.findByMttr", query = "SELECT f FROM Failuredata f WHERE f.mttr = :mttr")
    , @NamedQuery(name = "Failuredata.findByDescription", query = "SELECT f FROM Failuredata f WHERE f.description = :description")
    , @NamedQuery(name = "Failuredata.findByReleasedate", query = "SELECT f FROM Failuredata f WHERE f.releasedate = :releasedate")
    , @NamedQuery(name = "Failuredata.findByConfidentiality", query = "SELECT f FROM Failuredata f WHERE f.confidentiality = :confidentiality")
    , @NamedQuery(name = "Failuredata.findByCreateddate", query = "SELECT f FROM Failuredata f WHERE f.createddate = :createddate")})
public class Failuredata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "systems")
    private String systems;
    @Basic(optional = false)
    @Column(name = "components")
    private String components;
    @Basic(optional = false)
    @Column(name = "failureratelow")
    private double failureratelow;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "failureratemedium")
    private Double failureratemedium;
    @Column(name = "failureratehigh")
    private Double failureratehigh;
    @Column(name = "failureprobability")
    private Double failureprobability;
    @Column(name = "timeatrisk")
    private Double timeatrisk;
    @Column(name = "mttr")
    private Double mttr;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "releasedate")
    private String releasedate;
    @Column(name = "confidentiality")
    private Integer confidentiality;
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.DATE)
    private Date createddate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "failuredata")
    private Collection<Failuremodes> failuremodesCollection;
    @JoinColumn(name = "reference", referencedColumnName = "refid")
    @ManyToOne(optional = false)
    private Datarefer reference;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;

    public Failuredata() {
    }

    public Failuredata(Integer id) {
        this.id = id;
    }

    public Failuredata(Integer id, String systems, String components, double failureratelow, String releasedate, Date createddate) {
        this.id = id;
        this.systems = systems;
        this.components = components;
        this.failureratelow = failureratelow;
        this.releasedate = releasedate;
        this.createddate = createddate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystems() {
        return systems;
    }

    public void setSystems(String systems) {
        this.systems = systems;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public double getFailureratelow() {
        return failureratelow;
    }

    public void setFailureratelow(double failureratelow) {
        this.failureratelow = failureratelow;
    }

    public Double getFailureratemedium() {
        return failureratemedium;
    }

    public void setFailureratemedium(Double failureratemedium) {
        this.failureratemedium = failureratemedium;
    }

    public Double getFailureratehigh() {
        return failureratehigh;
    }

    public void setFailureratehigh(Double failureratehigh) {
        this.failureratehigh = failureratehigh;
    }

    public Double getFailureprobability() {
        return failureprobability;
    }

    public void setFailureprobability(Double failureprobability) {
        this.failureprobability = failureprobability;
    }

    public Double getTimeatrisk() {
        return timeatrisk;
    }

    public void setTimeatrisk(Double timeatrisk) {
        this.timeatrisk = timeatrisk;
    }

    public Double getMttr() {
        return mttr;
    }

    public void setMttr(Double mttr) {
        this.mttr = mttr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public Integer getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(Integer confidentiality) {
        this.confidentiality = confidentiality;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @XmlTransient
    public Collection<Failuremodes> getFailuremodesCollection() {
        return failuremodesCollection;
    }

    public void setFailuremodesCollection(Collection<Failuremodes> failuremodesCollection) {
        this.failuremodesCollection = failuremodesCollection;
    }

    public Datarefer getReference() {
        return reference;
    }

    public void setReference(Datarefer reference) {
        this.reference = reference;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Failuredata)) {
            return false;
        }
        Failuredata other = (Failuredata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Failuredata[ id=" + id + " ]";
    }
    
}
