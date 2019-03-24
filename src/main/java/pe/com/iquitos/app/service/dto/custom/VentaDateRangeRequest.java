package pe.com.iquitos.app.service.dto.custom;

import java.time.LocalDate;

public class VentaDateRangeRequest {
    LocalDate begin;
    LocalDate end;

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
