resource "aws_iam_role" "iam_role_for_lambda" {
  name               = "${var.name}_role"
  assume_role_policy = jsonencode(
    {
      Version : "2012-10-17",
      Statement : [
        {
          Action : "sts:AssumeRole",
          Principal : {
            Service : "lambda.amazonaws.com"
          },
          Effect : "Allow",
          Sid : ""
        }
      ]
    })
}

resource "aws_iam_role_policy_attachment" "base_policy" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaVPCAccessExecutionRole"
  role       = aws_iam_role.iam_role_for_lambda.name
}


resource "aws_iam_role_policy" "wodbot_secrets_policy" {
  name = "AllowReadSecretsManager"
  role = aws_iam_role.iam_role_for_lambda.id

  policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Sid      = "VisualEditor0"
        Effect   = "Allow"
        Action = [
          "secretsmanager:GetSecretValue",
          "secretsmanager:DescribeSecret"
        ]
        Resource = [
          aws_secretsmanager_secret.client_id.arn,
          aws_secretsmanager_secret.client_secret.arn,
          aws_secretsmanager_secret.public_key.arn
        ]
      },
    ]
  })
}

resource "aws_iam_role_policy_attachment" "aws_xray_write_only_access" {
  role       = aws_iam_role.iam_role_for_lambda.name
  policy_arn = "arn:aws:iam::aws:policy/AWSXrayWriteOnlyAccess"
}