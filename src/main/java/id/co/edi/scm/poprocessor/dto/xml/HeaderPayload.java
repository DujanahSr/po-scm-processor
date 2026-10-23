package id.co.edi.scm.poprocessor.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeaderPayload {

    @JacksonXmlProperty(localName = "PO_NUMBER")
    private String poNumber;

    @JacksonXmlProperty(localName = "PO_DATE")
    private String poDate;

    @JacksonXmlProperty(localName = "BUYER_NAME")
    private String buyerName;

    @JacksonXmlProperty(localName = "BUYER_ADDR")
    private String buyerAddr;

    @JacksonXmlElementWrapper(localName = "DETAILS")
    @JacksonXmlProperty(localName = "DETAIL")
    private List<DetailPayload> details;
}
