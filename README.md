# 2nd_Year_Assignments_Java
2st-year homeworks on computer science course at ITIS, KFU

## Semester work game

Семестровая работа за 1 семестр 2 курса. Сетевая игра с графическим интерфейсом.
Программа написана с использованием сокетов и графического интерфейса Swing, многопоточности. 
Реализован собственный протокол, взаимодействие client-server-client.
Программа состоит из трех модулей - Protocol, Client, Server. Реализована система комнат.  

[![Video]](https://user-images.githubusercontent.com/90205133/209449857-8330d4f9-1860-48a8-af61-ed5bece5d152.mp4)

## CMS

Content Management System на springframework без Spring Boot. 
- Используется Spring Security: два вида пользователей - User и Admin
- User может просматривать статьи, Admin имеет права на CRUD для статьи
- Hibernate, Spring Data JPA
- Есть rest-контроллер на CRUD для статьи
- Валидация ДТО с помощью javax.validation
- Предусмотрена защита от XSS (Html-фильтр) в тексте статьи
- Использован шаблонизатор JSP, для написания статей добавлен WYSIWYG-редактор
- ArticleSlugInterceptor - интерцептор, предназначенный для проверки существования статьи с заданным slug в базе данных
- Предусмотрена пагинация для списка статей
- Есть swagger, подключенный без бута

![Swagger](https://user-images.githubusercontent.com/90205133/236667558-207d7953-c7a0-4971-9c14-9131d981cd82.png)
