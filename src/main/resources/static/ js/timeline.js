// Текущая выбранная дата
let currentTimelineDate = new Date();

// Загрузка временной шкалы
async function loadTimeline() {
    const dateInput = document.getElementById('timelineDate');
    const selectedDate = dateInput.value;

    if (selectedDate) {
        currentTimelineDate = new Date(selectedDate);
    }

    const formattedDate = formatDateForAPI(currentTimelineDate);

    try {
        // Загружаем записи за выбранную дату
        const response = await fetch(`${API_BASE_URL}/entries?date=${formattedDate}`);
        if (!response.ok) throw new Error('Ошибка загрузки');

        const entries = await response.json();
        renderTimeline(entries);
    } catch (error) {
        console.error('Timeline error:', error);
        // Демо-данные если бэкенд не доступен
        const demoEntries = getDemoEntries(formattedDate);
        renderTimeline(demoEntries);
    }
}

// Демо-данные
function getDemoEntries(date) {
    return [
        {
            id: 1,
            date: date,
            startTime: '09:00',
            endTime: '10:30',
            task: 'Утреннее планирование',
            notes: 'Распланировать задачи на день',
            priority: 'HIGH'
        },
        {
            id: 2,
            date: date,
            startTime: '11:00',
            endTime: '12:00',
            task: 'Работа над проектом',
            notes: 'Закончить основную часть',
            priority: 'MEDIUM'
        },
        {
            id: 3,
            date: date,
            startTime: '14:00',
            endTime: '15:30',
            task: 'Встреча с командой',
            notes: 'Обсуждение спринта',
            priority: 'HIGH'
        },
        {
            id: 4,
            date: date,
            startTime: '16:00',
            endTime: '17:00',
            task: 'Обучение',
            notes: 'Просмотр курсов',
            priority: 'LOW'
        }
    ];
}

// Форматирование даты для API
function formatDateForAPI(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// Форматирование времени
function formatTime(timeStr) {
    if (!timeStr) return '';
    return timeStr.substring(0, 5);
}

// Генерация временных слотов (от 0:00 до 24:00 с шагом 15 минут)
function generateTimeSlots() {
    const slots = [];
    for (let hour = 0; hour < 24; hour++) {
        for (let minute = 0; minute < 60; minute += 15) {
            const hourStr = hour.toString().padStart(2, '0');
            const minuteStr = minute.toString().padStart(2, '0');
            slots.push(`${hourStr}:${minuteStr}`);
        }
    }
    // Добавляем 24:00 как границу
    slots.push('24:00');
    return slots;
}

// Группировка задач по временным слотам
function groupTasksBySlot(entries, slots) {
    const grouped = {};

    // Инициализация всех слотов
    slots.forEach(slot => {
        grouped[slot] = [];
    });

    // Распределение задач по слотам
    entries.forEach(entry => {
        const startTime = entry.startTime;
        const endTime = entry.endTime;

        // Находим индекс начального и конечного слота
        let startIndex = slots.findIndex(slot => slot >= startTime);
        let endIndex = slots.findIndex(slot => slot >= endTime);

        if (startIndex === -1) startIndex = 0;
        if (endIndex === -1) endIndex = slots.length - 1;

        // Добавляем задачу во все слоты, которые она занимает
        for (let i = startIndex; i < endIndex; i++) {
            const slot = slots[i];
            if (!grouped[slot].some(t => t.id === entry.id)) {
                grouped[slot].push(entry);
            }
        }
    });

    return grouped;
}

// Получение приоритета задачи для CSS класса
function getPriorityClass(priority) {
    switch(priority) {
        case 'HIGH': return 'priority-high';
        case 'MEDIUM': return 'priority-medium';
        default: return 'priority-low';
    }
}

// Получение текста приоритета
function getPriorityText(priority) {
    switch(priority) {
        case 'HIGH': return 'Высокий';
        case 'MEDIUM': return 'Средний';
        default: return 'Низкий';
    }
}

// Рендер временной шкалы
function renderTimeline(entries) {
    const timelineBody = document.getElementById('timelineBody');
    const slots = generateTimeSlots();
    const groupedTasks = groupTasksBySlot(entries, slots);

    if (entries.length === 0) {
        timelineBody.innerHTML = `
            <div class="empty-timeline">
                <div class="empty-timeline-icon">📭</div>
                <div class="empty-timeline-text">Нет задач на этот день</div>
                <a href="index.html#createForm" class="create-header-btn" style="display: inline-block; margin-top: 20px;">
                    ➕ Создать задачу
                </a>
            </div>
        `;
        return;
    }

    let html = '';
    const now = new Date();
    const todayStr = formatDateForAPI(now);
    const selectedDateStr = formatDateForAPI(currentTimelineDate);

    for (let i = 0; i < slots.length - 1; i++) {
        const slot = slots[i];
        const nextSlot = slots[i + 1];
        const tasks = groupedTasks[slot] || [];

        // Проверка, нужно ли показывать индикатор текущего времени
        const showCurrentTime = (selectedDateStr === todayStr);
        let isCurrentSlot = false;

        if (showCurrentTime) {
            const currentHour = now.getHours();
            const currentMinute = now.getMinutes();
            const slotHour = parseInt(slot.split(':')[0]);
            const slotMinute = parseInt(slot.split(':')[1]);

            const slotStart = slotHour * 60 + slotMinute;
            const currentTotal = currentHour * 60 + currentMinute;

            if (currentTotal >= slotStart && currentTotal < slotStart + 15) {
                isCurrentSlot = true;
            }
        }

        html += `
            <div class="timeline-row" data-time="${slot}">
                <div class="timeline-time-slot">
                    ${slot} - ${nextSlot}
                </div>
                <div class="timeline-tasks-slot" style="position: relative;">
                    ${isCurrentTime ? `<div class="current-time-indicator"></div>` : ''}
                    ${tasks.map(task => `
                        <div class="timeline-task-card ${getPriorityClass(task.priority)}" onclick="viewTask(${task.id})">
                            <div class="task-card-title">${escapeHtml(task.task)}</div>
                            <div class="task-card-time">
                                <span>⏰</span> ${formatTime(task.startTime)} - ${formatTime(task.endTime)}
                            </div>
                            <span class="task-card-priority ${getPriorityClass(task.priority)}">
                                ${getPriorityText(task.priority)}
                            </span>
                        </div>
                    `).join('')}
                </div>
            </div>
        `;
    }

    timelineBody.innerHTML = html;
}

// Просмотр задачи
async function viewTask(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/entries/${id}`);
        if (!response.ok) throw new Error('Ошибка загрузки');

        const task = await response.json();

        const modalContent = document.getElementById('taskModalContent');
        const priorityClass = getPriorityClass(task.priority);
        const priorityText = getPriorityText(task.priority);

        modalContent.innerHTML = `
            <div style="margin-bottom: 20px;">
                <div style="background: ${priorityClass === 'priority-high' ? '#ffebee' : priorityClass === 'priority-medium' ? '#fff3e0' : '#e8f5e8'}; padding: 12px; border-radius: 12px; margin-bottom: 16px;">
                    <span class="entry-priority ${priorityClass}" style="font-size: 0.9rem;">${priorityText} приоритет</span>
                </div>

                <div style="margin-bottom: 16px;">
                    <label style="font-weight: 600; color: #667eea;">📅 Дата и время</label>
                    <p style="margin-top: 4px;">${task.date} • ${task.startTime} - ${task.endTime}</p>
                </div>

                <div style="margin-bottom: 16px;">
                    <label style="font-weight: 600; color: #667eea;">📋 Задача</label>
                    <p style="margin-top: 4px; font-size: 1.1rem;">${escapeHtml(task.task)}</p>
                </div>

                ${task.notes ? `
                <div style="margin-bottom: 16px;">
                    <label style="font-weight: 600; color: #667eea;">📝 Заметки</label>
                    <p style="margin-top: 4px; background: #f5f5f5; padding: 12px; border-radius: 8px;">${escapeHtml(task.notes)}</p>
                </div>
                ` : ''}

                <div style="display: flex; gap: 12px; margin-top: 24px;">
                    <button onclick="editTaskFromTimeline(${task.id})" class="save-btn" style="flex: 1;">
                        ✏️ Редактировать
                    </button>
                    <button onclick="deleteTaskFromTimeline(${task.id})" class="save-btn cancel-btn" style="flex: 1;">
                        🗑️ Удалить
                    </button>
                </div>
            </div>
        `;

        document.getElementById('taskModal').classList.add('active');
    } catch (error) {
        console.error('View task error:', error);
        showNotification('Ошибка загрузки задачи', 'error');
    }
}

// Редактирование задачи из временной шкалы
function editTaskFromTimeline(id) {
    closeTaskModal();
    window.location.href = `index.html#createForm&edit=${id}`;
}

// Удаление задачи из временной шкалы
async function deleteTaskFromTimeline(id) {
    if (confirm('Вы уверены, что хотите удалить эту задачу?')) {
        try {
            await deleteEntry(id);
            showNotification('Задача удалена', 'success');
            closeTaskModal();
            loadTimeline();
        } catch (error) {
            console.error('Delete error:', error);
            showNotification('Ошибка при удалении', 'error');
        }
    }
}

// Закрытие модального окна задачи
function closeTaskModal() {
    document.getElementById('taskModal').classList.remove('active');
}

// Изменение даты
function changeDate(delta) {
    currentTimelineDate.setDate(currentTimelineDate.getDate() + delta);
    const dateInput = document.getElementById('timelineDate');
    dateInput.value = formatDateForAPI(currentTimelineDate);
    loadTimeline();
}

// Переход к сегодняшней дате
function goToToday() {
    currentTimelineDate = new Date();
    const dateInput = document.getElementById('timelineDate');
    dateInput.value = formatDateForAPI(currentTimelineDate);
    loadTimeline();
}

// Инициализация страницы
document.addEventListener('DOMContentLoaded', () => {
    const dateInput = document.getElementById('timelineDate');
    dateInput.value = formatDateForAPI(currentTimelineDate);
    loadTimeline();
});