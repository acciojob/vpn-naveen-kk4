package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
        countryName = countryName.toLowerCase();
        if(!(countryName.equals("ind") || countryName.equals("aus") || countryName.equals("usa") ||
                countryName.equals("chi") || countryName.equals("jpn")))throw new Exception("Country not found");
        Country country = new Country();
        switch (countryName){
            case "ind":country.setCountryName(CountryName.IND);
                break;
            case "aus":country.setCountryName(CountryName.AUS);
                break;
            case "usa":country.setCountryName(CountryName.USA);
                break;
            case "chi":country.setCountryName(CountryName.CHI);
                break;
            case "jpn":country.setCountryName(CountryName.JPN);
                break;

        }
        User user = new User();
        user.setPassword(password);

        user.setUsername(username);
        user.setConnected(Boolean.FALSE);
        user.setMaskedIp(null);
        user = userRepository3.save(user);
        String ogId = country.getCode()+user.getId();
        user.setOriginalIp(ogId);
        user.setOriginalCountry(country);
        country.setUser(user);
        userRepository3.save(user);
        return user;

    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
        User user = userRepository3.findById(userId).get();
        Country country = user.getOriginalCountry();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        serviceProvider.getCountryList().add(country);
        country.setServiceProvider(serviceProvider);
        serviceProviderRepository3.save(serviceProvider);
        user.getServiceProviderList().add(serviceProvider);
        serviceProvider.getUsers().add(user);
        userRepository3.save(user);
        return user;
    }
}
