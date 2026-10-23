package id.co.edi.scm.poprocessor.config;

import id.co.edi.scm.poprocessor.dto.response.ProcessResult;
import id.co.edi.scm.poprocessor.exception.ScmProcessingException;
import id.co.edi.scm.poprocessor.service.PoDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScmStartupRunner implements CommandLineRunner {

    private final PoDataService poDataService;

    @Override
    public void run(String... args) {
        log.info("==============================================================");
        log.info(" Sistem Pemroses Dokumen SCM PO - Inisialisasi Otomatis       ");
        log.info("==============================================================");
        try {
            ProcessResult result = poDataService.runFullCycle();
            log.info(">> Status Proses  : {}", result.isSuccess() ? "BERHASIL" : "GAGAL");
            log.info(">> Detail         : {}", result.getMessage());
            if (result.getOutputFile() != null) {
                log.info(">> Berkas XML     : {}", result.getOutputFile());
            }
        } catch (ScmProcessingException ex) {
            log.error(">> Kendala SCM [{}] : {}", ex.getSource(), ex.getMessage());
        } catch (Exception ex) {
            log.error(">> Terjadi kesalahan saat inisialisasi aplikasi: {}", ex.getMessage(), ex);
        }
        log.info("==============================================================");
    }
}
