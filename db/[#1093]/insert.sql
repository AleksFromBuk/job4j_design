INSERT INTO roles (role_name) VALUES
                                  ('Administrator'),
                                  ('User'),
                                  ('Manager');

-- ������� ������ � ������� �������������
INSERT INTO users (name, role_id) VALUES
                                      ('Alice', 1),
                                      ('Bob', 2),
                                      ('Charlie', 3);

-- ������� ������ � ������� ������
INSERT INTO rules (rule_name) VALUES
                                  ('View'),
                                  ('Edit'),
                                  ('Delete'),
                                  ('Create');

-- ������� ������ � ������� role_rules
INSERT INTO role_rules (role_id, rule_id) VALUES
                                              (1, 1),
                                              (1, 2),
                                              (1, 3),
                                              (1, 4),
                                              (2, 1),
                                              (2, 4),
                                              (3, 1),
                                              (3, 2);

-- ������� ������ � ������� ���������
INSERT INTO states (name, description, state_enum, actual) VALUES
                                                               ('New', 'Newly created item', 'NEW', true),
                                                               ('In Progress', 'Item is being worked on', 'IN_PROGRESS', true),
                                                               ('Completed', 'Item is completed', 'COMPLETED', true),
                                                               ('Cancelled', 'Item is cancelled', 'CANCELLED', false);

-- ������� ������ � ������� ���������
INSERT INTO categories (name) VALUES
                                  ('Bug'),
                                  ('Feature'),
                                  ('Task');

-- ������� ������ � ������� ������
INSERT INTO items (name, description, user_id, category_id, state_id) VALUES
                                                                          ('Fix login issue', 'Users cannot log in', 1, 1, 1),
                                                                          ('Add new feature', 'Implement new payment gateway', 2, 2, 2),
                                                                          ('Update documentation', 'Revise user manual', 3, 3, 3);

-- ������� ������ � ������� ������������
INSERT INTO comments (comment_text, item_id) VALUES
                                                 ('This is urgent', 1),
                                                 ('Working on it', 2),
                                                 ('Completed the task', 3);

-- ������� ������ � ������� ��������
INSERT INTO attachs (file_path, item_id) VALUES
                                             ('/path/to/fix_login_issue.png', 1),
                                             ('/path/to/add_new_feature_spec.pdf', 2),
                                             ('/path/to/update_documentation.pdf', 3);

