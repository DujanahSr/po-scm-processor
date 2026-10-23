package id.co.edi.scm.poprocessor.service;

import id.co.edi.scm.poprocessor.dto.response.ProcessResult;

public interface PoDataService {

    ProcessResult importFromExcel();

    ProcessResult exportToXml();

    ProcessResult runFullCycle();
}
