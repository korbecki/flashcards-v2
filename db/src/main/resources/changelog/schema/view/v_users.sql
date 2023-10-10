SELECT user_id, name, surname, user_name, password, email, is_activated
FROM USERS AS s
         LEFT JOIN activate a ON a.activate_id = s.activate_id;