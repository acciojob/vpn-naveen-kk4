package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin();
        admin.setPassword(password);
        admin.setUsername(username);
        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(providerName);
        Admin admin = adminRepository1.findById(adminId).get();
        serviceProvider.setAdmin(admin);
        admin.getServiceProviders().add(serviceProvider);
        adminRepository1.save(admin);
        return admin;

    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{

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
        country.setCode(country.getCountryName().toCode());
        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();
        serviceProvider.getCountryList().add(country);
        country.setServiceProvider(serviceProvider);
        serviceProviderRepository1.save(serviceProvider);
        return serviceProvider;




    }
}
