// Базовый URL вашего бэкенда
const API_BASE_URL = 'http://localhost:8080/api/v1';

// Состояние приложения
let currentTab = 'timeline';
let currentEntries = [];
let searchTimeout = null;

// Функция для показа уведомлений
function showNotification(message, type = 'info') {
    const notification = document.getElementById('notification');
    notification.innerHTML = `<div class="notification ${type}">${message}</div>`;
    setTimeout(() => {
        notification.innerHTML = '';
    }, 3000);
}

// ========== УПРАВЛЕНИЕ ЗАПИСЯМИ ==========

// Загрузка записей (всегда используем /task-diary, без /timeline)
async function loadEntries() {
    try {
        const url = `${API_BASE_URL}/task-diary`;
        const response = await fetch(url);
        if (!response.ok) throw new Error('Ошибка при загрузке записей');

        currentEntries = await response.json();

        // Для вкладки "timeline" сортируем по дате начала (starTask)
        if (currentTab === 'timeline') {
            const sorted = [...currentEntries].sort((a, b) =>
                new Date(a.starTask) - new Date(b.starTask)
            );
            displayEntries(sorted);
        } else {
            displayEntries(currentEntries);
        }
    } catch (error) {
        console.error('Error loading entries:', error);
        showNotification('Ошибка при загрузке записей', 'error');

        // Демо-данные (соответствуют структуре бэкенда)
        const demoEntries = [
            {
                id: 'demo-1',
                name: 'Пример задачи',
                taskNote: 'Это демо-запись, подключите бэкенд',
                priority: 'MIDDLE',
                starTask: new Date().toISOString(),
                finishTask: new Date(Date.now() + 3600000).toISOString()
            }
        ];
        displayEntries(demoEntries);
    }
}

// Поиск записей (локальный, так как бэк может не иметь /search)
async function searchEntries(query) {
    if (!query.trim()) {
        loadEntries();
        return;
    }

    try {
        // Пробуем отправить запрос на бэк (если есть)
        const response = await fetch(`${API_BASE_URL}/task-diary/search?q=${encodeURIComponent(query)}`);
        if (response.ok) {
            const entries = await response.json();
            displayEntries(entries);
            return;
        }
    } catch (e) {
        // Игнорируем ошибку, используем локальный поиск
    }

    // Локальный поиск по name и taskNote
    const filtered = currentEntries.filter(entry =>
        (entry.name && entry.name.toLowerCase().includes(query.toLowerCase())) ||
        (entry.taskNote && entry.taskNote.toLowerCase().includes(query.toLowerCase()))
    );
    displayEntries(filtered);
}

// Создание записи
async function createEntry(formData) {
    const payload = {
        name: formData.task,
        taskNote: formData.notes,
        priority: formData.priority,
        starTask: new Date(formData.date + 'T' + formData.startTime).toISOString(),
        finishTask: new Date(formData.dateEnd + 'T' + formData.endTime).toISOString()
    };

    const response = await fetch(`${API_BASE_URL}/task-diary`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error('Ошибка при создании');
    return await response.json();
}

// Обновление записи
async function updateEntry(id, formData) {
    const payload = {
        name: formData.task,
        taskNote: formData.notes,
        priority: formData.priority,
        starTask: new Date(formData.date + 'T' + formData.startTime).toISOString(),
        finishTask: new Date(formData.dateEnd + 'T' + formData.endTime).toISOString()
    };

    const response = await fetch(`${API_BASE_URL}/task-diary/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error('Ошибка при обновлении');
    return await response.json();
}

// Удаление записи
async function deleteEntry(id) {
    const response = await fetch(`${API_BASE_URL}/task-diary/${id}`, {
        method: 'DELETE'
    });
    if (!response.ok) throw new Error('Ошибка при удалении');
    return await response.json();
}

// Экспорт данных
async function exportData() {
    try {
        const response = await fetch(`${API_BASE_URL}/export?format=json`);
        if (!response.ok) throw new Error('Ошибка экспорта');
        const data = await response.json();
        const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `diary_export_${new Date().toISOString().split('T')[0]}.json`;
        a.click();
        URL.revokeObjectURL(url);
        showNotification('Экспорт выполнен успешно', 'success');
    } catch (error) {
        console.error('Export error:', error);
        showNotification('Ошибка при экспорте', 'error');
    }
}

// Статистика
async function loadStats() {
    try {
        const response = await fetch(`${API_BASE_URL}/stats`);
        if (!response.ok) throw new Error('Ошибка загрузки статистики');
        const stats = await response.json();
        displayStats(stats);
    } catch (error) {
        console.error('Stats error:', error);
        document.getElementById('statsContent').innerHTML = `
            <div style="text-align: center; color: #f44336;">❌ Ошибка загрузки статистики</div>
        `;
    }
}

function displayStats(stats) {
    const statsContent = document.getElementById('statsContent');
    statsContent.innerHTML = `
        <div style="display: grid; gap: 20px;">
            <div style="text-align: center; padding: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 16px; color: white;">
                <div style="font-size: 2rem; font-weight: bold;">${stats.completionRate || 0}%</div>
                <div>Продуктивность</div>
            </div>
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
                <div style="padding: 16px; background: #f5f5f5; border-radius: 12px; text-align: center;">
                    <div style="font-size: 1.8rem; font-weight: bold; color: #667eea;">${stats.totalTasks || 0}</div>
                    <div style="color: #666;">Всего задач</div>
                </div>
                <div style="padding: 16px; background: #f5f5f5; border-radius: 12px; text-align: center;">
                    <div style="font-size: 1.8rem; font-weight: bold; color: #4caf50;">${stats.completedTasks || 0}</div>
                    <div style="color: #666;">Выполнено</div>
                </div>
            </div>
            <div>
                <h4 style="margin-bottom: 12px;">По приоритетам:</h4>
                <div style="display: flex; gap: 12px;">
                    <div style="flex: 1; text-align: center; padding: 12px; background: #ffebee; border-radius: 12px;">
                        <div style="font-weight: bold; color: #f44336;">${stats.tasksByPriority?.HIGH || 0}</div>
                        <div style="font-size: 0.8rem;">Высокий</div>
                    </div>
                    <div style="flex: 1; text-align: center; padding: 12px; background: #fff3e0; border-radius: 12px;">
                        <div style="font-weight: bold; color: #ff9800;">${stats.tasksByPriority?.MIDDLE || 0}</div>
                        <div style="font-size: 0.8rem;">Средний</div>
                    </div>
                    <div style="flex: 1; text-align: center; padding: 12px; background: #e8f5e8; border-radius: 12px;">
                        <div style="font-weight: bold; color: #4caf50;">${stats.tasksByPriority?.LOW || 0}</div>
                        <div style="font-size: 0.8rem;">Низкий</div>
                    </div>
                </div>
            </div>
            ${stats.mostProductiveDay ? `<div style="padding: 12px; background: #e3f2fd; border-radius: 12px;"><strong>🏆 Самый продуктивный день:</strong> ${stats.mostProductiveDay}</div>` : ''}
            ${stats.averageTasksPerDay ? `<div style="padding: 12px; background: #f3e5f5; border-radius: 12px;"><strong>📊 Среднее задач в день:</strong> ${stats.averageTasksPerDay}</div>` : ''}
        </div>
    `;
}

// Отображение записей (адаптировано под структуру бэкенда)
function displayEntries(entries) {
    const entriesList = document.getElementById('entriesList');
    const entriesCount = document.getElementById('entriesCount');
    entriesCount.textContent = `Всего: ${entries.length} записей`;

    if (entries.length === 0) {
        entriesList.innerHTML = '<div class="entry-item" style="text-align: center; color: #999;">Нет записей</div>';
        return;
    }

    entriesList.innerHTML = entries.map(entry => {
        let priorityClass = 'priority-low';
        let priorityText = 'Низкий';
        if (entry.priority === 'MIDDLE') {
            priorityClass = 'priority-medium';
            priorityText = 'Средний';
        } else if (entry.priority === 'HIGH') {
            priorityClass = 'priority-high';
            priorityText = 'Высокий';
        }

        const startDate = new Date(entry.starTask);
        const finishDate = new Date(entry.finishTask);
        const startDateStr = startDate.toISOString().split('T')[0];
        const finishDateStr = finishDate.toISOString().split('T')[0];
        const startTimeStr = startDate.toTimeString().slice(0, 5);
        const finishTimeStr = finishDate.toTimeString().slice(0, 5);

        let dateTimeDisplay;
        if (startDateStr === finishDateStr) {
            dateTimeDisplay = `${startDateStr} • ${startTimeStr} - ${finishTimeStr}`;
        } else {
            dateTimeDisplay = `${startDateStr} ${startTimeStr} → ${finishDateStr} ${finishTimeStr}`;
        }

        return `
            <div class="entry-item" data-id="${entry.id}">
                <div class="entry-header">
                    <span class="entry-datetime">${dateTimeDisplay}</span>
                    <div style="display: flex; gap: 8px;">
                        <span class="entry-priority ${priorityClass}">${priorityText}</span>
                        <button onclick="editEntry('${entry.id}')" class="ai-suggestion-chip" style="background: #667eea; color: white;">✏️</button>
                        <button onclick="confirmDelete('${entry.id}')" class="ai-suggestion-chip" style="background: #f44336; color: white;">🗑️</button>
                    </div>
                </div>
                <div class="entry-title">${escapeHtml(entry.name)}</div>
                ${entry.taskNote ? `<div class="entry-notes">${escapeHtml(entry.taskNote)}</div>` : ''}
            </div>
        `;
    }).join('');
}

// Редактирование записи
window.editEntry = async function(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/task-diary/${id}`);
        if (!response.ok) throw new Error('Ошибка загрузки записи');
        const entry = await response.json();

        const startDate = new Date(entry.starTask);
        const finishDate = new Date(entry.finishTask);
        const dateStr = startDate.toISOString().split('T')[0];
        const dateEndStr = finishDate.toISOString().split('T')[0];
        const startTimeStr = startDate.toTimeString().slice(0, 5);
        const endTimeStr = finishDate.toTimeString().slice(0, 5);

        document.getElementById('editId').value = entry.id;
        document.getElementById('date').value = dateStr;
        document.getElementById('dateEnd').value = dateEndStr;
        document.getElementById('startTime').value = startTimeStr;
        document.getElementById('endTime').value = endTimeStr;
        document.getElementById('task').value = entry.name || '';
        document.getElementById('notes').value = entry.taskNote || '';

        const priorityRadio = document.querySelector(`input[name="priority"][value="${entry.priority}"]`);
        if (priorityRadio) priorityRadio.checked = true;

        document.getElementById('formTitle').innerHTML = '✏️ Редактирование записи';
        document.getElementById('submitBtn').textContent = '🔄 Обновить запись';
        document.getElementById('cancelEditBtn').style.display = 'block';

        const charCounter = document.getElementById('charCounter');
        charCounter.textContent = `${entry.taskNote?.length || 0}/500 символов`;

        scrollToForm();
    } catch (error) {
        console.error('Edit error:', error);
        showNotification('Ошибка загрузки записи', 'error');
    }
};

// Отмена редактирования
window.cancelEdit = function() {
    document.getElementById('diaryForm').reset();
    document.getElementById('editId').value = '';
    document.getElementById('formTitle').innerHTML = '➕ Новая запись';
    document.getElementById('submitBtn').textContent = '💾 Сохранить запись';
    document.getElementById('cancelEditBtn').style.display = 'none';

    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
    document.getElementById('dateEnd').value = today;
    document.getElementById('startTime').value = '09:00';
    document.getElementById('endTime').value = '10:00';
    document.getElementById('task').value = '';
    document.getElementById('notes').value = '';
    document.querySelector('input[name="priority"][value="LOW"]').checked = true;
};

// Подтверждение удаления
window.confirmDelete = function(id) {
    if (confirm('Вы уверены, что хотите удалить эту запись?')) {
        performDelete(id);
    }
};

async function performDelete(id) {
    try {
        await deleteEntry(id);
        showNotification('Запись удалена', 'success');
        loadEntries();
    } catch (error) {
        console.error('Delete error:', error);
        showNotification('Ошибка при удалении', 'error');
    }
}

// ========== ИИ-ПОМОЩНИК (без изменений) ==========
window.openAIDialog = function() {
    document.getElementById('aiModal').classList.add('active');
};
window.closeAIDialog = function() {
    document.getElementById('aiModal').classList.remove('active');
    document.getElementById('aiResponse').style.display = 'none';
    document.getElementById('aiPrompt').value = '';
};
window.useSuggestion = function(text) {
    document.getElementById('aiPrompt').value = text;
    sendToAI();
};
window.sendToAI = async function() {
    const prompt = document.getElementById('aiPrompt').value;
    if (!prompt.trim()) {
        showNotification('Введите ваш вопрос', 'error');
        return;
    }
    const responseDiv = document.getElementById('aiResponse');
    responseDiv.style.display = 'block';
    responseDiv.innerHTML = '🤔 Думаю...';
    try {
        const response = await fetch(`${API_BASE_URL}/ai/chat`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ message: prompt, context: { tab: currentTab, timestamp: new Date().toISOString() } })
        });
        if (!response.ok) throw new Error('Ошибка при обращении к ИИ');
        const data = await response.json();
        responseDiv.innerHTML = `
            <strong>🤖 Ответ:</strong>
            <p style="margin-top: 8px;">${escapeHtml(data.response)}</p>
            <small style="color: #666; display: block; margin-top: 8px;">${new Date().toLocaleTimeString()}</small>
        `;
    } catch (error) {
        console.error('AI Error:', error);
        responseDiv.innerHTML = `
            <strong>❌ Ошибка:</strong>
            <p style="color: #f44336; margin-top: 8px;">Не удалось получить ответ от ИИ. Использую локальный режим.</p>
            <p style="margin-top: 8px;">${getLocalAIResponse(prompt)}</p>
        `;
    }
};
function getLocalAIResponse(prompt) {
    const responses = {
        'организовать свой день': 'Рекомендую использовать технику Pomodoro: 25 минут работы, 5 минут отдыха. И начинайте день с самой сложной задачи.',
        'приоритетными': 'Расставьте приоритеты по матрице Эйзенхауэра: важные и срочные задачи делайте первыми.',
        'продуктивности': 'Совет: уберите телефон, закройте лишние вкладки и работайте блоками по 90 минут.',
        'шаблон': 'Вот шаблон: 1) Утренняя разминка 2) Главная задача 3) Обед 4) Встречи 5) Планирование на завтра'
    };
    for (let [key, value] of Object.entries(responses)) {
        if (prompt.toLowerCase().includes(key)) return value;
    }
    return 'Я внимательно изучил ваш запрос. Рекомендую разбить задачу на маленькие шаги и выполнять последовательно.';
}

// ========== НАВИГАЦИЯ ==========
window.switchTab = function(tab) {
    currentTab = tab;
    document.querySelectorAll('.nav-item').forEach(item => item.classList.remove('active'));
    if (tab === 'timeline') {
        document.querySelector('.nav-item:first-child').classList.add('active');
        document.getElementById('entriesTitle').textContent = '📅 Временная шкала';
    } else if (tab === 'all') {
        document.querySelectorAll('.nav-item')[2].classList.add('active');
        document.getElementById('entriesTitle').textContent = '📋 Все записи';
    }
    document.getElementById('searchInput').value = '';
    loadEntries();
};

window.scrollToForm = function() {
    document.getElementById('createForm').scrollIntoView({ behavior: 'smooth', block: 'start' });
    const form = document.getElementById('createForm');
    form.style.transition = 'box-shadow 0.3s';
    form.style.boxShadow = '0 0 0 3px #667eea, 0 20px 40px rgba(0,0,0,0.1)';
    setTimeout(() => { form.style.boxShadow = '0 20px 40px rgba(0,0,0,0.1)'; }, 2000);
};

window.openStatsModal = function() {
    document.getElementById('statsModal').classList.add('active');
    loadStats();
};
window.closeStatsModal = function() {
    document.getElementById('statsModal').classList.remove('active');
};
window.exportData = exportData;

// ========== ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ==========
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// ========== ИНИЦИАЛИЗАЦИЯ ==========

// Проверяем наличие dateEnd, если нет – создаём его в форме
document.addEventListener('DOMContentLoaded', function() {
    // Если элемент dateEnd отсутствует, добавляем его в форму
    if (!document.getElementById('dateEnd')) {
        const dateField = document.querySelector('label[for="date"]')?.parentElement;
        if (dateField) {
            const clone = dateField.cloneNode(true);
            const input = clone.querySelector('input');
            if (input) {
                input.id = 'dateEnd';
                input.name = 'dateEnd';
                const label = clone.querySelector('label');
                if (label) label.textContent = '📅 Дата окончания';
                dateField.parentElement.appendChild(clone);
            }
        } else {
            // Если не нашли родителя, создаём вручную в конце формы
            const form = document.getElementById('diaryForm');
            const wrapper = document.createElement('div');
            wrapper.className = 'form-group';
            wrapper.innerHTML = `
                <label for="dateEnd">📅 Дата окончания</label>
                <input type="date" id="dateEnd" required aria-required="true">
            `;
            // Вставляем перед последним form-group (приоритет) или перед form-actions
            const priorityGroup = document.querySelector('.priority-group')?.closest('.form-group');
            if (priorityGroup) {
                priorityGroup.parentElement.insertBefore(wrapper, priorityGroup);
            } else {
                form.appendChild(wrapper);
            }
        }
    }

    // Устанавливаем значения по умолчанию
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
    if (document.getElementById('dateEnd')) document.getElementById('dateEnd').value = today;
    document.getElementById('startTime').value = '09:00';
    document.getElementById('endTime').value = '10:00';

    loadEntries();
});

// Обработчик отправки формы
document.getElementById('diaryForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    const submitBtn = document.getElementById('submitBtn');
    submitBtn.disabled = true;
    const originalText = submitBtn.textContent;
    submitBtn.textContent = '⏳ Сохранение...';

    const editId = document.getElementById('editId').value;
    const formData = {
        date: document.getElementById('date').value,
        dateEnd: document.getElementById('dateEnd').value,
        startTime: document.getElementById('startTime').value,
        endTime: document.getElementById('endTime').value,
        task: document.getElementById('task').value,
        notes: document.getElementById('notes').value,
        priority: document.querySelector('input[name="priority"]:checked').value
    };

    try {
        if (editId) {
            await updateEntry(editId, formData);
            showNotification('Запись обновлена!', 'success');
            cancelEdit();
        } else {
            await createEntry(formData);
            showNotification('Запись создана!', 'success');
            document.getElementById('diaryForm').reset();
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('date').value = today;
            document.getElementById('dateEnd').value = today;
            document.getElementById('startTime').value = '09:00';
            document.getElementById('endTime').value = '10:00';
            document.querySelector('input[name="priority"][value="LOW"]').checked = true;
        }
        await loadEntries();
    } catch (error) {
        console.error('Save error:', error);
        showNotification(error.message, 'error');
    } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = originalText;
    }
});

// Счётчик символов
const notesTextarea = document.getElementById('notes');
const charCounter = document.getElementById('charCounter');
notesTextarea.addEventListener('input', function() {
    const length = this.value.length;
    charCounter.textContent = `${length}/500 символов`;
    charCounter.style.color = length > 500 ? 'red' : '#888';
});

// Поиск с debounce
const searchInput = document.getElementById('searchInput');
searchInput.addEventListener('input', function() {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
        searchEntries(this.value);
    }, 300);
});

// Закрытие модальных окон
window.onclick = function(event) {
    const aiModal = document.getElementById('aiModal');
    const statsModal = document.getElementById('statsModal');
    if (event.target === aiModal) closeAIDialog();
    if (event.target === statsModal) closeStatsModal();
};