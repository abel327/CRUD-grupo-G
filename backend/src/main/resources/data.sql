-- Limpar dados se necessário
DELETE FROM item_pedido;
DELETE FROM pedido;
DELETE FROM produto;
DELETE FROM barraca;
DELETE FROM categoria;
DELETE FROM status_pedido;

-- Status Pedido
INSERT INTO status_pedido (id, nome) VALUES (1, 'Recebido');
INSERT INTO status_pedido (id, nome) VALUES (2, 'Preparando');
INSERT INTO status_pedido (id, nome) VALUES (3, 'Pronto para Retirada');
INSERT INTO status_pedido (id, nome) VALUES (4, 'A Caminho');
INSERT INTO status_pedido (id, nome) VALUES (5, 'Entregue');

-- Categorias
INSERT INTO categoria (id, nome, image_url) VALUES (1, 'Frutas', 'https://images.unsplash.com/photo-1619566636858-adf3ef46400b?auto=format&fit=crop&q=80&w=300');
INSERT INTO categoria (id, nome, image_url) VALUES (2, 'Legumes', 'https://images.unsplash.com/photo-1518843875459-f738682238a6?auto=format&fit=crop&q=80&w=300');
INSERT INTO categoria (id, nome, image_url) VALUES (3, 'Verduras', 'https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&q=80&w=300');
INSERT INTO categoria (id, nome, image_url) VALUES (4, 'Temperos', 'https://images.unsplash.com/photo-1596040033229-a9821ebd058d?auto=format&fit=crop&q=80&w=300');

-- Barracas
INSERT INTO barraca (id, nome, descricao, avaliacao, image_url) VALUES (1, 'Hortifruti do João', 'Frutas e verduras fresquinhas todos os dias. Orgânicos selecionados.', 4.8, 'https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&q=80&w=1200');
INSERT INTO barraca (id, nome, descricao, avaliacao, image_url) VALUES (2, 'Temperos da Maria', 'Os melhores temperos para sua comida, especiarias raras e ervas frescas.', 4.9, 'https://images.unsplash.com/photo-1596040033229-a9821ebd058d?auto=format&fit=crop&q=80&w=300');

-- Produtos Barraca 1 (João)
INSERT INTO produto (id, nome, descricao, preco, unidade, em_oferta, image_url, barraca_id, categoria_id) VALUES (1, 'Banana Prata', 'Banana prata doce e fresca', 6.50, 'dúzia', true, 'https://images.unsplash.com/photo-1571501679680-de32f1e7aad4?auto=format&fit=crop&q=80&w=300', 1, 1);
INSERT INTO produto (id, nome, descricao, preco, unidade, em_oferta, image_url, barraca_id, categoria_id) VALUES (2, 'Maçã Fuji', 'Maçã fuji crocante e suculenta', 8.90, 'kg', false, 'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?auto=format&fit=crop&q=80&w=300', 1, 1);
INSERT INTO produto (id, nome, descricao, preco, unidade, em_oferta, image_url, barraca_id, categoria_id) VALUES (3, 'Tomate Carmem', 'Tomates maduros para salada', 5.90, 'kg', true, 'https://images.unsplash.com/photo-1592924357228-91a4daadcfea?auto=format&fit=crop&q=80&w=300', 1, 2);
INSERT INTO produto (id, nome, descricao, preco, unidade, em_oferta, image_url, barraca_id, categoria_id) VALUES (4, 'Alface Crespa', 'Maço de alface crespa', 2.50, 'maço', false, 'https://images.unsplash.com/photo-1622206151226-18ca2c9ab4a1?auto=format&fit=crop&q=80&w=300', 1, 3);

-- Produtos Barraca 2 (Maria)
INSERT INTO produto (id, nome, descricao, preco, unidade, em_oferta, image_url, barraca_id, categoria_id) VALUES (5, 'Pimenta do Reino', 'Pimenta do reino em grãos', 12.00, '100g', true, 'https://images.unsplash.com/photo-1596040033229-a9821ebd058d?auto=format&fit=crop&q=80&w=300', 2, 4);
INSERT INTO produto (id, nome, descricao, preco, unidade, em_oferta, image_url, barraca_id, categoria_id) VALUES (6, 'Orégano Seco', 'Orégano selecionado', 4.50, '50g', false, 'https://images.unsplash.com/photo-1596040033229-a9821ebd058d?auto=format&fit=crop&q=80&w=300', 2, 4);
