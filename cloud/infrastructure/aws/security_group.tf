resource "aws_security_group" "wodbot_lambda_security_group" {
  name        = "${var.name} security_group"
  description = "Security group encompassing all ${var.name} components"
  vpc_id      = aws_vpc.wodbot_vpc.id

  depends_on = [aws_iam_role.wodbot_lambda_iam_role]
}

resource "aws_security_group_rule" "egress-all-manage" {
  security_group_id = aws_security_group.wodbot_lambda_security_group.id
  type              = "egress"
  from_port         = 1
  to_port           = 65535
  protocol          = -1
  cidr_blocks       = ["0.0.0.0/0"]
}