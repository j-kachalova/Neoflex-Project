package com.kachalova.deal.entities;

import com.kachalova.deal.dto.EmploymentDto;
import com.kachalova.deal.dto.PassportDto;
import com.kachalova.deal.enums.Gender;
import com.kachalova.deal.enums.MaritalStatus;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    @Type(type = "jsonb")
    @Column(name = "passport", columnDefinition = "jsonb")
    private PassportDto passport;
    @Type(type = "jsonb")
    @Column(name = "employment", columnDefinition = "jsonb")
    private EmploymentDto employment;
    private String accountNumber;
}
