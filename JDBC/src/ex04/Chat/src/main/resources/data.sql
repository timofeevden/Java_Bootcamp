INSERT INTO users(login, password) VALUES 
('Valeryi', '123'),
('Dmitryi', '1234'),
('Nikolay', '12345'),
('Nikita', '123456'),
('Michail', '1234567');


INSERT INTO chatrooms(name, owner_id) VALUES 
('First Chat from Valeryi', 1),
('Second Chat from Valeryi', 1),
('Nikola news chat', 3),
('Chat from Nikita', 4),
('Funny News', 5);


INSERT INTO messages(author_id, chatroom_id, text, date_time) VALUES
(1, 1, 'Hello, this is my first chat!', '2024-06-10 09:00:00'),
(1, 2, 'Hello, this is my second chat!', '2024-06-20 16:00:00'),
(2, 2, 'Wow, you have 2 chats! I have no one', '2024-06-21 10:01:00'),
(3, 2, 'Nice chat, bro!', '2024-06-22 12:00:12'),
(3, 3, 'Nikita, write something! Chat is empty without you! Its me, Nikolay', '2024-06-23 11:00:11'),
(3, 2, 'Nice chat, bro!', '2024-06-23 14:30:12'),
(1, 4, 'Nikita, where are you? Join in my first chat! Its me, Valeryi', '2024-06-24 21:20:21');

