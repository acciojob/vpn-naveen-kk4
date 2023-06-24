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
    private String maskedIp;
    private String originalIp;

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

    public void setMaskedIp(String maskedIP) {
        this.maskedIp = maskedIP;
    }

    public void setOriginalIp(String originalIP) {
        this.originalIp = originalIP;
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

    public void setOriginalCountry(Country country) {
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

    public String getMaskedIp() {
        return maskedIp;
    }

    public String getOriginalIp() {
        return originalIp;
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

    public Country getOriginalCountry() {
        return country;
    }
}
