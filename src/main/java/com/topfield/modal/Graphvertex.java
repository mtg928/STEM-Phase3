/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "graphvertex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Graphvertex.findAll", query = "SELECT g FROM Graphvertex g")
    , @NamedQuery(name = "Graphvertex.findByVertexid", query = "SELECT g FROM Graphvertex g WHERE g.vertexid = :vertexid")
    , @NamedQuery(name = "Graphvertex.findByVertextype", query = "SELECT g FROM Graphvertex g WHERE g.vertextype = :vertextype")
    , @NamedQuery(name = "Graphvertex.findByVertexHeading", query = "SELECT g FROM Graphvertex g WHERE g.vertexHeading = :vertexHeading")
    , @NamedQuery(name = "Graphvertex.findByDescription", query = "SELECT g FROM Graphvertex g WHERE g.description = :description")
    , @NamedQuery(name = "Graphvertex.findByPointx", query = "SELECT g FROM Graphvertex g WHERE g.pointx = :pointx")
    , @NamedQuery(name = "Graphvertex.findByPointy", query = "SELECT g FROM Graphvertex g WHERE g.pointy = :pointy")
    , @NamedQuery(name = "Graphvertex.findByWeight", query = "SELECT g FROM Graphvertex g WHERE g.weight = :weight")
    , @NamedQuery(name = "Graphvertex.findByHeight", query = "SELECT g FROM Graphvertex g WHERE g.height = :height")
    , @NamedQuery(name = "Graphvertex.findByCalccomp", query = "SELECT g FROM Graphvertex g WHERE g.calccomp = :calccomp")})
public class Graphvertex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vertexid")
    private Integer vertexid;
    @Basic(optional = false)
    @Column(name = "vertextype")
    private String vertextype;
    @Basic(optional = false)
    @Column(name = "vertexHeading")
    private String vertexHeading;
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pointx")
    private Double pointx;
    @Column(name = "pointy")
    private Double pointy;
    @Column(name = "weight")
    private Double weight;
    @Column(name = "height")
    private Double height;
    @JoinColumn(name = "calccomp", referencedColumnName = "calcId")
    @ManyToOne(optional = false)
    private Calcfile calccomp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourcevtx")
    private Collection<Graphedge> graphedgeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetvtx")
    private Collection<Graphedge> graphedgeCollection1;
    @JoinColumn(name = "vtxspfhdr", referencedColumnName = "spfhdrid")
    @ManyToOne(optional = false)
    private Spfhdr vtxspfhdr;

    public Graphvertex() {
    }

    public Graphvertex(Integer vertexid) {
        this.vertexid = vertexid;
    }

    public Graphvertex(Integer vertexid, String vertextype, String vertexHeading, Calcfile calccomp) {
        this.vertexid = vertexid;
        this.vertextype = vertextype;
        this.vertexHeading = vertexHeading;
        this.calccomp = calccomp;
    }

    public Integer getVertexid() {
        return vertexid;
    }

    public void setVertexid(Integer vertexid) {
        this.vertexid = vertexid;
    }

    public String getVertextype() {
        return vertextype;
    }

    public void setVertextype(String vertextype) {
        this.vertextype = vertextype;
    }

    public String getVertexHeading() {
        return vertexHeading;
    }

    public void setVertexHeading(String vertexHeading) {
        this.vertexHeading = vertexHeading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPointx() {
        return pointx;
    }

    public void setPointx(Double pointx) {
        this.pointx = pointx;
    }

    public Double getPointy() {
        return pointy;
    }

    public void setPointy(Double pointy) {
        this.pointy = pointy;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Calcfile getCalccomp() {
        return calccomp;
    }

    public void setCalccomp(Calcfile calccomp) {
        this.calccomp = calccomp;
    }

    @XmlTransient
    public Collection<Graphedge> getGraphedgeCollection() {
        return graphedgeCollection;
    }

    public void setGraphedgeCollection(Collection<Graphedge> graphedgeCollection) {
        this.graphedgeCollection = graphedgeCollection;
    }

    @XmlTransient
    public Collection<Graphedge> getGraphedgeCollection1() {
        return graphedgeCollection1;
    }

    public void setGraphedgeCollection1(Collection<Graphedge> graphedgeCollection1) {
        this.graphedgeCollection1 = graphedgeCollection1;
    }

    public Spfhdr getVtxspfhdr() {
        return vtxspfhdr;
    }

    public void setVtxspfhdr(Spfhdr vtxspfhdr) {
        this.vtxspfhdr = vtxspfhdr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vertexid != null ? vertexid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Graphvertex)) {
            return false;
        }
        Graphvertex other = (Graphvertex) object;
        if ((this.vertexid == null && other.vertexid != null) || (this.vertexid != null && !this.vertexid.equals(other.vertexid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Graphvertex[ vertexid=" + vertexid + " ]";
    }
    
}
