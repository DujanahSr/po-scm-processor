package id.co.edi.scm.poprocessor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tm_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_number", nullable = false)
    private PoHeader header;

    @Column(name = "part_no", length = 50, nullable = false)
    private String partNo;

    @Column(name = "part_name", length = 100, nullable = false)
    private String partName;

    @Column(nullable = false)
    private int qty;

    @Column(length = 20, nullable = false)
    private String unit;

    @Column(nullable = false)
    private long price;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
