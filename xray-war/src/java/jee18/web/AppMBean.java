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
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author okaracalik
 */
@Named
@SessionScoped
public class AppMBean implements Serializable {

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

    public void clickSignOut() {
        System.out.println("clickSignOut");
    }

    public String getUsername() {
        Principal currentUser = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (currentUser == null) {
            return null;
        }
        return currentUser.getName();
    }

    public boolean isLoggedIn() {
        return getUsername() != null;
    }

}
