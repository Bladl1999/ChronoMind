// Базовый URL вашего бэкенда
const API_BASE_URL = 'http://localhost:8080/api';

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

// Загрузка записей
async function loadEntries() {
    try {
        let url = `${API_BASE_URL}/entries`;

        if (currentTab === 'timeline') {
            url = `${API_BASE_URL}/entries/timeline`;
        }

        const response = await fetch(url);

        if (!response.ok) throw new Error('Ошибка при загрузке записей');

        currentEntries = await response.json();
        displayEntries(currentEntries);
    } catch (error) {
        console.error('Error loading entries:', error);
        showNotification('Ошибка при загрузке записей', 'error');

        // Демо-данные если бэкенд не доступен
        const demoEntries = [
            {
                id: 1,
                date: new Date().toISOString().split('T')[0],
                startTime: '09:00',
                endTime: '10:00',
                task: 'Пример задачи',
                notes: 'Это демо-запись, подключите бэкенд',
                priority: 'MEDIUM'
            }
        ];
        displayEntries(demoEntries);
    }
}

// Поиск записей
async function searchEntries(query) {
    if (!query.trim()) {
        loadEntries();
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/entries/search?q=${encodeURIComponent(query)}`);
        if (!response.ok) throw new Error('Ошибка поиска');

        const entries = await response.json();
        displayEntries(entries);
    } catch (error) {
        console.error('Search error:', error);
        // Локальный поиск
        const filtered = currentEntries.filter(entry =>
            entry.task.toLowerCase().includes(query.toLowerCase()) ||
            (entry.notes && entry.notes.toLowerCase().includes(query.toLowerCase()))
        );
        displayEntries(filtered);
    }
}

// Создание записи
async function createEntry(formData) {
    const response = await fetch(`${API_BASE_URL}/entries`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
    });

    if (!response.ok) throw new Error('Ошибка при создании');
    return await response.json();
}

// Обновление записи
async function updateEntry(id, formData) {
    const response = await fetch(`${API_BASE_URL}/entries/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
    });

    if (!response.ok) throw new Error('Ошибка при обновлении');
    return await response.json();
}

// Удаление записи
async function deleteEntry(id) {
    const response = await fetch(`${API_BASE_URL}/entries/${id}`, {
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
            <div style="text-align: center; color: #f44336;">
                ❌ Ошибка загрузки статистики
            </div>
        `;
    }
}

// Отображение статистики
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
                        <div style="font-weight: bold; color: #ff9800;">${stats.tasksByPriority?.MEDIUM || 0}</div>
                        <div style="font-size: 0.8rem;">Средний</div>
                    </div>
                    <div style="flex: 1; text-align: center; padding: 12px; background: #e8f5e8; border-radius: 12px;">
                        <div style="font-weight: bold; color: #4caf50;">${stats.tasksByPriority?.LOW || 0}</div>
                        <div style="font-size: 0.8rem;">Низкий</div>
                    </div>
                </div>
            </div>

            ${stats.mostProductiveDay ? `
            <div style="padding: 12px; background: #e3f2fd; border-radius: 12px;">
                <strong>🏆 Самый продуктивный день:</strong> ${stats.mostProductiveDay}
            </div>
            ` : ''}

            ${stats.averageTasksPerDay ? `
            <div style="padding: 12px; background: #f3e5f5; border-radius: 12px;">
                <strong>📊 Среднее задач в день:</strong> ${stats.averageTasksPerDay}
            </div>
            ` : ''}
        </div>
    `;
}

// Отображение записей
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

        if (entry.priority === 'MEDIUM') {
            priorityClass = 'priority-medium';
            priorityText = 'Средний';
        } else if (entry.priority === 'HIGH') {
            priorityClass = 'priority-high';
            priorityText = 'Высокий';
        }

        return `
            <div class="entry-item" data-id="${entry.id}">
                <div class="entry-header">
                    <span class="entry-datetime">${entry.date} • ${entry.startTime} - ${entry.endTime}</span>
                    <div style="display: flex; gap: 8px;">
                        <span class="entry-priority ${priorityClass}">${priorityText}</span>
                        <button onclick="editEntry(${entry.id})" class="ai-suggestion-chip" style="background: #667eea; color: white;">✏️</button>
                        <button onclick="confirmDelete(${entry.id})" class="ai-suggestion-chip" style="background: #f44336; color: white;">🗑️</button>
                    </div>
                </div>
                <div class="entry-title">${escapeHtml(entry.task)}</div>
                ${entry.notes ? `<div class="entry-notes">${escapeHtml(entry.notes)}</div>` : ''}
            </div>
        `;
    }).join('');
}

// Редактирование записи
async function editEntry(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/entries/${id}`);
        if (!response.ok) throw new Error('Ошибка загрузки записи');

        const entry = await response.json();

        document.getElementById('editId').value = entry.id;
        document.getElementById('date').value = entry.date;
        document.getElementById('startTime').value = entry.startTime;
        document.getElementById('endTime').value = entry.endTime;
        document.getElementById('task').value = entry.task;
        document.getElementById('notes').value = entry.notes || '';
        document.querySelector(`input[name="priority"][value="${entry.priority}"]`).checked = true;

        document.getElementById('formTitle').innerHTML = '✏️ Редактирование записи';
        document.getElementById('submitBtn').textContent = '🔄 Обновить запись';
        document.getElementById('cancelEditBtn').style.display = 'block';

        // Обновляем счетчик символов
        const charCounter = document.getElementById('charCounter');
        charCounter.textContent = `${entry.notes?.length || 0}/500 символов`;

        scrollToForm();
    } catch (error) {
        console.error('Edit error:', error);
        showNotification('Ошибка загрузки записи', 'error');
    }
}

// Отмена редактирования
function cancelEdit() {
    document.getElementById('diaryForm').reset();
    document.getElementById('editId').value = '';
    document.getElementById('formTitle').innerHTML = '➕ Новая запись';
    document.getElementById('submitBtn').textContent = '💾 Сохранить запись';
    document.getElementById('cancelEditBtn').style.display = 'none';

    // Устанавливаем значения по умолчанию
    document.getElementById('date').value = new Date().toISOString().split('T')[0];
    document.getElementById('startTime').value = '09:00';
    document.getElementById('endTime').value = '10:00';
    document.getElementById('task').value = '';
    document.getElementById('notes').value = '';
    document.querySelector('input[name="priority"][value="LOW"]').checked = true;
}

// Подтверждение удаления
function confirmDelete(id) {
    if (confirm('Вы уверены, что хотите удалить эту запись?')) {
        performDelete(id);
    }
}

// Выполнение удаления
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

// ========== ИИ-ПОМОЩНИК ==========

// Открытие диалогового окна ИИ
function openAIDialog() {
    document.getElementById('aiModal').classList.add('active');
}

// Закрытие диалогового окна ИИ
function closeAIDialog() {
    document.getElementById('aiModal').classList.remove('active');
    document.getElementById('aiResponse').style.display = 'none';
    document.getElementById('aiPrompt').value = '';
}

// Использование предложения
function useSuggestion(text) {
    document.getElementById('aiPrompt').value = text;
    sendToAI();
}

// Отправка запроса к ИИ
async function sendToAI() {
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
            body: JSON.stringify({
                message: prompt,
                context: {
                    tab: currentTab,
                    timestamp: new Date().toISOString()
                }
            })
        });

        if (!response.ok) throw new Error('Ошибка при обращении к ИИ');

        const data = await response.json();

        responseDiv.innerHTML = `
            <strong>🤖 Ответ:</strong>
            <p style="margin-top: 8px;">${escapeHtml(data.response)}</p>
            <small style="color: #666; display: block; margin-top: 8px;">
                ${new Date().toLocaleTimeString()}
            </small>
        `;
    } catch (error) {
        console.error('AI Error:', error);
        responseDiv.innerHTML = `
            <strong>❌ Ошибка:</strong>
            <p style="color: #f44336; margin-top: 8px;">
                Не удалось получить ответ от ИИ. Использую локальный режим.
            </p>
            <p style="margin-top: 8px;">${getLocalAIResponse(prompt)}</p>
        `;
    }
}

// Локальные ответы ИИ (для демо)
function getLocalAIResponse(prompt) {
    const responses = {
        'организовать свой день': 'Рекомендую использовать технику Pomodoro: 25 минут работы, 5 минут отдыха. И начинайте день с самой сложной задачи.',
        'приоритетными': 'Расставьте приоритеты по матрице Эйзенхауэра: важные и срочные задачи делайте первыми.',
        'продуктивности': 'Совет: уберите телефон, закройте лишние вкладки и работайте блоками по 90 минут.',
        'шаблон': 'Вот шаблон: 1) Утренняя разминка 2) Главная задача 3) Обед 4) Встречи 5) Планирование на завтра'
    };

    for (let [key, value] of Object.entries(responses)) {
        if (prompt.toLowerCase().includes(key)) {
            return value;
        }
    }

    return 'Я внимательно изучил ваш запрос. Рекомендую разбить задачу на маленькие шаги и выполнять последовательно.';
}

// ========== НАВИГАЦИЯ ==========

// Переключение вкладок
function switchTab(tab) {
    currentTab = tab;

    document.querySelectorAll('.nav-item').forEach(item => {
        item.classList.remove('active');
    });

    if (tab === 'timeline') {
        document.querySelector('.nav-item:first-child').classList.add('active');
        document.getElementById('entriesTitle').textContent = '📅 Временная шкала';
    } else if (tab === 'all') {
        document.querySelectorAll('.nav-item')[2].classList.add('active');
        document.getElementById('entriesTitle').textContent = '📋 Все записи';
    }

    // Очищаем поиск
    document.getElementById('searchInput').value = '';
    loadEntries();
}

// Прокрутка к форме создания
function scrollToForm() {
    document.getElementById('createForm').scrollIntoView({
        behavior: 'smooth',
        block: 'start'
    });

    const form = document.getElementById('createForm');
    form.style.transition = 'box-shadow 0.3s';
    form.style.boxShadow = '0 0 0 3px #667eea, 0 20px 40px rgba(0,0,0,0.1)';

    setTimeout(() => {
        form.style.boxShadow = '0 20px 40px rgba(0,0,0,0.1)';
    }, 2000);
}

// Открытие модального окна статистики
function openStatsModal() {
    document.getElementById('statsModal').classList.add('active');
    loadStats();
}

// Закрытие модального окна статистики
function closeStatsModal() {
    document.getElementById('statsModal').classList.remove('active');
}

// ========== ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ==========

// Экранирование HTML
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// ========== ИНИЦИАЛИЗАЦИЯ ==========

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
            // Устанавливаем значения по умолчанию
            document.getElementById('date').value = new Date().toISOString().split('T')[0];
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

// Счетчик символов
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

// Загружаем записи при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    // Устанавливаем значения по умолчанию для формы
    document.getElementById('date').value = new Date().toISOString().split('T')[0];
    document.getElementById('startTime').value = '09:00';
    document.getElementById('endTime').value = '10:00';

    loadEntries();
});

// Закрытие модальных окон по клику вне их
window.onclick = function(event) {
    const aiModal = document.getElementById('aiModal');
    const statsModal = document.getElementById('statsModal');

    if (event.target === aiModal) {
        closeAIDialog();
    }
    if (event.target === statsModal) {
        closeStatsModal();
    }
}