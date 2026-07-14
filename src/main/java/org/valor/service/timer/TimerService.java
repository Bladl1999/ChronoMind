package org.valor.service.timer;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.valor.model.dto.SessionRequest;
import org.valor.model.dto.SessionResponse;
import org.valor.model.dto.TimerSettingsDto;
import org.valor.model.enums.SessionType;

import java.time.LocalDate;

public interface TimerService {
    SessionResponse saveSession(SessionRequest request, User user);

    Page<SessionResponse> getSessions(Pageable pageable, LocalDate from, LocalDate to, SessionType type, User user);

    void deleteSession(Long id, User user);

    TimerSettingsDto updateSettings(TimerSettingsDto settings, User user);

    Object getStats(LocalDate from, LocalDate to, User user);

    TimerSettingsDto getSettings(User user);
}
