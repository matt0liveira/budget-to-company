SET foreign_key_checks = 0;

TRUNCATE company;

SET foreign_key_checks = 1;

insert into company (id, name, cnpj, creation_date, last_update_date) values (1, "Teste", "3234243214312", utc_timestamp, utc_timestamp);


-- insert into user (name, email, password, balance) values ('Maria Joaquina', 'maria.joaquina@budget.com', '$2a$10$qe6KkNC3sduXvWNywQFy5.p0z7z9MxKceJnK8qH9/KNPxWmEKc.D.', 1000);
-- insert into user (name, email, password, balance) values ('Cirilo', 'cirilo@budget.com', '$2a$10$qe6KkNC3sduXvWNywQFy5.p0z7z9MxKceJnK8qH9/KNPxWmEKc.D.', 1250);
-- insert into user (name, email, password, balance) values ('Roberta', 'roberta@budget.com', '$2a$10$qe6KkNC3sduXvWNywQFy5.p0z7z9MxKceJnK8qH9/KNPxWmEKc.D.', 3000);
-- insert into user (name, email, password, balance) values ('Roberto', 'roberto@budget.com', '$2a$10$qe6KkNC3sduXvWNywQFy5.p0z7z9MxKceJnK8qH9/KNPxWmEKc.D.', 20000);

-- insert into category (description, color, user_id, inactive) values ('Saúde','#06C8F3', 1, false);
-- insert into category (description, color, user_id, inactive) values ('Alimentação','#06F343', 1, false);
-- insert into category (description, color, user_id, inactive) values ('Lazer','#EFF306', 2, false);

-- insert into transaction (code, value, user_id, type, category_id, description, date, creation_date) values ('24faa01b-c97b-47a6-be67-b054bfe78d55', 150, 1, 'EXPENSE', 1, 'Remédios para gripe', curdate(), utc_timestamp);
-- insert into transaction (code, value, user_id, type, category_id, description, date, creation_date) values ('a9f7a3f0-3dc1-46de-b254-7b02ae13926d', 65, 2, 'EXPENSE', 2, 'Noite da pizza', curdate(), utc_timestamp);
-- insert into transaction (code, value, user_id, type, category_id, description, date, creation_date) values ('f0e253fd-434c-40d0-9fff-65314af9c138', 65, 3, 'EXPENSE', 2, 'Noite da pizza', curdate(), utc_timestamp);

-- insert into permission (id, name, description) values (1, 'CHANGE_USERS_PROFILES_PERMISSIONS', 'Allows creation, changing and deleting of users');
-- insert into permission (id, name, description) values (2, 'CHANGE_CATEGORIES', 'Allows creation, changing and deleting of categories');
-- insert into permission (id, name, description) values (3, 'CHANGE_TRANSACTIONS', 'Allows creation, changing and deleting of transactions');
-- insert into permission (id, name, description) values (4, 'CONSULT_USERS_PROFILES_PERMISSIONS', 'Allows to consult users');
-- insert into permission (id, name, description) values (5, 'CONSULT_TRANSACTIONS', 'Allows to consult transactions');
-- insert into permission (id, name, description) values (6, 'CONSULT_CATEGORIES', 'Allows to consult categories');
-- insert into permission (id, name, description) values (7, 'CONSULT_REPORTS', 'Allows to consult reports');

-- insert into profile (id, name) values (1, 'INTERNAL'), (2, 'ADMIN'), (3, 'SUPERVISOR'), (4, 'USER');

-- insert into profile_permission (profile_id, permission_id) select 1, id from permission;
-- insert into profile_permission (profile_id, permission_id) select 2, id from permission where name like 'CONSULT_%';
-- insert into profile_permission (profile_id, permission_id) select 3, id from permission where name like 'CONSULT_%';

-- insert into user_profile (user_id, profile_id) values (1, 1), (2, 3);


-- INSERT INTO oauth2_registered_client
-- (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
-- VALUES('1', 'budget-backend', '2022-08-16 19:04:12', '$2a$12$3VM3Sb3T5PhaODIzGovv2uiV6ieewwZGO/pikiVJdzIJ0RSNwi5SK', NULL, '1', 'client_secret_basic', 'client_credentials', '', 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

-- INSERT INTO oauth2_registered_client
-- (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
-- VALUES('2', 'budget-web', '2022-08-16 19:04:12', '$2a$12$3VM3Sb3T5PhaODIzGovv2uiV6ieewwZGO/pikiVJdzIJ0RSNwi5SK', NULL, '2', 'client_secret_basic,client_secret_post', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000]}');

-- INSERT INTO oauth2_registered_client
-- (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
-- VALUES('3', 'foodanalytics', '2022-08-16 19:04:12', '$2a$12$3VM3Sb3T5PhaODIzGovv2uiV6ieewwZGO/pikiVJdzIJ0RSNwi5SK', NULL, '3', 'client_secret_basic', 'authorization_code', 'http://www.foodanalytics.local:8082', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');