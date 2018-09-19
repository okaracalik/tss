/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import jee18.logic.IAppSystem;

/**
 *
 * @author okaracalik
 */
@Named
@SessionScoped
public class AppMBean implements Serializable {

    @EJB
    private IAppSystem appSystem;

    private static final long serialVersionUID = 1523479642013931903L;

    private String currentLocale = Locale.ENGLISH.toString();

    private static final Map<String, Locale> availableLocales;

    static {
        availableLocales = new LinkedHashMap<>();
        availableLocales.put("English", Locale.ENGLISH);
        availableLocales.put("Deutsch", Locale.GERMAN);
    }

    public void setCurrentLocale(String newLocale) {
        this.currentLocale = newLocale;

        availableLocales.entrySet().stream().filter((entry) -> (entry.getValue().toString().equals(newLocale))).forEachOrdered((entry) -> {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(entry.getValue());
        });
    }

    public Map<String, Locale> getAvailableLocales() {
        return availableLocales;
    }

    public String getCurrentLocale() {
        return currentLocale;
    }

    public String clickSignOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/app/sign-in.xhtml";
    }

    public String getUsername() {
        Principal currentUser = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (currentUser == null) {
            return null;
        }
        return currentUser.getName();
    }

    public Boolean isSecretary() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("SECRETARY");
    }

    public Boolean isSupervisor() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("SUPERVISOR");
    }

    public Boolean isAssistant() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ASSISTANT");
    }

    public Boolean isEmployee() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("EMPLOYEE");
    }

    public Boolean isStaff() {
        return isSecretary() || isSupervisor() || isAssistant();
    }

    public Boolean isAuthenticated() {
        return isStaff() || isEmployee();
    }

    public boolean isLoggedIn() {
        return getUsername() != null;
    }

    public String getRole() {
        if (isSecretary()) {
            return "Secretary";
        }
        else if (isSupervisor()) {
            return "Supervisor";
        }
        else if (isAssistant()) {
            return "Assistant";
        }
        else if (isEmployee()) {
            return "Employee";
        }
        else {
            return "Undefined";
        }
    }

    public void generateData() {
        appSystem.generateData();
    }

}
