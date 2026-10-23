package id.co.edi.scm.poprocessor.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailPayload {

    @JacksonXmlProperty(localName = "PART_NO")
    private String partNo;

    @JacksonXmlProperty(localName = "PART_NAME")
    private String partName;

    @JacksonXmlProperty(localName = "QTY")
    private int qty;

    @JacksonXmlProperty(localName = "UNIT")
    private String unit;

    @JacksonXmlProperty(localName = "PRICE")
    private long price;
}
