package com.example.project_sem_4.service.voucher;

import com.example.project_sem_4.database.dto.VoucherDTO;
import com.example.project_sem_4.database.entities.Voucher;
import com.example.project_sem_4.database.repository.AccountRepository;
import com.example.project_sem_4.database.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public boolean saveVoucher(VoucherDTO voucherDTO){
        Voucher newVoucher = new Voucher();
        int voucherCodeInt = getVoucherCodeInt();
        newVoucher.setVoucherCodeInt(voucherCodeInt);
        newVoucher.setVoucherCode(getVoucherCode(voucherCodeInt));
        newVoucher.setName(voucherDTO.getName());
        newVoucher.setDiscount(voucherDTO.getDiscount());
        newVoucher.setExpired_date(voucherDTO.getExpired_date());
        newVoucher.set_used(false);
        Voucher result = voucherRepository.save(newVoucher);
        return result != null;
    };

    public Voucher getVoucherById(int id){
        return  voucherRepository.findById(String.valueOf(id)).orElse(null);
    }

    public boolean disableVoucher(String voucherCode){
        Voucher checkVoucher = voucherRepository.findByVoucherCode(voucherCode);
        if(checkVoucher == null || checkVoucher.getExpired_date().before(new Date())){return false;}
        checkVoucher.set_used(true);
        Voucher result = voucherRepository.save(checkVoucher);
        return result != null;
    };

    public int getVoucherCodeInt(){
       List<Voucher> listResult =  voucherRepository.findAllByOrderByVoucherCodeIntDesc();
       if (listResult.isEmpty()){return  1;}
        return listResult.get(0).getVoucherCodeInt() + 1;
    }

    public String getVoucherCode(int a){
      String result = "HNVC-QB-" + a;
      return result;
    };
}
