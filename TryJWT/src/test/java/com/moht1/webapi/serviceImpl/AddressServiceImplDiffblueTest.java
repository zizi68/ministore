package com.moht1.webapi.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moht1.webapi.model.Address;
import com.moht1.webapi.model.User;
import com.moht1.webapi.repository.AddressRepository;
import com.moht1.webapi.repository.DistrictRepository;
import com.moht1.webapi.repository.ProvinceRepository;
import com.moht1.webapi.repository.UserRepository;
import com.moht1.webapi.repository.WardRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddressServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AddressServiceImplDiffblueTest {
    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @MockBean
    private DistrictRepository districtRepository;

    @MockBean
    private ProvinceRepository provinceRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private WardRepository wardRepository;

    /**
     * Method under test: {@link AddressServiceImpl#findAllByUserId(Integer)}
     */
    @Test
    void testFindAllByUserId() {
        ArrayList<Address> addressList = new ArrayList<>();
        when(addressRepository.findByUser(Mockito.<User>any())).thenReturn(addressList);

        User user = new User();
        user.setAddresses(new HashSet<>());
        user.setCarts(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1);
        user.setImage("Image");
        user.setImports(new HashSet<>());
        user.setLastName("Doe");
        user.setOrder(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRoles(new HashSet<>());
        user.setStatus(true);
        user.setUsername("janedoe");
        when(userRepository.getById(Mockito.<Integer>any())).thenReturn(user);
        List<Address> actualFindAllByUserIdResult = addressServiceImpl.findAllByUserId(1);
        verify(addressRepository).findByUser(Mockito.<User>any());
        verify(userRepository).getById(Mockito.<Integer>any());
        assertTrue(actualFindAllByUserIdResult.isEmpty());
        assertSame(addressList, actualFindAllByUserIdResult);
    }

    /**
     * Method under test: {@link AddressServiceImpl#findAllByUserId(Integer)}
     */
    @Test
    void testFindAllByUserId2() {
        ArrayList<Address> addressList = new ArrayList<>();
        when(addressRepository.findByUser(Mockito.<User>any())).thenReturn(addressList);

        User user = new User();
        user.setAddresses(new HashSet<>());
        user.setCarts(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1);
        user.setImage("Image");
        user.setImports(new HashSet<>());
        user.setLastName("Doe");
        user.setOrder(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRoles(new HashSet<>());
        user.setStatus(false);
        user.setUsername("janedoe");
        when(userRepository.getById(Mockito.<Integer>any())).thenReturn(user);
        List<Address> actualFindAllByUserIdResult = addressServiceImpl.findAllByUserId(1);
        verify(addressRepository).findByUser(Mockito.<User>any());
        verify(userRepository).getById(Mockito.<Integer>any());
        assertTrue(actualFindAllByUserIdResult.isEmpty());
        assertSame(addressList, actualFindAllByUserIdResult);
    }
}

