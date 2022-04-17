resource "aws_apigatewayv2_api" "dummy-api" {
  name          = "${var.name}-main"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_stage" "dummy-api-stage" {
  api_id      = aws_apigatewayv2_api.dummy-api.id
  name        = "${var.name}-stage"
  auto_deploy = true
  access_log_settings {
    destination_arn = aws_cloudwatch_log_group.log_group.arn

    format = <<EOF
    { "requestId" : "$context.requestId", "sourceIp" : "$context.identity.sourceIp", "requestTime" : "$context.requestTime", "protocol" : "$context.protocol",  "httpMethod" : "$context.httpMethod", "resourcePath" : "$context.resourcePath", "routeKey" : "$context.routeKey", "status" : "$context.status", "responseLength" : "$context.responseLength", "integrationErrorMessage" : "$context.integrationErrorMessage" }
EOF
  }
}

resource "aws_apigatewayv2_integration" "hello_world" {
  api_id = aws_apigatewayv2_api.dummy-api.id

  integration_uri  = aws_lambda_function.java_lambda_function.invoke_arn
  integration_type = "AWS_PROXY"
  integration_method = "POST"
}

resource "aws_apigatewayv2_route" "hello_world" {
  api_id = aws_apigatewayv2_api.dummy-api.id

  route_key = "POST /interactions"
  target    = "integrations/${aws_apigatewayv2_integration.hello_world.id}"
}