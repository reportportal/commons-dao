INSERT INTO user_creation_bid(uuid, last_modified, email, project_name, role, metadata, inviting_user_id)
VALUES ('0647cf8f-02e3-4acd-ba3e-f74ec9d2c5cb', now(), 'superadminemail@domain.com', 1, 'PROJECT_MANAGER', '{
  "metadata": {
    "type": "internal"
  }
}', 1),

       ('6cb0ce2a-e974-44d8-ae78-d86baa38c356', now() - interval '30 day', 'defaultemail@domain.com', 2, 'PROJECT_MANAGER', '{
         "metadata": {
           "type": "internal"
         }
       }', 1),
       ('04ff29db-88ef-44c9-9273-5701f6969127', now() - interval '1 day', 'defaultemail@domain.com', 1, 'MEMBER', '{
         "metadata": {
           "type": "internal"
         }
       }', 1);