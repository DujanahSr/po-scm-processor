package id.co.edi.scm.poprocessor.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "HEADERS")
public class HeadersWrapper {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "HEADER")
    private List<HeaderPayload> headers;
}
