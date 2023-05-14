/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "faultdata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faultdata.findAll", query = "SELECT f FROM Faultdata f")
    , @NamedQuery(name = "Faultdata.findByFaultdataId", query = "SELECT f FROM Faultdata f WHERE f.faultdataId = :faultdataId")
    , @NamedQuery(name = "Faultdata.findByNode", query = "SELECT f FROM Faultdata f WHERE f.node = :node")
    , @NamedQuery(name = "Faultdata.findByName", query = "SELECT f FROM Faultdata f WHERE f.name = :name")
    , @NamedQuery(name = "Faultdata.findByDescription", query = "SELECT f FROM Faultdata f WHERE f.description = :description")
    , @NamedQuery(name = "Faultdata.findByFailurerate", query = "SELECT f FROM Faultdata f WHERE f.failurerate = :failurerate")
    , @NamedQuery(name = "Faultdata.findByProbability", query = "SELECT f FROM Faultdata f WHERE f.probability = :probability")
    , @NamedQuery(name = "Faultdata.findByTime", query = "SELECT f FROM Faultdata f WHERE f.time = :time")
    , @NamedQuery(name = "Faultdata.findByCategory", query = "SELECT f FROM Faultdata f WHERE f.category = :category")
    , @NamedQuery(name = "Faultdata.findByMethods", query = "SELECT f FROM Faultdata f WHERE f.methods = :methods")
    , @NamedQuery(name = "Faultdata.findByChild", query = "SELECT f FROM Faultdata f WHERE f.child = :child")
    , @NamedQuery(name = "Faultdata.findByChildGate", query = "SELECT f FROM Faultdata f WHERE f.childGate = :childGate")
    , @NamedQuery(name = "Faultdata.findByFdreferences", query = "SELECT f FROM Faultdata f WHERE f.fdreferences = :fdreferences")
    , @NamedQuery(name = "Faultdata.findByComments", query = "SELECT f FROM Faultdata f WHERE f.comments = :comments")})
public class Faultdata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "faultdataId")
    private Integer faultdataId;
    @Column(name = "node")
    private String node;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "failurerate")
    private Double failurerate;
    @Column(name = "probability")
    private Double probability;
    @Column(name = "time")
    private Double time;
    @Column(name = "category")
    private String category;
    @Column(name = "methods")
    private String methods;
    @Column(name = "child")
    private String child;
    @Column(name = "childGate")
    private String childGate;
    @Column(name = "fdreferences")
    private String fdreferences;
    @Column(name = "Comments")
    private String comments;
    
    @JoinColumn(name = "calcComp", referencedColumnName = "calcId")
    @ManyToOne(optional = false)
    private Calcfile calcComp;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Faultdata> faultdataCollection;
    
    @JoinColumn(name = "parent", referencedColumnName = "faultdataId")
    @ManyToOne//(fetch = FetchType.EAGER)
    private Faultdata parent;
    
    @JoinColumn(name = "faultHeader", referencedColumnName = "spfhdrid")
    @ManyToOne(optional = false)
    private Spfhdr faultHeader;

    public Faultdata() {
    }

    public Faultdata(Integer faultdataId) {
        this.faultdataId = faultdataId;
    }

    public Integer getFaultdataId() {
        return faultdataId;
    }

    public void setFaultdataId(Integer faultdataId) {
        this.faultdataId = faultdataId;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFailurerate() {
        return failurerate;
    }

    public void setFailurerate(Double failurerate) {
        this.failurerate = failurerate;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getChildGate() {
        return childGate;
    }

    public void setChildGate(String childGate) {
        this.childGate = childGate;
    }

    public String getFdreferences() {
        return fdreferences;
    }

    public void setFdreferences(String fdreferences) {
        this.fdreferences = fdreferences;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Calcfile getCalcComp() {
        return calcComp;
    }

    public void setCalcComp(Calcfile calcComp) {
        this.calcComp = calcComp;
    }

    @XmlTransient
    public Collection<Faultdata> getFaultdataCollection() {
        return faultdataCollection;
    }

    public void setFaultdataCollection(Collection<Faultdata> faultdataCollection) {
        this.faultdataCollection = faultdataCollection;
    }

    public Faultdata getParent() {
        return parent;
    }

    public void setParent(Faultdata parent) {
        this.parent = parent;
    }

    public Spfhdr getFaultHeader() {
        return faultHeader;
    }

    public void setFaultHeader(Spfhdr faultHeader) {
        this.faultHeader = faultHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faultdataId != null ? faultdataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faultdata)) {
            return false;
        }
        Faultdata other = (Faultdata) object;
        if ((this.faultdataId == null && other.faultdataId != null) || (this.faultdataId != null && !this.faultdataId.equals(other.faultdataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Faultdata[ faultdataId=" + faultdataId + " ]";
    }
    
}
