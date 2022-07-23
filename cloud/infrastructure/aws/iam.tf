resource "aws_iam_role" "wodbot_lambda_iam_role" {
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
  role       = aws_iam_role.wodbot_lambda_iam_role.name
}