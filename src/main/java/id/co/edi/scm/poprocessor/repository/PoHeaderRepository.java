package id.co.edi.scm.poprocessor.repository;

import id.co.edi.scm.poprocessor.entity.PoHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoHeaderRepository extends JpaRepository<PoHeader, String> {

    @Query("SELECT DISTINCT h FROM PoHeader h LEFT JOIN FETCH h.details ORDER BY h.poNumber ASC")
    List<PoHeader> findAllWithDetails();
}
