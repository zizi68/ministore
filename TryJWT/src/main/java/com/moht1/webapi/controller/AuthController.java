package com.moht1.webapi.controller;

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.Exception.ResponseObject;
import com.moht1.webapi.model.ERole;
import com.moht1.webapi.model.Role;
import com.moht1.webapi.model.User;
import com.moht1.webapi.payload.request.LoginRequest;
import com.moht1.webapi.payload.request.SignupRequest;
import com.moht1.webapi.payload.response.JwtResponse;
import com.moht1.webapi.repository.RoleRepository;
import com.moht1.webapi.repository.UserRepository;
import com.moht1.webapi.security.jwt.JwtUtils;
import com.moht1.webapi.security.services.UserDetailsImpl;
import com.moht1.webapi.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } catch (Exception e) {
            return AppUtils.returnJS(HttpStatus.NOT_FOUND, "Login fail!", null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.VALIDATION_NAME_E002.getMessage(), null);
        }
        if (signUpRequest.getPassword().trim().length() < 6 || signUpRequest.getPassword().trim().length() > 40) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.VALIDATION_PASSWORD_E002.getMessage(), null);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.VALIDATION_EMAIL_E002.getMessage(), null);
        }
        if (userRepository.existsByPhone(signUpRequest.getPhone())) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.VALIDATION_PHONE_E002.getMessage(), null);
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhone());
        ;

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(Constants.ROLE_404.getMessage()));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(Constants.ROLE_404.getMessage()));
                        roles.add(adminRole);
                        break;
                    case "staff":
                        Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF)
                                .orElseThrow(() -> new RuntimeException(Constants.ROLE_404.getMessage()));
                        roles.add(staffRole);
                        break;
                    case "shipper":
                        Role shipperRole = roleRepository.findByName(ERole.ROLE_SHIPPER)
                                .orElseThrow(() -> new RuntimeException(Constants.ROLE_404.getMessage()));
                        roles.add(shipperRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(Constants.ROLE_404.getMessage()));
                        roles.add(userRole);
                }
            });
        }
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRoles(roles);
        user.setStatus(true);
        if (user.getImage() == null || user.getImage().isEmpty()) {
            user.setImage("userDefaul.png");
        }

        try {
            User userUpdated = userRepository.save(user);
            return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), userUpdated);
        } catch (ConstraintViolationException e) {
            // TODO: handle exception
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.VALIDATION_EMAIL_E002.getMessage(), null);

        }
    }

    @GetMapping("/user")
    private ResponseEntity<List<User>> getUser() {
        ArrayList<User> list = (ArrayList<User>) userRepository.findAll();
        return ResponseEntity.ok(list);
    }
}