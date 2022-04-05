resource "aws_lambda_function" "java_lambda_function" {
  depends_on       = [aws_security_group.main]
  runtime          = "java11"
  filename         = "../../build/libs/cloud.jar"
  source_code_hash = filebase64sha256("../../build/libs/cloud.jar")
  function_name    = "${var.name}-lambda"
  handler          = "com.piotrm.wodbot.cloud.LambdaAPI"
  timeout          = 900
  memory_size      = 256
  role             = aws_iam_role.iam_role_for_lambda.arn

  vpc_config {
    subnet_ids         = [aws_subnet.main-private-1.id]
    security_group_ids = [aws_security_group.main.id]
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
