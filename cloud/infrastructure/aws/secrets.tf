resource "aws_secretsmanager_secret" "public_key" {
  name                    = "public_key"
  recovery_window_in_days = 0
}

resource "aws_secretsmanager_secret_version" "public_key_version" {
  secret_id     = aws_secretsmanager_secret.public_key.id
  secret_string = var.public_key
}

resource "aws_secretsmanager_secret" "client_id" {
  name                    = "client_id"
  recovery_window_in_days = 0
}

resource "aws_secretsmanager_secret_version" "client_id_version" {
  secret_id     = aws_secretsmanager_secret.client_id.id
  secret_string = var.client_id
}

resource "aws_secretsmanager_secret" "client_secret" {
  name                    = "client_secret"
  recovery_window_in_days = 0
}

resource "aws_secretsmanager_secret_version" "client_secret_version" {
  secret_id     = aws_secretsmanager_secret.client_secret.id
  secret_string = var.client_secret
}
