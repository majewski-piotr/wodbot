resource "aws_lambda_function" "java_lambda_function" {
  depends_on       = [aws_security_group.main]
  runtime          = "java11"
  filename         = "../../build/distributions/cloud.zip"
  source_code_hash = filebase64sha256("../../build/distributions/cloud.zip")
  function_name    = "${var.name}"
  handler          = "com.piotrm.wodbot.cloud.Handler"
  timeout          = 4
  memory_size      = 2048
  role             = aws_iam_role.iam_role_for_lambda.arn

  vpc_config {
    subnet_ids         = [aws_subnet.main-private-1.id]
    security_group_ids = [aws_security_group.main.id]
  }
  environment {
    variables = {

      PUBLIC_KEY        = var.public_key
      AWS_CSM_ENABLED   = "false"
      JAVA_TOOL_OPTIONS = "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
    }
  }
  tracing_config {
    mode = "Active"
  }
}
resource "aws_lambda_function_url" "test_live" {
  function_name      = aws_lambda_function.java_lambda_function.function_name
  authorization_type = "NONE"

  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["POST"]
    max_age           = 86400
  }
}

resource "aws_lambda_function_event_invoke_config" "lambda_configuration" {
  function_name                = aws_lambda_function.java_lambda_function.function_name
  maximum_event_age_in_seconds = 60
  maximum_retry_attempts       = 0
}

resource "aws_cloudwatch_log_group" "log_group" {
  name              = "/aws/lambda/${var.name}"
  retention_in_days = 7
}
#resource "aws_cloudwatch_event_rule" "warmer" {
#  name                = "warmer"
#  description         = "warms up lambda every 5 minutes"
#  schedule_expression = "cron(0/4 * * * ? *)"
#}
#
#resource "aws_cloudwatch_event_target" "check_lambda_everyday" {
#  rule      = "${aws_cloudwatch_event_rule.warmer.name}"
#  target_id = "lambda1"
#  arn       = "${aws_lambda_function.java_lambda_function.arn}"
#}
#
#resource "aws_lambda_permission" "allow_cloudwatch_to_call_excluder_transfer" {
#  statement_id  = "AllowExecutionFromCloudWatch"
#  action        = "lambda:InvokeFunction"
#  function_name = "${aws_lambda_function.java_lambda_function.function_name}"
#  principal     = "events.amazonaws.com"
#  source_arn    = "${aws_cloudwatch_event_rule.warmer.arn}"
#}

