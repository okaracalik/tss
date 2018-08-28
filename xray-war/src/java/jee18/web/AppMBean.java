/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

/**
 *
 * @author okaracalik
 */
public class AppMBean {

    private Locale locale;
    
    public AppMBean() {
    }
    
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        System.out.println("setLanguage");
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    
    public void clickSignOut() {
        System.out.println("clickSignOut");
    }
    
    
}
