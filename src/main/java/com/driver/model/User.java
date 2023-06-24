package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String maskedIP;
    private String originalIP;

    private Boolean connected;
    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<ServiceProvider> serviceProviderList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Connection> connectionList = new ArrayList<>();
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Country country;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaskedIP(String maskedIP) {
        this.maskedIP = maskedIP;
    }

    public void setOriginalIP(String originalIP) {
        this.originalIP = originalIP;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMaskedIP() {
        return maskedIP;
    }

    public String getOriginalIP() {
        return originalIP;
    }

    public Boolean getConnected() {
        return connected;
    }

    public List<ServiceProvider> getServiceProviderList() {
        return serviceProviderList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public Country getCountry() {
        return country;
    }
}
