INSERT INTO tb_usuario(id,email,primeiro_nome,senha_encryptada,ultimo_nome,telefone,`role`)VALUES(unhex(replace(uuid(),'-','')),'exemplo@email.com','System','$2a$12$7Z58zQz0nO6mPovGDBlQsOTLECvk3b93sC0IoHvDZc5u18fJ74nNG','Administrator','123456789','ADMIN');