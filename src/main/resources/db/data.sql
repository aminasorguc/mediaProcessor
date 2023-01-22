INSERT INTO users(id, firstName, lastName, password, email)
VALUES (1, 'TestFirstName', 'TestLastName', 'TestPassword', 'test@test.com');

INSERT INTO roles(id, name)
VALUES (1, "ROLE_ADMIN");

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1);