package id.co.edi.scm.poprocessor.controller;

import id.co.edi.scm.poprocessor.dto.response.ProcessResult;
import id.co.edi.scm.poprocessor.service.PoDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scm")
@RequiredArgsConstructor
@Tag(name = "Operasi Purchase Order", description = "Endpoint untuk memicu proses import Excel dan ekspor dokumen XML SCM")
public class ScmController {

    private final PoDataService poDataService;

    @PostMapping("/import")
    @Operation(
        summary = "Import Data Excel ke Database",
        description = "Membaca berkas PO_SCM.xlsx dari folder INBOX dan menyimpan sheet HEADER serta DETAIL ke tabel MySQL."
    )
    public ResponseEntity<ProcessResult> importExcel() {
        return ResponseEntity.ok(poDataService.importFromExcel());
    }

    @PostMapping("/export")
    @Operation(
        summary = "Ekspor Database ke Dokumen XML",
        description = "Mengambil data tm_header dan tm_detail dari database MySQL lalu membentuk file XML ke folder OUTBOX."
    )
    public ResponseEntity<ProcessResult> exportXml() {
        return ResponseEntity.ok(poDataService.exportToXml());
    }

    @PostMapping("/process")
    @Operation(
        summary = "Proses Siklus Lengkap (Import & Ekspor)",
        description = "Menjalankan proses import dari Excel dilanjutkan dengan pembuatan dokumen XML secara otomatis."
    )
    public ResponseEntity<ProcessResult> processAll() {
        return ResponseEntity.ok(poDataService.runFullCycle());
    }
}
