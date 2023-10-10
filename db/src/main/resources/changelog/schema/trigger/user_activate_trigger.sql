CREATE TRIGGER system_user_activate AFTER INSERT
    ON USERS
    FOR EACH ROW
    EXECUTE PROCEDURE fill_activate(user_id);