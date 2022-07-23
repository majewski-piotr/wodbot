resource "aws_lambda_function" "wodbot_lambda" {
  depends_on                     = [aws_security_group.wodbot_lambda_security_group]
  runtime                        = "java11"
  filename                       = "../../build/distributions/cloud.zip"
  source_code_hash               = filebase64sha256("../../build/distributions/cloud.zip")
  function_name                  = "${var.name}"
  handler                        = "com.piotrm.wodbot.cloud.Handler"
  timeout                        = 4
  memory_size                    = 2048
  role                           = aws_iam_role.wodbot_lambda_iam_role.arn

  environment {
    variables = {
      #THIS COULD BE A SECRET IT WOULD SLOW DOWN COLD STARTS SIGNIFICANTLY
      PUBLIC_KEY        = var.public_key
      AWS_CSM_ENABLED   = "false"
      JAVA_TOOL_OPTIONS = "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
    }
  }
}
resource "aws_lambda_function_url" "wodbot_lambda_url" {
  function_name      = aws_lambda_function.wodbot_lambda.function_name
  authorization_type = "NONE"
}

resource "aws_lambda_function_event_invoke_config" "lambda_configuration" {
  function_name                = aws_lambda_function.wodbot_lambda.function_name
  maximum_event_age_in_seconds = 60
  maximum_retry_attempts       = 0
}

resource "aws_cloudwatch_log_group" "wodbot_lambda_log_group" {
  name              = "/aws/lambda/${var.name}"
  retention_in_days = 7
}

