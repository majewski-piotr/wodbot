resource "aws_lambda_function" "java_lambda_function" {
  depends_on       = [aws_security_group.main]
  runtime          = "java11"
  filename         = "../../build/distributions/cloud.zip"
  source_code_hash = filebase64sha256("../../build/distributions/cloud.zip")
  function_name    = "${var.name}"
  handler          = "com.piotrm.wodbot.cloud.LambdaAPI"
  timeout          = 900
  memory_size      = 256
  role             = aws_iam_role.iam_role_for_lambda.arn

  vpc_config {
    subnet_ids         = [aws_subnet.main-private-1.id]
    security_group_ids = [aws_security_group.main.id]
  }
  environment {
    variables = {
      SECRET_NAME_CLIENT_ID     = aws_secretsmanager_secret.client_id.name
      SECRET_NAME_CLIENT_SECRET = aws_secretsmanager_secret.client_secret.name
      SECRET_NAME_PUBLIC_KEY    = aws_secretsmanager_secret.public_key.name
      AWS_CSM_ENABLED           = "false"
      JAVA_TOOL_OPTIONS         = "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
    }
  }
  tracing_config {
    mode = "Active"
  }
}

resource "aws_lambda_function_event_invoke_config" "lambda_configuration" {
  function_name                = aws_lambda_function.java_lambda_function.function_name
  maximum_event_age_in_seconds = 900
  maximum_retry_attempts       = 0
}

resource "aws_lambda_permission" "api_invoke" {
  function_name = aws_lambda_function.java_lambda_function.function_name
  statement_id  = "allow_wodbot_api_gateway_to_invoke_lambda"
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.dummy-api.execution_arn}/*/*"
  action        = "lambda:InvokeFunction"
}
resource "aws_cloudwatch_log_group" "log_group" {
  name              = "/aws/lambda/${var.name}"
  retention_in_days = 7
}
