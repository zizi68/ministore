package com.moht1.webapi.controller;

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.model.*;
import com.moht1.webapi.repository.DistrictRepository;
import com.moht1.webapi.repository.ProvinceRepository;
import com.moht1.webapi.repository.UserRepository;
import com.moht1.webapi.repository.WardRepository;
import com.moht1.webapi.service.AddressService;
import com.moht1.webapi.service.UserService;
import com.moht1.webapi.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private ProvinceRepository provinceDao;
    @Autowired
    private DistrictRepository districtDao;
    @Autowired
    private WardRepository wardDao;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/province")
    private ResponseEntity<List<Province>> getAllProvince() {
        ArrayList<Province> list = (ArrayList<Province>) provinceDao.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/district/{id}")
    private ResponseEntity<List<District>> getAllDistrictByProvinceId(@PathVariable("id") String provinceId) {
        return ResponseEntity.ok(districtDao.findAllDistrictByIdProvince(provinceId));
    }

    @GetMapping("/ward/{id}")
    private ResponseEntity<List<Ward>> getAllVillageByDistrictId(@PathVariable("id") String districtId) {
        return ResponseEntity.ok(wardDao.findAllVillageByIdDistrict(districtId));
    }
    
    @GetMapping("/address/{id}")
    private ResponseEntity<List<Address>> getAllAddressByUserId(@PathVariable("id") Integer userId) {
    	
        return ResponseEntity.ok(addressService.findAllByUserId(userId));
    }
    
    @PostMapping("/address/{userId}")
	public ResponseEntity<?> addAddressToUser(@PathVariable("userId") Integer id, @Valid @RequestBody Address address, BindingResult bindingResult) {
    	User user = userService.findById(id);
    	if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.USER_404.getMessage(), null);
		}
		if (bindingResult.hasErrors()) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		}
		address.setUser(user);
		address = addressService.addAddress(address);
		return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), address);
	}
    
    @PutMapping(value = "/address")
	public ResponseEntity<?> updateAddress(@Valid @RequestBody Address newAddress, BindingResult bindingResult) {
		Address oldAddress = null;
		oldAddress = addressService.findById(newAddress.getId());
		
		if(oldAddress == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.ADDRESS_404.getMessage(), null);
		
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);

		newAddress.setUser(oldAddress.getUser());
		addressService.updateAddress1(newAddress);
		return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), newAddress);
    }
			
    @PutMapping("/address/{id}")
   	public ResponseEntity<?> updateAddressToUser(@PathVariable("id") Integer id, @Valid @RequestBody Address address) {
    	Address oldAddress = addressService.findById(id);
		if (oldAddress == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.ADDRESS_404.getMessage(), null);
		}
		
		oldAddress.setSpecificAddress(address.getSpecificAddress());
		oldAddress.setWard(address.getWard());
		
   		addressService.updateAddress(oldAddress);
   		return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), oldAddress);
   	}
    
    @DeleteMapping("/address/{id}")
	public ResponseEntity<?> deleteAddressById(@PathVariable("id") Integer id) {
    	Address address = addressService.findById(id);
		if (address == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.ADDRESS_404.getMessage(), null);
		}
		if(address.getUser() != null) {
			User user = userService.findById(address.getUser().getId());
			user.getAddresses().remove(address);
			userRepository.save(user);
		}
		if(address.getWard()!= null) {
			Ward ward = wardDao.findById(address.getWard().getId()).get();
			ward.getAddresses().remove(address);
			wardDao.save(ward);
		}
		
		addressService.deleteById(id);
		
		return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), null);
	}
}
