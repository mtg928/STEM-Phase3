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
@Table(name = "graphedge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Graphedge.findAll", query = "SELECT g FROM Graphedge g")
    , @NamedQuery(name = "Graphedge.findByEdgeid", query = "SELECT g FROM Graphedge g WHERE g.edgeid = :edgeid")
    , @NamedQuery(name = "Graphedge.findByEdgetype", query = "SELECT g FROM Graphedge g WHERE g.edgetype = :edgetype")
    , @NamedQuery(name = "Graphedge.findBySourcelabel", query = "SELECT g FROM Graphedge g WHERE g.sourcelabel = :sourcelabel")
    , @NamedQuery(name = "Graphedge.findByTargetlabel", query = "SELECT g FROM Graphedge g WHERE g.targetlabel = :targetlabel")})
public class Graphedge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "edgeid")
    private Integer edgeid;
    @Basic(optional = false)
    @Column(name = "edgetype")
    private String edgetype;
    @Basic(optional = false)
    @Column(name = "sourcelabel")
    private String sourcelabel;
    @Column(name = "targetlabel")
    private String targetlabel;
    @JoinColumn(name = "sourcevtx", referencedColumnName = "vertexid")
    @ManyToOne(optional = false)
    private Graphvertex sourcevtx;
    @JoinColumn(name = "targetvtx", referencedColumnName = "vertexid")
    @ManyToOne(optional = false)
    private Graphvertex targetvtx;
    @JoinColumn(name = "vtxspfhdr", referencedColumnName = "spfhdrid")
    @ManyToOne(optional = false)
    private Spfhdr vtxspfhdr;

    public Graphedge() {
    }

    public Graphedge(Integer edgeid) {
        this.edgeid = edgeid;
    }

    public Graphedge(Integer edgeid, String edgetype, String sourcelabel) {
        this.edgeid = edgeid;
        this.edgetype = edgetype;
        this.sourcelabel = sourcelabel;
    }

    public Integer getEdgeid() {
        return edgeid;
    }

    public void setEdgeid(Integer edgeid) {
        this.edgeid = edgeid;
    }

    public String getEdgetype() {
        return edgetype;
    }

    public void setEdgetype(String edgetype) {
        this.edgetype = edgetype;
    }

    public String getSourcelabel() {
        return sourcelabel;
    }

    public void setSourcelabel(String sourcelabel) {
        this.sourcelabel = sourcelabel;
    }

    public String getTargetlabel() {
        return targetlabel;
    }

    public void setTargetlabel(String targetlabel) {
        this.targetlabel = targetlabel;
    }

    public Graphvertex getSourcevtx() {
        return sourcevtx;
    }

    public void setSourcevtx(Graphvertex sourcevtx) {
        this.sourcevtx = sourcevtx;
    }

    public Graphvertex getTargetvtx() {
        return targetvtx;
    }

    public void setTargetvtx(Graphvertex targetvtx) {
        this.targetvtx = targetvtx;
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
        hash += (edgeid != null ? edgeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Graphedge)) {
            return false;
        }
        Graphedge other = (Graphedge) object;
        if ((this.edgeid == null && other.edgeid != null) || (this.edgeid != null && !this.edgeid.equals(other.edgeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Graphedge[ edgeid=" + edgeid + " ]";
    }
    
}
