package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{
        countryName = countryName.toUpperCase();
            User user = userRepository2.findById(userId).get();
            if(user.getConnected())throw new Exception("Already connected");
            Country ogCountry = user.getOriginalCountry();
            if(ogCountry.toString().equals(countryName))return user;

        List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
        ServiceProvider serviceProvider = null;
        Country country = null;
        boolean flag = false;
        for(ServiceProvider serviceProvider1 : serviceProviderList){

            for(Country country1:serviceProvider1.getCountryList()){
                if(country1.getCountryName().toString().equals(countryName)){
                    flag = true;
                    serviceProvider=serviceProvider1;
                    country=country1;
                    break;
                }
            }
            if(flag)break;
        }
        if(!flag)throw new Exception("Unable to connect");
        user.setConnected(Boolean.TRUE);
        user.setMaskedIp(country.getCode()+"."+serviceProvider.getId()+"."+userId);
        Connection connection = new Connection();
        connection.setUser(user);
        connection.setServiceProvider(serviceProvider);
        connection = connectionRepository2.save(connection);
        user.getConnectionList().add(connection);
        serviceProvider.getConnectionList().add(connection);
        userRepository2.save(user);
        serviceProviderRepository2.save(serviceProvider);
        return user;


    }
    @Override
    public User disconnect(int userId) throws Exception {
           User user = userRepository2.findById(userId).get();
        List<Connection> connectionList = user.getConnectionList();
           if(connectionList.size()==0)throw new Exception("Already disconnected");
           user.setConnected(Boolean.FALSE);

           Connection connection =  connectionList.get(0);
           connectionRepository2.delete(connection);
           user.setMaskedIp(null);
           userRepository2.save(user);
           return user;


    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
          User sender = userRepository2.findById(senderId).get();
          User receiver = userRepository2.findById(receiverId).get();
          String senderCountryCode = sender.getOriginalCountry().getCode();
          String receiverCountryCode;

          if(!receiver.getConnected()){receiverCountryCode=receiver.getOriginalCountry().getCode();}
          else{
              receiverCountryCode = receiver.getMaskedIp().substring(0,3);
          }
          if(senderCountryCode.equals(receiverCountryCode))return sender;
          else{
              String countryName = "";
              switch(receiverCountryCode){
                  case "001":countryName="IND";break;
                  case "002":countryName="USA";break;
                  case "003":countryName="AUS";break;
                  case "004":countryName="CHI";break;
                  case "005":countryName="JPN";break;
              }
              try {
                  return this.connect(senderId, countryName);
              }
              catch (Exception e){
                  throw new Exception("Cannot establish communication");

              }

          }


    }
}
