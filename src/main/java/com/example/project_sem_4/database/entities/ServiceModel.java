package com.example.project_sem_4.database.entities;

import com.example.project_sem_4.database.dto.ServiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@Table(name = "services")
public class ServiceModel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    private String thumbnail;

    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name = "type_service_id")
    private TypeService typeService;
    @Column(insertable = false, updatable = false)
    private int type_service_id;

    public ServiceModel() {
        super();
    }

    public ServiceModel(ServiceDTO serviceDTO) {
        this.name = serviceDTO.getName();
        this.description = serviceDTO.getDescription();
        this.type_service_id = serviceDTO.getTypeServiceId();
        this.thumbnail = serviceDTO.getThumbnail();
    }
}
