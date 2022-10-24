Meetup-api
Использованные технологии:
    - Spring Boot
    - Hibernate без Spring Data Jpa
Дополнительно:
    - реализована возможность фильтрации и сортировки
    - в application.yml вынесены зависимые от окружения переменные
    - присутствует docker-compose файл
    - готовая коллекция с запросами для postman (./meetup-api)
    - написана спецификация openapi (./meetup-api/spec)
Для запуска использовать команду docker compose run из корневой директории проекта. Api будет доступен на 80 порте.
DDL скрипт находится в postgres/meetup-api/ddl
