resource "aws_cloudwatch_log_group" "log_group" {
  name              = "/aws/lambda/${var.name}-logs"
  retention_in_days = 30
}
