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
@Table(name = "userpreferences", catalog = "tcl_systems", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userpreferences.findAll", query = "SELECT u FROM Userpreferences u"),
    @NamedQuery(name = "Userpreferences.findByPreid", query = "SELECT u FROM Userpreferences u WHERE u.preid = :preid"),
    @NamedQuery(name = "Userpreferences.findByFramecolour", query = "SELECT u FROM Userpreferences u WHERE u.framecolour = :framecolour"),
    @NamedQuery(name = "Userpreferences.findByButtoncolour", query = "SELECT u FROM Userpreferences u WHERE u.buttoncolour = :buttoncolour"),
    @NamedQuery(name = "Userpreferences.findByNotification", query = "SELECT u FROM Userpreferences u WHERE u.notification = :notification"),
    @NamedQuery(name = "Userpreferences.findByFont", query = "SELECT u FROM Userpreferences u WHERE u.font = :font"),
    @NamedQuery(name = "Userpreferences.findBySelectdpro", query = "SELECT u FROM Userpreferences u WHERE u.selectdpro = :selectdpro"),
    @NamedQuery(name = "Userpreferences.findBySettings1", query = "SELECT u FROM Userpreferences u WHERE u.settings1 = :settings1"),
    @NamedQuery(name = "Userpreferences.findBySettings2", query = "SELECT u FROM Userpreferences u WHERE u.settings2 = :settings2"),
    @NamedQuery(name = "Userpreferences.findBySettings3", query = "SELECT u FROM Userpreferences u WHERE u.settings3 = :settings3"),
    @NamedQuery(name = "Userpreferences.findBySettings4", query = "SELECT u FROM Userpreferences u WHERE u.settings4 = :settings4")})
public class Userpreferences implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "preid")
    private Integer preid;
    @Basic(optional = false)
    @Column(name = "framecolour")
    private String framecolour;
    @Column(name = "buttoncolour")
    private String buttoncolour;
    @Basic(optional = false)
    @Column(name = "notification")
    private boolean notification;
    @Basic(optional = false)
    @Column(name = "font")
    private String font;
    @Column(name = "selectdpro")
    private Integer selectdpro;
    @Column(name = "settings1")
    private String settings1;
    @Column(name = "settings2")
    private String settings2;
    @Column(name = "settings3")
    private String settings3;
    @Column(name = "settings4")
    private String settings4;
    @JoinColumn(name = "preuser", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users preuser;

    public Userpreferences() {
    }

    public Userpreferences(Integer preid) {
        this.preid = preid;
    }

    public Userpreferences(Integer preid, String framecolour, boolean notification, String font) {
        this.preid = preid;
        this.framecolour = framecolour;
        this.notification = notification;
        this.font = font;
    }

    public Integer getPreid() {
        return preid;
    }

    public void setPreid(Integer preid) {
        this.preid = preid;
    }

    public String getFramecolour() {
        return framecolour;
    }

    public void setFramecolour(String framecolour) {
        this.framecolour = framecolour;
    }

    public String getButtoncolour() {
        return buttoncolour;
    }

    public void setButtoncolour(String buttoncolour) {
        this.buttoncolour = buttoncolour;
    }

    public boolean getNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Integer getSelectdpro() {
        return selectdpro;
    }

    public void setSelectdpro(Integer selectdpro) {
        this.selectdpro = selectdpro;
    }

    public String getSettings1() {
        return settings1;
    }

    public void setSettings1(String settings1) {
        this.settings1 = settings1;
    }

    public String getSettings2() {
        return settings2;
    }

    public void setSettings2(String settings2) {
        this.settings2 = settings2;
    }

    public String getSettings3() {
        return settings3;
    }

    public void setSettings3(String settings3) {
        this.settings3 = settings3;
    }

    public String getSettings4() {
        return settings4;
    }

    public void setSettings4(String settings4) {
        this.settings4 = settings4;
    }

    public Users getPreuser() {
        return preuser;
    }

    public void setPreuser(Users preuser) {
        this.preuser = preuser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preid != null ? preid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userpreferences)) {
            return false;
        }
        Userpreferences other = (Userpreferences) object;
        if ((this.preid == null && other.preid != null) || (this.preid != null && !this.preid.equals(other.preid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Userpreferences[ preid=" + preid + " ]";
    }
    
}
