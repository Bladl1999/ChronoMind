package org.valor.mapper;


import org.valor.model.dto.RecurrenceRequest;
import org.valor.model.dto.RecurrenceResponse;
import org.valor.model.entity.Recurrence;

public class RecurrenceMapper {
    public static Recurrence toEntity(RecurrenceRequest request) {
        if (request == null) return null;
        Recurrence recurrence = new Recurrence();
        recurrence.setType(request.type());
        recurrence.setInterval(request.interval());
        recurrence.setEndDate(request.endDate());
        return recurrence;
    }

    public static void updateEntity(Recurrence recurrence, RecurrenceRequest request) {
        if (request == null) return;
        recurrence.setType(request.type());
        recurrence.setInterval(request.interval());
        recurrence.setEndDate(request.endDate());
    }

    public static RecurrenceResponse toDto(Recurrence recurrence) {
        return new RecurrenceResponse(
                recurrence.getType(),
                recurrence.getInterval(),
                recurrence.getEndDate()
        );
    }
}