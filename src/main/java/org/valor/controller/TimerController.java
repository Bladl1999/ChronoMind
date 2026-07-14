package org.valor.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.valor.model.dto.SessionRequest;
import org.valor.model.dto.SessionResponse;
import org.valor.model.dto.TimerSettingsDto;
import org.valor.model.enums.SessionType;
import org.valor.service.timer.TimerService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/timer")
public class TimerController {

    private final TimerService timerService;

    @Autowired
    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse saveSession(@RequestBody  SessionRequest request,
                                       @AuthenticationPrincipal User user) {
        return timerService.saveSession(request, user);
    }

    @GetMapping("/sessions")
    public Page<SessionResponse> getSessions(Pageable pageable,
                                             @RequestParam(required = false) LocalDate from,
                                             @RequestParam(required = false) LocalDate to,
                                             @RequestParam(required = false) SessionType type,
                                             @AuthenticationPrincipal User user) {
        return timerService.getSessions(pageable, from, to, type, user);
    }

    @DeleteMapping("/sessions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable Long id,
                              @AuthenticationPrincipal User user) {
        timerService.deleteSession(id, user);
    }

    @GetMapping("/settings")
    public TimerSettingsDto getSettings(@AuthenticationPrincipal User user) {
        return timerService.getSettings(user);
    }

    @PutMapping("/settings")
    public TimerSettingsDto updateSettings(@RequestBody  TimerSettingsDto settings,
                                           @AuthenticationPrincipal User user) {
        return timerService.updateSettings(settings, user);
    }

    @GetMapping("/stats")
    public Object getStats(@RequestParam(required = false) LocalDate from,
                               @RequestParam(required = false) LocalDate to,
                               @AuthenticationPrincipal User user) {
        return timerService.getStats(from, to, user);
    }
}
