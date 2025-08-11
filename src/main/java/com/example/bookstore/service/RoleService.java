package com.example.bookstore.service;

import com.example.bookstore.domain.Role;
import com.example.bookstore.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRole(){
       return this.roleRepository.findAll();
    }

}
