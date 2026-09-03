    /**
 * Универсальный счётчик символов для полей с maxlength.
 * Автоматически находит все поля с атрибутом maxlength,
 * ищет рядом (в том же .form-group) элемент с классом .char-counter,
 * обновляет его содержимое при вводе.
 */
document.addEventListener('DOMContentLoaded', function() {
    // Находим все поля с maxlength
    const fields = document.querySelectorAll('input[maxlength], textarea[maxlength]');

    fields.forEach(function(field) {
        // Ищем счётчик внутри родительского .form-group или рядом
        let counter = field.closest('.form-group')?.querySelector('.char-counter');
        if (!counter) {
            // Если не нашли, пробуем искать среди соседних элементов
            counter = field.parentElement.querySelector('.char-counter');
        }
        if (!counter) return; // нет счётчика — пропускаем

        const max = parseInt(field.getAttribute('maxlength'), 10);

        // Функция обновления счётчика
        function updateCounter() {
            const current = field.value.length;
            counter.textContent = current + ' / ' + max;

            // Добавляем класс, если лимит превышен (или достигнут)
            if (current >= max) {
                counter.classList.add('limit-reached');
            } else {
                counter.classList.remove('limit-reached');
            }
        }

        // События: ввод, вставка, изменение
        field.addEventListener('input', updateCounter);
        field.addEventListener('change', updateCounter);
        field.addEventListener('paste', function() {
            setTimeout(updateCounter, 10); // отложенный вызов для вставки
        });

        // Инициализация при загрузке
        updateCounter();
    });
});