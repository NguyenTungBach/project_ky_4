package com.example.project_sem_4.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceDTO {
    @NotEmpty(message = "Thiếu tên dịch vụ")
    private String name;
    private String description;
    @NotNull(message = "Thiếu loại dịch vụ")
    @Min(value = 1, message= "Thiếu loại dịch vụ")
    private Integer typeServiceId;
    private String thumbnail;
}
