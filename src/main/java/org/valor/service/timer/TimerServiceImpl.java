package org.valor.service.timer;

import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.valor.model.dto.SessionRequest;
import org.valor.model.dto.SessionResponse;
import org.valor.model.dto.TimerSettingsDto;
import org.valor.model.enums.SessionType;

import java.time.LocalDate;

@Service
public class TimerServiceImpl implements TimerService{

    @Override
    public SessionResponse saveSession(SessionRequest request, User user) {
        return null;
    }

    @Override
    public Page<SessionResponse> getSessions(Pageable pageable, LocalDate from, LocalDate to, SessionType type, User user) {
        return null;
    }

    @Override
    public void deleteSession(Long id, User user) {

    }

    @Override
    public TimerSettingsDto updateSettings(TimerSettingsDto settings, User user) {
        return null;
    }

    @Override
    public Object getStats(LocalDate from, LocalDate to, User user) {
        return null;
    }

    @Override
    public TimerSettingsDto getSettings(User user) {
        return null;
    }
}
