<h3>Meetup-api</h3>


Использованные технологии:
<ul>
    <li>Spring Boot</li>
    <li>Hibernate без Spring Data Jpa</li>
</ul>
Дополнительно:
<ul>
    <li>реализована возможность фильтрации и сортировки</li>
    <li>в application.yml вынесены зависимые от окружения переменные</li>
    <li>подготовлен docker-compose файл</li>
    <li>готовая коллекция с запросами для postman (./meetup-api)</li>
    <li>написана спецификация openapi (./meetup-api/spec)</li>
</ul>
<hr>
Для запуска:
<ol>
<li>meetup-api -> mvn package</li>
<li>docker compose run из корневой директории проекта.</li>
</ol>
<p>Api будет доступен на 80 порте. DDL скрипт находится в postgres/meetup-api/ddl</p>
Эндпоинты:
<ul>
<li><p>Получить список всех ивентов <b>GET /event/</b></p>
    Дополнительные параметры запроса:
        <ul>
        <li>subject=тема</li>
        <li>organizer=организатор</li>
        <li>from=дата собатия от (формат mm:HH dd-MM-yyyy)</li>
        <li>to=дата события до (формат mm:HH dd-MM-yyyy)</li>
        <li>sort= сортировка.
        <br>Принимает параметры в формате key:value. Несколько параметров через запятую (key:value,key:value,key:value)
        <br>Ключи: subject, organizer, date. Значения: asc, desc
        <br>Порядок передачи параметров влияет на сортировку
        </li>
        </ul>
</li>
<li>
Получить ивент по id <b>GET /event/{id}</b>
<br>id - UUID
</li>
<li>
Создать новый ивент <b>POST /event/</b>
<br>В теле принимает объект json
<br>Пример:
<br>{
<br>    "subject": "subject",
<br>    "description": "description",
<br>    "organizer": "organizer",
<br>    "scheduled_time": "12:00 01-01-2022",
<br>    "location": "location"
<br>}
</li>
<li>
Обновить существующий ивент <b>PUT /event/{id}</b>
<br>id - UUID
<br>В теле принимает объект json
<br>Пример:
<br>{
<br>    "subject": "subject",
<br>    "description": "description",
<br>    "organizer": "organizer",
<br>    "scheduled_time": "12:00 01-01-2022",
<br>    "location": "location"
<br>}
</li>
<li>
Удалить существующий ивент <b>DELETE /event/{id}</b>
<br>id - UUID
</li>
</ul>
