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
