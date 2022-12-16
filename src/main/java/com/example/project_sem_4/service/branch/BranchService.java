package com.example.project_sem_4.service.branch;

import com.example.project_sem_4.database.dto.AccountDTO;
import com.example.project_sem_4.database.dto.BranchDTO;
import com.example.project_sem_4.database.dto.RegisterDTO;
import com.example.project_sem_4.database.entities.Account;
import com.example.project_sem_4.database.entities.Branch;
import com.example.project_sem_4.database.entities.MembershipClass;
import com.example.project_sem_4.database.entities.Role;
import com.example.project_sem_4.database.jdbc_query.QueryAccountByJDBC;
import com.example.project_sem_4.database.repository.BranchRepository;
import com.example.project_sem_4.enum_project.GenderEnum;
import com.example.project_sem_4.enum_project.constant.GenderConstant;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionBadRequest;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.sql.SQLDataException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@CrossOrigin()
@Log4j2
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    private QueryAccountByJDBC queryAccountByJDBC;

    public Branch saveBranch(BranchDTO branchDTO){
        Branch branch = new Branch();
      try{
          branch.setAddress(branchDTO.getAddress());
          branch.setHot_line(branchDTO.getHot_line());
          branch.setThumbnail(branchDTO.getThumbnail());
          branch.setStatus(1);
          branch.setCreated_at(new Date());
          return branchRepository.save(branch);
      }catch (Exception ex){
          return null;
      }
    }
}
