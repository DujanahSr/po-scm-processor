package id.co.edi.scm.poprocessor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tm_header")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoHeader {

    @Id
    @Column(name = "po_number", length = 20, nullable = false)
    private String poNumber;

    @Column(name = "po_date", nullable = false)
    private LocalDate poDate;

    @Column(name = "buyer_name", length = 100, nullable = false)
    private String buyerName;

    @Column(name = "buyer_addr", length = 255, nullable = false)
    private String buyerAddr;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PoDetail> details = new ArrayList<>();
}
