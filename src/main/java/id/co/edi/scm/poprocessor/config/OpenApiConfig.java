package id.co.edi.scm.poprocessor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI scmOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistem Pemroses Dokumen Purchase Order SCM")
                        .description("Layanan integrasi data SCM: Ekstraksi file Excel di INBOX, penyimpanan ke tabel MySQL (tm_header & tm_detail), serta transformasi ke format standar XML di folder OUTBOX.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Abu Dujanah Siregar")));
    }
}
