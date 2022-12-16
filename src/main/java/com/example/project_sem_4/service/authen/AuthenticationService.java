package com.example.project_sem_4.service.authen;

import com.example.project_sem_4.database.dto.AccountDTO;
import com.example.project_sem_4.database.dto.RegisterDTO;
import com.example.project_sem_4.database.dto.search.account.AccountSearchDTO;
import com.example.project_sem_4.database.entities.Account;
import com.example.project_sem_4.database.entities.MembershipClass;
import com.example.project_sem_4.database.entities.Role;
import com.example.project_sem_4.database.jdbc_query.QueryAccountByJDBC;
import com.example.project_sem_4.database.repository.AccountRepository;
import com.example.project_sem_4.database.repository.MembershipClassRepository;
import com.example.project_sem_4.database.repository.RoleRepository;
import com.example.project_sem_4.database.search_body.AccountSearchBody;
import com.example.project_sem_4.enum_project.GenderEnum;
import com.example.project_sem_4.enum_project.constant.GenderConstant;
import com.example.project_sem_4.enum_project.ERROR;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionBadRequest;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@CrossOrigin()
@Log4j2
public class AuthenticationService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    private MembershipClassRepository membershipClassRepository;

    @Autowired
    private QueryAccountByJDBC queryAccountByJDBC;


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

    public AccountDTO saveAccount(RegisterDTO registerDTO){
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
        if (accountRepository.findAccountsByEmail(registerDTO.getEmail()).size() >= 1){
            throw new ApiExceptionBadRequest("accounts","email","Email đã tồn tại");
        }
        //check if username has exist
        Optional<Account> byUsername = accountRepository.findByEmail(registerDTO.getEmail());
        if (byUsername.isPresent()) {
            return null;
        }
        Account account = new Account();
        account.setName(registerDTO.getName());
        account.setAddress(registerDTO.getAddress());
        account.setPhone(registerDTO.getPhone());
        account.setEmail(registerDTO.getEmail());
        account.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        switch (registerDTO.getGender()){
            case GenderConstant.CHECKGENER.MALE:
                account.setGender(GenderEnum.MALE.toString());
                break;
            case GenderConstant.CHECKGENER.FEMALE:
                account.setGender(GenderEnum.FEMALE.toString());
                break;
        }

        if (registerDTO.getThumbnail() !=null){
            account.setThumbnail(registerDTO.getThumbnail());
        }

        account.setCreated_at(new Date());
        account.setStatus(1);

        MembershipClass membershipClass = membershipClassRepository.findById(5).orElse(null);
        if (membershipClass == null){
            throw new ApiExceptionNotFound("membership_classes","id","với giá trị nhập cứng là "+5);
        }
        account.setMembershipClass(membershipClass);
        account.setRoles(roles);
        Account save = accountRepository.save(account);
        return new AccountDTO(save);
    }

    public Account getAccount(String email) {
        Optional<Account> byUsername = accountRepository.findByEmail(email);
        return byUsername.orElse(null);
    }
    @Transactional
    public Account updateAccount(RegisterDTO account, int id){
        Account checkAccount = accountRepository.findById(id).orElse(null);
        if (checkAccount == null){
            throw new ApiExceptionNotFound("accounts","id", "có giá trị là " + id);
        }
//        Account.AccountBuilder updateAccount = Account.builder()
//                .name(account.getName());
        if (!checkAccount.getEmail().equals(account.getEmail())){
            if (accountRepository.findAccountsByEmail(account.getEmail()).size() >= 1){
                throw new ApiExceptionBadRequest("accounts","email","Email đã tồn tại");
            }
            checkAccount.setEmail(account.getEmail());
        }

        if (account.getPassword() != null){
            checkAccount.setPassword(passwordEncoder.encode(account.getPassword()));
        }

        if (account.getMember_ship_class_id() != null){
            MembershipClass membershipClass = membershipClassRepository.findById(account.getMember_ship_class_id()).orElse(null);
            if (membershipClass == null){
                throw new ApiExceptionNotFound("membership_classes","id","với giá trị là "+account.getMember_ship_class_id());
            }
            checkAccount.setMembershipClass(membershipClass);
        }

        if (account.getTotal_payment() != null){
            checkAccount.setTotal_payment(account.getTotal_payment());
        }

        if (account.getRoles() != null && account.getRoles().size() <= 0){
            throw new ApiExceptionBadRequest("accounts","roles","Yêu cầu tồn tại ít nhất một quyền");
        }

        if (account.getGender() != null){
            if (!account.getGender().equals(GenderConstant.CHECKGENER.MALE) && !account.getGender().equals(GenderConstant.CHECKGENER.MALE)){
                throw new ApiExceptionBadRequest("accounts","gender","Sai quy định dạng giới tính");
            }
        }

        if (account.getRoles() != null && account.getRoles().size() >0){
            Set<Role> roles = new HashSet<>();
            for (Role role: account.getRoles()) {
                Optional<Role> userRoleOptional = roleRepository.findByName(role.getName());
                Role userRole = userRoleOptional.orElse(null);
                if (userRole == null) {
                    //create new role
//            userRole = roleRepository.save(new Role(USER_ROLE));
                    return null;
                }
                roles.add(userRoleOptional.get());
            }

            checkAccount.setRoles(roles);
        }

        return accountRepository.save(checkAccount);
    }

    public Account findAccountById(int id){
        return accountRepository.findById(id).orElse(null);
    }
    public List<Account> findAccountByRole_id(int id){
        return accountRepository.findAccountsByRole_id(id);
    }

    public Map<String, Object> findAllAccount(AccountSearchBody searchBody){
        Gson gson = new Gson();
        Type listType = new TypeToken<Set<Role>>(){}.getType();
        List<AccountSearchDTO> listContentPage = queryAccountByJDBC.filterWithPaging(searchBody);
        for (AccountSearchDTO check: listContentPage) {
            check.setRoles(gson.fromJson(check.getRolesListBefore(),listType));
        }
        List<AccountSearchDTO> listContentNoPage = queryAccountByJDBC.filterWithNoPaging(searchBody);

        Map<String, Object> responses = new HashMap<>();
        responses.put("content",listContentPage);
        responses.put("currentPage",searchBody.getPage());
        responses.put("totalItems",listContentNoPage.size());
        responses.put("totalPage",(int) Math.ceil((double) listContentNoPage.size() / searchBody.getLimit()));
        return responses;

//        return accountRepository.findAll();
    }

}
