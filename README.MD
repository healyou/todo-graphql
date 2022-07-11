# Запуск приложения

## Запуск тестов
    Тесты с wiremock для мокирования сторонных сервисов. Команда gradle
    'build'

## Запуск приложения для разработки

### Профили приложения
-Dspring.profiles.active=dev_docker -> сервисы, к которым обращается приложение должны быть развёрнуты на docker

-Dspring.profiles.active=dev_postman -> запросы к сервисам замокированы через postman

-Dspring.profiles.active=prod -> для запуска в проде, сервисы, к которым обращается приложение, будут развёрнуты на docker 

### Запуск
    1) Запустить spring приложение через main метод класса с указанием предпочитаемого профиля
      ru.lappi.todographql.TodoGraphqlApplication -Dspring.profiles.active=dev_postman
    3) Приложение будет принимать запросы по url:
      http://localhost:8333/graphql

## Запуск в докере
    1) Собираем проект через gradle:
      'clean build'
    2) Создаём docker image из корня проекта (image с 1 war файлом):
      docker build -t todo-graphql-app:v1 .
    3) Запуск приложения в docker из корня проекта (app c 'prod' профилем, 
    при этом сторонних сервисов по умолчанию не будет):
      'docker compose up -d'
    3) Приложение будет принимать запросы по url:
      http://localhost:8333/graphql
