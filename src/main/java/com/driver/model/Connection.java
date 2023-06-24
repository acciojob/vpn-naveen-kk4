package com.driver.model;

import javax.persistence.*;

@Table
@Entity
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn
    private ServiceProvider serviceProvider;
    @ManyToOne
    @JoinColumn
    private User user;

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public User getUser() {
        return user;
    }
}
