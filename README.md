# Library Control System

Небольшоое CRUD web-приложение для управления выдачей книг в библиотеке. Написано с использованием фреймворка Spring MVC и шаблонизатора Thymeleaf. Приложение работает на основе базы данных PostgreSQL. В качестве базы для построения html-страниц был использован шаблон с сайта [HTML 5UP](https://html5up.net/).

## Опции

- Управление авторами
- Управление читателями
- Управление издательствами
- Управление выдачей книг читателям

## Запуск
Для запуска веб-приложения потребуется установленный [Docker](https://www.docker.com/).

Предварительно соберите приложение и поместите получившийся **.war** файл в папку **target** корня проекта.

Откройте в консоли корень проекта и введите следующую команду в терминал, чтобы выполнить сборку проекта:
```sh
docker-compose build
```
Затем введите в терминал команду запуска:
```sh
docker-compose up
```
После запуска веб-приложение будет доступно по адресу: http://localhost:8080/
