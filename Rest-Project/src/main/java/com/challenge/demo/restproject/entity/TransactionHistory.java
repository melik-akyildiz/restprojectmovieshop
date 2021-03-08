package com.challenge.demo.restproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @SequenceGenerator(name = "th_id_generator", sequenceName = "th_id_seq",allocationSize = 1)
    @GeneratedValue(generator = "th_id_generator")
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;
    @Column(name = "products")
    private String products;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Long bonus;

    @ManyToOne(targetEntity = Accounts.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name = "account_id", referencedColumnName = "id" )
    private Accounts ownerAccount;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private String status;

    @JsonProperty("created_at")
    private LocalDate createdAt;

    @JsonProperty("rent_to")
    private LocalDate rentTo;

    @JsonProperty("updated_at")
    private LocalDate updatedAt;

    @PrePersist
    void preSave() {
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
    }

    @PreUpdate
    void preUpdate() {
        if (updatedAt == null) {
            updatedAt = LocalDate.now();
        }
    }


}
