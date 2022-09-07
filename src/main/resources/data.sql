-- configure running this file in properties or just run code from here
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');

-- user passwords Mike 1111 Nick 2222 Nora 3333
INSERT INTO users (first_name, last_name, email, password, role_id) VALUES ('Mike', 'Brown', 'mike@mail.com', '$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO', 1);
INSERT INTO users (first_name, last_name, email, password, role_id) VALUES ('Nick', 'Green', 'nick@mail.com', '$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEwC', 2);
INSERT INTO users (first_name, last_name, email, password, role_id) VALUES ('Nora', 'White', 'nora@mail.com', '$2a$10$yYQaJrHzjOgD5wWCyelp0e1Yv1KEKeqUlYfLZQ1OQvyUrnEcX/rOy', 2);

INSERT INTO todos (title, created_at, owner_id) VALUES ('Mike''s To-Do #1', '2020-09-16 14:00:04.810221', 1);
INSERT INTO todos (title, created_at, owner_id) VALUES ('Mike''s To-Do #2', '2020-09-16 14:00:11.480271', 1);
INSERT INTO todos (title, created_at, owner_id) VALUES ('Mike''s To-Do #3', '2020-09-16 14:00:16.351238', 1);
INSERT INTO todos (title, created_at, owner_id) VALUES ('Nick''s To-Do #1', '2020-09-16 14:14:54.532337', 2);
INSERT INTO todos (title, created_at, owner_id) VALUES ('Nick''s To-Do #2', '2020-09-16 14:15:04.707176', 2);
INSERT INTO todos (title, created_at, owner_id) VALUES ('Nora''s To-Do #1', '2020-09-16 14:15:32.464391', 3);
INSERT INTO todos (title, created_at, owner_id) VALUES ('Nora''s To-Do #2', '2020-09-16 14:15:39.16246', 3);

INSERT INTO tasks (name, priority, todo_id, state) VALUES ('Task #2', 'LOW', 1, 'NEW');
INSERT INTO tasks (name, priority, todo_id, state) VALUES ('Task #1', 'HIGH', 1, 'DONE');
INSERT INTO tasks (name, priority, todo_id, state) VALUES ('Task #3', 'MEDIUM', 1, 'DOING');

INSERT INTO todo_collaborator (todo_id, collaborator_id) VALUES (1, 2);
INSERT INTO todo_collaborator (todo_id, collaborator_id) VALUES (1, 3);
INSERT INTO todo_collaborator (todo_id, collaborator_id) VALUES (4, 3);
INSERT INTO todo_collaborator (todo_id, collaborator_id) VALUES (4, 1);
INSERT INTO todo_collaborator (todo_id, collaborator_id) VALUES (6, 2);
INSERT INTO todo_collaborator (todo_id, collaborator_id) VALUES (6, 1);
