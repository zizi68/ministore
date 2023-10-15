package com.moht1.webapi.serviceImpl;

import com.moht1.webapi.model.Address;
import com.moht1.webapi.model.User;
import com.moht1.webapi.repository.*;
import com.moht1.webapi.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private WardRepository wardRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Address> findAllByUserId(Integer userId) {
        User user = userRepository.getById(userId);
        if (user == null) {
            return null;
        }
        return addressRepository.findByUser(user);
    }

    @Override
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address findById(Integer id) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            return null;
        }
        return address.get();
    }

    @Override
    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            boolean isFound = addressRepository.existsById(id);
            if (isFound) {
                addressRepository.deleteById(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAddress1(Address address) {
        // TODO Auto-generated method stub
        addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

}
