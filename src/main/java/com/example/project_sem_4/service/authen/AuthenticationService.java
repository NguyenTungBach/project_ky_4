package com.example.project_sem_4.service.authen;

import com.example.project_sem_4.database.dto.AccountDTO;
import com.example.project_sem_4.database.dto.RegisterDTO;
import com.example.project_sem_4.database.entities.Account;
import com.example.project_sem_4.database.entities.Role;
import com.example.project_sem_4.database.repository.AccountRepository;
import com.example.project_sem_4.database.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        Account account = accountOptional.orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException("Email not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role:
                account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetails userDetail
                = new User(account.getEmail(), account.getPassword(), authorities);
        return
                userDetail;

    }

    public AccountDTO saveAccount(RegisterDTO registerDTO) {
        //create new user role if not exist
        Set<Role> roles = new HashSet<>();
        for (Role role: registerDTO.getRoles()) {
            Optional<Role> userRoleOptional = roleRepository.findByName(role.getName());
            Role userRole = userRoleOptional.orElse(null);
            if (userRole == null) {
                //create new role
//            userRole = roleRepository.save(new Role(USER_ROLE));
                return null;
            }
            roles.add(userRoleOptional.get());
        }
        //check if username has exist
        Optional<Account> byUsername = accountRepository.findByEmail(registerDTO.getEmail());
        if (byUsername.isPresent()) {
            return null;
        }
        Account account = new Account();

        account.setEmail(registerDTO.getEmail());
        account.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setStatus(1);
        account.setRoles(roles);
        Account save = accountRepository.save(account);
        return new AccountDTO(save);
    }

    public Account getAccount(String email) {
        Optional<Account> byUsername = accountRepository.findByEmail(email);
        return byUsername.orElse(null);
    }
}
